package com.example.backend.repository;

import com.example.backend.model.FilterCriteria;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilterCriteriaRepository extends JpaRepository<FilterCriteria, Integer> {
    @EntityGraph(attributePaths = { "criteriaType", "comparisonCondition" })
    List<FilterCriteria> findByFilterFilterId(Integer filterId);
}