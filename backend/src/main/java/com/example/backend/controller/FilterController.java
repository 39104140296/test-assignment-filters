package com.example.backend.controller;

import com.example.backend.model.Filter;
import com.example.backend.model.FilterCriteria;
import com.example.backend.service.FilterService;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/with-criteria")
    public ResponseEntity<List<Filter>> getAllFiltersWithCriteria() {
        List<Filter> filters = filterService.getAllFiltersWithCriteria();
        return ResponseEntity.ok(filters);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Filter> getFilterById(@PathVariable Integer id) {
        return filterService.getFilterById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{filterId}/criteria")
    public ResponseEntity<FilterCriteria> addFilterCriteria(@PathVariable Integer filterId,
            @RequestBody FilterCriteria filterCriteria) {
        return filterService.addFilterCriteria(filterId, filterCriteria)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<Filter> createFilter(@RequestBody Filter filter) {
        Filter createdFilter = filterService.createFilter(filter);
        return new ResponseEntity<>(createdFilter, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Filter> updateFilter(@PathVariable Integer id, @RequestBody Filter filter) {
        return filterService.updateFilter(id, filter)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilter(@PathVariable Integer id) {
        boolean isDeleted = filterService.deleteFilter(id);
        if (isDeleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
