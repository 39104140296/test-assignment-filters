package com.example.backend.controller;

import com.example.backend.model.FilterCriteria;
import com.example.backend.service.FilterCriteriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/filter-criteria")
public class FilterCriteriaController {

    private final FilterCriteriaService filterCriteriaService;

    public FilterCriteriaController(FilterCriteriaService filterCriteriaService) {
        this.filterCriteriaService = filterCriteriaService;
    }

    @GetMapping
    public ResponseEntity<List<FilterCriteria>> getAllFilterCriteria() {
        return ResponseEntity.ok(filterCriteriaService.findAllFilterCriteria());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilterCriteria> getFilterCriteriaById(@PathVariable Long id) {
        FilterCriteria filterCriteria = filterCriteriaService.findFilterCriteriaById(id);
        return filterCriteria != null ? ResponseEntity.ok(filterCriteria) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<FilterCriteria> createFilterCriteria(@RequestBody FilterCriteria filterCriteria) {
        return ResponseEntity.ok(filterCriteriaService.saveFilterCriteria(filterCriteria));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FilterCriteria> updateFilterCriteria(@PathVariable Long id,
            @RequestBody FilterCriteria filterCriteriaDetails) {
        FilterCriteria filterCriteria = filterCriteriaService.findFilterCriteriaById(id);
        if (filterCriteria == null) {
            return ResponseEntity.notFound().build();
        }
        // Update the fields of filterCriteria with filterCriteriaDetails
        // Example: filterCriteria.setValue(filterCriteriaDetails.getValue());
        return ResponseEntity.ok(filterCriteriaService.saveFilterCriteria(filterCriteria));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFilterCriteria(@PathVariable Long id) {
        if (filterCriteriaService.findFilterCriteriaById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        filterCriteriaService.deleteFilterCriteria(id);
        return ResponseEntity.ok().build();
    }
}
