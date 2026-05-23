package com.ouremr.product.appointments;

import com.ouremr.product.dto.AppointmentDTO;
import com.ouremr.product.dto.AppointmentPatientDetailDTO;
import com.ouremr.product.dto.AppointmentStatusCountsDTO;
import com.ouremr.product.tables.*;
import com.ouremr.product.util.HUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentsServiceImpl implements AppointmentsService{

    @PersistenceContext
    private EntityManager em;

    @Override
    public AppointmentStatusCountsDTO getAppointmentStatusCounts(Long userId) {

        AppointmentStatusCountsDTO dto = new AppointmentStatusCountsDTO();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<EmployeeProfile> empQuery = cb.createQuery(EmployeeProfile.class);
            Root<EmployeeProfile> emp = empQuery.from(EmployeeProfile.class);
            empQuery.select(emp).where(cb.equal(emp.get("employeeProfileId"), userId));
            EmployeeProfile employee = em.createQuery(empQuery).getSingleResult();

            if (employee != null) {
                dto.setEmployeeId(employee.getEmployeeProfileId());
                dto.setEmployeeName(employee.getEmployeeProfileFirstName() + " " + HUtil.getOrDefault(employee.getEmployeeProfileLastName(), ""));
                dto.setRole(employee.getEmployeeProfileRoleName());
            }

            CriteriaQuery<Object[]> countQuery = cb.createQuery(Object[].class);

            Root<SchedulerAppointment> appointment = countQuery.from(SchedulerAppointment.class);

            Join<SchedulerAppointment, SchedulerAppointmentStatus> status = appointment.join("schedulerAppointmentStatus", JoinType.LEFT);

            Expression<Long> total = cb.count(appointment);

            Expression<Long> cancelled = cb.sum(cb.<Long>selectCase().when(cb.equal(status.get("schedulerAppointmentName"), "Cancelled"), 1L).otherwise(0L));
            Expression<Long> waiting = cb.sum(cb.<Long>selectCase().when(cb.equal(status.get("schedulerAppointmentName"), "Waiting"), 1L).otherwise(0L));
            Expression<Long> completed = cb.sum(cb.<Long>selectCase().when(cb.equal(status.get("schedulerAppointmentName"), "Completed"), 1L).otherwise(0L));
            Expression<Long> pending = cb.sum(cb.<Long>selectCase().when(cb.equal(status.get("schedulerAppointmentName"), "Pending"), 1L).otherwise(0L));
            Expression<Long> noShow = cb.sum(cb.<Long>selectCase().when(cb.equal(status.get("schedulerAppointmentName"), "No Show"), 1L).otherwise(0L));

            countQuery.multiselect(total, cancelled, waiting, completed, pending, noShow);

            Object[] countResult = em.createQuery(countQuery).getSingleResult();

            if (!HUtil.isNullOrEmpty(countResult)) {
                dto.setTotalAppointments(countResult[0] != null ? (Long) countResult[0] : 0L);
                dto.setCancelledCount(countResult[1] != null ? (Long) countResult[1] : 0L);
                dto.setWaitingCount(countResult[2] != null ? (Long) countResult[2] : 0L);
                dto.setCompletedCount(countResult[3] != null ? (Long) countResult[3] : 0L);
                dto.setPendingCount(countResult[4] != null ? (Long) countResult[4] : 0L);
                dto.setNoShowCount(countResult[5] != null ? (Long) countResult[5] : 0L);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dto;
    }
    
    @Override
    public List<AppointmentDTO> getAppointments(Integer userId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<SchedulerAppointment> appointment = query.from(SchedulerAppointment.class);
        Join<SchedulerAppointment, SchedulerAppointmentStatus> status = appointment.join("schedulerAppointmentStatus", JoinType.LEFT);
        Join<SchedulerAppointment, SchedulerAppointmentType> type = appointment.join("schedulerAppointmentType", JoinType.LEFT);
        Join<SchedulerAppointment, EmployeeProfile> provider = appointment.join("schedulerAppointmentProvider", JoinType.LEFT);
        Root<PatientRegistration> patient = query.from(PatientRegistration.class);

        Predicate patientJoin = cb.equal(appointment.get("schedulerAppointmentPatientId").as(Long.class), patient.get("patientRegistrationId"));

        Expression<String> patientName = cb.concat(patient.get("patientRegistrationFirstName"), cb.concat(" ",
                cb.coalesce(patient.get("patientRegistrationLastName"), "")));

        Expression<String> providerName = cb.concat(provider.get("employeeProfileFirstName"), cb.concat(" ",
                cb.coalesce(provider.get("employeeProfileLastName"), "")));

        Subquery<Long> sub = query.subquery(Long.class);
        Root<SchedulerAppointment> subRoot = sub.from(SchedulerAppointment.class);

        sub.select(cb.count(subRoot)).where(cb.equal(
                subRoot.get("schedulerAppointmentPatientId"), appointment.get("schedulerAppointmentPatientId")));

        Expression<String> patientType = cb.<String>selectCase().when(cb.greaterThan(sub, 1L), "Reg").otherwise("New");

        query.multiselect(
                patient.get("patientRegistrationId"),
                patientName,
                providerName,
                status.get("schedulerAppointmentName"),
                type.get("schedulerAppointmentTypeName"),
                appointment.get("schedulerAppointmentAppointmentDate"),
                appointment.get("schedulerAppointmentReason"),
                patientType,
                appointment.get("schedulerAppointmentStartTime"),
                appointment.get("schedulerAppointmentEndTime")
        );

        query.where(patientJoin);

        List<Object[]> results = em.createQuery(query).getResultList();

        List<AppointmentDTO> list = new ArrayList<>();

        for (Object[] row : results) {
            AppointmentDTO dto = new AppointmentDTO();

            dto.setPatientId((Long) row[0]);
            dto.setPatientName((String) row[1]);
            dto.setProviderName((String) row[2]);
            dto.setStatus((String) row[3]);
            dto.setType((String) row[4]);
            dto.setAppointmentDate((java.time.OffsetDateTime) row[5]);
            dto.setReason((String) row[6]);
            dto.setPatientType((String) row[7]);
            dto.setStartTime((String) row[8]);
            dto.setEndTime((String) row[9]);

            list.add(dto);
        }
        return list;
    }

    @Override
    public AppointmentPatientDetailDTO getAppointmentPatientDetail(Long appointmentId) {

        AppointmentPatientDetailDTO dto = null;

        try {

            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

            Root<SchedulerAppointment> appointment =
                    cq.from(SchedulerAppointment.class);

            Root<PatientRegistration> patient =
                    cq.from(PatientRegistration.class);

            Join<SchedulerAppointment, SchedulerAppointmentStatus> status =
                    appointment.join("schedulerAppointmentStatus", JoinType.LEFT);

            Join<SchedulerAppointment, SchedulerAppointmentType> type =
                    appointment.join("schedulerAppointmentType", JoinType.LEFT);

            /*
             * patient first + middle + last name
             */
            Expression<String> patientName =
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

            /*
             * state + city
             */
            Expression<String> stateAndCity =
                    cb.concat(
                            cb.coalesce(patient.get("patientRegistrationCity"), ""),
                            cb.concat(
                                    ", ",
                                    cb.coalesce(patient.get("patientRegistrationState"), "")
                            )
                    );

            cq.multiselect(

                    patientName, // 0 patientName

                    patient.get("patientRegistrationSex"), // 1 gender

                    patient.get("patientRegistrationId"), // 2 patientId

                    patient.get("patientRegistrationEmailId"), // 3 emailId

                    patient.get("patientRegistrationMobileNo"), // 4 mobileNo

                    patient.get("patientRegistrationDob"), // 5 dob

                    stateAndCity, // 6 stateAndCity

                    appointment.get("schedulerAppointmentAppointmentDate"), // 7 apptDate

                    appointment.get("schedulerAppointmentStartTime"), // 8 apptStartTime

                    appointment.get("schedulerAppointmentEndTime"), // 9 apptEndTime

                    status.get("schedulerAppointmentName"), // 10 appointmentStatus

                    type.get("schedulerAppointmentTypeName"), // 11 apptSessionType

                    appointment.get("schedulerAppointmentReason") // 12 reason
            );

            cq.where(
                    cb.and(

                            cb.equal(
                                    cb.toInteger(
                                            appointment.get("schedulerAppointmentPatientId")
                                    ),
                                    patient.get("patientRegistrationId")
                            ),

                            cb.equal(
                                    appointment.get("schedulerAppointmentId"),
                                    appointmentId
                            )
                    )
            );

            Object[] obj =
                    em.createQuery(cq)
                            .getSingleResult();

            dto = new AppointmentPatientDetailDTO();

            dto.setPatientName(
                    obj[0] != null ? obj[0].toString().trim().replaceAll("\\s+", " ") : ""
            );

            dto.setGender(
                    obj[1] != null ? obj[1].toString() : ""
            );

            dto.setPatientId(
                    obj[2] != null ? ((Number) obj[2]).intValue() : null
            );

            dto.setEmailId(
                    obj[3] != null ? obj[3].toString() : ""
            );

            dto.setMobileNo(
                    obj[4] != null ? obj[4].toString() : ""
            );

            dto.setDob(
                    obj[5] != null ? obj[5].toString() : ""
            );

            dto.setStateAndCity(
                    obj[6] != null ? obj[6].toString() : ""
            );

            dto.setApptDate(
                    obj[7] != null ? obj[7].toString() : ""
            );

            dto.setApptStartTime(
                    obj[8] != null ? obj[8].toString() : ""
            );

            dto.setApptEndTime(
                    obj[9] != null ? obj[9].toString() : ""
            );

            dto.setAppointmentStatus(
                    obj[10] != null ? obj[10].toString() : ""
            );

            dto.setApptSessionType(
                    obj[11] != null ? obj[11].toString() : ""
            );

            dto.setReason(
                    obj[12] != null ? obj[12].toString() : ""
            );

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dto;
    }

}
