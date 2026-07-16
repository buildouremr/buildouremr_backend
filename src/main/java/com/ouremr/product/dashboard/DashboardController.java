package com.ouremr.product.dashboard;

import com.ouremr.product.dto.AppointmentDTO;
import com.ouremr.product.dto.AppointmentStatusCountsDTO;
import com.ouremr.product.dto.TeamListDTO;
import com.ouremr.product.emrbean.EMRResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    DashboardService dashboardService;

    @GetMapping("/getSummary")
    public ResponseEntity<EMRResponseBean> getSummary(@RequestParam(value = "userId") Long userId, @RequestParam(value = "apptDate") String apptDate) {
        EMRResponseBean response = new EMRResponseBean();
        AppointmentStatusCountsDTO data = dashboardService.getDashboardSummary(userId, apptDate);
        response.setData(data);
        response.setStatus("SUCCESS");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAppointments")
    public ResponseEntity<EMRResponseBean> getAppointments(@RequestParam(value = "userId") Integer userId, @RequestParam(value = "apptDate") String apptDate, @RequestParam(value = "pageNo") Integer pageNo) {
        EMRResponseBean response = new EMRResponseBean();
        List<AppointmentDTO> data = dashboardService.getAppointments(userId, apptDate, pageNo);
        response.setData(data);
        response.setStatus("SUCCESS");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getTeamList")
    public ResponseEntity<EMRResponseBean> getTeamList(@RequestParam(value = "userId") Long userId) {
        EMRResponseBean response = new EMRResponseBean();
        List<TeamListDTO> data = dashboardService.getTeamList(userId);
        response.setData(data);
        response.setStatus("SUCCESS");
        return ResponseEntity.ok(response);
    }
}