package com.ouremr.product.patientregistration;

import com.ouremr.product.dto.AppointmentStatusCountsDTO;
import com.ouremr.product.dto.CreatePatientDTO;
import com.ouremr.product.dto.DropdownDTO;
import com.ouremr.product.emrbean.EMRResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "http://localhost:3000")
public class PatientRegistrationController {

    @Autowired
    PatientRegistrationService patientRegistrationService;

    @GetMapping(value = "/getPatients")
    public EMRResponseBean getPatients(@RequestParam(value = "patientname") String patientName) {
        EMRResponseBean response = new EMRResponseBean();
        try {
            response.setData(patientRegistrationService.getPatients(patientName));
        } catch (Exception e) {
            response.setData("ERROR_FETCHING_SUMMARY");
        }
        return response;
    }

    @GetMapping(value = "/getAllPatients")
    public EMRResponseBean getAllPatients() {
        EMRResponseBean response = new EMRResponseBean();
        try {
            response.setData(patientRegistrationService.getAllPatients());
        } catch (Exception e) {
            response.setData("ERROR_FETCHING_SUMMARY");
        }

        return response;
    }

    @GetMapping("/getChronicDiseases")
    public EMRResponseBean getChronicDiseases() {
        EMRResponseBean response = new EMRResponseBean();
        try {
            response.setData(patientRegistrationService.getChronicDiseases());
        } catch (Exception e) {
            response.setData("ERROR_FETCHING_CHRONIC_DISEASES");
        }
        return response;
    }

    @PostMapping("/createNewPatient")
    public EMRResponseBean createNewPatient(@RequestBody CreatePatientDTO bean) {
        EMRResponseBean response = new EMRResponseBean();
        try {
            response.setData(patientRegistrationService.createNewPatient(bean));
        } catch (Exception e) {
            response.setData("ERROR_FETCHING_CHRONIC_DISEASES");
        }
        return response;
    }
}
