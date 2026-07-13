package com.ouremr.product.patientregistration;

import com.ouremr.product.dto.CreatePatientDTO;
import com.ouremr.product.dto.ExistingPatientsDTO;
import com.ouremr.product.dto.PatientDetailsDTO;
import com.ouremr.product.repositories.ChronicDiseaseRepository;
import com.ouremr.product.repositories.PatientRegistrationRepository;
import com.ouremr.product.tables.ChronicDisease;
import com.ouremr.product.tables.EmployeeProfile;
import com.ouremr.product.tables.PatientRegistration;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PatientRegistrationServiceImpl implements PatientRegistrationService{

    @Autowired
    PatientRegistrationRepository patientRegistrationRepository;

    @Autowired
    private ChronicDiseaseRepository chronicDiseaseRepository;

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

    @Override
    public List<PatientDetailsDTO> getAllPatients() {

        List<PatientDetailsDTO> response = new ArrayList<>();

        try {

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

            Root<PatientRegistration> patient =
                    cq.from(PatientRegistration.class);

            Root<EmployeeProfile> doctor =
                    cq.from(EmployeeProfile.class);

            cq.multiselect(
                    patient,
                    doctor.get("employeeProfileId"),

                    cb.concat(
                            cb.concat(
                                    cb.coalesce(
                                            doctor.get("employeeProfileFirstName"),
                                            ""),
                                    " "
                            ),
                            cb.concat(
                                    cb.coalesce(
                                            doctor.get("employeeProfileMiddleName"),
                                            ""),
                                    cb.concat(
                                            " ",
                                            cb.coalesce(
                                                    doctor.get("employeeProfileLastName"),
                                                    "")
                                    )
                            )
                    )
            );

            cq.where(
                    cb.or(
                            cb.equal(
                                    patient.get("patientRegistrationPrincipalDoctor"),
                                    doctor.get("employeeProfileId")
                            ),
                            cb.isNull(
                                    patient.get("patientRegistrationPrincipalDoctor")
                            )
                    )
            );

            cq.orderBy(
                    cb.asc(
                            patient.get("patientRegistrationId")
                    )
            );

            List<Object[]> results =
                    em.createQuery(cq)
                            .getResultList();

            Map<Long, String> chronicDiseaseMap =
                    chronicDiseaseRepository.findAll()
                            .stream()
                            .collect(Collectors.toMap(
                                    ChronicDisease::getChronicDiseaseId,
                                    ChronicDisease::getChronicDiseaseName
                            ));

            for (Object[] obj : results) {

                PatientRegistration patientEntity =
                        (PatientRegistration) obj[0];

                PatientDetailsDTO dto =
                        new PatientDetailsDTO();

                dto.setPatientRegistrationId(
                        patientEntity.getPatientRegistrationId());

                dto.setPatientRegistrationFirstName(
                        patientEntity.getPatientRegistrationFirstName());

                dto.setPatientRegistrationMiddleName(
                        patientEntity.getPatientRegistrationMiddleName());

                dto.setPatientRegistrationLastName(
                        patientEntity.getPatientRegistrationLastName());

                dto.setPatientRegistrationGuardianName(
                        patientEntity.getPatientRegistrationGuardianName());

                dto.setPatientRegistrationDob(
                        patientEntity.getPatientRegistrationDob());

                dto.setPatientRegistrationSex(
                        patientEntity.getPatientRegistrationSex());

                dto.setPatientRegistrationAddress(
                        patientEntity.getPatientRegistrationAddress());

                dto.setPatientRegistrationState(
                        patientEntity.getPatientRegistrationState());

                dto.setPatientRegistrationCity(
                        patientEntity.getPatientRegistrationCity());

                dto.setPatientRegistrationPincode(
                        patientEntity.getPatientRegistrationPincode());

                dto.setPatientRegistrationInsuranceName(
                        patientEntity.getPatientRegistrationInsuranceName());

                String chronicIds =
                        patientEntity.getPatientRegistrationChronic();

                String chronicNames = null;

                if (chronicIds != null && !chronicIds.trim().isEmpty()) {

                    chronicNames = Arrays.stream(chronicIds.split(","))
                            .map(String::trim)
                            .filter(s -> !s.isEmpty())
                            .map(Long::valueOf)
                            .map(chronicDiseaseMap::get)
                            .filter(Objects::nonNull)
                            .collect(Collectors.joining(", "));
                }

                dto.setPatientRegistrationChronic(chronicNames);

                dto.setPatientRegistrationCallReminder(
                        patientEntity.getPatientRegistrationCallReminder());

                dto.setPatientRegistrationTextReminder(
                        patientEntity.getPatientRegistrationTextReminder());

                dto.setPatientRegistrationMobileNo(
                        patientEntity.getPatientRegistrationMobileNo());

                dto.setPatientRegistrationOtherMobileNo(
                        patientEntity.getPatientRegistrationOtherMobileNo());

                dto.setPatientRegistrationImage(
                        patientEntity.getPatientRegistrationImage());

                dto.setPatientRegistrationBloodGroup(
                        patientEntity.getPatientRegistrationBloodGroup());

                dto.setPatientRegistrationEmailId(
                        patientEntity.getPatientRegistrationEmailId());

                dto.setPatientRegistrationActive(
                        patientEntity.getPatientRegistrationActive());

                dto.setPrincipalDoctorId(
                        obj[1] != null
                                ? ((Number) obj[1]).longValue()
                                : null);

                dto.setPrincipalDoctorName(
                        obj[2] != null
                                ? obj[2].toString().trim().replaceAll("\\s+", " ")
                                : null);

                response.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

        return response;
    }

    @Override
    public List<ChronicDisease> getChronicDiseases() {
        return chronicDiseaseRepository
                .findByChronicDiseaseIsActive("true");
    }

    @Override
    public Boolean createNewPatient(CreatePatientDTO bean) {
        return null;
    }

}
