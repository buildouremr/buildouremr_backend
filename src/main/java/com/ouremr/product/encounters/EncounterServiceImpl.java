package com.ouremr.product.encounters;

import com.ouremr.product.dto.EncounterResponseDTO;
import com.ouremr.product.repositories.EncounterRepository;
import com.ouremr.product.repositories.PatientRegistrationRepository;
import com.ouremr.product.tables.Encounter;
import com.ouremr.product.tables.PatientRegistration;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class EncounterServiceImpl implements EncounterService {

    @Autowired
    private EncounterRepository encounterRepository;

    @Autowired
    private PatientRegistrationRepository patientRegistrationRepository;

    @Override
    @Transactional
    public EncounterResponseDTO startOrGetActiveEncounter(Long patientId, Long userId) {
        if (patientId == null) {
            throw new IllegalArgumentException("Patient ID is required");
        }

        PatientRegistration patient = patientRegistrationRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + patientId));

        Optional<Encounter> activeEncounterOpt = encounterRepository.findByPatientAndEncounterIsCompletedFalse(patient);

        Encounter encounter;
        if (activeEncounterOpt.isPresent()) {
            encounter = activeEncounterOpt.get();
        } else {
            encounter = new Encounter();
            encounter.setPatient(patient);
            encounter.setEncounterCreatedBy(userId);
            encounter.setEncounterModifiedBy(userId);
            encounter.setEncounterCreatedOn(new Date());
            encounter.setEncounterModifiedOn(new Date());
            encounter.setEncounterIsCompleted(false);

            encounter = encounterRepository.save(encounter);
        }

        return mapToResponseDTO(encounter);
    }

    @Override
    @Transactional
    public EncounterResponseDTO signEncounter(Long encounterId, Long userId) {
        if (encounterId == null) {
            throw new IllegalArgumentException("Encounter ID is required");
        }

        Encounter encounter = encounterRepository.findById(encounterId)
                .orElseThrow(() -> new RuntimeException("Encounter not found with ID: " + encounterId));

        if (Boolean.TRUE.equals(encounter.getEncounterIsCompleted())) {
            // Already checked out, return gracefully instead of failing
            return mapToResponseDTO(encounter);
        }

        encounter.setEncounterIsCompleted(true);
        encounter.setEncounterSignedBy(userId);
        encounter.setEncounterCompletedOn(new Date());
        encounter.setEncounterModifiedBy(userId);
        encounter.setEncounterModifiedOn(new Date());

        encounter = encounterRepository.save(encounter);

        return mapToResponseDTO(encounter);
    }

    private EncounterResponseDTO mapToResponseDTO(Encounter encounter) {
        EncounterResponseDTO dto = new EncounterResponseDTO();
        dto.setEncounterId(encounter.getEncounterId());
        dto.setPatientId(encounter.getPatient().getPatientRegistrationId());
        dto.setIsCompleted(encounter.getEncounterIsCompleted());
        dto.setCreatedOn(encounter.getEncounterCreatedOn());
        dto.setCreatedBy(encounter.getEncounterCreatedBy());
        return dto;
    }
}
