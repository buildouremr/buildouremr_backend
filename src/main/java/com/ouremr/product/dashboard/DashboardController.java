package com.ouremr.product.dashboard;

import com.ouremr.product.dto.AppointmentDTO;
import com.ouremr.product.dto.AppointmentStatusCountsDTO;
import com.ouremr.product.dto.TeamListDTO;
import com.ouremr.product.emrbean.EMRResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:3000")
public class DashboardController {

    @Autowired
    DashboardService dashboardService;

    @GetMapping("/getSummary")
    public EMRResponseBean getSummary(@RequestParam(value = "userId") Long userId, @RequestParam(value = "apptDate") String apptDate) {
        EMRResponseBean response = new EMRResponseBean();

        try {
            AppointmentStatusCountsDTO data = dashboardService.getDashboardSummary(userId, apptDate);
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
            List<AppointmentDTO> data = dashboardService.getAppointments(userId, apptDate, pageNo);
            response.setData(data);
        } catch (Exception e) {
            response.setData("ERROR_FETCHING_APPOINTMENTS");
            e.printStackTrace();
        }

        return response;
    }

    @GetMapping("/getTeamList")
    public EMRResponseBean getTeamList(@RequestParam(value = "userId") Long userId) {
        EMRResponseBean response = new EMRResponseBean();

        try {
            List<TeamListDTO> data = dashboardService.getTeamList(userId);
            response.setData(data);
        } catch (Exception e) {
            response.setData("ERROR_FETCHING_TEAM");
        }

        return response;
    }
}