package com.ouremr.product.dashboard;

import com.ouremr.product.dto.AppointmentDTO;
import com.ouremr.product.dto.AppointmentStatusCountsDTO;
import com.ouremr.product.dto.TeamListDTO;

import java.util.List;

public interface DashboardService {

    AppointmentStatusCountsDTO getDashboardSummary(Long userId);

    List<AppointmentDTO> getAppointments(Integer userId);

    List<TeamListDTO> getTeamList(Long userId);
}