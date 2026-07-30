package com.ouremr.product.appointments;

import com.ouremr.product.dto.*;
import com.ouremr.product.repositories.*;
import com.ouremr.product.tables.*;
import com.ouremr.product.util.HUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentsServiceImpl implements AppointmentsService {

    private static final Logger log = LoggerFactory.getLogger(AppointmentsServiceImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private PatientRegistrationRepository patientRegistrationRepository;

    @Autowired
    private SchedulerAppointmentRepository schedulerAppointmentRepository;

    @Autowired
    private EmployeeProfileRepository employeeProfileRepository;

    @Autowired
    private SchedulerAppointmentStatusRepository schedulerAppointmentStatusRepository;

    @Autowired
    private SchedulerAppointmentTypesRepository schedulerAppointmentTypesRepository;

    // ─────────────────────────────────────────────────────────────────────────
    // getAppointmentStatusCounts
    // ─────────────────────────────────────────────────────────────────────────

    @Override
    public AppointmentStatusCountsDTO getAppointmentStatusCounts(Long userId, String apptDate) {

        AppointmentStatusCountsDTO dto = new AppointmentStatusCountsDTO();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<EmployeeProfile> empQuery = cb.createQuery(EmployeeProfile.class);
            Root<EmployeeProfile> emp = empQuery.from(EmployeeProfile.class);
            empQuery.select(emp).where(cb.equal(emp.get("employeeProfileId"), userId));

            List<EmployeeProfile> empList = em.createQuery(empQuery).getResultList();
            if (!empList.isEmpty()) {
                EmployeeProfile employee = empList.get(0);
                dto.setEmployeeId(employee.getEmployeeProfileId());
                dto.setEmployeeName(employee.getEmployeeProfileFirstName() + " "
                        + HUtil.getOrDefault(employee.getEmployeeProfileLastName(), ""));
                dto.setRole(employee.getEmployeeProfileRoleName());
            }

            CriteriaQuery<Object[]> countQuery = cb.createQuery(Object[].class);
            Root<SchedulerAppointment> appointment = countQuery.from(SchedulerAppointment.class);
            Join<SchedulerAppointment, SchedulerAppointmentStatus> status =
                    appointment.join("schedulerAppointmentStatus", JoinType.LEFT);

            Expression<Long> total     = cb.count(appointment);
            Expression<Long> cancelled = cb.sum(cb.<Long>selectCase().when(cb.equal(status.get("schedulerAppointmentName"), "Cancelled"),        1L).otherwise(0L));
            Expression<Long> waiting   = cb.sum(cb.<Long>selectCase().when(cb.equal(status.get("schedulerAppointmentName"), "Waiting"),          1L).otherwise(0L));
            Expression<Long> completed = cb.sum(cb.<Long>selectCase().when(cb.equal(status.get("schedulerAppointmentName"), "Completed"),        1L).otherwise(0L));
            Expression<Long> pending   = cb.sum(cb.<Long>selectCase().when(cb.equal(status.get("schedulerAppointmentName"), "Pending"),          1L).otherwise(0L));
            Expression<Long> noShow    = cb.sum(cb.<Long>selectCase().when(cb.equal(status.get("schedulerAppointmentName"), "No Show"),          1L).otherwise(0L));

            List<Predicate> predicates = new ArrayList<>();
            if (apptDate != null && !apptDate.trim().isEmpty()) {
                LocalDate localDate = LocalDate.parse(apptDate);
                predicates.add(cb.equal(appointment.get("schedulerAppointmentAppointmentDate"), localDate));
            }

            if (!predicates.isEmpty()) {
                countQuery.where(predicates.toArray(new Predicate[0]));
            }

            countQuery.multiselect(total, cancelled, waiting, completed, pending, noShow);

            Object[] countResult = em.createQuery(countQuery).getSingleResult();

            if (!HUtil.isNullOrEmpty(countResult)) {
                dto.setTotalAppointments(countResult[0] != null ? (Long) countResult[0] : 0L);
                dto.setCancelledCount(countResult[1]  != null ? (Long) countResult[1]  : 0L);
                dto.setWaitingCount(countResult[2]    != null ? (Long) countResult[2]  : 0L);
                dto.setCompletedCount(countResult[3]  != null ? (Long) countResult[3]  : 0L);
                dto.setPendingCount(countResult[4]    != null ? (Long) countResult[4]  : 0L);
                dto.setNoShowCount(countResult[5]     != null ? (Long) countResult[5]  : 0L);
            }

        } catch (Exception e) {
            log.error("Failed to fetch appointment status counts", e);
        }
        return dto;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // getAppointments
    // ─────────────────────────────────────────────────────────────────────────

    @Override
    public List<AppointmentDTO> getAppointments(Integer userId, String apptDate, Integer pageNo) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<SchedulerAppointment> appointment = query.from(SchedulerAppointment.class);

        Join<SchedulerAppointment, SchedulerAppointmentStatus> status  = appointment.join("schedulerAppointmentStatus",  JoinType.LEFT);
        Join<SchedulerAppointment, SchedulerAppointmentType>   type    = appointment.join("schedulerAppointmentType",    JoinType.LEFT);
        Join<SchedulerAppointment, EmployeeProfile>            provider = appointment.join("schedulerAppointmentProvider", JoinType.LEFT);

        // Patient via LEFT JOIN using a subquery-style approach —
        // we use a correlated join so appointments without a matching patient still appear.
        Root<PatientRegistration> patient = query.from(PatientRegistration.class);
        Predicate patientJoin = cb.equal(
                appointment.get("schedulerAppointmentPatientId").as(Long.class),
                patient.get("patientRegistrationId")
        );

        Expression<String> patientName = cb.concat(
                cb.coalesce(patient.get("patientRegistrationFirstName"), ""),
                cb.concat(" ", cb.coalesce(patient.get("patientRegistrationLastName"), ""))
        );

        Expression<String> providerName = cb.concat(
                cb.coalesce(provider.get("employeeProfileFirstName"), ""),
                cb.concat(" ", cb.coalesce(provider.get("employeeProfileLastName"), ""))
        );

        // Sub-query: count of appointments for same patient (to determine New vs Reg)
        Subquery<Long> sub = query.subquery(Long.class);
        Root<SchedulerAppointment> subRoot = sub.from(SchedulerAppointment.class);
        sub.select(cb.count(subRoot))
                .where(cb.equal(subRoot.get("schedulerAppointmentPatientId"),
                        appointment.get("schedulerAppointmentPatientId")));

        Expression<String> patientType = cb.<String>selectCase()
                .when(cb.greaterThan(sub, 1L), "Reg")
                .otherwise("New");

        query.multiselect(
                patient.get("patientRegistrationId"),           // 0  patientId
                patientName,                                    // 1  patientName
                providerName,                                   // 2  providerName
                status.get("schedulerAppointmentName"),         // 3  status
                type.get("schedulerAppointmentTypeName"),       // 4  type
                appointment.get("schedulerAppointmentAppointmentDate"),  // 5  date
                appointment.get("schedulerAppointmentReason"),  // 6  reason
                patientType,                                    // 7  patientType
                appointment.get("schedulerAppointmentStartTime"),        // 8  startTime
                appointment.get("schedulerAppointmentEndTime"),          // 9  endTime
                appointment.get("schedulerAppointmentId")       // 10 apptId
        );

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(patientJoin);

        if (apptDate != null && !apptDate.trim().isEmpty()) {
            predicates.add(cb.equal(
                    cb.function("DATE", java.sql.Date.class,
                            appointment.get("schedulerAppointmentAppointmentDate")),
                    java.sql.Date.valueOf(apptDate)
            ));
        }

        query.where(predicates.toArray(new Predicate[0]));
        query.orderBy(cb.asc(appointment.get("schedulerAppointmentStartTime")));

        int pageSize = 10;
        int page = (pageNo == null || pageNo < 1) ? 1 : pageNo;

        TypedQuery<Object[]> typedQuery = em.createQuery(query);
        typedQuery.setFirstResult((page - 1) * pageSize);
        typedQuery.setMaxResults(pageSize);

        List<Object[]> results = typedQuery.getResultList();
        List<AppointmentDTO> list = new ArrayList<>();

        for (Object[] row : results) {
            AppointmentDTO dto = new AppointmentDTO();
            dto.setPatientId(row[0] != null ? ((Number) row[0]).longValue() : null);
            dto.setPatientName(row[1] != null ? row[1].toString().trim() : "");
            dto.setProviderName(row[2] != null ? row[2].toString().trim().replaceAll("\\s+", " ") : "");
            dto.setStatus(row[3] != null ? row[3].toString() : "");
            dto.setType(row[4] != null ? row[4].toString() : "");
            dto.setAppointmentDate(row[5] != null ? (LocalDate) row[5] : null);
            dto.setReason(row[6] != null ? row[6].toString() : "");
            dto.setPatientType(row[7] != null ? row[7].toString() : "New");
            dto.setStartTime(row[8] != null ? row[8].toString() : "");
            dto.setApptId(row[10] != null ? ((Number) row[10]).longValue() : null);
            list.add(dto);
        }

        return list;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // getAppointmentPatientDetail
    // ─────────────────────────────────────────────────────────────────────────

    @Override
    public AppointmentPatientDetailDTO getAppointmentPatientDetail(Long appointmentId) {

        AppointmentPatientDetailDTO dto = null;
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
            Root<SchedulerAppointment> appointment = cq.from(SchedulerAppointment.class);
            Root<PatientRegistration>  patient     = cq.from(PatientRegistration.class);

            Join<SchedulerAppointment, SchedulerAppointmentStatus> status = appointment.join("schedulerAppointmentStatus", JoinType.LEFT);
            Join<SchedulerAppointment, SchedulerAppointmentType>   type   = appointment.join("schedulerAppointmentType",   JoinType.LEFT);

            Expression<String> patientName = cb.concat(
                    cb.concat(cb.coalesce(patient.get("patientRegistrationFirstName"), ""), " "),
                    cb.concat(cb.coalesce(patient.get("patientRegistrationMiddleName"), ""),
                            cb.concat(" ", cb.coalesce(patient.get("patientRegistrationLastName"), "")))
            );

            Expression<String> stateAndCity = cb.concat(
                    cb.coalesce(patient.get("patientRegistrationCity"),  ""),
                    cb.concat(", ", cb.coalesce(patient.get("patientRegistrationState"), ""))
            );

            cq.multiselect(
                    patientName,                                                    // 0
                    patient.get("patientRegistrationSex"),                          // 1
                    patient.get("patientRegistrationId"),                           // 2
                    patient.get("patientRegistrationEmailId"),                      // 3
                    patient.get("patientRegistrationMobileNo"),                     // 4
                    patient.get("patientRegistrationDob"),                          // 5
                    stateAndCity,                                                   // 6
                    appointment.get("schedulerAppointmentAppointmentDate"),         // 7
                    appointment.get("schedulerAppointmentStartTime"),               // 8
                    appointment.get("schedulerAppointmentEndTime"),                 // 9
                    status.get("schedulerAppointmentName"),                         // 10
                    type.get("schedulerAppointmentTypeName"),                       // 11
                    appointment.get("schedulerAppointmentReason")                   // 12
            );

            cq.where(cb.and(
                    cb.equal(cb.toInteger(appointment.get("schedulerAppointmentPatientId")),
                            patient.get("patientRegistrationId")),
                    cb.equal(appointment.get("schedulerAppointmentId"), appointmentId)
            ));

            List<Object[]> rows = em.createQuery(cq).getResultList();
            if (rows.isEmpty()) return null;

            Object[] obj = rows.get(0);
            dto = new AppointmentPatientDetailDTO();
            dto.setPatientName(obj[0]  != null ? obj[0].toString().trim().replaceAll("\\s+", " ") : "");
            dto.setGender(obj[1]       != null ? obj[1].toString() : "");
            dto.setPatientId(obj[2]    != null ? ((Number) obj[2]).intValue() : null);
            dto.setEmailId(obj[3]      != null ? obj[3].toString() : "");
            dto.setMobileNo(obj[4]     != null ? obj[4].toString() : "");
            dto.setDob(obj[5]          != null ? obj[5].toString() : "");
            dto.setStateAndCity(obj[6] != null ? obj[6].toString() : "");
            dto.setApptDate(obj[7]     != null ? obj[7].toString() : "");
            dto.setApptStartTime(obj[8]  != null ? obj[8].toString() : "");
            dto.setApptEndTime(obj[9]    != null ? obj[9].toString() : "");
            dto.setAppointmentStatus(obj[10] != null ? obj[10].toString() : "");
            dto.setApptSessionType(obj[11]   != null ? obj[11].toString() : "");
            dto.setReason(obj[12]            != null ? obj[12].toString() : "");

        } catch (Exception e) {
            log.error("Failed to fetch appointment patient details for id={}", appointmentId, e);
        }
        return dto;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // createAppointments
    // ─────────────────────────────────────────────────────────────────────────

    @Override
    @Transactional
    public void createAppointments(CreateAppointmentDTO bean) {

        // ── Input validation ──────────────────────────────────────────────────
        if (bean == null) {
            throw new IllegalArgumentException("Request body is required.");
        }
        if (bean.getProviderId() == null) {
            throw new IllegalArgumentException("Doctor selection is required.");
        }
        if (!HUtil.isValidString(bean.getAppointmentDate())) {
            throw new IllegalArgumentException("Appointment date is required.");
        }
        if (!HUtil.isValidString(bean.getAppointmentTime())) {
            throw new IllegalArgumentException("Appointment time (slot) is required.");
        }
        if (!HUtil.isValidString(bean.getAppointmentTypeId())) {
            throw new IllegalArgumentException("Appointment type is required.");
        }

        // Validate date format
        LocalDate appointmentDate;
        try {
            appointmentDate = LocalDate.parse(bean.getAppointmentDate());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid appointment date format. Expected YYYY-MM-DD.");
        }

        // ── Resolve foreign keys ──────────────────────────────────────────────
        EmployeeProfile provider = employeeProfileRepository
                .findById(bean.getProviderId())
                .orElseThrow(() -> new IllegalArgumentException("Selected doctor was not found."));

        long typeId;
        try {
            typeId = Long.parseLong(bean.getAppointmentTypeId());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid appointment type.");
        }

        SchedulerAppointmentType appointmentType = schedulerAppointmentTypesRepository
                .findById(typeId)
                .orElseThrow(() -> new IllegalArgumentException("Selected appointment type was not found."));

        // Default status = "Scheduled" (ID=1 as per seed data)
        SchedulerAppointmentStatus appointmentStatus = schedulerAppointmentStatusRepository
                .findById(1L)
                .orElseThrow(() -> new RuntimeException("Appointment status configuration is missing. Contact support."));

        // ── Update existing appointment ───────────────────────────────────────
        if (HUtil.isValidString(bean.getAppointmentId())) {
            long apptId;
            try {
                apptId = Long.parseLong(bean.getAppointmentId());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid appointment ID.");
            }

            SchedulerAppointment appointment = schedulerAppointmentRepository
                    .findById(apptId)
                    .orElseThrow(() -> new IllegalArgumentException("Appointment not found."));

            appointment.setSchedulerAppointmentProviderId(bean.getProviderId());
            appointment.setSchedulerAppointmentAppointmentDate(appointmentDate);
            appointment.setSchedulerAppointmentStartTime(bean.getAppointmentTime());
            appointment.setSchedulerAppointmentReason(HUtil.getOrDefault(bean.getChiefComplaint(), ""));
            appointment.setSchedulerAppointmentType(appointmentType);

            schedulerAppointmentRepository.save(appointment);
            return;
        }

        // ── Resolve or create patient ─────────────────────────────────────────
        PatientRegistration patient;

        if (HUtil.isValidString(bean.getPatientId())) {
            // Existing patient — look up by ID
            long patientId;
            try {
                patientId = Long.parseLong(bean.getPatientId());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid patient ID.");
            }
            patient = patientRegistrationRepository
                    .findById(patientId)
                    .orElseThrow(() -> new IllegalArgumentException("Selected patient was not found."));
        } else {
            // New patient — validate required fields
            if (!HUtil.isValidString(bean.getFirstName())) {
                throw new IllegalArgumentException("First name is required for new patients.");
            }
            if (!HUtil.isValidString(bean.getMobileNumber())) {
                throw new IllegalArgumentException("Phone number is required for new patients.");
            }

            patient = new PatientRegistration();
            patient.setPatientRegistrationFirstName(bean.getFirstName().trim());
            patient.setPatientRegistrationLastName(
                    HUtil.isValidString(bean.getLastName()) ? bean.getLastName().trim() : null);
            patient.setPatientRegistrationMiddleName(
                    HUtil.isValidString(bean.getMiddleName()) ? bean.getMiddleName().trim() : null);
            patient.setPatientRegistrationMobileNo(bean.getMobileNumber().trim());
            patient.setPatientRegistrationEmailId(
                    HUtil.isValidString(bean.getEmail()) ? bean.getEmail().trim() : null);
            patient.setPatientRegistrationSex(
                    HUtil.isValidString(bean.getGender()) ? bean.getGender() : null);
            patient.setPatientRegistrationAddress(
                    HUtil.isValidString(bean.getLocation()) ? bean.getLocation().trim() : null);
            patient.setPatientRegistrationChronic(
                    HUtil.isValidString(bean.getChronicDisease()) ? bean.getChronicDisease().trim() : null);
            patient.setPatientRegistrationActive(true);

            if (HUtil.isValidString(bean.getDateOfBirth())) {
                try {
                    patient.setPatientRegistrationDob(LocalDate.parse(bean.getDateOfBirth()));
                } catch (Exception e) {
                    throw new IllegalArgumentException("Invalid date of birth format. Expected YYYY-MM-DD.");
                }
            }

            patient = patientRegistrationRepository.save(patient);
        }

        // ── Build patient full name ───────────────────────────────────────────
        String patientFullName = HUtil.getOrDefault(patient.getPatientRegistrationFirstName(), "")
                + (HUtil.isValidString(patient.getPatientRegistrationMiddleName())
                    ? " " + patient.getPatientRegistrationMiddleName() : "")
                + (HUtil.isValidString(patient.getPatientRegistrationLastName())
                    ? " " + patient.getPatientRegistrationLastName() : "");

        // ── Create appointment record ─────────────────────────────────────────
        SchedulerAppointment appointment = new SchedulerAppointment();
        appointment.setSchedulerAppointmentPatientId(patient.getPatientRegistrationId());
        appointment.setSchedulerAppointmentPatientName(patientFullName.trim());
        appointment.setSchedulerAppointmentProviderId(provider.getEmployeeProfileId());
        appointment.setSchedulerAppointmentAppointmentDate(appointmentDate);
        appointment.setSchedulerAppointmentStartTime(bean.getAppointmentTime());
        appointment.setSchedulerAppointmentReason(HUtil.getOrDefault(bean.getChiefComplaint(), ""));
        appointment.setSchedulerAppointmentType(appointmentType);
        appointment.setSchedulerAppointmentStatus(appointmentStatus);

        schedulerAppointmentRepository.save(appointment);
        log.info("Created appointment for patient={} provider={} date={} time={}",
                patient.getPatientRegistrationId(), provider.getEmployeeProfileId(),
                appointmentDate, bean.getAppointmentTime());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // getCreateApptDetails
    // ─────────────────────────────────────────────────────────────────────────

    @Override
    public CreateAppointmentBasicInfo getCreateApptDetails() {

        List<EmployeeProfile>          employeeProfiles         = employeeProfileRepository.findAll();
        List<SchedulerAppointmentType> schedulerAppointmentTypes = schedulerAppointmentTypesRepository.findAll();

        CreateAppointmentBasicInfo dto = new CreateAppointmentBasicInfo();

        List<DropdownDTO> providers = employeeProfiles.stream().map(emp -> {
            String fullName = (HUtil.getOrDefault(emp.getEmployeeProfileFirstName(), "") + " "
                    + HUtil.getOrDefault(emp.getEmployeeProfileMiddleName() != null ? emp.getEmployeeProfileMiddleName() : "", "") + " "
                    + HUtil.getOrDefault(emp.getEmployeeProfileLastName(), ""))
                    .trim().replaceAll("\\s+", " ");
            return new DropdownDTO(emp.getEmployeeProfileId(), fullName);
        }).toList();

        List<DropdownDTO> appointmentTypes = schedulerAppointmentTypes.stream()
                .map(t -> new DropdownDTO(t.getSchedulerAppointmentTypeId(), t.getSchedulerAppointmentTypeName()))
                .toList();

        dto.setProviders(providers);
        dto.setAppointmentTypes(appointmentTypes);
        return dto;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // searchPatients
    // ─────────────────────────────────────────────────────────────────────────

    @Override
    public List<ExistingPatientsDTO> searchPatients(String patientName) {

        if (patientName == null || patientName.trim().length() < 2) {
            return new ArrayList<>();
        }

        String searchTerm = "%" + patientName.trim().toLowerCase() + "%";

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<PatientRegistration> patient = cq.from(PatientRegistration.class);

        // Full name expression for search
        Expression<String> fullName = cb.lower(
                cb.concat(
                        cb.coalesce(patient.get("patientRegistrationFirstName"), ""),
                        cb.concat(" ", cb.coalesce(patient.get("patientRegistrationLastName"), ""))
                )
        );

        Expression<String> patientNameExpr = cb.concat(
                cb.coalesce(patient.get("patientRegistrationFirstName"), ""),
                cb.concat(" ", cb.coalesce(patient.get("patientRegistrationLastName"), ""))
        );

        cq.multiselect(
                patient.get("patientRegistrationId"),       // 0
                patientNameExpr,                            // 1
                patient.get("patientRegistrationDob"),      // 2
                patient.get("patientRegistrationMobileNo"), // 3
                patient.get("patientRegistrationSex")       // 4
        );

        cq.where(cb.like(fullName, searchTerm));
        cq.orderBy(cb.asc(patient.get("patientRegistrationFirstName")));

        List<Object[]> results = em.createQuery(cq)
                .setMaxResults(10)
                .getResultList();

        List<ExistingPatientsDTO> list = new ArrayList<>();
        for (Object[] row : results) {
            ExistingPatientsDTO dto = new ExistingPatientsDTO();
            dto.setPatientId(row[0] != null ? ((Number) row[0]).intValue() : null);
            dto.setPatientName(row[1] != null ? row[1].toString().trim() : "");
            dto.setPatientDob(row[2] != null ? row[2].toString() : "");
            dto.setPatientMobileNo(row[3] != null ? row[3].toString() : "");
            dto.setPatientGender(row[4] != null ? row[4].toString() : "");
            list.add(dto);
        }
        return list;
    }
}
