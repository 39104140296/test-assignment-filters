package com.example.backend.controller;

import com.example.backend.model.Filter;
import com.example.backend.service.FilterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/filters")
public class FilterController {

    private final FilterService filterService;

    public FilterController(FilterService filterService) {
        this.filterService = filterService;
    }

    @GetMapping
    public ResponseEntity<List<Filter>> getAllFilters() {
        return ResponseEntity.ok(filterService.findAllFilters());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Filter> getFilterById(@PathVariable Long id) {
        Filter filter = filterService.findFilterById(id);
        return filter != null ? ResponseEntity.ok(filter) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Filter> createFilter(@RequestBody Filter filter) {
        return ResponseEntity.ok(filterService.saveFilter(filter));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Filter> updateFilter(@PathVariable Long id, @RequestBody Filter filterDetails) {
        Filter filter = filterService.findFilterById(id);
        if (filter == null) {
            return ResponseEntity.notFound().build();
        }
        filter.setFilterName(filterDetails.getFilterName());
        filter.setCreatedAt(filterDetails.getCreatedAt());
        return ResponseEntity.ok(filterService.saveFilter(filter));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFilter(@PathVariable Long id) {
        if (filterService.findFilterById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        filterService.deleteFilter(id);
        return ResponseEntity.ok().build();
    }
}
