package com.example.backend.service;

import com.example.backend.repository.FilterCriteriaRepository;
import com.example.backend.repository.FilterRepository;
import com.example.backend.repository.ComparisonConditionRepository;
import com.example.backend.repository.CriteriaTypeRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import com.example.backend.dto.CriteriaTypeDTO;
import com.example.backend.dto.ComparisonConditionDTO;
import com.example.backend.dto.FilterDTO;
import com.example.backend.dto.FilterCriteriaDTO;
import com.example.backend.service.FilterService;
// import com.example.backend.dto.CreateFilterDTO;
// import com.example.backend.model.ComparisonCondition;
// import com.example.backend.model.CriteriaType;
import com.example.backend.model.Filter;
// import com.example.backend.model.FilterCriteria;

// import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    // public List<FilterCriteriaDTO> getFilterCriteria(Integer filterId) {
    // return filterCriteriaRepository.findByFilterFilterId(filterId).stream()
    // .map(filterCriteria -> new FilterCriteriaDTO()
    // .setCriteriaId(filterCriteria.getCriteriaId())
    // .setCriteriaTypeId(filterCriteria.getCriteriaType().getCriteriaTypeId())
    // .setCriteriaTypeName(filterCriteria.getCriteriaType().getTypeName())
    // .setConditionId(filterCriteria.getComparisonCondition().getConditionId())
    // .setConditionName(filterCriteria.getComparisonCondition().getConditionName())
    // .setCriteriaValue(filterCriteria.getCriteriaValue()))
    // .collect(Collectors.toList());
    // }

    @Transactional
    public Filter updateFilterName(Integer filterId, String newFilterName) {
        Filter filter = filterRepository.findById(filterId)
                .orElseThrow(() -> new EntityNotFoundException("Filter not found with id :: " + filterId));

        filter.setFilterName(newFilterName);
        return filterRepository.save(filter);
    }

    // @Transactional
    // public void updateFilterCriteria(Integer filterId, List<FilterCriteriaDTO>
    // criteriaDTOList) {
    // Filter filter = filterRepository.findById(filterId)
    // .orElseThrow(() -> new EntityNotFoundException("Filter not found: " +
    // filterId));

    // List<FilterCriteria> existingCriteria =
    // filterCriteriaRepository.findByFilterFilterId(filterId);
    // filterCriteriaRepository.deleteAll(existingCriteria);

    // List<FilterCriteria> updatedCriteria = criteriaDTOList.stream()
    // .map(dto -> mapDtoToEntity(dto, filter))
    // .collect(Collectors.toList());

    // filterCriteriaRepository.saveAll(updatedCriteria);
    // }

    public List<FilterCriteriaDTO> getFilterCriteria(Integer filterId) {
        return filterCriteriaRepository.findAllByFilter_FilterId(filterId).stream()
                .map(fc -> new FilterCriteriaDTO()
                        .setCriteriaId(fc.getCriteriaId())
                        .setFilterId(fc.getFilter().getFilterId())
                        .setCriteriaTypeId(fc.getCriteriaType().getCriteriaTypeId())
                        .setConditionId(fc.getComparisonCondition().getConditionId())
                        .setCriteriaValue(fc.getCriteriaValue()))
                .collect(Collectors.toList());
    }

    // @Transactional
    // public Filter createFilterAndCriteria(CreateFilterDTO createFilterDTO) {
    // Filter newFilter = new Filter();
    // newFilter.setFilterName(createFilterDTO.getFilterName());

    // newFilter.setCreatedAt(new Date());

    // Filter savedFilter = filterRepository.save(newFilter);

    // List<FilterCriteria> filterCriteriaList =
    // createFilterDTO.getCriteria().stream()
    // .map(dto -> mapDtoToEntity(dto, savedFilter))
    // .collect(Collectors.toList());

    // filterCriteriaRepository.saveAll(filterCriteriaList);

    // return savedFilter;
    // }

    // @Transactional
    // public void deleteFilterAndCriteria(Integer filterId) {
    // List<FilterCriteria> criteria =
    // filterCriteriaRepository.findByFilterFilterId(filterId);
    // filterCriteriaRepository.deleteAll(criteria);

    // filterRepository.deleteById(filterId);
    // }

    public List<CriteriaTypeDTO> getAllCriteriaTypes() {
        return criteriaTypeRepository.findAll().stream()
                .map(ct -> new CriteriaTypeDTO()
                        .setCriteriaTypeId(ct.getCriteriaTypeId())
                        .setTypeName(ct.getTypeName()))
                .collect(Collectors.toList());
    }

    public List<ComparisonConditionDTO> getAllComparisonConditions() {
        return comparisonConditionRepository.findAll().stream()
                .map(cc -> new ComparisonConditionDTO()
                        .setConditionId(cc.getConditionId())
                        .setCriteriaTypeId(cc.getCriteriaType().getCriteriaTypeId())
                        .setConditionName(cc.getConditionName()))
                .collect(Collectors.toList());
    }
}