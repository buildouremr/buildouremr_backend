package com.ouremr.product.patientchart;

import com.ouremr.product.dto.PatientPrescriptionDTO;
import com.ouremr.product.dto.PatientVisitChartDTO;
import com.ouremr.product.dto.PatientVitalsDTO;
import com.ouremr.product.repositories.*;
import com.ouremr.product.tables.*;
import com.ouremr.product.dto.PatientProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientChartServiceImpl implements PatientChartService {

    @Autowired
    private ChartRepository chartRepository;

    @Autowired
    private PatientVitalsRepository vitalsRepository;

    @Autowired
    private PatientPrescriptionRepository prescriptionRepository;

    @Autowired
    private PatientRegistrationRepository patientRepository;
    
    @Autowired
    private EncounterRepository encounterRepository;

    @Autowired
    private SchedulerAppointmentRepository appointmentRepository;

    @Autowired
    private EmployeeProfileRepository employeeProfileRepository;

    @Autowired
    private PatientReasonVisitRepository patientReasonVisitRepository;

    @Autowired
    private PatientSymptomsRepository patientSymptomsRepository;

    @Autowired
    private PatientExaminationRepository patientExaminationRepository;

    @Autowired
    private PatientDiagnosisTestsRepository patientDiagnosisTestsRepository;

    @Autowired
    private PatientDiagnosisNotesRepository patientDiagnosisNotesRepository;

    @Autowired
    private PatientTreatmentPlanRepository patientTreatmentPlanRepository;

    @Autowired
    private PatientAdviceRepository patientAdviceRepository;

    @Override
    public PatientVisitChartDTO getChartByAppointmentId(Long appointmentId) {
        Optional<SchedulerAppointment> apptOpt = appointmentRepository.findById(appointmentId);
        if (apptOpt.isPresent()) {
            Long patientId = apptOpt.get().getSchedulerAppointmentPatientId();
            PatientVisitChartDTO dto = getPatientSnapshot(patientId, null);
            dto.setAppointmentId(appointmentId);
            return dto;
        }
        return null;
    }

    @Override
    public PatientVisitChartDTO getChartByEncounterId(Long patientId, Long encounterId) {
        return getPatientSnapshot(patientId, encounterId);
    }

    @Override
    @Transactional
    public PatientVisitChartDTO saveChart(PatientVisitChartDTO dto) {
        Chart chart = chartRepository.findByPatientId(dto.getPatientId()).orElse(null);
        
        if (chart == null) {
            chart = new Chart();
            chart.setPatientId(dto.getPatientId());
            chart.setCreatedOn(LocalDateTime.now());
            chart.setCreatedBy("1");
        } else {
            chart.setModifiedOn(LocalDateTime.now());
            chart.setModifiedBy("1");
        }
        
        chart.setStatus(dto.getStatus());
        Chart savedChart = chartRepository.save(chart);
        
        // Handle patient_reason_visit
        if (dto.getReasonForVisit() != null && dto.getEncounterId() != null) {
            Optional<PatientReasonVisit> reasonOpt = patientReasonVisitRepository.findByEncounter_EncounterIdAndPatient_PatientRegistrationId(dto.getEncounterId(), dto.getPatientId());
            PatientReasonVisit reasonVisit = reasonOpt.orElse(new PatientReasonVisit());
            if (reasonVisit.getPatientReasonVisitId() == null) {
                Encounter encounter = new Encounter();
                encounter.setEncounterId(dto.getEncounterId());
                reasonVisit.setEncounter(encounter);
                
                PatientRegistration pr = new PatientRegistration();
                pr.setPatientRegistrationId(dto.getPatientId());
                reasonVisit.setPatient(pr);
                
                reasonVisit.setPatientReasonVisitCreatedOn(LocalDateTime.now());
                reasonVisit.setPatientReasonVisitCreatedBy("1"); 
            }
            reasonVisit.setPatientReasonVisitData(dto.getReasonForVisit());
            reasonVisit.setPatientReasonVisitModifiedOn(LocalDateTime.now());
            patientReasonVisitRepository.save(reasonVisit);
        }

        // Handle patient_symptoms
        if (dto.getSymptoms() != null && !dto.getSymptoms().isEmpty() && dto.getEncounterId() != null) {
            Optional<PatientSymptoms> symptomsOpt = patientSymptomsRepository.findByEncounter_EncounterId(dto.getEncounterId());
            PatientSymptoms patientSymptoms = symptomsOpt.orElse(new PatientSymptoms());
            if (patientSymptoms.getPatientSymptomsId() == null) {
                Encounter encounter = new Encounter();
                encounter.setEncounterId(dto.getEncounterId());
                patientSymptoms.setEncounter(encounter);
                
                PatientRegistration pr = new PatientRegistration();
                pr.setPatientRegistrationId(dto.getPatientId());
                patientSymptoms.setPatient(pr);
                
                patientSymptoms.setPatientSymptomsCreatedOn(LocalDateTime.now());
                patientSymptoms.setPatientSymptomsCreatedBy("1");
            }
            patientSymptoms.setPatientSymptomsData(dto.getSymptoms());
            patientSymptoms.setPatientSymptomsModifiedOn(LocalDateTime.now());
            patientSymptomsRepository.save(patientSymptoms);
        }

        // Handle patient_examination
        if (dto.getExamination() != null && dto.getEncounterId() != null) {
            Optional<PatientExamination> examOpt = patientExaminationRepository.findByEncounter_EncounterId(dto.getEncounterId());
            PatientExamination patientExamination = examOpt.orElse(new PatientExamination());
            if (patientExamination.getPatientExaminationId() == null) {
                Encounter encounter = new Encounter();
                encounter.setEncounterId(dto.getEncounterId());
                patientExamination.setEncounter(encounter);
                
                PatientRegistration pr = new PatientRegistration();
                pr.setPatientRegistrationId(dto.getPatientId());
                patientExamination.setPatient(pr);
                
                patientExamination.setPatientExaminationCreatedOn(LocalDateTime.now());
                patientExamination.setPatientExaminationCreatedBy("1");
            }
            try {
                com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                String dataStr = mapper.writeValueAsString(dto.getExamination());
                patientExamination.setPatientExaminationData(dataStr);
            } catch (Exception e) {
                patientExamination.setPatientExaminationData("");
            }
            patientExamination.setPatientExaminationModifiedOn(LocalDateTime.now());
            patientExaminationRepository.save(patientExamination);
        }

        // Handle patient_diagnosis_tests
        if (dto.getDiagnosisTests() != null && dto.getEncounterId() != null) {
            Optional<PatientDiagnosisTests> dtOpt = patientDiagnosisTestsRepository.findByEncounter_EncounterId(dto.getEncounterId());
            PatientDiagnosisTests dt = dtOpt.orElse(new PatientDiagnosisTests());
            if (dt.getPatientDiagnosisTestsId() == null) {
                Encounter encounter = new Encounter();
                encounter.setEncounterId(dto.getEncounterId());
                dt.setEncounter(encounter);
                
                PatientRegistration pr = new PatientRegistration();
                pr.setPatientRegistrationId(dto.getPatientId());
                dt.setPatient(pr);
                
                dt.setPatientDiagnosisTestsCreatedOn(LocalDateTime.now());
                dt.setPatientDiagnosisTestsCreatedBy("1");
            }
            dt.setPatientDiagnosisTestsData(dto.getDiagnosisTests());
            dt.setPatientDiagnosisTestsModifiedOn(LocalDateTime.now());
            patientDiagnosisTestsRepository.save(dt);
        }

        // Handle patient_diagnosis_notes (Assessments)
        if (dto.getAssessments() != null && dto.getEncounterId() != null) {
            java.util.List<PatientDiagnosisNotes> existingNotes = patientDiagnosisNotesRepository.findByEncounter_EncounterIdAndIsActiveTrue(dto.getEncounterId());
            java.util.Set<String> newAssessments = new java.util.HashSet<>(dto.getAssessments());
            java.util.Set<Long> updatedIds = new java.util.HashSet<>();
            
            for (PatientDiagnosisNotes existing : existingNotes) {
                if (existing.getPatientDiagnosisNotesData() != null && newAssessments.contains(existing.getPatientDiagnosisNotesData().trim())) {
                    updatedIds.add(existing.getPatientDiagnosisNotesId());
                    newAssessments.remove(existing.getPatientDiagnosisNotesData().trim());
                }
            }
            
            for (String newAssessmentText : newAssessments) {
                if (newAssessmentText == null || newAssessmentText.trim().isEmpty()) {
                    continue;
                }
                PatientDiagnosisNotes dn = new PatientDiagnosisNotes();
                Encounter enc = new Encounter();
                enc.setEncounterId(dto.getEncounterId());
                dn.setEncounter(enc);
                dn.setPatient(new PatientRegistration() {{ setPatientRegistrationId(dto.getPatientId()); }});
                dn.setPatientDiagnosisNotesCreatedOn(LocalDateTime.now());
                dn.setPatientDiagnosisNotesCreatedBy("1");
                dn.setPatientDiagnosisNotesData(newAssessmentText.trim());
                dn.setPatientDiagnosisNotesModifiedOn(LocalDateTime.now());
                dn.setIsActive(true);
                patientDiagnosisNotesRepository.save(dn);
            }
            
            for (PatientDiagnosisNotes existing : existingNotes) {
                if (!updatedIds.contains(existing.getPatientDiagnosisNotesId())) {
                    existing.setIsActive(false);
                    existing.setPatientDiagnosisNotesModifiedOn(LocalDateTime.now());
                    patientDiagnosisNotesRepository.save(existing);
                }
            }
        }

        // Handle patient_treatment_plan
        if (dto.getTreatmentPlan() != null && dto.getEncounterId() != null) {
            Optional<PatientTreatmentPlan> tpOpt = patientTreatmentPlanRepository.findByEncounter_EncounterId(dto.getEncounterId());
            PatientTreatmentPlan tp = tpOpt.orElse(new PatientTreatmentPlan());
            if (tp.getPatientTreatmentPlanId() == null) {
                Encounter encounter = new Encounter();
                encounter.setEncounterId(dto.getEncounterId());
                tp.setEncounter(encounter);
                
                PatientRegistration pr = new PatientRegistration();
                pr.setPatientRegistrationId(dto.getPatientId());
                tp.setPatient(pr);
                
                tp.setPatientTreatmentPlanCreatedOn(LocalDateTime.now());
                tp.setPatientTreatmentPlanCreatedBy("1");
            }
            try {
                com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                String dataStr = mapper.writeValueAsString(dto.getTreatmentPlan());
                tp.setPatientTreatmentPlanData(dataStr);
            } catch (Exception e) {
                tp.setPatientTreatmentPlanData("");
            }
            tp.setPatientTreatmentPlanModifiedOn(LocalDateTime.now());
            patientTreatmentPlanRepository.save(tp);
        }

        // Handle patient_advice
        if (dto.getAdvice() != null && dto.getEncounterId() != null) {
            Optional<PatientAdvice> advOpt = patientAdviceRepository.findByEncounter_EncounterId(dto.getEncounterId());
            PatientAdvice adv = advOpt.orElse(new PatientAdvice());
            if (adv.getPatientAdviceId() == null) {
                Encounter encounter = new Encounter();
                encounter.setEncounterId(dto.getEncounterId());
                adv.setEncounter(encounter);
                
                PatientRegistration pr = new PatientRegistration();
                pr.setPatientRegistrationId(dto.getPatientId());
                adv.setPatient(pr);
                
                adv.setPatientAdviceCreatedOn(LocalDateTime.now());
                adv.setPatientAdviceCreatedBy("1");
            }
            try {
                com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                String dataStr = mapper.writeValueAsString(dto.getAdvice());
                adv.setPatientAdviceData(dataStr);
            } catch (Exception e) {
                adv.setPatientAdviceData("");
            }
            adv.setPatientAdviceModifiedOn(LocalDateTime.now());
            patientAdviceRepository.save(adv);
        }

        // Handle Vitals
        if (dto.getVitals() != null && !dto.getVitals().isEmpty() && dto.getEncounterId() != null) {
            java.util.Map<String, Object> finalData = new java.util.HashMap<>();
            
            List<PatientVitals> existing = vitalsRepository.findByEncounter_EncounterId(dto.getEncounterId());
            if (existing != null && !existing.isEmpty()) {
                finalData.putAll(existing.get(0).getPatientVitalData());
                vitalsRepository.deleteAll(existing);
            } else {
                List<PatientVitals> latestVitals = vitalsRepository.findByPatientIdOrderByPatientVitalsCreatedOnDesc(dto.getPatientId());
                if (latestVitals != null && !latestVitals.isEmpty()) {
                    finalData.putAll(latestVitals.get(0).getPatientVitalData());
                }
            }
    
            java.util.Map<String, Object> incoming = dto.getVitals().get(0).getPatientVitalData();
            String nowStr = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a"));
            if (incoming != null) {
                for (java.util.Map.Entry<String, Object> entry : incoming.entrySet()) {
                    String key = entry.getKey();
                    String newVal = entry.getValue() != null ? entry.getValue().toString() : "";
                    
                    String oldVal = "";
                    if (finalData.containsKey(key)) {
                        Object oldObj = finalData.get(key);
                        if (oldObj instanceof java.util.Map) {
                            Object ov = ((java.util.Map<?, ?>) oldObj).get("value");
                            oldVal = ov != null ? ov.toString() : "";
                        }
                    }
                    
                    if (!newVal.equals(oldVal)) {
                        finalData.put(key, java.util.Map.of("value", newVal, "date", nowStr));
                    }
                }
            }
            
            PatientVitals vitals = new PatientVitals();
            Encounter encounter = new Encounter();
            encounter.setEncounterId(dto.getEncounterId());
            vitals.setEncounter(encounter);
            vitals.setPatientId(dto.getPatientId());
            vitals.setPatientVitalData(finalData);
            vitals.setPatientVitalsCreatedOn(LocalDateTime.now());
            vitalsRepository.save(vitals);
        }

        // Handle Prescriptions
        if (dto.getPrescriptions() != null && dto.getEncounterId() != null) {
            java.util.List<PatientPrescription> existingPrescriptions = prescriptionRepository.findByPatientPrescriptionEncounterIdAndIsActiveTrue(dto.getEncounterId());
            java.util.Map<Long, PatientPrescription> existingMap = existingPrescriptions.stream()
                .collect(java.util.stream.Collectors.toMap(PatientPrescription::getPatientPrescriptionId, p -> p));
            java.util.Set<Long> updatedIds = new java.util.HashSet<>();
            
            for (PatientPrescriptionDTO pDto : dto.getPrescriptions()) {
                if (pDto.getDrugName() == null || pDto.getDrugName().trim().isEmpty()) {
                    continue; 
                }
                PatientPrescription p;
                if (pDto.getPatientPrescriptionId() != null && existingMap.containsKey(pDto.getPatientPrescriptionId())) {
                    p = existingMap.get(pDto.getPatientPrescriptionId());
                    updatedIds.add(pDto.getPatientPrescriptionId());
                    p.setPatientPrescriptionModifiedOn(LocalDateTime.now());
                } else {
                    p = new PatientPrescription();
                    p.setPatientPrescriptionCreatedOn(LocalDateTime.now());
                }
                p.setPatientPrescriptionEncounterId(dto.getEncounterId());
                p.setPatientPrescriptionPatientId(dto.getPatientId());
                p.setMedicationId(pDto.getMedicationId());
                p.setPatientPrescriptionDrugName(pDto.getDrugName());
                p.setPatientPrescriptionFrequency(pDto.getFrequency());
                p.setPatientPrescriptionDuration(pDto.getDuration());
                p.setPatientPrescriptionInstruction(pDto.getInstruction());
                
                prescriptionRepository.save(p);
            }
            
            for (PatientPrescription existing : existingPrescriptions) {
                if (!updatedIds.contains(existing.getPatientPrescriptionId())) {
                    existing.setIsActive(false);
                    existing.setPatientPrescriptionModifiedOn(LocalDateTime.now());
                    prescriptionRepository.save(existing);
                }
            }
        }

        return getPatientSnapshot(dto.getPatientId(), dto.getEncounterId());
    }

    @Override
    public List<PatientVisitChartDTO> getPatientChartHistory(Long patientId) {
        // A chart history is basically a list of encounters for the patient. 
        // For simplicity, we can fetch all encounters for the patient and convert them to DTOs
        // Or if we need a list of charts, we have to rethink since there's only 1 chart now.
        // We'll return an empty list for now until the frontend is updated to fetch encounters history.
        return new ArrayList<>();
    }

    @Override
    public PatientVisitChartDTO getPatientSnapshot(Long patientId, Long encounterId) {
        PatientVisitChartDTO dto = new PatientVisitChartDTO();
        dto.setPatientId(patientId);
        dto.setEncounterId(encounterId);

        Optional<PatientRegistration> patientOpt = patientRepository.findById(patientId);
        if (patientOpt.isPresent()) {
            PatientRegistration p = patientOpt.get();
            dto.setPatientName(p.getPatientRegistrationFirstName() + " " + (p.getPatientRegistrationLastName() != null ? p.getPatientRegistrationLastName() : ""));
            dto.setPatientGender(p.getPatientRegistrationSex());
            if (p.getPatientRegistrationDob() != null) {
                dto.setPatientDob(p.getPatientRegistrationDob().toString());
                int age = Period.between(p.getPatientRegistrationDob(), java.time.LocalDate.now()).getYears();
                dto.setPatientAge(String.valueOf(age));
            }
            dto.setPatientAllergies(p.getPatientRegistrationAllergies());
            dto.setPatientChronicConditions(p.getPatientRegistrationChronic());
            dto.setPatientRiskFactors(p.getPatientRegistrationRiskFactors());
            dto.setPatientInsurance(p.getPatientRegistrationInsuranceName());
        }

        String enteredBy = "Unknown";
        if (encounterId != null) {
            Optional<Encounter> encounterOpt = encounterRepository.findById(encounterId);
            if (encounterOpt.isPresent() && encounterOpt.get().getEncounterCreatedBy() != null) {
                Optional<EmployeeProfile> empOpt = employeeProfileRepository.findById(encounterOpt.get().getEncounterCreatedBy());
                if (empOpt.isPresent()) {
                    EmployeeProfile emp = empOpt.get();
                    enteredBy = emp.getEmployeeProfileFirstName() + " " + (emp.getEmployeeProfileLastName() != null ? emp.getEmployeeProfileLastName() : "");
                }
            }
        }
        dto.setEnteredBy(enteredBy.trim());
        
        Optional<Chart> chartOpt = chartRepository.findByPatientId(patientId);
        if (chartOpt.isPresent()) {
            dto.setPatientVisitChartId(chartOpt.get().getChartId());
            dto.setStatus(chartOpt.get().getStatus());
        }

        if (encounterId != null) {
            Optional<PatientReasonVisit> reasonOpt = patientReasonVisitRepository.findByEncounter_EncounterIdAndPatient_PatientRegistrationId(encounterId, patientId);
            dto.setReasonForVisit(reasonOpt.isPresent() ? reasonOpt.get().getPatientReasonVisitData() : "");

            Optional<PatientSymptoms> symptomsOpt = patientSymptomsRepository.findByEncounter_EncounterId(encounterId);
            dto.setSymptoms(symptomsOpt.isPresent() ? symptomsOpt.get().getPatientSymptomsData() : new ArrayList<>());

            Optional<PatientExamination> examOpt = patientExaminationRepository.findByEncounter_EncounterId(encounterId);
            if (examOpt.isPresent() && examOpt.get().getPatientExaminationData() != null && !examOpt.get().getPatientExaminationData().isEmpty()) {
                String data = examOpt.get().getPatientExaminationData();
                try {
                    com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                    java.util.List<String> list = mapper.readValue(data, new com.fasterxml.jackson.core.type.TypeReference<java.util.List<String>>(){});
                    dto.setExamination(list);
                } catch (Exception e) {
                    dto.setExamination(java.util.Collections.singletonList(data));
                }
            } else {
                dto.setExamination(new java.util.ArrayList<>());
            }

            Optional<PatientDiagnosisTests> dtOpt = patientDiagnosisTestsRepository.findByEncounter_EncounterId(encounterId);
            dto.setDiagnosisTests(dtOpt.isPresent() ? dtOpt.get().getPatientDiagnosisTestsData() : "");

            List<PatientDiagnosisNotes> activeNotes = patientDiagnosisNotesRepository.findByEncounter_EncounterIdAndIsActiveTrue(encounterId);
            List<String> assessments = new java.util.ArrayList<>();
            for (PatientDiagnosisNotes dn : activeNotes) {
                if (dn.getPatientDiagnosisNotesData() != null && !dn.getPatientDiagnosisNotesData().trim().isEmpty()) {
                    assessments.add(dn.getPatientDiagnosisNotesData().trim());
                }
            }
            dto.setAssessments(assessments);

            Optional<PatientTreatmentPlan> tpOpt = patientTreatmentPlanRepository.findByEncounter_EncounterId(encounterId);
            if (tpOpt.isPresent() && tpOpt.get().getPatientTreatmentPlanData() != null && !tpOpt.get().getPatientTreatmentPlanData().isEmpty()) {
                String data = tpOpt.get().getPatientTreatmentPlanData();
                try {
                    com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                    java.util.List<String> list = mapper.readValue(data, new com.fasterxml.jackson.core.type.TypeReference<java.util.List<String>>(){});
                    dto.setTreatmentPlan(list);
                } catch (Exception e) {
                    dto.setTreatmentPlan(java.util.Collections.singletonList(data));
                }
            } else {
                dto.setTreatmentPlan(new java.util.ArrayList<>());
            }

            Optional<PatientAdvice> advOpt = patientAdviceRepository.findByEncounter_EncounterId(encounterId);
            if (advOpt.isPresent() && advOpt.get().getPatientAdviceData() != null && !advOpt.get().getPatientAdviceData().isEmpty()) {
                String data = advOpt.get().getPatientAdviceData();
                try {
                    com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                    java.util.List<String> list = mapper.readValue(data, new com.fasterxml.jackson.core.type.TypeReference<java.util.List<String>>(){});
                    dto.setAdvice(list);
                } catch (Exception e) {
                    dto.setAdvice(java.util.Collections.singletonList(data));
                }
            } else {
                dto.setAdvice(new java.util.ArrayList<>());
            }

            List<PatientVitals> vitalsList = vitalsRepository.findByEncounter_EncounterId(encounterId);
            if (vitalsList != null && !vitalsList.isEmpty()) {
                List<PatientVitalsDTO> vDtoList = new ArrayList<>();
                for (PatientVitals v : vitalsList) {
                    PatientVitalsDTO vDto = new PatientVitalsDTO();
                    vDto.setPatientVitalsId(v.getPatientVitalsId());
                    vDto.setPatientVisitChartId(chartOpt.isPresent() ? chartOpt.get().getChartId() : null);
                    vDto.setPatientVitalData(v.getPatientVitalData());
                    vDtoList.add(vDto);
                }
                dto.setVitals(vDtoList);
            } else {
                // Carry forward previous vitals if they exist for a new encounter
                List<PatientVitals> pastVitals = vitalsRepository.findByPatientIdOrderByPatientVitalsCreatedOnDesc(patientId);
                if (pastVitals != null && !pastVitals.isEmpty()) {
                    PatientVitals recent = pastVitals.get(0);
                    PatientVitalsDTO vDto = new PatientVitalsDTO();
                    vDto.setPatientVitalsId(null); // Treat as new for the new encounter
                    vDto.setPatientVisitChartId(chartOpt.isPresent() ? chartOpt.get().getChartId() : null);
                    vDto.setPatientVitalData(recent.getPatientVitalData());
                    List<PatientVitalsDTO> vDtoList = new ArrayList<>();
                    vDtoList.add(vDto);
                    dto.setVitals(vDtoList);
                }
            }
            
            List<PatientPrescription> prescriptions = prescriptionRepository.findByPatientPrescriptionEncounterIdAndIsActiveTrue(encounterId);
            List<PatientPrescriptionDTO> pDtos = new ArrayList<>();
            for (PatientPrescription p : prescriptions) {
                PatientPrescriptionDTO pDto = new PatientPrescriptionDTO();
                pDto.setPatientPrescriptionId(p.getPatientPrescriptionId());
                pDto.setPatientVisitChartId(chartOpt.isPresent() ? chartOpt.get().getChartId() : null);
                pDto.setMedicationId(p.getMedicationId());
                if (p.getMedication() != null) {
                    pDto.setDrugName(p.getMedication().getName());
                } else {
                    pDto.setDrugName(p.getPatientPrescriptionDrugName());
                }
                pDto.setFrequency(p.getPatientPrescriptionFrequency());
                pDto.setDuration(p.getPatientPrescriptionDuration());
                pDto.setInstruction(p.getPatientPrescriptionInstruction());
                pDtos.add(pDto);
            }
            dto.setPrescriptions(pDtos);
        }

        populateDemographicsVitals(dto, patientId);
        return dto;
    }

    private com.ouremr.product.dto.PatientHeaderDTO.MetricDate extractMetricDate(java.util.Map<String, Object> vitalData, String key) {
        if (vitalData != null && vitalData.containsKey(key)) {
            Object obj = vitalData.get(key);
            if (obj instanceof java.util.Map) {
                java.util.Map<?, ?> map = (java.util.Map<?, ?>) obj;
                String val = map.containsKey("value") && map.get("value") != null ? map.get("value").toString() : "-";
                String date = map.containsKey("date") && map.get("date") != null ? map.get("date").toString() : "-";
                
                if (!date.equals("-") && date.contains(" ")) {
                    String[] parts = date.split(" ");
                    String datePart = parts[0]; 
                    if (datePart.contains("-") && datePart.length() == 10) {
                        try {
                            java.time.LocalDate ld = java.time.LocalDate.parse(datePart);
                            date = ld.format(java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy"));
                        } catch (Exception e) {
                            date = datePart;
                        }
                    } else {
                        date = datePart;
                    }
                }
                
                return new com.ouremr.product.dto.PatientHeaderDTO.MetricDate(val, date);
            }
        }
        return new com.ouremr.product.dto.PatientHeaderDTO.MetricDate("-", "-");
    }

    @Override
    public com.ouremr.product.dto.PatientHeaderDTO getPatientHeader(Long patientId) {
        com.ouremr.product.dto.PatientHeaderDTO header = new com.ouremr.product.dto.PatientHeaderDTO();
        Optional<PatientRegistration> pOpt = patientRepository.findById(patientId);
        if (pOpt.isPresent()) {
            PatientRegistration p = pOpt.get();
            header.setName(p.getPatientRegistrationFirstName() + " " + (p.getPatientRegistrationLastName() != null ? p.getPatientRegistrationLastName() : ""));
            header.setGender(p.getPatientRegistrationSex());
            if (p.getPatientRegistrationDob() != null) {
                header.setDob(p.getPatientRegistrationDob().toString());
                int age = Period.between(p.getPatientRegistrationDob(), java.time.LocalDate.now()).getYears();
                header.setAge(String.valueOf(age));
            } else {
                header.setDob("-");
                header.setAge("-");
            }
            List<PatientVitals> latestVitalsList = vitalsRepository.findByPatientIdOrderByPatientVitalsCreatedOnDesc(patientId);
            java.util.Map<String, Object> vitalData = null;
            if (latestVitalsList != null && !latestVitalsList.isEmpty()) {
                vitalData = latestVitalsList.get(0).getPatientVitalData();
            }

            header.setHeight(extractMetricDate(vitalData, "Height"));
            header.setWeight(extractMetricDate(vitalData, "Weight"));
            header.setBmi(extractMetricDate(vitalData, "BMI"));
            header.setBloodGroup(extractMetricDate(vitalData, "Blood Group"));
            
            header.setInsurance(p.getPatientRegistrationInsuranceName());
            header.setAllergies(p.getPatientRegistrationAllergies());
            header.setChronicConditions(p.getPatientRegistrationChronic());
            header.setRiskFactors(p.getPatientRegistrationRiskFactors());
            
            header.setLastVisit(null);
            header.setNextVisit(null);
            return header;
        }
        return null;
    }

    @Override
    public PatientProfileDTO getPatientProfile(Long patientId) {
        PatientProfileDTO profile = new PatientProfileDTO();
        
        Optional<PatientRegistration> pOpt = patientRepository.findById(patientId);
        if (!pOpt.isPresent()) return null;
        PatientRegistration p = pOpt.get();
        
        PatientProfileDTO.Header header = new PatientProfileDTO.Header();
        header.setName(p.getPatientRegistrationFirstName() + " " + (p.getPatientRegistrationLastName() != null ? p.getPatientRegistrationLastName() : ""));
        header.setId("PT-" + p.getPatientRegistrationId());
        header.setStatus("Stable");
        header.setGender(p.getPatientRegistrationSex());
        if (p.getPatientRegistrationDob() != null) {
            header.setDob(p.getPatientRegistrationDob().toString());
            header.setAge(Period.between(p.getPatientRegistrationDob(), java.time.LocalDate.now()).getYears() + " yrs");
        } else {
            header.setDob("-");
            header.setAge("-");
        }
        List<PatientVitals> latestVitalsList = vitalsRepository.findByPatientIdOrderByPatientVitalsCreatedOnDesc(patientId);
        java.util.Map<String, Object> vitalData = null;
        if (latestVitalsList != null && !latestVitalsList.isEmpty()) {
            vitalData = latestVitalsList.get(0).getPatientVitalData();
        }
        
        com.ouremr.product.dto.PatientHeaderDTO.MetricDate bg = extractMetricDate(vitalData, "Blood Group");
        header.setBloodGroup(new PatientProfileDTO.MetricDate(bg.getValue(), bg.getDate()));
        
        com.ouremr.product.dto.PatientHeaderDTO.MetricDate h = extractMetricDate(vitalData, "Height");
        header.setHeight(new PatientProfileDTO.MetricDate(h.getValue(), h.getDate()));
        
        com.ouremr.product.dto.PatientHeaderDTO.MetricDate w = extractMetricDate(vitalData, "Weight");
        header.setWeight(new PatientProfileDTO.MetricDate(w.getValue(), w.getDate()));
        
        com.ouremr.product.dto.PatientHeaderDTO.MetricDate bmi = extractMetricDate(vitalData, "BMI");
        header.setBmi(new PatientProfileDTO.MetricDate(bmi.getValue(), bmi.getDate()));
        
        header.setLastVisit("-");
        profile.setHeader(header);
        
        PatientProfileDTO.Alerts alerts = new PatientProfileDTO.Alerts();
        List<String> allergies = new ArrayList<>();
        if (p.getPatientRegistrationAllergies() != null && !p.getPatientRegistrationAllergies().trim().isEmpty()) {
            allergies.add("Allergy: " + p.getPatientRegistrationAllergies());
        } else {
            allergies.add("No Known Allergies");
        }
        alerts.setAllergies(allergies);
        
        List<String> conditions = new ArrayList<>();
        if (p.getPatientRegistrationChronic() != null && !p.getPatientRegistrationChronic().trim().isEmpty()) {
            conditions.add("Active Condition: " + p.getPatientRegistrationChronic());
        }
        
        if (conditions.isEmpty()) {
            conditions.add("No Active Conditions");
        }
        
        alerts.setConditions(conditions);
        profile.setAlerts(alerts);
        
        List<PatientProfileDTO.VitalItem> vitals = new ArrayList<>();
        if (latestVitalsList != null && !latestVitalsList.isEmpty()) {
            PatientVitals v = latestVitalsList.get(0);
            if (v.getPatientVitalData() != null && !v.getPatientVitalData().isEmpty()) {
                String[] coreVitals = {"BP", "HR", "SpO2", "Temp"};
                for (String k : coreVitals) {
                    String vitalValue = "-";
                    if (v.getPatientVitalData().containsKey(k)) {
                        Object obj = v.getPatientVitalData().get(k);
                        if (obj instanceof java.util.Map) {
                            java.util.Map<?, ?> map = (java.util.Map<?, ?>) obj;
                            if (map.containsKey("value") && map.get("value") != null) {
                                String val = map.get("value").toString();
                                if (!val.trim().isEmpty() && !val.equals("-")) {
                                    vitalValue = val;
                                }
                            }
                        }
                    }
                    if (vitalValue.equals("-")) {
                        if (k.equals("BP")) vitalValue = "-- / --";
                        if (k.equals("HR")) vitalValue = "-- bpm";
                        if (k.equals("SpO2")) vitalValue = "--%";
                        if (k.equals("Temp")) vitalValue = "-- °C";
                    }
                    vitals.add(new PatientProfileDTO.VitalItem(k, vitalValue, ""));
                }
            } else {
                setEmptyVitals(vitals);
            }
        } else {
            setEmptyVitals(vitals);
        }
        
        profile.setVitals(vitals);
        
        PatientProfileDTO.Tables tables = new PatientProfileDTO.Tables();
        
        List<PatientProfileDTO.ConditionItem> conditionItems = new ArrayList<>();
        if (p.getPatientRegistrationChronic() != null && !p.getPatientRegistrationChronic().trim().isEmpty()) {
            PatientProfileDTO.ConditionItem ci = new PatientProfileDTO.ConditionItem();
            ci.setCondition(p.getPatientRegistrationChronic());
            ci.setStatus("Active");
            ci.setSince("-");
            ci.setNotes("From Registration");
            conditionItems.add(ci);
        }
        
        if (conditionItems.isEmpty()) {
             PatientProfileDTO.ConditionItem ci = new PatientProfileDTO.ConditionItem();
             ci.setCondition("No Known Conditions");
             ci.setStatus("-");
             ci.setSince("-");
             ci.setNotes("-");
             conditionItems.add(ci);
        }
        
        tables.setConditions(conditionItems);
        
        tables.setMedications(new ArrayList<>());
        
        List<PatientProfileDTO.AllergyItem> allergyItems = new ArrayList<>();
        if (p.getPatientRegistrationAllergies() != null && !p.getPatientRegistrationAllergies().trim().isEmpty()) {
            PatientProfileDTO.AllergyItem ai = new PatientProfileDTO.AllergyItem();
            ai.setAllergy(p.getPatientRegistrationAllergies());
            ai.setType("-");
            ai.setSeverity("-");
            ai.setReaction("-");
            ai.setRecordedOn("-");
            allergyItems.add(ai);
        }
        tables.setAllergies(allergyItems);
        
        profile.setTables(tables);
        
        PatientProfileDTO.ClinicalJourney journey = new PatientProfileDTO.ClinicalJourney();
        journey.setConsultations(new PatientProfileDTO.JourneyMetric("-", "-"));
        journey.setTreatmentChanges(new PatientProfileDTO.JourneyMetric("-", "0"));
        journey.setImportantEvents(new PatientProfileDTO.JourneyMetric("-", "0"));
        profile.setClinicalJourney(journey);
        
        return profile;
    }

    private void setEmptyVitals(List<PatientProfileDTO.VitalItem> vitals) {
        vitals.add(new PatientProfileDTO.VitalItem("BP", "-- / --", ""));
        vitals.add(new PatientProfileDTO.VitalItem("HR", "-- bpm", ""));
        vitals.add(new PatientProfileDTO.VitalItem("SpO2", "--%", ""));
        vitals.add(new PatientProfileDTO.VitalItem("Temp", "-- °C", ""));
    }
    
    private void populateDemographicsVitals(PatientVisitChartDTO dto, Long patientId) {
        List<PatientVitals> latestVitalsList = vitalsRepository.findByPatientIdOrderByPatientVitalsCreatedOnDesc(patientId);
        java.util.Map<String, Object> vitalData = null;
        if (latestVitalsList != null && !latestVitalsList.isEmpty()) {
            vitalData = latestVitalsList.get(0).getPatientVitalData();
        }

        dto.setPatientHeight(extractMetricDate(vitalData, "Height").getValue());
        dto.setPatientWeight(extractMetricDate(vitalData, "Weight").getValue());
        dto.setPatientBmi(extractMetricDate(vitalData, "BMI").getValue());
        dto.setPatientBloodGroup(extractMetricDate(vitalData, "Blood Group").getValue());
    }

}
