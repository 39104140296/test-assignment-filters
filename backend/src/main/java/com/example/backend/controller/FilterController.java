package com.example.backend.controller;

import com.example.backend.service.FilterService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/filters")
public class FilterController {

    private final FilterService filterService;

    public FilterController(FilterService filterService) {
        this.filterService = filterService;
    }
}