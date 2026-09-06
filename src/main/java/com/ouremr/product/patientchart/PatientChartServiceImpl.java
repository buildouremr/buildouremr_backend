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
    private com.ouremr.product.repositories.UserLoginRepository userLoginRepository;

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

    @Autowired
    private PatientAllergiesRepository patientAllergiesRepository;

    @Autowired
    private PatientChronicConditionsRepository patientChronicConditionsRepository;

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
                p.setPatientPrescriptionRoute(pDto.getRoute());
                
                String userId = dto.getEnteredBy() != null && !dto.getEnteredBy().trim().isEmpty() ? dto.getEnteredBy() : "1";
                if (p.getPatientPrescriptionId() == null) {
                    p.setPatientPrescriptionCreatedBy(userId);
                } else {
                    p.setPatientPrescriptionModifiedBy(userId);
                }
                
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
            header.setId("PT-" + p.getPatientRegistrationId());
            header.setStatus("Stable");
            header.setGender(p.getPatientRegistrationSex());
            if (p.getPatientRegistrationDob() != null) {
                header.setDob(p.getPatientRegistrationDob().toString());
                int age = Period.between(p.getPatientRegistrationDob(), java.time.LocalDate.now()).getYears();
                header.setAge(age + " yrs");
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
            
            // Try fetching from PatientAllergies table first
            List<PatientAllergies> paList = patientAllergiesRepository.findByPatientIdOrderByPatientAllergiesCreatedOnDesc(patientId);
            if (paList != null && !paList.isEmpty()) {
                java.util.Map<String, Object> aData = paList.get(0).getPatientAllergiesData();
                header.setAllergiesData(aData);
                // Maintain string representation for backward compatibility
                if (aData != null && !aData.isEmpty()) {
                    header.setAllergies(String.join(", ", aData.keySet()));
                } else {
                    header.setAllergies("");
                }
            } else {
                header.setAllergies(p.getPatientRegistrationAllergies());
            }

            // Try fetching from PatientChronicConditions table first
            Optional<PatientChronicConditions> pccOpt = patientChronicConditionsRepository.findFirstByPatientIdOrderByCreatedAtDesc(patientId);
            if (pccOpt.isPresent()) {
                java.util.Map<String, Object> cData = pccOpt.get().getPatientChronicConditionsData();
                header.setChronicConditionsData(cData);
                if (cData != null && !cData.isEmpty()) {
                    header.setChronicConditions(String.join(", ", cData.keySet()));
                } else {
                    header.setChronicConditions("");
                }
            } else {
                header.setChronicConditions(p.getPatientRegistrationChronic());
            }

            header.setRiskFactors(p.getPatientRegistrationRiskFactors());
            
            // Last Visit from encounters
            Optional<Encounter> lastEncOpt = encounterRepository.findLatestByPatientId(patientId);
            if (lastEncOpt.isPresent()) {
                java.util.Date lastDate = lastEncOpt.get().getEncounterCreatedOn();
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd MMM yyyy");
                header.setLastVisit(sdf.format(lastDate));
            } else {
                header.setLastVisit("-");
            }
            header.setNextVisit(null);
            return header;
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public PatientProfileDTO getPatientProfile(Long patientId) {
        PatientProfileDTO profile = new PatientProfileDTO();
        
        Optional<PatientRegistration> pOpt = patientRepository.findById(patientId);
        if (!pOpt.isPresent()) return null;
        PatientRegistration p = pOpt.get();
        
        // ─── HEADER ───────────────────────────────────────────────────────
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
        
        // Last Visit from encounters
        Optional<Encounter> lastEncOpt = encounterRepository.findLatestByPatientId(patientId);
        if (lastEncOpt.isPresent()) {
            java.util.Date lastDate = lastEncOpt.get().getEncounterCreatedOn();
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd MMM yyyy");
            header.setLastVisit(sdf.format(lastDate));
        } else {
            header.setLastVisit("-");
        }
        profile.setHeader(header);
        
        // ─── ALERTS (Allergy & Condition Pills) ──────────────────────────
        PatientProfileDTO.Alerts alerts = new PatientProfileDTO.Alerts();
        List<String> alertAllergies = new ArrayList<>();
        List<String> alertConditions = new ArrayList<>();
        
        // Read allergies from PatientAllergies table
        List<PatientAllergies> paList = patientAllergiesRepository.findByPatientIdOrderByPatientAllergiesCreatedOnDesc(patientId);
        java.util.Map<String, Object> allergiesData = null;
        if (paList != null && !paList.isEmpty()) {
            allergiesData = paList.get(0).getPatientAllergiesData();
        }
        
        if (allergiesData != null && !allergiesData.isEmpty()) {
            for (String allergyName : allergiesData.keySet()) {
                alertAllergies.add("Allergy: " + allergyName);
            }
        } else if (p.getPatientRegistrationAllergies() != null && !p.getPatientRegistrationAllergies().trim().isEmpty()) {
            for (String a : p.getPatientRegistrationAllergies().split(",")) {
                if (a.trim().length() > 0) {
                    alertAllergies.add("Allergy: " + a.trim());
                }
            }
        }
        alerts.setAllergies(alertAllergies);
        
        // Read chronic conditions from PatientChronicConditions table
        Optional<PatientChronicConditions> pccOpt = patientChronicConditionsRepository.findFirstByPatientIdOrderByCreatedAtDesc(patientId);
        java.util.Map<String, Object> chronicData = null;
        if (pccOpt.isPresent()) {
            chronicData = pccOpt.get().getPatientChronicConditionsData();
        }
        
        if (chronicData != null && !chronicData.isEmpty()) {
            for (java.util.Map.Entry<String, Object> entry : chronicData.entrySet()) {
                String condName = entry.getKey();
                String status = "Active";
                if (entry.getValue() instanceof java.util.Map) {
                    java.util.Map<?, ?> meta = (java.util.Map<?, ?>) entry.getValue();
                    if (meta.containsKey("status") && meta.get("status") != null) {
                        String s = meta.get("status").toString();
                        if ("inactive".equalsIgnoreCase(s)) {
                            continue; // Don't show inactive conditions in alert pills
                        }
                    }
                }
                alertConditions.add("Active Condition: " + condName);
            }
        } else if (p.getPatientRegistrationChronic() != null && !p.getPatientRegistrationChronic().trim().isEmpty()) {
            for (String c : p.getPatientRegistrationChronic().split(",")) {
                if (c.trim().length() > 0) {
                    alertConditions.add("Active Condition: " + c.trim());
                }
            }
        }
        alerts.setConditions(alertConditions);
        profile.setAlerts(alerts);
        
        // ─── VITALS ──────────────────────────────────────────────────────
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
        
        // ─── TABLES ──────────────────────────────────────────────────────
        PatientProfileDTO.Tables tables = new PatientProfileDTO.Tables();
        
        // ── Conditions & Problems ─────────────────────────────────────
        List<PatientProfileDTO.ConditionItem> conditionItems = new ArrayList<>();
        if (chronicData != null && !chronicData.isEmpty()) {
            for (java.util.Map.Entry<String, Object> entry : chronicData.entrySet()) {
                PatientProfileDTO.ConditionItem ci = new PatientProfileDTO.ConditionItem();
                ci.setCondition(entry.getKey());
                ci.setNotes("-");
                
                String condStatus = "Active";
                String condDate = "-";
                if (entry.getValue() instanceof java.util.Map) {
                    java.util.Map<?, ?> meta = (java.util.Map<?, ?>) entry.getValue();
                    if (meta.containsKey("date") && meta.get("date") != null) {
                        condDate = meta.get("date").toString();
                    }
                    if (meta.containsKey("status") && meta.get("status") != null) {
                        condStatus = meta.get("status").toString().substring(0, 1).toUpperCase() 
                                   + meta.get("status").toString().substring(1).toLowerCase();
                    }
                }
                ci.setStatus(condStatus);
                ci.setSince(condDate);
                conditionItems.add(ci);
            }
        } else if (p.getPatientRegistrationChronic() != null && !p.getPatientRegistrationChronic().trim().isEmpty()) {
            for (String c : p.getPatientRegistrationChronic().split(",")) {
                if (c.trim().length() > 0) {
                    PatientProfileDTO.ConditionItem ci = new PatientProfileDTO.ConditionItem();
                    ci.setCondition(c.trim());
                    ci.setStatus("Active");
                    ci.setSince("-");
                    ci.setNotes("-");
                    conditionItems.add(ci);
                }
            }
        }
        tables.setConditions(conditionItems);
        
        // ── Medications ───────────────────────────────────────────────
        List<PatientProfileDTO.MedicationItem> medicationItems = new ArrayList<>();
        List<PatientPrescription> prescriptions = prescriptionRepository.findActiveByPatientId(patientId);
        if (prescriptions != null) {
            java.text.SimpleDateFormat dateFmt = new java.text.SimpleDateFormat("dd MMM yyyy");
            for (PatientPrescription rx : prescriptions) {
                PatientProfileDTO.MedicationItem mi = new PatientProfileDTO.MedicationItem();
                mi.setName(rx.getPatientPrescriptionDrugName() != null ? rx.getPatientPrescriptionDrugName() : "-");
                mi.setFrequency(rx.getPatientPrescriptionFrequency() != null ? rx.getPatientPrescriptionFrequency() : "-");
                mi.setDuration(rx.getPatientPrescriptionDuration() != null ? rx.getPatientPrescriptionDuration() : "-");
                mi.setStatus(rx.getIsActive());
                
                // Get route and type from joined Medication entity
                if (rx.getMedication() != null) {
                    mi.setRoute(rx.getPatientPrescriptionRoute() != null ? rx.getPatientPrescriptionRoute() : (rx.getMedication().getRoute() != null ? rx.getMedication().getRoute() : "-"));
                    mi.setType(rx.getMedication().getForm() != null ? rx.getMedication().getForm() : "-");
                } else {
                    mi.setRoute(rx.getPatientPrescriptionRoute() != null ? rx.getPatientPrescriptionRoute() : "-");
                    mi.setType("-");
                }
                
                // Calculate start and end dates
                if (rx.getPatientPrescriptionCreatedOn() != null) {
                    java.time.LocalDateTime startLdt = rx.getPatientPrescriptionCreatedOn();
                    java.util.Date startDateObj = java.util.Date.from(startLdt.atZone(java.time.ZoneId.systemDefault()).toInstant());
                    mi.setStartDate(dateFmt.format(startDateObj));
                    
                    // Calculate end date from duration (e.g. "5 Days", "7 Days", "14 Days")
                    int durationDays = parseDurationDays(rx.getPatientPrescriptionDuration());
                    if (durationDays > 0) {
                        java.time.LocalDateTime endLdt = startLdt.plusDays(durationDays);
                        java.util.Date endDateObj = java.util.Date.from(endLdt.atZone(java.time.ZoneId.systemDefault()).toInstant());
                        mi.setEndDate(dateFmt.format(endDateObj));
                    } else {
                        mi.setEndDate("-");
                    }
                } else {
                    mi.setStartDate("-");
                    mi.setEndDate("-");
                }
                
                String prescriberName = "Unknown";
                if (rx.getPatientPrescriptionCreatedBy() != null) {
                    try {
                        Long uid = Long.parseLong(rx.getPatientPrescriptionCreatedBy());
                        java.util.Optional<com.ouremr.product.tables.UserLogin> uOpt = userLoginRepository.findById(uid);
                        if (uOpt.isPresent()) {
                            prescriberName = uOpt.get().getUserName();
                        } else {
                            prescriberName = rx.getPatientPrescriptionCreatedBy();
                        }
                    } catch (NumberFormatException e) {
                        prescriberName = rx.getPatientPrescriptionCreatedBy();
                    }
                }
                mi.setPrescriber(prescriberName);
                medicationItems.add(mi);
            }
        }
        tables.setMedications(medicationItems);
        
        // ── Allergies & Risks ─────────────────────────────────────────
        List<PatientProfileDTO.AllergyItem> allergyItems = new ArrayList<>();
        if (allergiesData != null && !allergiesData.isEmpty()) {
            for (java.util.Map.Entry<String, Object> entry : allergiesData.entrySet()) {
                PatientProfileDTO.AllergyItem ai = new PatientProfileDTO.AllergyItem();
                ai.setAllergy(entry.getKey());
                ai.setType("-");
                ai.setSeverity("-");
                ai.setReaction("-");
                
                if (entry.getValue() instanceof java.util.Map) {
                    java.util.Map<?, ?> meta = (java.util.Map<?, ?>) entry.getValue();
                    if (meta.containsKey("date") && meta.get("date") != null) {
                        ai.setRecordedOn(meta.get("date").toString());
                    } else {
                        ai.setRecordedOn("-");
                    }
                } else {
                    ai.setRecordedOn("-");
                }
                allergyItems.add(ai);
            }
        } else if (p.getPatientRegistrationAllergies() != null && !p.getPatientRegistrationAllergies().trim().isEmpty()) {
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
        
        // ─── CLINICAL JOURNEY ─────────────────────────────────────────
        PatientProfileDTO.ClinicalJourney journey = new PatientProfileDTO.ClinicalJourney();
        java.util.List<PatientProfileDTO.JourneyItem> journeyItems = new java.util.ArrayList<>();
        java.util.List<com.ouremr.product.tables.Encounter> encounters = encounterRepository.findAllByPatientIdDesc(patientId);
        
        java.text.SimpleDateFormat journeyFmt = new java.text.SimpleDateFormat("dd MMM yyyy");
        for (com.ouremr.product.tables.Encounter enc : encounters) {
            PatientProfileDTO.JourneyItem ji = new PatientProfileDTO.JourneyItem();
            ji.setDate(enc.getEncounterCreatedOn() != null ? journeyFmt.format(enc.getEncounterCreatedOn()) : "-");
            
            // Map the Note Name directly into Type dynamically
            ji.setType((enc.getEncounterNoteName() != null && !enc.getEncounterNoteName().trim().isEmpty()) ? enc.getEncounterNoteName() : "Consultation");
            
            // Map provider name (encounterBy or createdBy)
            String providerName = "Unknown";
            Long providerId = enc.getEncounterBy() != null ? enc.getEncounterBy() : enc.getEncounterCreatedBy();
            if (providerId != null) {
                java.util.Optional<com.ouremr.product.tables.UserLogin> uOpt = userLoginRepository.findById(providerId);
                if (uOpt.isPresent()) {
                    providerName = uOpt.get().getUserName();
                }
            }
            ji.setProvider(providerName);
            
            ji.setIsCompleted(enc.getEncounterIsCompleted() != null ? enc.getEncounterIsCompleted() : false);
            journeyItems.add(ji);
        }
        
        journey.setItems(journeyItems);
        profile.setClinicalJourney(journey);
        
        // ─── UNSIGNED CHART INFO ──────────────────────────────────────
        Optional<com.ouremr.product.tables.Encounter> activeEncOpt = encounterRepository.findActiveUnsignedByPatientId(patientId);
        PatientProfileDTO.UnsignedChartInfo unsignedInfo = new PatientProfileDTO.UnsignedChartInfo();
        if (activeEncOpt.isPresent()) {
            com.ouremr.product.tables.Encounter enc = activeEncOpt.get();
            unsignedInfo.setEncounterId(enc.getEncounterId());
            unsignedInfo.setHasUnsignedChart(true);
            
            java.util.Date encDate = enc.getEncounterModifiedOn() != null ? enc.getEncounterModifiedOn() : enc.getEncounterCreatedOn();
            if (encDate != null) {
                java.text.SimpleDateFormat fullFmt = new java.text.SimpleDateFormat("dd MMMM yyyy, HH:mm");
                java.text.SimpleDateFormat ampmFmt = new java.text.SimpleDateFormat("dd MMMM yyyy, hh:mm a");
                
                long daysAgo = java.time.temporal.ChronoUnit.DAYS.between(
                    encDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate(), 
                    java.time.LocalDate.now()
                );
                String daysAgoStr = daysAgo == 0 ? "today" : (daysAgo + " days ago");
                
                unsignedInfo.setDaysAgo(daysAgoStr);
                unsignedInfo.setFormattedDate(ampmFmt.format(encDate));
                unsignedInfo.setLastUpdated(fullFmt.format(encDate) + " (" + daysAgoStr + ")");
            } else {
                unsignedInfo.setLastUpdated("-");
                unsignedInfo.setFormattedDate("-");
                unsignedInfo.setDaysAgo("-");
            }
            
            String providerName = "Dr. Ashok";
            Long pId = enc.getEncounterBy() != null ? enc.getEncounterBy() : enc.getEncounterCreatedBy();
            if (pId != null) {
                Optional<com.ouremr.product.tables.UserLogin> uOpt = userLoginRepository.findById(pId);
                if (uOpt.isPresent()) {
                    providerName = uOpt.get().getUserName();
                }
            }
            unsignedInfo.setProviderName(providerName);
        } else {
            unsignedInfo.setHasUnsignedChart(false);
        }
        profile.setUnsignedChart(unsignedInfo);
        
        return profile;
    }

    /**
     * Parse duration string like "5 Days", "7 days", "14 Days" to integer days.
     * Returns 0 if parsing fails.
     */
    private int parseDurationDays(String duration) {
        if (duration == null || duration.trim().isEmpty()) return 0;
        try {
            String numStr = duration.replaceAll("[^0-9]", "");
            if (!numStr.isEmpty()) {
                return Integer.parseInt(numStr);
            }
        } catch (NumberFormatException e) {
            // ignore
        }
        return 0;
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

    @Override
    @Transactional
    public void savePatientAllergies(Long patientId, java.util.Map<String, Object> allergiesData) {
        PatientAllergies pa = new PatientAllergies();
        pa.setPatientId(patientId);
        pa.setPatientAllergiesData(allergiesData);
        pa.setPatientAllergiesCreatedOn(LocalDateTime.now());
        pa.setPatientAllergiesCreatedBy("1");
        patientAllergiesRepository.save(pa);
    }

    @Override
    @Transactional
    public void savePatientChronicConditions(Long patientId, java.util.Map<String, Object> chronicConditionsData) {
        try {
            PatientChronicConditions pcc = new PatientChronicConditions(patientId, chronicConditionsData);
            patientChronicConditionsRepository.save(pcc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
