package com.example.backend.service;

import com.example.backend.controller.request.CreateFilterRequest;
import com.example.backend.controller.request.UpdateFilterRequest;
import com.example.backend.controller.response.GetFilterOptionsResponse;
import com.example.backend.dto.ComparisonConditionDTO;
import com.example.backend.dto.CriteriaTypeDTO;
import com.example.backend.dto.FilterCriteriaDTO;
import com.example.backend.dto.FilterDTO;
import com.example.backend.model.ComparisonCondition;
import com.example.backend.model.CriteriaType;
import com.example.backend.model.Filter;
import com.example.backend.model.FilterCriteria;
import com.example.backend.repository.ComparisonConditionRepository;
import com.example.backend.repository.CriteriaTypeRepository;
import com.example.backend.repository.FilterCriteriaRepository;
import com.example.backend.repository.FilterRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FilterService {

    private final FilterRepository filterRepository;
    private final FilterCriteriaRepository filterCriteriaRepository;
    private final ComparisonConditionRepository comparisonConditionRepository;
    private final CriteriaTypeRepository criteriaTypeRepository;

    public FilterService(FilterRepository filterRepository,
            FilterCriteriaRepository filterCriteriaRepository,
            ComparisonConditionRepository comparisonConditionRepository,
            CriteriaTypeRepository criteriaTypeRepository) {
        this.filterRepository = filterRepository;
        this.filterCriteriaRepository = filterCriteriaRepository;
        this.comparisonConditionRepository = comparisonConditionRepository;
        this.criteriaTypeRepository = criteriaTypeRepository;
    }

    public List<FilterDTO> getAllFilters() {
        return filterRepository.findAll().stream()
                .map(FilterDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<FilterCriteriaDTO> getFilterCriteria(Integer filterId) {
        final List<FilterCriteria> filterCriteriaEntities = filterCriteriaRepository.findAllByFilterId(filterId);
        return filterCriteriaEntities.stream()
                .map(FilterCriteriaDTO::new)
                .toList();
    }

    @Transactional
    public FilterDTO updateFilter(Integer filterId, UpdateFilterRequest request) {
        Filter filter = filterRepository.findById(filterId)
                .orElseThrow(() -> new EntityNotFoundException("Filter not found with id: " + filterId));

        if (!Objects.equals(filter.getName(), request.getFilterName())) {
            filter.setName(request.getFilterName());
            filter = filterRepository.save(filter);
        }
        filterCriteriaRepository.deleteAllByFilterId(filterId);

        for (FilterCriteriaDTO requestFilterCriteria : request.getFilterCriteria()) {
            final ComparisonCondition comparisonCondition = comparisonConditionRepository
                    .findById(requestFilterCriteria.getComparisonCondition().getId())
                    .orElseThrow(() -> new EntityNotFoundException("ComparisonCondition not found"));

            final FilterCriteria filterCriteria = new FilterCriteria()
                    .setFilter(filter)
                    .setCriteriaType(comparisonCondition.getCriteriaType())
                    .setComparisonCondition(comparisonCondition)
                    .setValue(requestFilterCriteria.getCriteriaValue());
            filterCriteriaRepository.save(filterCriteria);
        }

        return new FilterDTO(filter);
    }

    @Transactional
    public FilterDTO createFilter(CreateFilterRequest request) {
        final Filter filter = new Filter()
                .setName(request.getFilterName())
                .setCreatedAt(new Date());

        final Filter savedFilter = filterRepository.save(filter);
        for (FilterCriteriaDTO criteriaDTO : request.getCriteria()) {
            final Integer criteriaTypeId = criteriaDTO.getCriteriaType().getId();
            final CriteriaType criteriaType = criteriaTypeRepository.findById(criteriaTypeId)
                    .orElseThrow(
                            () -> new EntityNotFoundException("CriteriaType not found with id: " + criteriaTypeId));

            final Integer conditionId = criteriaDTO.getComparisonCondition().getId();
            final ComparisonCondition comparisonCondition = comparisonConditionRepository.findById(conditionId)
                    .orElseThrow(
                            () -> new EntityNotFoundException("ComparisonCondition not found with id: " + conditionId));

            final FilterCriteria filterCriteria = new FilterCriteria()
                    .setFilter(savedFilter)
                    .setCriteriaType(criteriaType)
                    .setComparisonCondition(comparisonCondition)
                    .setValue(criteriaDTO.getCriteriaValue());
            filterCriteriaRepository.save(filterCriteria);
        }

        return new FilterDTO(savedFilter);
    }

    @Transactional
    public void deleteFilterAndCriteria(Integer filterId) {
        final Filter filter = filterRepository.findById(filterId)
                .orElseThrow(() -> new EntityNotFoundException("Filter not found with id: " + filterId));
        filterCriteriaRepository.deleteAllByFilterId(filter.getId());
        filterRepository.delete(filter);
    }

    public GetFilterOptionsResponse getFilterOptions() {
        final List<CriteriaTypeDTO> criteriaTypes = criteriaTypeRepository.findAll().stream()
                .map(CriteriaTypeDTO::new)
                .toList();
        final List<ComparisonConditionDTO> comparisonConditions = comparisonConditionRepository.findAll().stream()
                .map(ComparisonConditionDTO::new)
                .toList();

        return new GetFilterOptionsResponse(criteriaTypes, comparisonConditions);
    }
}
