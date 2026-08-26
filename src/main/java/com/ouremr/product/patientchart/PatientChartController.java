package com.ouremr.product.patientchart;

import com.ouremr.product.dto.PatientProfileDTO;
import com.ouremr.product.dto.PatientVitalsDTO;
import com.ouremr.product.dto.PatientVisitChartDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ouremr.product.dto.PatientChartDTO;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/patient-chart")
public class PatientChartController {

    @Autowired
    private PatientChartService patientChartService;

    @Autowired
    private AmbientScribeService ambientScribeService;

    @PostMapping(value = "/ambient-scribe", produces = "application/json")
    public ResponseEntity<?> processAmbientScribe(@RequestParam("audio") org.springframework.web.multipart.MultipartFile audio) {
        try {
            String jsonResult = ambientScribeService.processAudio(audio);
            return new ResponseEntity<>(jsonResult, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("{\"error\": \"" + e.getMessage() + "\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<?> getChartByAppointmentId(@PathVariable Long appointmentId) {
        try {
            PatientVisitChartDTO chart = patientChartService.getChartByAppointmentId(appointmentId);
            return new ResponseEntity<>(chart, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching chart: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{patientId}/snapshot")
    public ResponseEntity<?> getPatientSnapshot(@PathVariable Long patientId) {
        try {
            PatientVisitChartDTO snapshot = patientChartService.getPatientSnapshot(patientId);
            return new ResponseEntity<>(snapshot, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching snapshot: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{patientId}/profile")
    public ResponseEntity<?> getPatientProfile(@PathVariable Long patientId) {
        try {
            com.ouremr.product.dto.PatientProfileDTO profile = patientChartService.getPatientProfile(patientId);
            if (profile != null) {
                return new ResponseEntity<>(profile, HttpStatus.OK);
            }
            return new ResponseEntity<>("Patient not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching profile: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveChart(@RequestBody PatientVisitChartDTO chartDTO) {
        try {
            PatientVisitChartDTO savedChart = patientChartService.saveChart(chartDTO);
            return new ResponseEntity<>(savedChart, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error saving chart: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/history/{patientId}")
    public ResponseEntity<?> getPatientChartHistory(@PathVariable Long patientId) {
        try {
            List<PatientVisitChartDTO> history = patientChartService.getPatientChartHistory(patientId);
            return new ResponseEntity<>(history, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching chart history: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/encounter/{encounterId}/section/{sectionName}")
    public ResponseEntity<?> autoSaveSection(
            @PathVariable Long encounterId, 
            @PathVariable String sectionName, 
            @RequestBody PatientChartDTO dto) {
        try {
            patientChartService.autoSaveSection(encounterId, sectionName, dto, 1L); // Hardcoded userId for now, similar to existing methods
            return new ResponseEntity<>("Saved", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error saving section: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/encounter/{encounterId}/vitals")
    public ResponseEntity<?> saveVitals(@PathVariable Long encounterId, @RequestBody List<PatientVitalsDTO> vitals) {
        try {
            patientChartService.saveVitals(encounterId, vitals, 1L);
            return ResponseEntity.ok(Map.of("status", "SUCCESS"));
        } catch (Exception e) {
            return new ResponseEntity<>("Error saving vitals: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
