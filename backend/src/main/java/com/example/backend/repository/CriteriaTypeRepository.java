package com.example.backend.repository;

import com.example.backend.model.CriteriaType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CriteriaTypeRepository extends JpaRepository<CriteriaType, Long> {
}
