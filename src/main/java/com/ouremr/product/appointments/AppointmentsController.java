package com.ouremr.product.appointments;

import com.ouremr.product.dto.*;
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
    public EMRResponseBean getAppointmentStatusCounts(@RequestParam(value = "userId") Long userId, @RequestParam(value = "apptDate") String apptDate) {
        EMRResponseBean response = new EMRResponseBean();

        try {
            AppointmentStatusCountsDTO data = appointmentservice.getAppointmentStatusCounts(userId, apptDate);
            response.setData(data);
        } catch (Exception e) {
            response.setData("ERROR_FETCHING_SUMMARY");
        }

        return response;
    }

    @GetMapping("/getAppointments")
    public EMRResponseBean getAppointments(@RequestParam(value = "userId") Integer userId, @RequestParam(value = "apptDate") String apptDate, @RequestParam(value = "pageNo") Integer pageNo) {
        EMRResponseBean response = new EMRResponseBean();

        try {
            List<AppointmentDTO> data = appointmentservice.getAppointments(userId, apptDate, pageNo);
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

    @PostMapping("/createAppointments")
    public EMRResponseBean createAppointments(@RequestBody CreateAppointmentDTO bean) {
        EMRResponseBean response = new EMRResponseBean();

        try {
            response.setData(appointmentservice.createAppointments(bean));
        } catch (Exception e) {
            response.setData("ERROR_FETCHING_APPOINTMENTS");
            e.printStackTrace();
        }

        return response;
    }

    @GetMapping("/getCreateApptDetails")
    public EMRResponseBean getCreateApptDetails() {
        EMRResponseBean response = new EMRResponseBean();
        try {
            CreateAppointmentBasicInfo data = appointmentservice.getCreateApptDetails();
            response.setData(data);
        } catch (Exception e) {
            response.setData("ERROR_FETCHING_APPOINTMENTS");
            e.printStackTrace();
        }

        return response;
    }
}
