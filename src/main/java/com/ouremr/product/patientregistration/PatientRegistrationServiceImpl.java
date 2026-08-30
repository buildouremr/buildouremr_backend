package com.ouremr.product.patientregistration;

import com.ouremr.product.dto.CreatePatientDTO;
import com.ouremr.product.dto.ExistingPatientsDTO;
import com.ouremr.product.dto.PatientDetailsDTO;
import com.ouremr.product.repositories.ChronicDiseaseRepository;
import com.ouremr.product.repositories.PatientRegistrationRepository;
import com.ouremr.product.tables.ChronicDisease;
import com.ouremr.product.tables.EmployeeProfile;
import com.ouremr.product.tables.PatientRegistration;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.ouremr.product.tables.SchedulerAppointment;
import com.ouremr.product.tables.SchedulerAppointmentStatus;
import com.ouremr.product.repositories.SchedulerAppointmentRepository;
import com.ouremr.product.repositories.SchedulerAppointmentStatusRepository;

import java.util.*;
import java.util.stream.Collectors;
import com.ouremr.product.repositories.PatientVitalsRepository;
import com.ouremr.product.tables.PatientVitals;
import java.time.LocalDateTime;

@Service
public class PatientRegistrationServiceImpl implements PatientRegistrationService{

    @Autowired
    PatientRegistrationRepository patientRegistrationRepository;

    @Autowired
    private ChronicDiseaseRepository chronicDiseaseRepository;

    @Autowired
    private SchedulerAppointmentRepository appointmentRepository;

    @Autowired
    private SchedulerAppointmentStatusRepository statusRepository;
    
    @Autowired
    private PatientVitalsRepository vitalsRepository;

    @Autowired
    EntityManager em;

    @Override
    public List<ExistingPatientsDTO> getPatients(String patientName) {

        List<ExistingPatientsDTO> response = new ArrayList<>();

        try {

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

            Root<PatientRegistration> patient =
                    cq.from(PatientRegistration.class);

            Expression<String> fullName =
                    cb.concat(
                            cb.concat(
                                    cb.coalesce(patient.get("patientRegistrationFirstName"), ""),
                                    " "
                            ),
                            cb.concat(
                                    cb.coalesce(patient.get("patientRegistrationMiddleName"), ""),
                                    cb.concat(
                                            " ",
                                            cb.coalesce(patient.get("patientRegistrationLastName"), "")
                                    )
                            )
                    );

            cq.multiselect(
                    patient.get("patientRegistrationId"),      // 0
                    fullName,                                 // 1
                    patient.get("patientRegistrationDob"),    // 2
                    patient.get("patientRegistrationMobileNo"), // 3
                    patient.get("patientRegistrationSex")     // 4
            );

            List<Predicate> predicates = new ArrayList<>();

            if (patientName != null && !patientName.trim().isEmpty()) {

                String normalized =
                        patientName.trim().replace(",", " ");

                String[] tokens = normalized.split("\\s+");

                for (String token : tokens) {

                    String search = "%" + token.toLowerCase() + "%";

                    predicates.add(
                            cb.or(
                                    cb.like(
                                            cb.lower(
                                                    cb.coalesce(
                                                            patient.get("patientRegistrationFirstName"),
                                                            ""
                                                    )
                                            ),
                                            search
                                    ),
                                    cb.like(
                                            cb.lower(
                                                    cb.coalesce(
                                                            patient.get("patientRegistrationMiddleName"),
                                                            ""
                                                    )
                                            ),
                                            search
                                    ),
                                    cb.like(
                                            cb.lower(
                                                    cb.coalesce(
                                                            patient.get("patientRegistrationLastName"),
                                                            ""
                                                    )
                                            ),
                                            search
                                    )
                            )
                    );
                }

                cq.where(cb.and(predicates.toArray(new Predicate[0])));
            }

            cq.orderBy(
                    cb.asc(patient.get("patientRegistrationFirstName"))
            );

            List<Object[]> results =
                    em.createQuery(cq)
                            .setMaxResults(20)
                            .getResultList();

            for (Object[] obj : results) {

                ExistingPatientsDTO dto =
                        new ExistingPatientsDTO();

                dto.setPatientId(
                        obj[0] != null
                                ? ((Number) obj[0]).intValue()
                                : null
                );

                dto.setPatientName(
                        obj[1] != null
                                ? obj[1].toString().trim().replaceAll("\\s+", " ")
                                : ""
                );

                dto.setPatientDob(
                        obj[2] != null
                                ? obj[2].toString()
                                : ""
                );

                dto.setPatientMobileNo(
                        obj[3] != null
                                ? obj[3].toString()
                                : ""
                );

                dto.setPatientGender(
                        obj[4] != null
                                ? obj[4].toString()
                                : ""
                );

                response.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    public List<PatientDetailsDTO> getAllPatients() {

        List<PatientDetailsDTO> response = new ArrayList<>();

        try {

            String hql = "SELECT p, d.employeeProfileId, " +
                         "CONCAT(COALESCE(d.employeeProfileFirstName, ''), ' ', " +
                         "COALESCE(d.employeeProfileMiddleName, ''), ' ', " +
                         "COALESCE(d.employeeProfileLastName, '')) " +
                         "FROM PatientRegistration p " +
                         "LEFT JOIN EmployeeProfile d ON p.patientRegistrationPrincipalDoctor = d.employeeProfileId " +
                         "ORDER BY p.patientRegistrationId ASC";

            List<Object[]> results = em.createQuery(hql, Object[].class).getResultList();

            Map<Long, String> chronicDiseaseMap =
                    chronicDiseaseRepository.findAll()
                            .stream()
                            .collect(Collectors.toMap(
                                    ChronicDisease::getChronicDiseaseId,
                                    ChronicDisease::getChronicDiseaseName
                            ));

            for (Object[] obj : results) {

                PatientRegistration patientEntity =
                        (PatientRegistration) obj[0];

                PatientDetailsDTO dto =
                        new PatientDetailsDTO();

                dto.setPatientRegistrationId(
                        patientEntity.getPatientRegistrationId());

                dto.setPatientRegistrationFirstName(
                        patientEntity.getPatientRegistrationFirstName());

                dto.setPatientRegistrationMiddleName(
                        patientEntity.getPatientRegistrationMiddleName());

                dto.setPatientRegistrationLastName(
                        patientEntity.getPatientRegistrationLastName());

                dto.setPatientRegistrationGuardianName(
                        patientEntity.getPatientRegistrationGuardianName());

                dto.setPatientRegistrationDob(
                        patientEntity.getPatientRegistrationDob());

                dto.setPatientRegistrationSex(
                        patientEntity.getPatientRegistrationSex());

                dto.setPatientRegistrationAddress(
                        patientEntity.getPatientRegistrationAddress());

                dto.setPatientRegistrationState(
                        patientEntity.getPatientRegistrationState());

                dto.setPatientRegistrationCity(
                        patientEntity.getPatientRegistrationCity());

                dto.setPatientRegistrationPincode(
                        patientEntity.getPatientRegistrationPincode());

                dto.setPatientRegistrationInsuranceName(
                        patientEntity.getPatientRegistrationInsuranceName());

                String chronicIds =
                        patientEntity.getPatientRegistrationChronic();

                String chronicNames = null;

                if (chronicIds != null && !chronicIds.trim().isEmpty()) {

                    chronicNames = Arrays.stream(chronicIds.split(","))
                            .map(String::trim)
                            .filter(s -> !s.isEmpty())
                            .map(s -> {
                                try {
                                    Long id = Long.valueOf(s);
                                    String name = chronicDiseaseMap.get(id);
                                    return name != null ? name : s;
                                } catch (NumberFormatException e) {
                                    return s;
                                }
                            })
                            .filter(Objects::nonNull)
                            .collect(Collectors.joining(", "));
                }

                dto.setPatientRegistrationChronic(chronicNames);

                dto.setPatientRegistrationCallReminder(
                        patientEntity.getPatientRegistrationCallReminder());

                dto.setPatientRegistrationTextReminder(
                        patientEntity.getPatientRegistrationTextReminder());

                dto.setPatientRegistrationMobileNo(
                        patientEntity.getPatientRegistrationMobileNo());

                dto.setPatientRegistrationOtherMobileNo(
                        patientEntity.getPatientRegistrationOtherMobileNo());

                dto.setPatientRegistrationImage(
                        patientEntity.getPatientRegistrationImage());


                dto.setPatientRegistrationEmailId(
                        patientEntity.getPatientRegistrationEmailId());

                dto.setPatientRegistrationActive(
                        patientEntity.getPatientRegistrationActive());

                dto.setPrincipalDoctorId(
                        obj[1] != null
                                ? ((Number) obj[1]).longValue()
                                : null);

                dto.setPrincipalDoctorName(
                        obj[2] != null
                                ? obj[2].toString().trim().replaceAll("\\s+", " ")
                                : null);

                response.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

        return response;
    }

    @Override
    public List<ChronicDisease> getChronicDiseases() {
        return chronicDiseaseRepository
                .findByChronicDiseaseIsActive("true");
    }

    @Override
    @Transactional
    public Boolean createNewPatient(CreatePatientDTO bean) {
        if (bean == null) throw new IllegalArgumentException("Request data cannot be null");
        if (bean.getPatientFirstName() == null || bean.getPatientFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("First Name is required");
        }
        if (bean.getPatientLastName() == null || bean.getPatientLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Last Name is required");
        }
        if (bean.getPatientMobileNumber() == null || bean.getPatientMobileNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Mobile Number is required");
        }

        // Duplicate check based on Name + Mobile
        List<PatientRegistration> existing = patientRegistrationRepository.findByPatientRegistrationFirstNameIgnoreCaseAndPatientRegistrationLastNameIgnoreCaseAndPatientRegistrationMobileNo(
                bean.getPatientFirstName().trim(),
                bean.getPatientLastName().trim(),
                bean.getPatientMobileNumber().trim()
        );
        if (!existing.isEmpty()) {
            throw new IllegalArgumentException("A patient with this name and mobile number already exists.");
        }

        PatientRegistration patient = new PatientRegistration();
        patient.setPatientRegistrationFirstName(bean.getPatientFirstName().trim());
        patient.setPatientRegistrationMiddleName(bean.getPatientMiddleName() != null ? bean.getPatientMiddleName().trim() : null);
        patient.setPatientRegistrationLastName(bean.getPatientLastName().trim());
        patient.setPatientRegistrationMobileNo(bean.getPatientMobileNumber().trim());
        patient.setPatientRegistrationOtherMobileNo(bean.getPatientEmergencyContact() != null ? bean.getPatientEmergencyContact().trim() : null);
        patient.setPatientRegistrationEmailId(bean.getPatientEmailId() != null ? bean.getPatientEmailId().trim() : null);
        
        if (bean.getDateOfBirth() != null && !bean.getDateOfBirth().trim().isEmpty()) {
            try {
                // Expected YYYY-MM-DD from frontend, but let's parse safely
                patient.setPatientRegistrationDob(LocalDate.parse(bean.getDateOfBirth().trim()));
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid Date of Birth format.");
            }
        }
        
        // Map Gender: "Male" -> "M", "Female" -> "F", "Other" -> "O"
        if (bean.getGender() != null) {
            String g = bean.getGender().trim().toLowerCase();
            if (g.startsWith("m")) patient.setPatientRegistrationSex("M");
            else if (g.startsWith("f")) patient.setPatientRegistrationSex("F");
            else patient.setPatientRegistrationSex("O");
        }

        patient.setPatientRegistrationAddress(bean.getPatientLocation() != null ? bean.getPatientLocation().trim() : null);
        patient.setPatientRegistrationChronic(bean.getPatientChronicHistory() != null ? bean.getPatientChronicHistory().trim() : null);
        patient.setPatientRegistrationActive(true);

        if (bean.getHeight() != null && !bean.getHeight().trim().isEmpty() ||
            bean.getWeight() != null && !bean.getWeight().trim().isEmpty() ||
            bean.getBmi() != null && !bean.getBmi().trim().isEmpty() ||
            bean.getBloodGroup() != null && !bean.getBloodGroup().trim().isEmpty()) {
            
            // Note: Since these properties are moved to PatientVitals JSONB, we should save them there instead.
            // But they are not attached to an encounter yet for a newly registered patient.
            // So we might need to handle this in a separate service or wait for their first chart.
            // For now, we skip saving them to PatientRegistration.
        }

        PatientRegistration savedPatient = patientRegistrationRepository.save(patient);

        // Schedule Appointment if details are provided
        if (bean.getProviderId() != null && bean.getAppointmentDate() != null && !bean.getAppointmentDate().trim().isEmpty()) {
            SchedulerAppointment appt = new SchedulerAppointment();
            appt.setSchedulerAppointmentPatientId(savedPatient.getPatientRegistrationId());
            appt.setSchedulerAppointmentPatientName(savedPatient.getPatientRegistrationFirstName() + " " + savedPatient.getPatientRegistrationLastName());
            appt.setSchedulerAppointmentProviderId(bean.getProviderId());
            
            try {
                appt.setSchedulerAppointmentAppointmentDate(LocalDate.parse(bean.getAppointmentDate().trim()));
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid Appointment Date format.");
            }
            
            appt.setSchedulerAppointmentStartTime(bean.getAppointmentTime());
            
            // Calculate end time (+15 mins)
            if (bean.getAppointmentTime() != null && bean.getAppointmentTime().contains(":")) {
                String[] parts = bean.getAppointmentTime().split(":");
                try {
                    int h = Integer.parseInt(parts[0]);
                    int m = Integer.parseInt(parts[1]);
                    m += 15;
                    if (m >= 60) {
                        m -= 60;
                        h += 1;
                    }
                    appt.setSchedulerAppointmentEndTime(String.format("%02d:%02d", h, m));
                } catch (Exception e) {
                    appt.setSchedulerAppointmentEndTime(bean.getAppointmentTime());
                }
            }
            
            appt.setSchedulerAppointmentReason(bean.getChiefComplaint());
            
            // Set status to "Pending" (Assuming ID 1 is Pending, typical in this DB)
            SchedulerAppointmentStatus status = statusRepository.findById(1L).orElse(null);
            if (status != null) {
                appt.setSchedulerAppointmentStatus(status);
            }
            
            appointmentRepository.save(appt);
        }

        return true;
    }

}
