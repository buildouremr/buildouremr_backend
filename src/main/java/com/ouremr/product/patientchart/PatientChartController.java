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
    public ResponseEntity<?> getPatientSnapshot(@PathVariable Long patientId, @RequestParam(required = false) Long encounterId) {
        try {
            PatientVisitChartDTO snapshot = patientChartService.getPatientSnapshot(patientId, encounterId);
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

    @GetMapping("/{patientId}/header")
    public ResponseEntity<?> getPatientHeader(@PathVariable Long patientId) {
        try {
            com.ouremr.product.dto.PatientHeaderDTO header = patientChartService.getPatientHeader(patientId);
            if (header != null) {
                return new ResponseEntity<>(header, HttpStatus.OK);
            }
            return new ResponseEntity<>("Patient not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching header: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveChart(@RequestBody PatientVisitChartDTO chartDTO) {
        try {
            System.out.println("Payload: " + chartDTO.getReasonForVisit());
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

    @GetMapping("/encounter/{encounterId}")
    public ResponseEntity<?> getChartByEncounterId(@PathVariable Long encounterId, @RequestParam Long patientId) {
        try {
            PatientVisitChartDTO chart = patientChartService.getChartByEncounterId(patientId, encounterId);
            return new ResponseEntity<>(chart, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching chart: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/patient/{patientId}/allergies")
    public ResponseEntity<?> savePatientAllergies(@PathVariable Long patientId, @RequestBody Map<String, Object> allergiesData) {
        try {
            patientChartService.savePatientAllergies(patientId, allergiesData);
            return new ResponseEntity<>("Allergies saved successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error saving allergies: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/patient/{patientId}/chronic-conditions")
    public ResponseEntity<?> savePatientChronicConditions(@PathVariable Long patientId, @RequestBody Map<String, Object> chronicConditionsData) {
        try {
            patientChartService.savePatientChronicConditions(patientId, chronicConditionsData);
            return new ResponseEntity<>("Chronic conditions saved successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error saving chronic conditions: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
