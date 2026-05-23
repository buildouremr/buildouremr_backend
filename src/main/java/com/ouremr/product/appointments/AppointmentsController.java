package com.ouremr.product.appointments;

import com.ouremr.product.dto.AppointmentDTO;
import com.ouremr.product.dto.AppointmentPatientDetailDTO;
import com.ouremr.product.dto.AppointmentStatusCountsDTO;
import com.ouremr.product.emrbean.EMRResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "http://localhost:3000")
public class AppointmentsController {

    @Autowired
    AppointmentsService appointmentservice;

    @GetMapping("/getAppointmentStatusCounts")
    public EMRResponseBean getAppointmentStatusCounts(@RequestParam(value = "userId") Long userId) {
        EMRResponseBean response = new EMRResponseBean();

        try {
            AppointmentStatusCountsDTO data = appointmentservice.getAppointmentStatusCounts(userId);
            response.setData(data);
        } catch (Exception e) {
            response.setData("ERROR_FETCHING_SUMMARY");
        }

        return response;
    }

    @GetMapping("/getAppointments")
    public EMRResponseBean getAppointments(@RequestParam(value = "userId") Integer userId) {
        EMRResponseBean response = new EMRResponseBean();

        try {
            List<AppointmentDTO> data = appointmentservice.getAppointments(userId);
            response.setData(data);
        } catch (Exception e) {
            response.setData("ERROR_FETCHING_APPOINTMENTS");
            e.printStackTrace();
        }

        return response;
    }

    @GetMapping("/getAppointmentPatientDetails")
    public EMRResponseBean getAppointmentPatientDetails(@RequestParam(value = "appointmentId") Long appointmentId) {
        EMRResponseBean response = new EMRResponseBean();

        try {
            AppointmentPatientDetailDTO data = appointmentservice.getAppointmentPatientDetail(appointmentId);
            response.setData(data);
        } catch (Exception e) {
            response.setData("ERROR_FETCHING_APPOINTMENTS");
            e.printStackTrace();
        }

        return response;
    }
}
