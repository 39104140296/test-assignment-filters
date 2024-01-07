package com.example.backend.service;

import com.example.backend.model.FilterCriteria;
import com.example.backend.repository.FilterCriteriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilterCriteriaService {

    private final FilterCriteriaRepository filterCriteriaRepository;

    public FilterCriteriaService(FilterCriteriaRepository filterCriteriaRepository) {
        this.filterCriteriaRepository = filterCriteriaRepository;
    }

    public List<FilterCriteria> findAllFilterCriteria() {
        return filterCriteriaRepository.findAll();
    }

    public FilterCriteria findFilterCriteriaById(Long id) {
        return filterCriteriaRepository.findById(id).orElse(null);
    }

    public FilterCriteria saveFilterCriteria(FilterCriteria filterCriteria) {
        return filterCriteriaRepository.save(filterCriteria);
    }

    public void deleteFilterCriteria(Long id) {
        filterCriteriaRepository.deleteById(id);
    }
}
