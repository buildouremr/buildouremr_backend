package com.ouremr.product.dashboard;

import com.ouremr.product.dto.AppointmentDTO;
import com.ouremr.product.dto.AppointmentStatusCountsDTO;
import com.ouremr.product.dto.TeamListDTO;

import java.util.List;

public interface DashboardService {

    AppointmentStatusCountsDTO getDashboardSummary(Long userId, String apptDate);

    List<AppointmentDTO> getAppointments(Integer userId, String apptDate, Integer pageNo);

    List<TeamListDTO> getTeamList(Long userId);
}