package com.example.backend.repository;

import com.example.backend.model.ComparisonCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComparisonConditionRepository extends JpaRepository<ComparisonCondition, Long> {
}
