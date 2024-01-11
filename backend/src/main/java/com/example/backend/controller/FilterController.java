package com.example.backend.controller;

import com.example.backend.service.FilterService;
import com.example.backend.model.Filter;

import java.util.List;

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
}