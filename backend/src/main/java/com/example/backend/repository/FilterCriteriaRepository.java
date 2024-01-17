package com.example.backend.repository;

import com.example.backend.model.FilterCriteria;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilterCriteriaRepository extends JpaRepository<FilterCriteria, Integer> {
    List<FilterCriteria> findAllByFilter_FilterId(Integer filterId);
}
