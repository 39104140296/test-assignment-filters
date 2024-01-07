package com.example.backend.service;

import com.example.backend.model.CriteriaType;
import com.example.backend.repository.CriteriaTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CriteriaTypeService {

    private final CriteriaTypeRepository criteriaTypeRepository;

    public CriteriaTypeService(CriteriaTypeRepository criteriaTypeRepository) {
        this.criteriaTypeRepository = criteriaTypeRepository;
    }

    public List<CriteriaType> findAllCriteriaTypes() {
        return criteriaTypeRepository.findAll();
    }

    public CriteriaType findCriteriaTypeById(Long id) {
        return criteriaTypeRepository.findById(id).orElse(null);
    }

    public CriteriaType saveCriteriaType(CriteriaType criteriaType) {
        return criteriaTypeRepository.save(criteriaType);
    }

    public void deleteCriteriaType(Long id) {
        criteriaTypeRepository.deleteById(id);
    }
}
