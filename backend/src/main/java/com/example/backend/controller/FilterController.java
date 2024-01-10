package com.example.backend.controller;

import com.example.backend.model.Filter;
import com.example.backend.service.FilterService;

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

    @GetMapping("/with-criteria")
    public ResponseEntity<List<Filter>> getAllFiltersWithCriteria() {
        List<Filter> filters = filterService.getAllFiltersWithCriteria();
        return ResponseEntity.ok(filters);
    }

    @PostMapping("/")
    public ResponseEntity<Filter> createFilter(@RequestBody Filter filter) {
        Filter createdFilter = filterService.createFilter(filter);
        return new ResponseEntity<>(createdFilter, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Filter> updateFilter(@PathVariable Integer id, @RequestBody Filter filter) {
        return filterService.updateFilter(id, filter)
                .map(updatedFilter -> ResponseEntity.ok(updatedFilter))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilter(@PathVariable Integer id) {
        boolean isDeleted = filterService.deleteFilter(id);
        return isDeleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
