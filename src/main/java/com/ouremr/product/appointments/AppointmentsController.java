package com.ouremr.product.appointments;

import com.ouremr.product.dto.*;
import com.ouremr.product.emrbean.EMRResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentsController {

    @Autowired
    AppointmentsService appointmentservice;

    @GetMapping("/getAppointmentStatusCounts")
    public ResponseEntity<EMRResponseBean> getAppointmentStatusCounts(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "apptDate") String apptDate) {
        EMRResponseBean response = new EMRResponseBean();
        AppointmentStatusCountsDTO data = appointmentservice.getAppointmentStatusCounts(userId, apptDate);
        response.setData(data);
        response.setStatus("SUCCESS");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAppointments")
    public ResponseEntity<EMRResponseBean> getAppointments(
            @RequestParam(value = "userId") Integer userId,
            @RequestParam(value = "apptDate") String apptDate,
            @RequestParam(value = "pageNo") Integer pageNo) {
        EMRResponseBean response = new EMRResponseBean();
        List<AppointmentDTO> data = appointmentservice.getAppointments(userId, apptDate, pageNo);
        response.setData(data);
        response.setStatus("SUCCESS");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAppointmentPatientDetails")
    public ResponseEntity<EMRResponseBean> getAppointmentPatientDetails(
            @RequestParam(value = "appointmentId") Long appointmentId) {
        EMRResponseBean response = new EMRResponseBean();
        AppointmentPatientDetailDTO data = appointmentservice.getAppointmentPatientDetail(appointmentId);
        response.setData(data);
        response.setStatus("SUCCESS");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/createAppointments")
    public ResponseEntity<EMRResponseBean> createAppointments(@RequestBody CreateAppointmentDTO bean) {
        EMRResponseBean response = new EMRResponseBean();
        appointmentservice.createAppointments(bean);
        response.setData(true);
        response.setStatus("SUCCESS");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getCreateApptDetails")
    public ResponseEntity<EMRResponseBean> getCreateApptDetails() {
        EMRResponseBean response = new EMRResponseBean();
        CreateAppointmentBasicInfo data = appointmentservice.getCreateApptDetails();
        response.setData(data);
        response.setStatus("SUCCESS");
        return ResponseEntity.ok(response);
    }
}
