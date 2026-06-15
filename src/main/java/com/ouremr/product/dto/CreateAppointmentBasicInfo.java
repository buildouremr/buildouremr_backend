package com.ouremr.product.dto;

import java.util.List;

public class CreateAppointmentBasicInfo {

    private List<DropdownDTO> providers;
    private List<DropdownDTO> appointmentTypes;

    public List<DropdownDTO> getProviders() {
        return providers;
    }

    public void setProviders(List<DropdownDTO> providers) {
        this.providers = providers;
    }

    public List<DropdownDTO> getAppointmentTypes() {
        return appointmentTypes;
    }

    public void setAppointmentTypes(List<DropdownDTO> appointmentTypes) {
        this.appointmentTypes = appointmentTypes;
    }
}