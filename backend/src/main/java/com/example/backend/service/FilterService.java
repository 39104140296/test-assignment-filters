package com.example.backend.service;

import com.example.backend.model.Filter;
import com.example.backend.model.FilterCriteria;
import com.example.backend.repository.FilterRepository;
import com.example.backend.repository.FilterCriteriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FilterService {

    private final FilterRepository filterRepository;
    private final FilterCriteriaRepository filterCriteriaRepository;

    public FilterService(FilterRepository filterRepository, FilterCriteriaRepository filterCriteriaRepository) {
        this.filterRepository = filterRepository;
        this.filterCriteriaRepository = filterCriteriaRepository;
    }

    public List<Filter> getAllFiltersWithCriteria() {
        return filterRepository.findAll();
    }

    public Optional<Filter> getFilterById(Integer id) {
        return filterRepository.findById(id);
    }

    public Optional<FilterCriteria> addFilterCriteria(Integer filterId, FilterCriteria filterCriteria) {
        return filterRepository.findById(filterId).map(filter -> {
            filterCriteria.setFilter(filter);
            return filterCriteriaRepository.save(filterCriteria);
        });
    }

    @Transactional
    public Filter createFilter(Filter filter) {
        return filterRepository.save(filter);
    }

    @Transactional
    public Optional<Filter> updateFilter(Integer id, Filter filterDetails) {
        return filterRepository.findById(id).map(filter -> {
            filter.setFilterName(filterDetails.getFilterName());
            filter.setFilterCriteriaList(filterDetails.getFilterCriteriaList());
            return filterRepository.save(filter);
        });
    }

    @Transactional
    public boolean deleteFilter(Integer id) {
        return filterRepository.findById(id).map(filter -> {
            filterRepository.delete(filter);
            return true;
        }).orElse(false);
    }
}