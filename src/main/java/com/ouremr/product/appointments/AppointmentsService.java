package com.ouremr.product.appointments;

import com.ouremr.product.dto.*;

import java.util.List;

public interface AppointmentsService {

    AppointmentStatusCountsDTO getAppointmentStatusCounts(Long userId, String apptDate);

    List<AppointmentDTO> getAppointments(Integer userId, String apptDate, Integer pageNo);

    AppointmentPatientDetailDTO getAppointmentPatientDetail(Long appointmentId);

    Boolean createAppointments(CreateAppointmentDTO bean);

    CreateAppointmentBasicInfo getCreateApptDetails();
}
