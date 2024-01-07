package com.example.backend.service;

import com.example.backend.model.Filter;
import com.example.backend.repository.FilterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilterService {

    private final FilterRepository filterRepository;

    public FilterService(FilterRepository filterRepository) {
        this.filterRepository = filterRepository;
    }

    public List<Filter> findAllFilters() {
        return filterRepository.findAll();
    }

    public Filter findFilterById(Long id) {
        return filterRepository.findById(id).orElse(null);
    }

    public Filter saveFilter(Filter filter) {
        return filterRepository.save(filter);
    }

    public void deleteFilter(Long id) {
        filterRepository.deleteById(id);
    }
}
