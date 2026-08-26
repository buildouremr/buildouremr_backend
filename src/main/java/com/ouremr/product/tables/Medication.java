package com.ouremr.product.tables;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "medication")
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "generic_name")
    private String genericName;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "form")
    private String form;

    @Column(name = "strength")
    private String strength;

    @Column(name = "route")
    private String route;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "ndc_code")
    private String ndcCode;

    @Column(name = "created_on", updatable = false)
    private LocalDateTime createdOn;

    @PrePersist
    protected void onCreate() {
        this.createdOn = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGenericName() { return genericName; }
    public void setGenericName(String genericName) { this.genericName = genericName; }

    public String getBrandName() { return brandName; }
    public void setBrandName(String brandName) { this.brandName = brandName; }

    public String getForm() { return form; }
    public void setForm(String form) { this.form = form; }

    public String getStrength() { return strength; }
    public void setStrength(String strength) { this.strength = strength; }

    public String getRoute() { return route; }
    public void setRoute(String route) { this.route = route; }

    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }

    public String getNdcCode() { return ndcCode; }
    public void setNdcCode(String ndcCode) { this.ndcCode = ndcCode; }

    public LocalDateTime getCreatedOn() { return createdOn; }
    public void setCreatedOn(LocalDateTime createdOn) { this.createdOn = createdOn; }
}
