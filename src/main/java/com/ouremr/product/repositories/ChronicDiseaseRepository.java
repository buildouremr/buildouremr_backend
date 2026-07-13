package com.ouremr.product.repositories;

import com.ouremr.product.tables.ChronicDisease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChronicDiseaseRepository extends JpaRepository<ChronicDisease, Long> {

    List<ChronicDisease> findByChronicDiseaseIsActive(String active);
}