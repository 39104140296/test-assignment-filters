package com.example.backend.repository;

import com.example.backend.model.FilterCriteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilterCriteriaRepository extends JpaRepository<FilterCriteria, Integer> {
}