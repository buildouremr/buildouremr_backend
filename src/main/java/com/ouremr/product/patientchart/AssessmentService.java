package com.ouremr.product.patientchart;

import com.ouremr.product.dto.AssessmentDTO;
import com.ouremr.product.repositories.AssessmentMasterRepository;
import com.ouremr.product.tables.AssessmentMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import com.ouremr.product.repositories.PatientDiagnosisNotesRepository;
import com.ouremr.product.tables.PatientDiagnosisNotes;

@Service
public class AssessmentService {

    @Autowired
    private AssessmentMasterRepository assessmentMasterRepository;

    @Autowired
    private PatientDiagnosisNotesRepository patientDiagnosisNotesRepository;

    public List<AssessmentDTO> searchAssessments(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new java.util.ArrayList<>();
        }
        return assessmentMasterRepository.findByNameContainingIgnoreCase(keyword)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<AssessmentDTO> getRecentAssessments() {
        List<PatientDiagnosisNotes> recentNotes = patientDiagnosisNotesRepository.findTop50ByOrderByPatientDiagnosisNotesModifiedOnDesc();
        java.util.Set<String> uniqueAssessments = new java.util.LinkedHashSet<>();

        for (PatientDiagnosisNotes note : recentNotes) {
            if (note.getPatientDiagnosisNotesData() != null && !note.getPatientDiagnosisNotesData().trim().isEmpty()) {
                // Check if active (note.getIsActive() defaults to true if null in older DB records)
                if (note.getIsActive() == null || note.getIsActive()) {
                    uniqueAssessments.add(note.getPatientDiagnosisNotesData().trim());
                }
            }
            if (uniqueAssessments.size() >= 10) {
                break;
            }
        }
        
        List<AssessmentDTO> dtos = new java.util.ArrayList<>();
        long idCounter = 1;
        for (String name : uniqueAssessments) {
            AssessmentDTO dto = new AssessmentDTO();
            dto.setId(idCounter++);
            dto.setName(name);
            dtos.add(dto);
        }
        return dtos;
    }

    private AssessmentDTO convertToDTO(AssessmentMaster entity) {
        AssessmentDTO dto = new AssessmentDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }
}
