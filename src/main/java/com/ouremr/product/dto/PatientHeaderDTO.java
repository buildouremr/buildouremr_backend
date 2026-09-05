package com.ouremr.product.dto;

public class PatientHeaderDTO {
    private String name;
    private String gender;
    private String dob;
    private String age;
    public static class MetricDate {
        private String value;
        private String date;

        public MetricDate() {}

        public MetricDate(String value, String date) {
            this.value = value;
            this.date = date;
        }

        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }

        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }
    }

    private MetricDate height;
    private MetricDate weight;
    private MetricDate bmi;
    private MetricDate bloodGroup;
    private String lastVisit;
    private String nextVisit;
    private String insurance;
    private String allergies;
    private String chronicConditions;
    private String riskFactors;
    private java.util.Map<String, Object> allergiesData;
    private java.util.Map<String, Object> chronicConditionsData;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    
    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }
    
    public String getAge() { return age; }
    public void setAge(String age) { this.age = age; }
    
    public MetricDate getHeight() { return height; }
    public void setHeight(MetricDate height) { this.height = height; }
    
    public MetricDate getWeight() { return weight; }
    public void setWeight(MetricDate weight) { this.weight = weight; }
    
    public MetricDate getBmi() { return bmi; }
    public void setBmi(MetricDate bmi) { this.bmi = bmi; }
    
    public MetricDate getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(MetricDate bloodGroup) { this.bloodGroup = bloodGroup; }
    
    public String getLastVisit() { return lastVisit; }
    public void setLastVisit(String lastVisit) { this.lastVisit = lastVisit; }
    
    public String getNextVisit() { return nextVisit; }
    public void setNextVisit(String nextVisit) { this.nextVisit = nextVisit; }
    
    public String getInsurance() { return insurance; }
    public void setInsurance(String insurance) { this.insurance = insurance; }
    
    public String getAllergies() { return allergies; }
    public void setAllergies(String allergies) { this.allergies = allergies; }
    
    public String getChronicConditions() { return chronicConditions; }
    public void setChronicConditions(String chronicConditions) { this.chronicConditions = chronicConditions; }
    
    public String getRiskFactors() { return riskFactors; }
    public void setRiskFactors(String riskFactors) { this.riskFactors = riskFactors; }

    public java.util.Map<String, Object> getAllergiesData() { return allergiesData; }
    public void setAllergiesData(java.util.Map<String, Object> allergiesData) { this.allergiesData = allergiesData; }

    public java.util.Map<String, Object> getChronicConditionsData() { return chronicConditionsData; }
    public void setChronicConditionsData(java.util.Map<String, Object> chronicConditionsData) { this.chronicConditionsData = chronicConditionsData; }
    
    private String id;
    private String status;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
