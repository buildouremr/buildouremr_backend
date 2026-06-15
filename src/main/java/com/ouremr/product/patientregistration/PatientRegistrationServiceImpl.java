package com.ouremr.product.patientregistration;

import com.ouremr.product.dto.ExistingPatientsDTO;
import com.ouremr.product.tables.PatientRegistration;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientRegistrationServiceImpl implements PatientRegistrationService{

    @Autowired
    EntityManager em;

    @Override
    public List<ExistingPatientsDTO> getPatients(String patientName) {

        List<ExistingPatientsDTO> response = new ArrayList<>();

        try {

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

            Root<PatientRegistration> patient =
                    cq.from(PatientRegistration.class);

            Expression<String> fullName =
                    cb.concat(
                            cb.concat(
                                    cb.coalesce(patient.get("patientRegistrationFirstName"), ""),
                                    " "
                            ),
                            cb.concat(
                                    cb.coalesce(patient.get("patientRegistrationMiddleName"), ""),
                                    cb.concat(
                                            " ",
                                            cb.coalesce(patient.get("patientRegistrationLastName"), "")
                                    )
                            )
                    );

            cq.multiselect(
                    patient.get("patientRegistrationId"),      // 0
                    fullName,                                 // 1
                    patient.get("patientRegistrationDob"),    // 2
                    patient.get("patientRegistrationMobileNo"), // 3
                    patient.get("patientRegistrationSex")     // 4
            );

            List<Predicate> predicates = new ArrayList<>();

            if (patientName != null && !patientName.trim().isEmpty()) {

                String normalized =
                        patientName.trim().replace(",", " ");

                String[] tokens = normalized.split("\\s+");

                for (String token : tokens) {

                    String search = "%" + token.toLowerCase() + "%";

                    predicates.add(
                            cb.or(
                                    cb.like(
                                            cb.lower(
                                                    cb.coalesce(
                                                            patient.get("patientRegistrationFirstName"),
                                                            ""
                                                    )
                                            ),
                                            search
                                    ),
                                    cb.like(
                                            cb.lower(
                                                    cb.coalesce(
                                                            patient.get("patientRegistrationMiddleName"),
                                                            ""
                                                    )
                                            ),
                                            search
                                    ),
                                    cb.like(
                                            cb.lower(
                                                    cb.coalesce(
                                                            patient.get("patientRegistrationLastName"),
                                                            ""
                                                    )
                                            ),
                                            search
                                    )
                            )
                    );
                }

                cq.where(cb.and(predicates.toArray(new Predicate[0])));
            }

            cq.orderBy(
                    cb.asc(patient.get("patientRegistrationFirstName"))
            );

            List<Object[]> results =
                    em.createQuery(cq)
                            .setMaxResults(20)
                            .getResultList();

            for (Object[] obj : results) {

                ExistingPatientsDTO dto =
                        new ExistingPatientsDTO();

                dto.setPatientId(
                        obj[0] != null
                                ? ((Number) obj[0]).intValue()
                                : null
                );

                dto.setPatientName(
                        obj[1] != null
                                ? obj[1].toString().trim().replaceAll("\\s+", " ")
                                : ""
                );

                dto.setPatientDob(
                        obj[2] != null
                                ? obj[2].toString()
                                : ""
                );

                dto.setPatientMobileNo(
                        obj[3] != null
                                ? obj[3].toString()
                                : ""
                );

                dto.setPatientGender(
                        obj[4] != null
                                ? obj[4].toString()
                                : ""
                );

                response.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
}
