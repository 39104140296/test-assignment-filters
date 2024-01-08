package com.example.backend.service;

import com.example.backend.model.Filter;
import com.example.backend.repository.FilterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FilterService {

    private final FilterRepository filterRepository;

    public FilterService(FilterRepository filterRepository) {
        this.filterRepository = filterRepository;
    }

    public List<Filter> getAllFiltersWithCriteria() {
        // The filters are fetched with their criteria due to the FetchType.LAZY
        // and initialized by the entity graph defined in the repository
        return filterRepository.findAll();
    }

    public Optional<Filter> getFilterById(Integer id) {
        return filterRepository.findById(id);
    }
}