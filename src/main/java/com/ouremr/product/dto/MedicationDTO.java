package com.ouremr.product.dto;

import java.time.LocalDateTime;

public class MedicationDTO {
    private Long id;
    private String name;
    private String genericName;
    private String brandName;
    private String form;
    private String strength;
    private String route;
    private String manufacturer;
    private String ndcCode;

    // Getters and Setters
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
}
