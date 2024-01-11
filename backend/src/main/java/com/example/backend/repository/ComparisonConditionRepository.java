package com.example.backend.repository;

import com.example.backend.model.ComparisonCondition;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComparisonConditionRepository extends JpaRepository<ComparisonCondition, Integer> {
    @Override
    @EntityGraph(attributePaths = { "criteriaType" })
    List<ComparisonCondition> findAll();
}
