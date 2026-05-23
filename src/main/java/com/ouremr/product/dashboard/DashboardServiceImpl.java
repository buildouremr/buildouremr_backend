package com.ouremr.product.dashboard;

import com.ouremr.product.appointments.AppointmentsService;
import com.ouremr.product.dto.AppointmentDTO;
import com.ouremr.product.dto.AppointmentStatusCountsDTO;
import com.ouremr.product.dto.TeamListDTO;
import com.ouremr.product.tables.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    AppointmentsService appointmentsService;

    @Override
    public AppointmentStatusCountsDTO getDashboardSummary(Long userId) {
        return appointmentsService.getAppointmentStatusCounts(userId);
    }

    @Override
    public List<AppointmentDTO> getAppointments(Integer userId) {
        return appointmentsService.getAppointments(userId);
    }

    @Override
    public List<TeamListDTO> getTeamList(Long userId) {
        List<TeamListDTO> list = null;
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
            Root<EmployeeProfile> employee = query.from(EmployeeProfile.class);
            Join<EmployeeProfile, EmployeeSpeciality> speciality = employee.join("employeeSpeciality", JoinType.LEFT);

            query.multiselect(employee.get("employeeProfileId"),
                    employee.get("employeeProfileFirstName"),
                    employee.get("employeeProfileLastName"),
                    employee.get("employeeProfileMiddleName"),
                    employee.get("employeeProfileMobileNo"),
                    employee.get("employeeProfileImage"),
                    employee.get("employeeProfileRoleName"),
                    employee.get("employeeProfileIsActive"),
                    speciality.get("employeeSpecialityName")
            );

            query.where(cb.and(cb.equal(employee.get("employeeProfileRoles").get("employeeProfileRoleId"), 1L),
                            cb.notEqual(employee.get("employeeProfileId"), userId)));

            List<Object[]> results = em.createQuery(query).getResultList();

            list = new ArrayList<>();

            for (Object[] row : results) {

                TeamListDTO dto = new TeamListDTO();

                dto.setEmployeeProfileId(((Long) row[0]).intValue());
                dto.setEmployeeFirstName((String) row[1]);
                dto.setEmployeeLastName((String) row[2]);
                dto.setEmployeeMiddleName((String) row[3]);
                dto.setEmployeeMobileNo((String) row[4]);
                dto.setEmployeeImage((String) row[5]);
                dto.setEmployeeRole((String) row[6]);
                dto.setEmployeeIsActive((Boolean) row[7]);
                dto.setEmployeeSpeciality((String) row[8]);

                list.add(dto);
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
        return list;
    }
}