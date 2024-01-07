package com.example.backend.service;

import com.example.backend.model.ComparisonCondition;
import com.example.backend.repository.ComparisonConditionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComparisonConditionService {

    private final ComparisonConditionRepository comparisonConditionRepository;

    public ComparisonConditionService(ComparisonConditionRepository comparisonConditionRepository) {
        this.comparisonConditionRepository = comparisonConditionRepository;
    }

    public List<ComparisonCondition> findAllComparisonConditions() {
        return comparisonConditionRepository.findAll();
    }

    public ComparisonCondition findComparisonConditionById(Long id) {
        return comparisonConditionRepository.findById(id).orElse(null);
    }

    public ComparisonCondition saveComparisonCondition(ComparisonCondition comparisonCondition) {
        return comparisonConditionRepository.save(comparisonCondition);
    }

    public void deleteComparisonCondition(Long id) {
        comparisonConditionRepository.deleteById(id);
    }
}
