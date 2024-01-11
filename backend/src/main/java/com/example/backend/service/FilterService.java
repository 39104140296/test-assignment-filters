package com.example.backend.service;

import com.example.backend.repository.FilterRepository;

import org.springframework.stereotype.Service;

@Service
public class FilterService {

    private final FilterRepository filterRepository;

    public FilterService(FilterRepository filterRepository) {
        this.filterRepository = filterRepository;
    }
}