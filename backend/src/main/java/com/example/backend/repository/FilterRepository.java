package com.example.backend.repository;

import com.example.backend.model.Filter;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilterRepository extends JpaRepository<Filter, Integer> {

    @EntityGraph(attributePaths = { "filterCriteriaList.criteriaType", "filterCriteriaList.comparisonCondition" })
    @Override
    List<Filter> findAll();
}