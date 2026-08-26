package com.ouremr.product.patientchart;

import com.ouremr.product.dto.MedicationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/medications")
public class MedicationController {

    @Autowired
    private MedicationService medicationService;

    @GetMapping
    public ResponseEntity<List<MedicationDTO>> getAllMedications() {
        return ResponseEntity.ok(medicationService.getAllMedications());
    }
}
