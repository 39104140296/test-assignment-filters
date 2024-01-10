package com.example.backend.service;

import com.example.backend.model.Filter;
import com.example.backend.repository.FilterRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FilterService {

    private final FilterRepository filterRepository;

    public FilterService(FilterRepository filterRepository) {
        this.filterRepository = filterRepository;
    }

    public List<Filter> getAllFiltersWithCriteria() {
        return filterRepository.findAll();
    }

    @Transactional
    public Filter createFilter(Filter filter) {
        return filterRepository.save(filter);
    }

    @Transactional
    public Optional<Filter> updateFilter(Integer id, Filter filterDetails) {
        return filterRepository.findById(id).map(existingFilter -> {
            existingFilter.setFilterName(filterDetails.getFilterName());
            existingFilter.setFilterCriteriaList(filterDetails.getFilterCriteriaList());
            return filterRepository.save(existingFilter);
        });
    }

    @Transactional
    public boolean deleteFilter(Integer id) {
        if (filterRepository.existsById(id)) {
            filterRepository.deleteById(id);
            return true;
        }
        return false;
    }
}