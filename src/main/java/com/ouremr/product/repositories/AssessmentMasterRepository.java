package com.ouremr.product.repositories;

import com.ouremr.product.tables.AssessmentMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssessmentMasterRepository extends JpaRepository<AssessmentMaster, Long> {
    List<AssessmentMaster> findByNameContainingIgnoreCase(String name);
    List<AssessmentMaster> findTop10ByOrderByCreatedOnDesc();
}
