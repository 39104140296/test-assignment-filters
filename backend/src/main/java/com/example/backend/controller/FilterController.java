package com.example.backend.controller;

import com.example.backend.service.FilterService;
import com.example.backend.dto.FilterCriteriaDTO;
import com.example.backend.model.Filter;
import com.example.backend.model.FilterCriteria;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/filters")
public class FilterController {

    private final FilterService filterService;

    public FilterController(FilterService filterService) {
        this.filterService = filterService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Filter>> getAllFilters() {
        List<Filter> filters = filterService.getAllFilters();
        return ResponseEntity.ok(filters);
    }

    @GetMapping("/{filterId}/criteria")
    public ResponseEntity<List<FilterCriteria>> getFilterCriteria(@PathVariable Integer filterId) {
        List<FilterCriteria> filterCriteria = filterService.getFilterCriteriaByFilterId(filterId);
        return ResponseEntity.ok(filterCriteria);
    }

    @PutMapping("/{filterId}/name")
    public ResponseEntity<Filter> updateFilterName(@PathVariable Integer filterId,
            @RequestBody Map<String, String> updateRequest) {
        String newFilterName = updateRequest.get("filterName");
        Filter updatedFilter = filterService.updateFilterName(filterId, newFilterName);
        return ResponseEntity.ok(updatedFilter);
    }

    @PutMapping("/{filterId}/criteria")
    public ResponseEntity<Void> updateFilterCriteria(@PathVariable Integer filterId,
            @RequestBody List<FilterCriteriaDTO> criteriaDTOList) {
        filterService.updateFilterCriteria(filterId, criteriaDTOList);
        return ResponseEntity.ok().build();
    }
}