package com.ouremr.product.patientchart;

import com.ouremr.product.dto.MedicationDTO;
import com.ouremr.product.repositories.MedicationRepository;
import com.ouremr.product.tables.Medication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicationService {

    @Autowired
    private MedicationRepository medicationRepository;

    public List<MedicationDTO> getAllMedications() {
        return medicationRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<MedicationDTO> searchMedications(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new java.util.ArrayList<>();
        }
        return medicationRepository.findByNameContainingIgnoreCase(keyword)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private MedicationDTO convertToDTO(Medication entity) {
        MedicationDTO dto = new MedicationDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setGenericName(entity.getGenericName());
        dto.setBrandName(entity.getBrandName());
        dto.setForm(entity.getForm());
        dto.setStrength(entity.getStrength());
        dto.setRoute(entity.getRoute());
        dto.setManufacturer(entity.getManufacturer());
        dto.setNdcCode(entity.getNdcCode());
        return dto;
    }
}
