package eu.wisercat.filter.service;

import eu.wisercat.filter.controller.request.CreateFilterRequest;
import eu.wisercat.filter.controller.request.UpdateFilterRequest;
import eu.wisercat.filter.controller.response.GetFilterOptionsResponse;
import eu.wisercat.filter.dto.ComparisonConditionDTO;
import eu.wisercat.filter.dto.CriteriaTypeDTO;
import eu.wisercat.filter.dto.FilterCriteriaDTO;
import eu.wisercat.filter.dto.FilterDTO;
import eu.wisercat.filter.model.ComparisonCondition;
import eu.wisercat.filter.model.Filter;
import eu.wisercat.filter.model.FilterCriteria;
import eu.wisercat.filter.model.User;
import eu.wisercat.filter.repository.ComparisonConditionRepository;
import eu.wisercat.filter.repository.CriteriaTypeRepository;
import eu.wisercat.filter.repository.FilterCriteriaRepository;
import eu.wisercat.filter.repository.FilterRepository;
import eu.wisercat.filter.security.UserDetailsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class FilterService {

    private final UserDetailsService userDetailsService;
    private final FilterRepository filterRepository;
    private final FilterCriteriaRepository filterCriteriaRepository;
    private final ComparisonConditionRepository comparisonConditionRepository;
    private final CriteriaTypeRepository criteriaTypeRepository;

    public FilterService(UserDetailsService userDetailsService,
            FilterRepository filterRepository,
            FilterCriteriaRepository filterCriteriaRepository,
            ComparisonConditionRepository comparisonConditionRepository,
            CriteriaTypeRepository criteriaTypeRepository) {
        this.userDetailsService = userDetailsService;
        this.filterRepository = filterRepository;
        this.filterCriteriaRepository = filterCriteriaRepository;
        this.comparisonConditionRepository = comparisonConditionRepository;
        this.criteriaTypeRepository = criteriaTypeRepository;
    }

    public List<FilterDTO> getAllFilters() {
        final User user = userDetailsService.getLoggedInUser();
        return filterRepository.findAllByUserId(user.getId()).stream()
                .map(FilterDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<FilterCriteriaDTO> getFilterCriteria(Integer filterId) {
        final User user = userDetailsService.getLoggedInUser();
        final Filter filter = filterRepository.findById(filterId).orElse(null);
        if (filter == null || !filter.belongsTo(user)) {
            throw new EntityNotFoundException("Filter not found with id: " + filterId);
        }

        final List<FilterCriteria> filterCriteriaEntities = filterCriteriaRepository.findAllByFilterId(filterId);
        return filterCriteriaEntities.stream()
                .map(FilterCriteriaDTO::new)
                .toList();
    }

    @Transactional
    public FilterDTO updateFilter(Integer filterId, UpdateFilterRequest request) {
        final User user = userDetailsService.getLoggedInUser();
        Filter filter = filterRepository.findById(filterId).orElse(null);
        if (filter == null || !filter.belongsTo(user)) {
            throw new EntityNotFoundException("Filter not found with id: " + filterId);
        }

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
                    .setUser(user)
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
        final User user = userDetailsService.getLoggedInUser();
        final Filter filter = new Filter()
                .setName(request.getFilterName())
                .setUser(user)
                .setCreatedAt(new Date());

        final Filter savedFilter = filterRepository.save(filter);
        for (FilterCriteriaDTO criteriaDTO : request.getCriteria()) {
            final Integer conditionId = criteriaDTO.getComparisonCondition().getId();
            final ComparisonCondition comparisonCondition = comparisonConditionRepository.findById(conditionId)
                    .orElseThrow(() -> new EntityNotFoundException("Condition not found with id: " + conditionId));

            final FilterCriteria filterCriteria = new FilterCriteria()
                    .setUser(user)
                    .setFilter(savedFilter)
                    .setCriteriaType(comparisonCondition.getCriteriaType())
                    .setComparisonCondition(comparisonCondition)
                    .setValue(criteriaDTO.getCriteriaValue());
            filterCriteriaRepository.save(filterCriteria);
        }

        return new FilterDTO(savedFilter);
    }

    @Transactional
    public void deleteFilterAndCriteria(Integer filterId) {
        final User user = userDetailsService.getLoggedInUser();
        final Filter filter = filterRepository.findById(filterId).orElse(null);
        if (filter == null || !filter.belongsTo(user)) {
            throw new EntityNotFoundException("Filter not found with id: " + filterId);
        }
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
