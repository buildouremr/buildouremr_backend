package com.ouremr.product.tables;

import jakarta.persistence.*;

@Entity
@Table(name = "chronic_disease")
public class ChronicDisease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chronic_disease_id")
    private Long chronicDiseaseId;

    @Column(name = "chronic_disease_name")
    private String chronicDiseaseName;

    @Column(name = "chronic_disease_iaactive")
    private String chronicDiseaseIsActive;

    public Long getChronicDiseaseId() {
        return chronicDiseaseId;
    }

    public void setChronicDiseaseId(Long chronicDiseaseId) {
        this.chronicDiseaseId = chronicDiseaseId;
    }

    public String getChronicDiseaseName() {
        return chronicDiseaseName;
    }

    public void setChronicDiseaseName(String chronicDiseaseName) {
        this.chronicDiseaseName = chronicDiseaseName;
    }

    public String getChronicDiseaseIsActive() {
        return chronicDiseaseIsActive;
    }

    public void setChronicDiseaseIsActive(String chronicDiseaseIsActive) {
        this.chronicDiseaseIsActive = chronicDiseaseIsActive;
    }
}