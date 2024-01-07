package com.example.backend.controller;

import com.example.backend.model.ComparisonCondition;
import com.example.backend.service.ComparisonConditionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comparison-conditions")
public class ComparisonConditionController {

    private final ComparisonConditionService comparisonConditionService;

    public ComparisonConditionController(ComparisonConditionService comparisonConditionService) {
        this.comparisonConditionService = comparisonConditionService;
    }

    @GetMapping
    public ResponseEntity<List<ComparisonCondition>> getAllComparisonConditions() {
        return ResponseEntity.ok(comparisonConditionService.findAllComparisonConditions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComparisonCondition> getComparisonConditionById(@PathVariable Long id) {
        ComparisonCondition comparisonCondition = comparisonConditionService.findComparisonConditionById(id);
        return comparisonCondition != null ? ResponseEntity.ok(comparisonCondition) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ComparisonCondition> createComparisonCondition(
            @RequestBody ComparisonCondition comparisonCondition) {
        return ResponseEntity.ok(comparisonConditionService.saveComparisonCondition(comparisonCondition));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComparisonCondition> updateComparisonCondition(@PathVariable Long id,
            @RequestBody ComparisonCondition comparisonConditionDetails) {
        ComparisonCondition comparisonCondition = comparisonConditionService.findComparisonConditionById(id);
        if (comparisonCondition == null) {
            return ResponseEntity.notFound().build();
        }
        comparisonCondition.setConditionName(comparisonConditionDetails.getConditionName());
        return ResponseEntity.ok(comparisonConditionService.saveComparisonCondition(comparisonCondition));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComparisonCondition(@PathVariable Long id) {
        if (comparisonConditionService.findComparisonConditionById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        comparisonConditionService.deleteComparisonCondition(id);
        return ResponseEntity.ok().build();
    }
}
