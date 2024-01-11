package com.example.backend.service;

import com.example.backend.repository.FilterCriteriaRepository;
import com.example.backend.repository.FilterRepository;
import com.example.backend.model.Filter;
import com.example.backend.model.FilterCriteria;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilterService {

    private final FilterRepository filterRepository;
    private final FilterCriteriaRepository filterCriteriaRepository;

    @Autowired
    public FilterService(FilterRepository filterRepository, FilterCriteriaRepository filterCriteriaRepository) {
        this.filterRepository = filterRepository;
        this.filterCriteriaRepository = filterCriteriaRepository;
    }

    public List<Filter> getAllFilters() {
        return filterRepository.findAll();
    }

    public List<FilterCriteria> getFilterCriteriaByFilterId(Integer filterId) {
        return filterCriteriaRepository.findByFilterFilterId(filterId);
    }
}