package com.ouremr.product.appointments;

import com.ouremr.product.dto.*;

import java.util.List;

public interface AppointmentsService {

    AppointmentStatusCountsDTO getAppointmentStatusCounts(Long userId, String apptDate);

    List<AppointmentDTO> getAppointments(Integer userId, String apptDate, Integer pageNo);

    AppointmentPatientDetailDTO getAppointmentPatientDetail(Long appointmentId);

    /**
     * Create a new appointment.
     * Throws {@link IllegalArgumentException} if required fields are missing or invalid.
     * Throws {@link RuntimeException} if referenced entities (provider, type, status, patient) are not found.
     */
    void createAppointments(CreateAppointmentDTO bean);

    CreateAppointmentBasicInfo getCreateApptDetails();

    /**
     * Search existing patients by name (partial, case-insensitive).
     * Used in the New Appointment modal for Existing Patient selection.
     *
     * @param patientName partial name to search
     * @return up to 10 matching patients
     */
    List<ExistingPatientsDTO> searchPatients(String patientName);
}
