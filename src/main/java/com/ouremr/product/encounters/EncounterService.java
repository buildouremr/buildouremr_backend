package com.ouremr.product.encounters;

import com.ouremr.product.dto.EncounterResponseDTO;

public interface EncounterService {
    EncounterResponseDTO startOrGetActiveEncounter(Long patientId, Long userId);
    EncounterResponseDTO signEncounter(Long encounterId, Long userId);
}
