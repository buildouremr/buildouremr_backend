package com.ouremr.product.tables;

import jakarta.persistence.*;

@Entity
@Table(name = "location")
public class Location {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "location_name", nullable = false)
    private String locationName;

    @Column(name = "location_is_active")
    private Boolean locationIsActive = true;

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Boolean getLocationIsActive() {
        return locationIsActive;
    }

    public void setLocationIsActive(Boolean locationIsActive) {
        this.locationIsActive = locationIsActive;
    }
}
