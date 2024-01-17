package com.example.backend.controller;

import com.example.backend.service.FilterService;

import jakarta.persistence.EntityNotFoundException;

import com.example.backend.dto.FilterDTO;
import com.example.backend.dto.FilterCriteriaDTO;
import com.example.backend.controller.request.CreateFilterRequest;
import com.example.backend.controller.request.UpdateFilterRequest;
import com.example.backend.controller.response.GetFilterOptionsResponse;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/filters")
public class FilterController {

    private final FilterService filterService;

    public FilterController(FilterService filterService) {
        this.filterService = filterService;
    }

    @GetMapping
    public ResponseEntity<List<FilterDTO>> getAllFilters() {
        final List<FilterDTO> filters = filterService.getAllFilters();
        return ResponseEntity.ok(filters);
    }

    @GetMapping("/{filterId}")
    public ResponseEntity<List<FilterCriteriaDTO>> getFilterCriteria(@PathVariable Integer filterId) {
        final List<FilterCriteriaDTO> filterCriteriaDTOs = filterService.getFilterCriteria(filterId);
        return ResponseEntity.ok(filterCriteriaDTOs);
    }

    @PutMapping("/{filterId}")
    public ResponseEntity<FilterDTO> updateFilter(
            @PathVariable Integer filterId,
            @RequestBody UpdateFilterRequest updateRequest) {
        final FilterDTO filterDTO = filterService.updateFilter(filterId, updateRequest);
        return ResponseEntity.ok(filterDTO);
    }

    @PostMapping
    public ResponseEntity<FilterDTO> createFilter(@RequestBody CreateFilterRequest createFilterRequest) {
        final FilterDTO filterDTO = filterService.createFilter(createFilterRequest);
        return new ResponseEntity<>(filterDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{filterId}")
    public ResponseEntity<Void> deleteFilter(@PathVariable Integer filterId) {
        filterService.deleteFilterAndCriteria(filterId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/options")
    public ResponseEntity<GetFilterOptionsResponse> getFilterOptions() {
        GetFilterOptionsResponse filterOptions = filterService.getFilterOptions();
        return ResponseEntity.ok(filterOptions);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ EntityNotFoundException.class })
    public EntityNotFoundException handleException(EntityNotFoundException exception) {
        return exception;
    }
}
