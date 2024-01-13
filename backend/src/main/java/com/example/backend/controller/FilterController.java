package com.example.backend.controller;

import com.example.backend.service.FilterService;
import com.example.backend.dto.FilterDTO;
import com.example.backend.dto.FilterCriteriaDTO;
import com.example.backend.dto.ComparisonConditionDTO;
// import com.example.backend.dto.CreateFilterDTO;
import com.example.backend.dto.CriteriaTypeDTO;
import com.example.backend.model.Filter;

import java.util.List;
import java.util.Map;

// import org.springframework.http.HttpStatus;
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

    @PutMapping("/{filterId}/name")
    public ResponseEntity<Filter> updateFilterName(@PathVariable Integer filterId,
            @RequestBody Map<String, String> updateRequest) {
        String newFilterName = updateRequest.get("filterName");
        Filter updatedFilter = filterService.updateFilterName(filterId, newFilterName);
        return ResponseEntity.ok(updatedFilter);
    }

    // @PutMapping("/{filterId}/criteria")
    // public ResponseEntity<Void> updateFilterCriteria(@PathVariable Integer
    // filterId,
    // @RequestBody List<FilterCriteriaDTO> criteriaDTOList) {
    // filterService.updateFilterCriteria(filterId, criteriaDTOList);
    // return ResponseEntity.ok().build();
    // }

    // @PostMapping("/")
    // public ResponseEntity<Filter> createFilter(@RequestBody CreateFilterDTO
    // createFilterDTO) {
    // Filter filter = filterService.createFilterAndCriteria(createFilterDTO);
    // return new ResponseEntity<>(filter, HttpStatus.CREATED);
    // }

    // @DeleteMapping("/{filterId}")
    // public ResponseEntity<Void> deleteFilter(@PathVariable Integer filterId) {
    // filterService.deleteFilterAndCriteria(filterId);
    // return ResponseEntity.noContent().build();
    // }

    @GetMapping("/criteria-types")
    public ResponseEntity<List<CriteriaTypeDTO>> getCriteriaTypes() {
        List<CriteriaTypeDTO> criteriaTypeDTOs = filterService.getAllCriteriaTypes();
        return ResponseEntity.ok(criteriaTypeDTOs);
    }

    @GetMapping("/comparison-conditions")
    public ResponseEntity<List<ComparisonConditionDTO>> getComparisonConditions() {
        List<ComparisonConditionDTO> comparisonConditionDTOs = filterService.getAllComparisonConditions();
        return ResponseEntity.ok(comparisonConditionDTOs);
    }
}