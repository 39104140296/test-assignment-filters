package com.example.backend.controller;

import com.example.backend.model.CriteriaType;
import com.example.backend.service.CriteriaTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/criteria-types")
public class CriteriaTypeController {

    private final CriteriaTypeService criteriaTypeService;

    public CriteriaTypeController(CriteriaTypeService criteriaTypeService) {
        this.criteriaTypeService = criteriaTypeService;
    }

    @GetMapping
    public ResponseEntity<List<CriteriaType>> getAllCriteriaTypes() {
        return ResponseEntity.ok(criteriaTypeService.findAllCriteriaTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CriteriaType> getCriteriaTypeById(@PathVariable Long id) {
        CriteriaType criteriaType = criteriaTypeService.findCriteriaTypeById(id);
        return criteriaType != null ? ResponseEntity.ok(criteriaType) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CriteriaType> createCriteriaType(@RequestBody CriteriaType criteriaType) {
        return ResponseEntity.ok(criteriaTypeService.saveCriteriaType(criteriaType));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CriteriaType> updateCriteriaType(@PathVariable Long id,
            @RequestBody CriteriaType criteriaTypeDetails) {
        CriteriaType criteriaType = criteriaTypeService.findCriteriaTypeById(id);
        if (criteriaType == null) {
            return ResponseEntity.notFound().build();
        }
        criteriaType.setTypeName(criteriaTypeDetails.getTypeName());
        criteriaType.setDataType(criteriaTypeDetails.getDataType());
        return ResponseEntity.ok(criteriaTypeService.saveCriteriaType(criteriaType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCriteriaType(@PathVariable Long id) {
        if (criteriaTypeService.findCriteriaTypeById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        criteriaTypeService.deleteCriteriaType(id);
        return ResponseEntity.ok().build();
    }
}
