package com.ouremr.product.dto;

import java.util.List;

public class PatientProfileDTO {
    private Header header;
    private Alerts alerts;
    private List<VitalItem> vitals;
    private Tables tables;
    private ClinicalJourney clinicalJourney;

    public Header getHeader() { return header; }
    public void setHeader(Header header) { this.header = header; }
    public Alerts getAlerts() { return alerts; }
    public void setAlerts(Alerts alerts) { this.alerts = alerts; }
    public List<VitalItem> getVitals() { return vitals; }
    public void setVitals(List<VitalItem> vitals) { this.vitals = vitals; }
    public Tables getTables() { return tables; }
    public void setTables(Tables tables) { this.tables = tables; }
    public ClinicalJourney getClinicalJourney() { return clinicalJourney; }
    public void setClinicalJourney(ClinicalJourney clinicalJourney) { this.clinicalJourney = clinicalJourney; }

    public static class MetricDate {
        private String value;
        private String date;
        public MetricDate() {}
        public MetricDate(String value, String date) { this.value = value; this.date = date; }
        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }
        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }
    }

    public static class MetricUnit {
        private String value;
        private String unit;
        public MetricUnit() {}
        public MetricUnit(String value, String unit) { this.value = value; this.unit = unit; }
        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }
        public String getUnit() { return unit; }
        public void setUnit(String unit) { this.unit = unit; }
    }

    public static class JourneyMetric {
        private String last;
        private String total;
        public JourneyMetric() {}
        public JourneyMetric(String last, String total) { this.last = last; this.total = total; }
        public String getLast() { return last; }
        public void setLast(String last) { this.last = last; }
        public String getTotal() { return total; }
        public void setTotal(String total) { this.total = total; }
    }

    public static class Header {
        private String name;
        private String status;
        private String id;
        private String gender;
        private String age;
        private String dob;
        private MetricDate bloodGroup;
        private MetricDate height;
        private MetricDate weight;
        private MetricDate bmi;
        private String lastVisit;

        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getGender() { return gender; }
        public void setGender(String gender) { this.gender = gender; }
        public String getAge() { return age; }
        public void setAge(String age) { this.age = age; }
        public String getDob() { return dob; }
        public void setDob(String dob) { this.dob = dob; }
        public MetricDate getBloodGroup() { return bloodGroup; }
        public void setBloodGroup(MetricDate bloodGroup) { this.bloodGroup = bloodGroup; }
        public MetricDate getHeight() { return height; }
        public void setHeight(MetricDate height) { this.height = height; }
        public MetricDate getWeight() { return weight; }
        public void setWeight(MetricDate weight) { this.weight = weight; }
        public MetricDate getBmi() { return bmi; }
        public void setBmi(MetricDate bmi) { this.bmi = bmi; }
        public String getLastVisit() { return lastVisit; }
        public void setLastVisit(String lastVisit) { this.lastVisit = lastVisit; }
    }

    public static class Alerts {
        private List<String> allergies;
        private List<String> conditions;
        public List<String> getAllergies() { return allergies; }
        public void setAllergies(List<String> allergies) { this.allergies = allergies; }
        public List<String> getConditions() { return conditions; }
        public void setConditions(List<String> conditions) { this.conditions = conditions; }
    }

    public static class VitalItem {
        private String label;
        private String value;
        private String unit;
        public VitalItem() {}
        public VitalItem(String label, String value, String unit) {
            this.label = label; this.value = value; this.unit = unit;
        }
        public String getLabel() { return label; }
        public void setLabel(String label) { this.label = label; }
        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }
        public String getUnit() { return unit; }
        public void setUnit(String unit) { this.unit = unit; }
    }

    public static class Tables {
        private List<ConditionItem> conditions;
        private List<MedicationItem> medications;
        private List<AllergyItem> allergies;
        
        public List<ConditionItem> getConditions() { return conditions; }
        public void setConditions(List<ConditionItem> conditions) { this.conditions = conditions; }
        public List<MedicationItem> getMedications() { return medications; }
        public void setMedications(List<MedicationItem> medications) { this.medications = medications; }
        public List<AllergyItem> getAllergies() { return allergies; }
        public void setAllergies(List<AllergyItem> allergies) { this.allergies = allergies; }
    }

    public static class JourneyItem {
        private String date;
        private String type;
        private String provider;
        private Boolean isCompleted;
        
        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getProvider() { return provider; }
        public void setProvider(String provider) { this.provider = provider; }
        public Boolean getIsCompleted() { return isCompleted; }
        public void setIsCompleted(Boolean isCompleted) { this.isCompleted = isCompleted; }
    }

    public static class ClinicalJourney {
        private List<JourneyItem> items;
        
        public List<JourneyItem> getItems() { return items; }
        public void setItems(List<JourneyItem> items) { this.items = items; }
    }

    public static class ConditionItem {
        private String condition;
        private String status;
        private String since;
        private String notes;
        private String period;
        
        // getters & setters
        public String getCondition() { return condition; }
        public void setCondition(String condition) { this.condition = condition; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getSince() { return since; }
        public void setSince(String since) { this.since = since; }
        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }
        public String getPeriod() { return period; }
        public void setPeriod(String period) { this.period = period; }
    }

    public static class MedicationItem {
        private String name;
        private String type;
        private String frequency;
        private String route;
        private String duration;
        private String startDate;
        private String endDate;
        private String prescriber;
        private Boolean status;
        
        // getters & setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getFrequency() { return frequency; }
        public void setFrequency(String frequency) { this.frequency = frequency; }
        public String getRoute() { return route; }
        public void setRoute(String route) { this.route = route; }
        public String getDuration() { return duration; }
        public void setDuration(String duration) { this.duration = duration; }
        public String getStartDate() { return startDate; }
        public void setStartDate(String startDate) { this.startDate = startDate; }
        public String getEndDate() { return endDate; }
        public void setEndDate(String endDate) { this.endDate = endDate; }
        public String getPrescriber() { return prescriber; }
        public void setPrescriber(String prescriber) { this.prescriber = prescriber; }
        public Boolean getStatus() { return status; }
        public void setStatus(Boolean status) { this.status = status; }
    }

    public static class AllergyItem {
        private String allergy;
        private String type;
        private String severity;
        private String reaction;
        private String recordedOn;
        
        // getters & setters
        public String getAllergy() { return allergy; }
        public void setAllergy(String allergy) { this.allergy = allergy; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getSeverity() { return severity; }
        public void setSeverity(String severity) { this.severity = severity; }
        public String getReaction() { return reaction; }
        public void setReaction(String reaction) { this.reaction = reaction; }
        public String getRecordedOn() { return recordedOn; }
        public void setRecordedOn(String recordedOn) { this.recordedOn = recordedOn; }
    }
}
