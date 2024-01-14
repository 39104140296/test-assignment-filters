package com.example.backend.controller;

import com.example.backend.service.FilterService;
import com.example.backend.dto.FilterDTO;
import com.example.backend.dto.FilterCriteriaDTO;
import com.example.backend.controller.request.CreateFilterRequest;
import com.example.backend.controller.request.GetFilterOptionsRequest;
import com.example.backend.controller.request.UpdateFilterRequest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/filters")
public class FilterController {

    private final FilterService filterService;

    public FilterController(FilterService filterService) {
        this.filterService = filterService;
    }

    @GetMapping
    public ResponseEntity<List<FilterDTO>> getAllFilters() {
        List<FilterDTO> filters = filterService.getAllFilters();
        return ResponseEntity.ok(filters);
    }

    @GetMapping("/{filterId}")
    public ResponseEntity<List<FilterCriteriaDTO>> getFilterCriteria(@PathVariable Integer filterId) {
        List<FilterCriteriaDTO> filterCriteriaDTOs = filterService.getFilterCriteria(filterId);
        return ResponseEntity.ok(filterCriteriaDTOs);
    }

    @PutMapping("/{filterId}")
    public ResponseEntity<FilterDTO> updateFilter(@PathVariable Integer filterId,
            @RequestBody UpdateFilterRequest updateRequest) {
        FilterDTO filterDTO = filterService.updateFilter(filterId, updateRequest);
        return ResponseEntity.ok(filterDTO);
    }

    @PostMapping
    public ResponseEntity<FilterDTO> createFilter(@RequestBody CreateFilterRequest createFilterRequest) {
        FilterDTO filterDTO = filterService.createFilter(createFilterRequest);
        return new ResponseEntity<>(filterDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{filterId}")
    public ResponseEntity<Void> deleteFilter(@PathVariable Integer filterId) {
        filterService.deleteFilterAndCriteria(filterId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filter-options")
    public ResponseEntity<GetFilterOptionsRequest> getFilterOptions() {
        GetFilterOptionsRequest filterOptions = filterService.getFilterOptions();
        return ResponseEntity.ok(filterOptions);
    }
}