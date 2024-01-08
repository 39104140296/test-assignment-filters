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
}
