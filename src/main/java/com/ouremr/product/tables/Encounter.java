package com.ouremr.product.tables;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "encounter")
public class Encounter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "encounter_id")
    private Long encounterId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "encounter_patient_id", referencedColumnName = "patient_registration_id", nullable = false)
    private PatientRegistration patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "encounter_location_id", referencedColumnName = "location_id")
    private Location encounterLocation;

    @Column(name = "encounter_by")
    private Long encounterBy;

    @Column(name = "encounter_created_by", nullable = false)
    private Long encounterCreatedBy;

    @Column(name = "encounter_modified_by", nullable = false)
    private Long encounterModifiedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "encounter_created_on", nullable = false)
    private Date encounterCreatedOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "encounter_modified_on", nullable = false)
    private Date encounterModifiedOn;

    @Column(name = "encounter_is_checkout", nullable = false)
    private Boolean encounterIsCheckout = false;

    @Column(name = "encounter_checkout_by")
    private Long encounterCheckoutBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "encounter_checkout_on")
    private Date encounterCheckoutOn;

    @Column(name = "encounter_iscompleted", nullable = false)
    private Boolean encounterIsCompleted = false;

    @Column(name = "encounter_signedby")
    private Long encounterSignedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "encounter_completed_on")
    private Date encounterCompletedOn;

    @Column(name = "encounter_note_name")
    private String encounterNoteName;

    public Long getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(Long encounterId) {
        this.encounterId = encounterId;
    }

    public PatientRegistration getPatient() {
        return patient;
    }

    public void setPatient(PatientRegistration patient) {
        this.patient = patient;
    }

    public Location getEncounterLocation() {
        return encounterLocation;
    }

    public void setEncounterLocation(Location encounterLocation) {
        this.encounterLocation = encounterLocation;
    }

    public Long getEncounterBy() {
        return encounterBy;
    }

    public void setEncounterBy(Long encounterBy) {
        this.encounterBy = encounterBy;
    }

    public Long getEncounterCreatedBy() {
        return encounterCreatedBy;
    }

    public void setEncounterCreatedBy(Long encounterCreatedBy) {
        this.encounterCreatedBy = encounterCreatedBy;
    }

    public Long getEncounterModifiedBy() {
        return encounterModifiedBy;
    }

    public void setEncounterModifiedBy(Long encounterModifiedBy) {
        this.encounterModifiedBy = encounterModifiedBy;
    }

    public Date getEncounterCreatedOn() {
        return encounterCreatedOn;
    }

    public void setEncounterCreatedOn(Date encounterCreatedOn) {
        this.encounterCreatedOn = encounterCreatedOn;
    }

    public Date getEncounterModifiedOn() {
        return encounterModifiedOn;
    }

    public void setEncounterModifiedOn(Date encounterModifiedOn) {
        this.encounterModifiedOn = encounterModifiedOn;
    }

    public Boolean getEncounterIsCheckout() {
        return encounterIsCheckout;
    }

    public void setEncounterIsCheckout(Boolean encounterIsCheckout) {
        this.encounterIsCheckout = encounterIsCheckout;
    }

    public Long getEncounterCheckoutBy() {
        return encounterCheckoutBy;
    }

    public void setEncounterCheckoutBy(Long encounterCheckoutBy) {
        this.encounterCheckoutBy = encounterCheckoutBy;
    }

    public Date getEncounterCheckoutOn() {
        return encounterCheckoutOn;
    }

    public void setEncounterCheckoutOn(Date encounterCheckoutOn) {
        this.encounterCheckoutOn = encounterCheckoutOn;
    }

    public Boolean getEncounterIsCompleted() {
        return encounterIsCompleted;
    }

    public void setEncounterIsCompleted(Boolean encounterIsCompleted) {
        this.encounterIsCompleted = encounterIsCompleted;
    }

    public Long getEncounterSignedBy() {
        return encounterSignedBy;
    }

    public void setEncounterSignedBy(Long encounterSignedBy) {
        this.encounterSignedBy = encounterSignedBy;
    }

    public Date getEncounterCompletedOn() {
        return encounterCompletedOn;
    }

    public void setEncounterCompletedOn(Date encounterCompletedOn) {
        this.encounterCompletedOn = encounterCompletedOn;
    }

    public String getEncounterNoteName() {
        return encounterNoteName;
    }

    public void setEncounterNoteName(String encounterNoteName) {
        this.encounterNoteName = encounterNoteName;
    }
}
