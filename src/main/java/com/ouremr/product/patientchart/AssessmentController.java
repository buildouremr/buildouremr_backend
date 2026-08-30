package com.ouremr.product.patientchart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import com.ouremr.product.emrbean.EMRResponseBean;

@RestController
@RequestMapping("/api/assessments")
public class AssessmentController {

    @Autowired
    private AssessmentService assessmentService;

    @GetMapping("/search")
    public ResponseEntity<EMRResponseBean> searchAssessments(@RequestParam("keyword") String keyword) {
        EMRResponseBean response = new EMRResponseBean();
        response.setData(assessmentService.searchAssessments(keyword));
        response.setStatus("SUCCESS");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/recent")
    public ResponseEntity<EMRResponseBean> getRecentAssessments() {
        EMRResponseBean response = new EMRResponseBean();
        response.setData(assessmentService.getRecentAssessments());
        response.setStatus("SUCCESS");
        return ResponseEntity.ok(response);
    }
}
