package com.ouremr.product.patientchart;

import com.ouremr.product.dto.PatientPrescriptionDTO;
import com.ouremr.product.dto.PatientVisitChartDTO;
import com.ouremr.product.dto.PatientVitalsDTO;
import com.ouremr.product.repositories.PatientPrescriptionRepository;
import com.ouremr.product.repositories.PatientRegistrationRepository;
import com.ouremr.product.repositories.PatientVisitChartRepository;
import com.ouremr.product.repositories.PatientVitalsRepository;
import com.ouremr.product.tables.*;
import com.ouremr.product.repositories.*;
import com.ouremr.product.dto.PatientChartDTO;
import com.ouremr.product.dto.PatientProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientChartServiceImpl implements PatientChartService {

    @Autowired
    private PatientVisitChartRepository chartRepository;

    @Autowired
    private PatientVitalsRepository vitalsRepository;

    @Autowired
    private PatientPrescriptionRepository prescriptionRepository;

    @Autowired
    private PatientRegistrationRepository patientRepository;
    
    @Autowired
    private EncounterRepository encounterRepository;

    @Override
    public PatientVisitChartDTO getChartByAppointmentId(Long appointmentId) {
        Optional<PatientVisitChart> chartOpt = chartRepository.findByPatientVisitChartAppointmentId(appointmentId);
        if (chartOpt.isPresent()) {
            return convertToDTO(chartOpt.get());
        }
        return null;
    }

    @Override
    @Transactional
    public PatientVisitChartDTO saveChart(PatientVisitChartDTO dto) {
        PatientVisitChart chart = new PatientVisitChart();
        if (dto.getPatientVisitChartId() != null) {
            chart = chartRepository.findById(dto.getPatientVisitChartId())
                    .orElseThrow(() -> new RuntimeException("Chart not found"));
            chart.setPatientVisitChartModifiedOn(LocalDateTime.now());
        } else {
            chart.setPatientVisitChartCreatedOn(LocalDateTime.now());
        }

        chart.setPatientVisitChartPatientId(dto.getPatientId());
        chart.setPatientVisitChartAppointmentId(dto.getAppointmentId());
        chart.setPatientVisitChartReasonForVisit(dto.getReasonForVisit());
        chart.setPatientVisitChartSymptoms(dto.getSymptoms());
        chart.setPatientVisitChartExamination(dto.getExamination());
        chart.setPatientVisitChartDiagnosisTests(dto.getDiagnosisTests());
        chart.setPatientVisitChartDiagnosisNotes(dto.getDiagnosisNotes());
        chart.setPatientVisitChartTreatmentPlan(dto.getTreatmentPlan());
        chart.setPatientVisitChartAdvice(dto.getAdvice());
        chart.setPatientVisitChartStatus(dto.getStatus());

        PatientVisitChart savedChart = chartRepository.save(chart);

        // Handle Vitals
        if (dto.getVitals() != null && !dto.getVitals().isEmpty()) {
            for (PatientVitalsDTO vDto : dto.getVitals()) {
                PatientVitals vitals = new PatientVitals();
                if (vDto.getPatientVitalsId() != null) {
                    vitals = vitalsRepository.findById(vDto.getPatientVitalsId())
                            .orElse(new PatientVitals());
                    vitals.setPatientVitalsModifiedOn(LocalDateTime.now());
                } else {
                    vitals.setPatientVitalsCreatedOn(LocalDateTime.now());
                }
                vitals.setPatientVisitChartId(savedChart.getPatientVisitChartId());
                vitals.setVitalHeader(vDto.getVitalHeader());
                vitals.setVitalData(vDto.getVitalData());
                vitalsRepository.save(vitals);
            }
        }

        // Handle Prescriptions
        if (dto.getPrescriptions() != null) {
            // Very simple approach: delete old and insert new, or update existing. Let's delete and re-insert for MVP simplicity, or update if IDs exist.
            prescriptionRepository.deleteByPatientVisitChartId(savedChart.getPatientVisitChartId());
            
            for (PatientPrescriptionDTO pDto : dto.getPrescriptions()) {
                PatientPrescription p = new PatientPrescription();
                p.setPatientVisitChartId(savedChart.getPatientVisitChartId());
                p.setMedicationId(pDto.getMedicationId());
                p.setPatientPrescriptionDrugName(pDto.getDrugName());
                p.setPatientPrescriptionFrequency(pDto.getFrequency());
                p.setPatientPrescriptionDuration(pDto.getDuration());
                p.setPatientPrescriptionInstruction(pDto.getInstruction());
                p.setPatientPrescriptionCreatedOn(LocalDateTime.now());
                prescriptionRepository.save(p);
            }
        }

        return convertToDTO(savedChart);
    }

    @Override
    public List<PatientVisitChartDTO> getPatientChartHistory(Long patientId) {
        List<PatientVisitChart> charts = chartRepository.findByPatientVisitChartPatientIdOrderByPatientVisitChartCreatedOnDesc(patientId);
        return charts.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public PatientVisitChartDTO getPatientSnapshot(Long patientId) {
        PatientVisitChartDTO dto = new PatientVisitChartDTO();
        dto.setPatientId(patientId);
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
            dto.setPatientBloodGroup("-");
            dto.setPatientHeight("-"); // Populated by helper below
            dto.setPatientWeight("-"); // Populated by helper below
            dto.setPatientBmi("-");    // Populated by helper below
            dto.setPatientAllergies(p.getPatientRegistrationAllergies());
            dto.setPatientChronicConditions(p.getPatientRegistrationChronic());
            dto.setPatientRiskFactors(p.getPatientRegistrationRiskFactors());
            dto.setPatientInsurance(p.getPatientRegistrationInsuranceName());
        }
        
        // Carry forward previous vitals if they exist
        List<PatientVisitChart> pastCharts = chartRepository.findByPatientVisitChartPatientIdOrderByPatientVisitChartCreatedOnDesc(patientId);
        for (PatientVisitChart past : pastCharts) {
            List<PatientVitals> pastVitals = vitalsRepository.findByPatientVisitChartId(past.getPatientVisitChartId());
            if (pastVitals != null && !pastVitals.isEmpty()) {
                List<PatientVitalsDTO> vDtoList = new ArrayList<>();
                for (PatientVitals v : pastVitals) {
                    PatientVitalsDTO vDto = new PatientVitalsDTO();
                    vDto.setPatientVitalsId(null); // Treat as new for the new encounter
                    vDto.setVitalHeader(v.getVitalHeader());
                    vDto.setVitalData(v.getVitalData());
                    vDtoList.add(vDto);
                }
                dto.setVitals(vDtoList);
                break;
            }
        }
        
        populateDemographicsVitals(dto, patientId);
        
        return dto;
    }

    private PatientVisitChartDTO convertToDTO(PatientVisitChart chart) {
        PatientVisitChartDTO dto = new PatientVisitChartDTO();
        dto.setPatientVisitChartId(chart.getPatientVisitChartId());
        dto.setPatientId(chart.getPatientVisitChartPatientId());
        dto.setAppointmentId(chart.getPatientVisitChartAppointmentId());
        dto.setReasonForVisit(chart.getPatientVisitChartReasonForVisit());
        dto.setSymptoms(chart.getPatientVisitChartSymptoms());
        dto.setExamination(chart.getPatientVisitChartExamination());
        dto.setDiagnosisTests(chart.getPatientVisitChartDiagnosisTests());
        dto.setDiagnosisNotes(chart.getPatientVisitChartDiagnosisNotes());
        dto.setTreatmentPlan(chart.getPatientVisitChartTreatmentPlan());
        dto.setAdvice(chart.getPatientVisitChartAdvice());
        dto.setStatus(chart.getPatientVisitChartStatus());

        Optional<PatientRegistration> patientOpt = patientRepository.findById(chart.getPatientVisitChartPatientId());
        if (patientOpt.isPresent()) {
            PatientRegistration p = patientOpt.get();
            dto.setPatientName(p.getPatientRegistrationFirstName() + " " + (p.getPatientRegistrationLastName() != null ? p.getPatientRegistrationLastName() : ""));
            dto.setPatientGender(p.getPatientRegistrationSex());
            if (p.getPatientRegistrationDob() != null) {
                dto.setPatientDob(p.getPatientRegistrationDob().toString());
                int age = Period.between(p.getPatientRegistrationDob(), java.time.LocalDate.now()).getYears();
                dto.setPatientAge(String.valueOf(age));
            }
            dto.setPatientBloodGroup("-");
            dto.setPatientHeight("-");
            dto.setPatientWeight("-");
            dto.setPatientBmi("-");
            dto.setPatientAllergies(p.getPatientRegistrationAllergies());
            dto.setPatientChronicConditions(p.getPatientRegistrationChronic());
            dto.setPatientRiskFactors(p.getPatientRegistrationRiskFactors());
            dto.setPatientInsurance(p.getPatientRegistrationInsuranceName());
        }

        List<PatientVitals> vitalsList = vitalsRepository.findByPatientVisitChartId(chart.getPatientVisitChartId());
        if (vitalsList != null && !vitalsList.isEmpty()) {
            List<PatientVitalsDTO> vDtoList = new ArrayList<>();
            for (PatientVitals v : vitalsList) {
                PatientVitalsDTO vDto = new PatientVitalsDTO();
                vDto.setPatientVitalsId(v.getPatientVitalsId());
                vDto.setPatientVisitChartId(v.getPatientVisitChartId());
                vDto.setVitalHeader(v.getVitalHeader());
                vDto.setVitalData(v.getVitalData());
                vDtoList.add(vDto);
            }
            dto.setVitals(vDtoList);
        }
        
        if (dto.getVitals() == null || dto.getVitals().isEmpty()) {
            List<PatientVisitChart> pastCharts = chartRepository.findByPatientVisitChartPatientIdOrderByPatientVisitChartCreatedOnDesc(chart.getPatientVisitChartPatientId());
            for (PatientVisitChart past : pastCharts) {
                // Skip the current chart we are looking at
                if (past.getPatientVisitChartId().equals(chart.getPatientVisitChartId())) continue;
                
                List<PatientVitals> pastVitals = vitalsRepository.findByPatientVisitChartId(past.getPatientVisitChartId());
                if (pastVitals != null && !pastVitals.isEmpty()) {
                    List<PatientVitalsDTO> vDtoList = new ArrayList<>();
                    for (PatientVitals v : pastVitals) {
                        PatientVitalsDTO vDto = new PatientVitalsDTO();
                        vDto.setPatientVitalsId(null); // New vital for current chart
                        vDto.setPatientVisitChartId(chart.getPatientVisitChartId());
                        vDto.setVitalHeader(v.getVitalHeader());
                        vDto.setVitalData(v.getVitalData());
                        vDtoList.add(vDto);
                    }
                    dto.setVitals(vDtoList);
                    break;
                }
            }
        }
        
        populateDemographicsVitals(dto, chart.getPatientVisitChartPatientId());

        List<PatientPrescription> prescriptions = prescriptionRepository.findByPatientVisitChartId(chart.getPatientVisitChartId());
        List<PatientPrescriptionDTO> pDtos = new ArrayList<>();
        for (PatientPrescription p : prescriptions) {
            PatientPrescriptionDTO pDto = new PatientPrescriptionDTO();
            pDto.setPatientPrescriptionId(p.getPatientPrescriptionId());
            pDto.setPatientVisitChartId(p.getPatientVisitChartId());
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

        return dto;
    }

    @Override
    public PatientProfileDTO getPatientProfile(Long patientId) {
        PatientProfileDTO profile = new PatientProfileDTO();
        
        Optional<PatientRegistration> pOpt = patientRepository.findById(patientId);
        if (!pOpt.isPresent()) return null;
        PatientRegistration p = pOpt.get();
        
        // 1. Header
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
        header.setBloodGroup(new PatientProfileDTO.MetricDate("Unknown", "-"));
        header.setHeight(new PatientProfileDTO.MetricDate("-", "-"));
        header.setWeight(new PatientProfileDTO.MetricDate("-", "-"));
        header.setBmi(new PatientProfileDTO.MetricDate("-", "-"));
        List<PatientVitals> allVitals = vitalsRepository.findByPatientIdOrderByPatientVitalsCreatedOnDesc(patientId);
        if (allVitals != null) {
            boolean hFound = false, wFound = false, bFound = false, bgFound = false;
            for (PatientVitals v : allVitals) {
                if (v.getVitalHeader() != null) {
                    if (!hFound && v.getVitalHeader().equalsIgnoreCase("Height")) {
                        header.setHeight(new PatientProfileDTO.MetricDate(v.getVitalData(), "-"));
                        hFound = true;
                    }
                    if (!wFound && v.getVitalHeader().equalsIgnoreCase("Weight")) {
                        header.setWeight(new PatientProfileDTO.MetricDate(v.getVitalData(), "-"));
                        wFound = true;
                    }
                    if (!bFound && v.getVitalHeader().equalsIgnoreCase("BMI")) {
                        header.setBmi(new PatientProfileDTO.MetricDate(v.getVitalData(), "-"));
                        bFound = true;
                    }
                    if (!bgFound && v.getVitalHeader().equalsIgnoreCase("Blood Group")) {
                        header.setBloodGroup(new PatientProfileDTO.MetricDate(v.getVitalData(), "-"));
                        bgFound = true;
                    }
                }
                if (hFound && wFound && bFound && bgFound) break;
            }
        }
        
        List<PatientVisitChart> history = chartRepository.findByPatientVisitChartPatientIdOrderByPatientVisitChartCreatedOnDesc(patientId);
        if (!history.isEmpty()) {
            LocalDateTime lastDate = history.get(0).getPatientVisitChartCreatedOn();
            header.setLastVisit(lastDate != null ? lastDate.toLocalDate().toString() : "Unknown");
        } else {
            header.setLastVisit("No visits");
        }
        profile.setHeader(header);
        
        // 2. Alerts
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
        
        // Loop through history to add diagnoses to alerts too
        for (PatientVisitChart chart : history) {
            if (chart.getPatientVisitChartDiagnosisNotes() != null && !chart.getPatientVisitChartDiagnosisNotes().trim().isEmpty()) {
                if (!conditions.contains("Active Condition: " + chart.getPatientVisitChartDiagnosisNotes())) {
                    conditions.add("Active Condition: " + chart.getPatientVisitChartDiagnosisNotes());
                }
            }
        }
        
        if (conditions.isEmpty()) {
            conditions.add("No Active Conditions");
        }
        
        alerts.setConditions(conditions);
        profile.setAlerts(alerts);
        
        // 3. Vitals
        List<PatientProfileDTO.VitalItem> vitals = new ArrayList<>();
        if (!history.isEmpty()) {
            List<PatientVitals> vList = vitalsRepository.findByPatientVisitChartId(history.get(0).getPatientVisitChartId());
            if (vList != null && !vList.isEmpty()) {
                for (PatientVitals v : vList) {
                    vitals.add(new PatientProfileDTO.VitalItem(
                        v.getVitalHeader() != null ? v.getVitalHeader() : "Unknown",
                        v.getVitalData() != null ? v.getVitalData() : "-",
                        "" // Units are implicit in the user's header/data usually, or leave empty
                    ));
                }
            } else {
                setEmptyVitals(vitals);
            }
        } else {
            setEmptyVitals(vitals);
        }
        
        profile.setVitals(vitals);
        
        // 4. Tables
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
        
        // Add diagnoses from past charts as conditions
        for (PatientVisitChart chart : history) {
            if (chart.getPatientVisitChartDiagnosisNotes() != null && !chart.getPatientVisitChartDiagnosisNotes().trim().isEmpty()) {
                PatientProfileDTO.ConditionItem ci = new PatientProfileDTO.ConditionItem();
                ci.setCondition(chart.getPatientVisitChartDiagnosisNotes());
                ci.setStatus("Active");
                ci.setSince(chart.getPatientVisitChartCreatedOn() != null ? chart.getPatientVisitChartCreatedOn().toLocalDate().toString() : "-");
                ci.setNotes("From Encounter");
                conditionItems.add(ci);
            }
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
        
        List<PatientProfileDTO.MedicationItem> medItems = new ArrayList<>();
        if (!history.isEmpty()) {
            List<PatientPrescription> prescriptions = prescriptionRepository.findByPatientVisitChartId(history.get(0).getPatientVisitChartId());
            for (PatientPrescription pres : prescriptions) {
                PatientProfileDTO.MedicationItem mi = new PatientProfileDTO.MedicationItem();
                mi.setName(pres.getMedication() != null ? pres.getMedication().getName() : pres.getPatientPrescriptionDrugName());
                mi.setType("-");
                mi.setFrequency(pres.getPatientPrescriptionFrequency() != null ? pres.getPatientPrescriptionFrequency() : "-");
                mi.setRoute("-");
                mi.setDuration(pres.getPatientPrescriptionDuration() != null ? pres.getPatientPrescriptionDuration() : "-");
                mi.setStartDate(pres.getPatientPrescriptionCreatedOn() != null ? pres.getPatientPrescriptionCreatedOn().toLocalDate().toString() : "-");
                mi.setPrescriber(pres.getPatientPrescriptionCreatedBy() != null ? pres.getPatientPrescriptionCreatedBy() : "-");
                medItems.add(mi);
            }
        }
        tables.setMedications(medItems);
        
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
        
        // 5. Clinical Journey
        PatientProfileDTO.ClinicalJourney journey = new PatientProfileDTO.ClinicalJourney();
        journey.setConsultations(new PatientProfileDTO.JourneyMetric(header.getLastVisit(), history.size() + " visits"));
        journey.setTreatmentChanges(new PatientProfileDTO.JourneyMetric("-", "0"));
        journey.setImportantEvents(new PatientProfileDTO.JourneyMetric("-", "0"));
        profile.setClinicalJourney(journey);
        
        return profile;
    }

    private void setEmptyVitals(List<PatientProfileDTO.VitalItem> vitals) {
        vitals.add(new PatientProfileDTO.VitalItem("BP", "-", "mmHg"));
        vitals.add(new PatientProfileDTO.VitalItem("HR", "-", "bpm"));
        vitals.add(new PatientProfileDTO.VitalItem("RR", "-", "/min"));
        vitals.add(new PatientProfileDTO.VitalItem("SpO₂", "-", "%"));
        vitals.add(new PatientProfileDTO.VitalItem("Temp", "-", "°F"));
    }
    
    private void populateDemographicsVitals(PatientVisitChartDTO dto, Long patientId) {
        List<PatientVitals> allVitals = vitalsRepository.findByPatientIdOrderByPatientVitalsCreatedOnDesc(patientId);
        boolean heightFound = false, weightFound = false, bmiFound = false, bgFound = false;
        
        if (allVitals != null) {
            for (PatientVitals v : allVitals) {
                if (v.getVitalHeader() != null) {
                    if (!heightFound && v.getVitalHeader().equalsIgnoreCase("Height")) {
                        dto.setPatientHeight(v.getVitalData());
                        heightFound = true;
                    }
                    if (!weightFound && v.getVitalHeader().equalsIgnoreCase("Weight")) {
                        dto.setPatientWeight(v.getVitalData());
                        weightFound = true;
                    }
                    if (!bmiFound && v.getVitalHeader().equalsIgnoreCase("BMI")) {
                        dto.setPatientBmi(v.getVitalData());
                        bmiFound = true;
                    }
                    if (!bgFound && v.getVitalHeader().equalsIgnoreCase("Blood Group")) {
                        dto.setPatientBloodGroup(v.getVitalData());
                        bgFound = true;
                    }
                }
                if (heightFound && weightFound && bmiFound && bgFound) break;
            }
        }
        
        if (!heightFound) dto.setPatientHeight("-");
        if (!weightFound) dto.setPatientWeight("-");
        if (!bmiFound) dto.setPatientBmi("-");
        if (!bgFound) dto.setPatientBloodGroup("-");
    }

    @Override
    @Transactional
    public void autoSaveSection(Long encounterId, String section, PatientChartDTO dto, Long userId) {
        Encounter encounter = encounterRepository.findById(encounterId)
            .orElseThrow(() -> new RuntimeException("Encounter not found"));
            
        PatientVisitChart chart = chartRepository.findByEncounterId(encounterId)
            .orElse(new PatientVisitChart());
            
        chart.setEncounterId(encounterId);
        
        // Ensure patientId is stored as requested
        if (dto.getPatientId() != null) {
            chart.setPatientVisitChartPatientId(dto.getPatientId());
        } else if (chart.getPatientVisitChartPatientId() == null) {
            chart.setPatientVisitChartPatientId(encounter.getPatient().getPatientRegistrationId()); // fallback to encounter patient
        }
        
        LocalDateTime now = LocalDateTime.now();
        if (chart.getPatientVisitChartId() == null) {
            chart.setPatientVisitChartCreatedOn(now);
        }
        chart.setPatientVisitChartModifiedOn(now);

        switch (section.toLowerCase()) {
            case "reasonforvisit":
                chart.setPatientVisitChartReasonForVisit(dto.getContent());
                break;
            case "symptoms":
                chart.setPatientVisitChartSymptoms(dto.getContent());
                break;
            case "examination":
                chart.setPatientVisitChartExamination(dto.getContent());
                break;
            case "diagnosis":
                // Map diagnosis to diagnosisNotes in PatientVisitChart
                chart.setPatientVisitChartDiagnosisNotes(dto.getContent());
                break;
            case "treatmentplan":
                chart.setPatientVisitChartTreatmentPlan(dto.getContent());
                break;
            case "advice":
                chart.setPatientVisitChartAdvice(dto.getContent());
                break;
            default:
                throw new IllegalArgumentException("Unknown section: " + section);
        }
        
        chartRepository.save(chart);
    }
    @Override
    @Transactional
    public void saveVitals(Long encounterId, List<PatientVitalsDTO> vitalsDtoList, Long userId) {
        Encounter encounter = encounterRepository.findById(encounterId)
            .orElseThrow(() -> new RuntimeException("Encounter not found"));
            
        PatientVisitChart chart = chartRepository.findByEncounterId(encounterId)
            .orElseGet(() -> {
                PatientVisitChart newChart = new PatientVisitChart();
                newChart.setEncounterId(encounterId);
                newChart.setPatientVisitChartPatientId(encounter.getPatient().getPatientRegistrationId());
                newChart.setPatientVisitChartCreatedOn(LocalDateTime.now());
                return chartRepository.save(newChart);
            });
            
        // First delete existing vitals for this chart
        List<PatientVitals> existing = vitalsRepository.findByPatientVisitChartId(chart.getPatientVisitChartId());
        vitalsRepository.deleteAll(existing);
        
        if (vitalsDtoList != null) {
            for (PatientVitalsDTO vDto : vitalsDtoList) {
                PatientVitals vitals = new PatientVitals();
                vitals.setPatientVisitChartId(chart.getPatientVisitChartId());
                vitals.setEncounterId(encounterId);
                vitals.setPatientId(chart.getPatientVisitChartPatientId());
                vitals.setVitalHeader(vDto.getVitalHeader());
                vitals.setVitalData(vDto.getVitalData());
                vitals.setPatientVitalsCreatedOn(LocalDateTime.now());
                vitalsRepository.save(vitals);
            }
        }
    }
}
