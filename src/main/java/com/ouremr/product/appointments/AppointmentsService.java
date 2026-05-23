package com.ouremr.product.appointments;

import com.ouremr.product.dto.AppointmentDTO;
import com.ouremr.product.dto.AppointmentPatientDetailDTO;
import com.ouremr.product.dto.AppointmentStatusCountsDTO;

import java.util.List;

public interface AppointmentsService {

    AppointmentStatusCountsDTO getAppointmentStatusCounts(Long userId);

    List<AppointmentDTO> getAppointments(Integer userId);

    AppointmentPatientDetailDTO getAppointmentPatientDetail(Long appointmentId);
}
