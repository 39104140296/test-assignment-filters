package com.example.backend.service;

import com.example.backend.repository.FilterCriteriaRepository;
import com.example.backend.repository.FilterRepository;
import com.example.backend.repository.ComparisonConditionRepository;
import com.example.backend.repository.CriteriaTypeRepository;

import jakarta.persistence.EntityNotFoundException;

import com.example.backend.dto.CriteriaTypeDTO;
import com.example.backend.controller.request.CreateFilterRequest;
import com.example.backend.controller.request.GetFilterOptionsRequest;
import com.example.backend.controller.request.UpdateFilterRequest;
import com.example.backend.dto.ComparisonConditionDTO;
import com.example.backend.dto.FilterDTO;
import com.example.backend.dto.FilterCriteriaDTO;
import com.example.backend.service.FilterService;
import com.example.backend.model.ComparisonCondition;
import com.example.backend.model.CriteriaType;
import com.example.backend.model.Filter;
import com.example.backend.model.FilterCriteria;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FilterService {

    private final FilterRepository filterRepository;
    private final FilterCriteriaRepository filterCriteriaRepository;
    private final ComparisonConditionRepository comparisonConditionRepository;
    private final CriteriaTypeRepository criteriaTypeRepository;

    @Autowired
    public FilterService(FilterRepository filterRepository, FilterCriteriaRepository filterCriteriaRepository,
            ComparisonConditionRepository comparisonConditionRepository,
            CriteriaTypeRepository criteriaTypeRepository) {
        this.filterRepository = filterRepository;
        this.filterCriteriaRepository = filterCriteriaRepository;
        this.comparisonConditionRepository = comparisonConditionRepository;
        this.criteriaTypeRepository = criteriaTypeRepository;
    }

    public List<FilterDTO> getAllFilters() {
        return filterRepository.findAll().stream()
                .map(filter -> new FilterDTO()
                        .setFilterName(filter.getFilterName())
                        .setFilterId(filter.getFilterId()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FilterCriteriaDTO> getFilterCriteria(Integer filterId) {
        List<FilterCriteria> filterCriteriaEntities = filterCriteriaRepository
                .findAllByFilter_FilterId(filterId);
        return filterCriteriaEntities.stream()
                .map(fc -> {
                    CriteriaType criteriaType = fc.getComparisonCondition().getCriteriaType();
                    Integer criteriaTypeIdValue = criteriaType != null
                            ? criteriaType.getCriteriaTypeId()
                            : null;

                    CriteriaTypeDTO criteriaTypeDTO = new CriteriaTypeDTO()
                            .setCriteriaTypeId(fc.getCriteriaType().getCriteriaTypeId())
                            .setTypeName(fc.getCriteriaType().getTypeName());
                    ComparisonConditionDTO comparisonConditionDTO = new ComparisonConditionDTO()
                            .setConditionId(fc.getComparisonCondition().getConditionId())
                            .setCriteriaTypeId(criteriaTypeIdValue)
                            .setConditionName(
                                    fc.getComparisonCondition().getConditionName());

                    return new FilterCriteriaDTO()
                            .setCriteriaId(fc.getCriteriaId())
                            .setFilterId(fc.getFilter().getFilterId())
                            .setCriteriaType(criteriaTypeDTO)
                            .setComparisonCondition(comparisonConditionDTO)
                            .setCriteriaValue(fc.getCriteriaValue());
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public FilterDTO updateFilter(Integer filterId, UpdateFilterRequest updateRequest) {
        Filter filter = filterRepository.findById(filterId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Filter not found with id: " + filterId));

        filter.setFilterName(updateRequest.getFilterName());
        Filter updatedFilter = filterRepository.save(filter);

        List<FilterCriteria> existingCriteria = filterCriteriaRepository.findAllByFilter_FilterId(filterId);
        filterCriteriaRepository.deleteAll(existingCriteria);

        for (FilterCriteriaDTO criteriaDTO : updateRequest.getFilterCriteria()) {
            CriteriaType criteriaType = criteriaTypeRepository
                    .findById(criteriaDTO.getCriteriaType().getCriteriaTypeId())
                    .orElseThrow(() -> new EntityNotFoundException("CriteriaType not found"));

            ComparisonCondition comparisonCondition = comparisonConditionRepository
                    .findById(criteriaDTO.getComparisonCondition().getConditionId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "ComparisonCondition not found"));

            FilterCriteria filterCriteria = new FilterCriteria();
            filterCriteria.setFilter(updatedFilter);
            filterCriteria.setCriteriaType(criteriaType);
            filterCriteria.setComparisonCondition(comparisonCondition);
            filterCriteria.setCriteriaValue(criteriaDTO.getCriteriaValue());
            filterCriteriaRepository.save(filterCriteria);
        }

        return new FilterDTO()
                .setFilterId(updatedFilter.getFilterId())
                .setFilterName(updatedFilter.getFilterName());
    }

    @Transactional
    public FilterDTO createFilter(CreateFilterRequest createFilterRequest) {
        final Filter filter = new Filter();
        filter.setFilterName(createFilterRequest.getFilterName());
        filter.setCreatedAt(new Date());

        Filter savedFilter = filterRepository.save(filter);
        for (FilterCriteriaDTO criteriaDTO : createFilterRequest.getCriteria()) {

            FilterCriteria filterCriteria = new FilterCriteria();
            filterCriteria.setFilter(savedFilter);

            CriteriaType criteriaType = criteriaTypeRepository
                    .findById(criteriaDTO.getCriteriaType().getCriteriaTypeId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "CriteriaType not found with id: " + criteriaDTO
                                    .getCriteriaType().getCriteriaTypeId()));
            filterCriteria.setCriteriaType(criteriaType);

            ComparisonCondition comparisonCondition = comparisonConditionRepository
                    .findById(criteriaDTO.getComparisonCondition().getConditionId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "ComparisonCondition not found with id: " + criteriaDTO
                                    .getComparisonCondition().getConditionId()));
            filterCriteria.setComparisonCondition(comparisonCondition);

            filterCriteria.setCriteriaValue(criteriaDTO.getCriteriaValue());

            filterCriteriaRepository.save(filterCriteria);
        }

        return new FilterDTO()
                .setFilterId(savedFilter.getFilterId())
                .setFilterName(savedFilter.getFilterName());
    }

    @Transactional
    public void deleteFilterAndCriteria(Integer filterId) {
        final Filter filter = filterRepository.findById(filterId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Filter not found with id: " + filterId));

        List<FilterCriteria> filterCriteriaList = filterCriteriaRepository.findAllByFilter_FilterId(filterId);
        filterCriteriaRepository.deleteAll(filterCriteriaList);

        filterRepository.delete(filter);
    }

    public GetFilterOptionsRequest getFilterOptions() {
        GetFilterOptionsRequest getFilterOptionsRequest = new GetFilterOptionsRequest();

        getFilterOptionsRequest.setCriteriaTypes(
                criteriaTypeRepository.findAll().stream()
                        .map(ct -> new CriteriaTypeDTO()
                                .setCriteriaTypeId(ct.getCriteriaTypeId())
                                .setTypeName(ct.getTypeName()))
                        .collect(Collectors.toList()));

        getFilterOptionsRequest.setComparisonConditions(
                comparisonConditionRepository.findAll().stream()
                        .map(cc -> new ComparisonConditionDTO()
                                .setConditionId(cc.getConditionId())
                                .setCriteriaTypeId(cc.getCriteriaType()
                                        .getCriteriaTypeId())
                                .setConditionName(cc.getConditionName()))
                        .collect(Collectors.toList()));

        return getFilterOptionsRequest;
    }
}