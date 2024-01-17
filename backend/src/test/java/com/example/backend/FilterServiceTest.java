package com.example.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
import com.example.backend.service.FilterService;

import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FilterServiceTest {

    @InjectMocks
    private FilterService filterService;

    @Mock
    private FilterRepository filterRepository;

    @Mock
    private FilterCriteriaRepository filterCriteriaRepository;

    @Mock
    private CriteriaTypeRepository criteriaTypeRepository;

    @Mock
    private ComparisonConditionRepository comparisonConditionRepository;

    @Captor
    private ArgumentCaptor<Filter> filterCaptor;

    @Captor
    private ArgumentCaptor<FilterCriteria> criteriaCaptor;

    @Test
    public void getAllFilters_ShouldReturnListOfFilters() {
        // given
        final Filter filter = createFilter();
        when(filterRepository.findAll()).thenReturn(Collections.singletonList(filter));

        // when
        final List<FilterDTO> result = filterService.getAllFilters();

        // then
        assertEquals(1, result.size());
        assertEquals(filter.getFilterId(), result.get(0).getFilterId());
        assertEquals(filter.getFilterName(), result.get(0).getFilterName());
    }

    @Test
    public void getFilterCriteria() {
        // given
        final Filter filter = createFilter();
        final FilterCriteria filterCriteria = createFilterCriteria(filter);
        when(filterCriteriaRepository.findAllByFilter_FilterId(filter.getFilterId()))
                .thenReturn(List.of(filterCriteria));

        // when
        final List<FilterCriteriaDTO> result = filterService.getFilterCriteria(filter.getFilterId());

        // then
        assertEquals(1, result.size());
        assertEquals(filterCriteria.getCriteriaId(), result.getFirst().getCriteriaId());
        assertEquals(filterCriteria.getFilter().getFilterId(), result.getFirst().getFilterId());

        assertEquals(filterCriteria.getCriteriaType().getCriteriaTypeId(),
                result.getFirst().getCriteriaType().getCriteriaTypeId());
        assertEquals(filterCriteria.getCriteriaType().getTypeName(), result.getFirst().getCriteriaType().getTypeName());

        assertEquals(filterCriteria.getComparisonCondition().getCriteriaType().getCriteriaTypeId(),
                result.getFirst().getComparisonCondition().getCriteriaTypeId());
        assertEquals(filterCriteria.getComparisonCondition().getConditionId(),
                result.getFirst().getComparisonCondition().getConditionId());
        assertEquals(filterCriteria.getComparisonCondition().getConditionName(),
                result.getFirst().getComparisonCondition().getConditionName());

        assertEquals(filterCriteria.getCriteriaValue(), result.getFirst().getCriteriaValue());
    }

    @Test
    public void getFilterCriteriaReturnsEmptyListIfCriteriaNotFound() {
        // given
        final Filter filter = createFilter();
        when(filterCriteriaRepository.findAllByFilter_FilterId(filter.getFilterId()))
                .thenReturn(List.of());

        // when
        final List<FilterCriteriaDTO> result = filterService.getFilterCriteria(filter.getFilterId());

        // then
        assertEquals(0, result.size());
    }

    @Test
    public void getFilterCriteriaReturnsEmptyListWhenNoCriteriaExistForFilter() {
        // given
        final Filter filter = createFilter();
        when(filterCriteriaRepository.findAllByFilter_FilterId(filter.getFilterId()))
                .thenReturn(Collections.emptyList());

        // when
        final List<FilterCriteriaDTO> result = filterService.getFilterCriteria(filter.getFilterId());

        // then
        assertTrue(result.isEmpty());
    }

    @Test
    public void updateFilterUpdatesExistingFilterAndCriteria() {
        // given
        final Filter existingFilter = createFilter();
        final Filter updatedFilter = existingFilter.setFilterName("Updated filter");
        final FilterCriteria existingFilterCriteria = createFilterCriteria(existingFilter);
        final FilterCriteria updatedFilterCriteria = existingFilterCriteria.setCriteriaValue("Updated criteria value");

        final ComparisonConditionDTO comparisonConditionDTO = new ComparisonConditionDTO()
                .setConditionId(existingFilterCriteria.getComparisonCondition().getConditionId())
                .setCriteriaTypeId(
                        existingFilterCriteria.getComparisonCondition().getCriteriaType().getCriteriaTypeId())
                .setConditionName(existingFilterCriteria.getComparisonCondition().getConditionName());
        final CriteriaTypeDTO criteriaTypeDTO = new CriteriaTypeDTO()
                .setCriteriaTypeId(existingFilterCriteria.getCriteriaType().getCriteriaTypeId());
        final FilterCriteriaDTO filterCriteriaDTO = new FilterCriteriaDTO()
                .setFilterId(existingFilter.getFilterId())
                .setCriteriaId(existingFilterCriteria.getCriteriaId())
                .setCriteriaValue(updatedFilterCriteria.getCriteriaValue())
                .setComparisonCondition(comparisonConditionDTO)
                .setCriteriaType(criteriaTypeDTO);

        final UpdateFilterRequest updateRequest = new UpdateFilterRequest();
        updateRequest.setFilterName(updatedFilter.getFilterName());
        updateRequest.setFilterCriteria(List.of(filterCriteriaDTO));

        when(filterRepository.findById(existingFilter.getFilterId())).thenReturn(Optional.of(existingFilter));
        when(filterRepository.save(existingFilter)).thenReturn(updatedFilter);
        when(filterCriteriaRepository.findAllByFilter_FilterId(existingFilter.getFilterId()))
                .thenReturn(List.of(existingFilterCriteria));
        when(criteriaTypeRepository.findById(existingFilterCriteria.getCriteriaType().getCriteriaTypeId()))
                .thenReturn(Optional.of(existingFilterCriteria.getCriteriaType()));
        when(comparisonConditionRepository.findById(existingFilterCriteria.getComparisonCondition().getConditionId()))
                .thenReturn(Optional.of(existingFilterCriteria.getComparisonCondition()));

        // when
        final FilterDTO result = filterService.updateFilter(existingFilter.getFilterId(), updateRequest);

        // then
        verify(filterCriteriaRepository).deleteAll(List.of(existingFilterCriteria));
        verify(filterCriteriaRepository).save(any(FilterCriteria.class));
        assertEquals(updatedFilter.getFilterId(), result.getFilterId());
        assertEquals(updatedFilter.getFilterName(), result.getFilterName());
    }

    @Test
    public void updateFilterThrowsExceptionWhenFilterNotFound() {
        // given
        final int nonExistentFilterId = 1;
        final UpdateFilterRequest updateRequest = new UpdateFilterRequest();
        updateRequest.setFilterName("New Filter Name");

        when(filterRepository.findById(nonExistentFilterId)).thenReturn(Optional.empty());

        // when -> then
        assertThrows(EntityNotFoundException.class, () -> {
            filterService.updateFilter(nonExistentFilterId, updateRequest);
        });

        verify(filterRepository, never()).save(any(Filter.class));
    }

    @Test
    public void updateFilterThrowsExceptionWhenCriteriaTypeNotFound() {
        // given
        final Filter filter = createFilter();
        final CriteriaTypeDTO criteriaTypeDTO = new CriteriaTypeDTO().setCriteriaTypeId(1);
        final FilterCriteriaDTO filterCriteriaDTO = new FilterCriteriaDTO().setCriteriaType(criteriaTypeDTO);
        final UpdateFilterRequest updateRequest = new UpdateFilterRequest()
                .setFilterCriteria(List.of(filterCriteriaDTO));
        when(filterRepository.findById(filter.getFilterId())).thenReturn(Optional.of(filter));
        when(criteriaTypeRepository.findById(anyInt())).thenReturn(Optional.empty());

        // when -> then
        assertThrows(EntityNotFoundException.class,
                () -> filterService.updateFilter(filter.getFilterId(), updateRequest));
    }

    @Test
    public void updateFilterThrowsExceptionWhenComparisonConditionNotFound() {
        // given
        final Filter filter = createFilter();
        final CriteriaTypeDTO criteriaTypeDTO = new CriteriaTypeDTO().setCriteriaTypeId(1);
        final FilterCriteriaDTO filterCriteriaDTO = new FilterCriteriaDTO()
                .setCriteriaType(criteriaTypeDTO)
                .setComparisonCondition(new ComparisonConditionDTO().setConditionId(1));
        final UpdateFilterRequest updateRequest = new UpdateFilterRequest()
                .setFilterCriteria(List.of(filterCriteriaDTO));
        when(filterRepository.findById(filter.getFilterId())).thenReturn(Optional.of(filter));
        when(criteriaTypeRepository.findById(anyInt())).thenReturn(Optional.of(new CriteriaType()));
        when(comparisonConditionRepository.findById(anyInt())).thenReturn(Optional.empty());

        // when -> then
        assertThrows(EntityNotFoundException.class,
                () -> filterService.updateFilter(filter.getFilterId(), updateRequest));
    }

    @Test
    public void createFilterCreatesNewFilterAndCriteria() {
        // given
        final String filterName = "New filter";
        final CriteriaTypeDTO criteriaTypeDTO = new CriteriaTypeDTO().setCriteriaTypeId(1);
        final ComparisonConditionDTO comparisonConditionDTO = new ComparisonConditionDTO().setConditionId(1);
        final FilterCriteriaDTO filterCriteriaDTO = new FilterCriteriaDTO()
                .setCriteriaType(criteriaTypeDTO)
                .setComparisonCondition(comparisonConditionDTO);
        final CreateFilterRequest createRequest = new CreateFilterRequest()
                .setFilterName(filterName)
                .setCriteria(List.of(filterCriteriaDTO));
        final Filter filter = new Filter().setFilterId(1).setFilterName(filterName);
        when(filterRepository.save(any(Filter.class))).thenReturn(filter);
        when(criteriaTypeRepository.findById(anyInt())).thenReturn(Optional.of(new CriteriaType()));
        when(comparisonConditionRepository.findById(anyInt())).thenReturn(Optional.of(new ComparisonCondition()));

        // when
        final FilterDTO result = filterService.createFilter(createRequest);

        // then
        assertEquals(filter.getFilterId(), result.getFilterId());
        assertEquals(filter.getFilterName(), result.getFilterName());
    }

    @Test
    public void createFilterThrowsExceptionWhenCriteriaTypeNotFound() {
        // given
        final CriteriaTypeDTO criteriaTypeDTO = new CriteriaTypeDTO().setCriteriaTypeId(1);
        final FilterCriteriaDTO filterCriteriaDTO = new FilterCriteriaDTO().setCriteriaType(criteriaTypeDTO);
        final CreateFilterRequest createRequest = new CreateFilterRequest().setCriteria(List.of(filterCriteriaDTO));
        when(criteriaTypeRepository.findById(anyInt())).thenReturn(Optional.empty());

        // when -> then
        assertThrows(EntityNotFoundException.class, () -> filterService.createFilter(createRequest));
    }

    @Test
    public void createFilterThrowsExceptionWhenComparisonConditionNotFound() {
        // given
        final CriteriaTypeDTO criteriaTypeDTO = new CriteriaTypeDTO().setCriteriaTypeId(1);
        final ComparisonConditionDTO comparisonConditionDTO = new ComparisonConditionDTO().setConditionId(1);
        final FilterCriteriaDTO filterCriteriaDTO = new FilterCriteriaDTO()
                .setCriteriaType(criteriaTypeDTO)
                .setComparisonCondition(comparisonConditionDTO);
        final CreateFilterRequest createRequest = new CreateFilterRequest().setCriteria(List.of(filterCriteriaDTO));

        when(criteriaTypeRepository.findById(anyInt())).thenReturn(Optional.of(new CriteriaType()));
        when(comparisonConditionRepository.findById(anyInt())).thenReturn(Optional.empty());

        // when -> then
        assertThrows(EntityNotFoundException.class, () -> filterService.createFilter(createRequest));
    }

    @Test
    public void deleteFilterAndCriteria() {
        // given
        final Filter filter = createFilter();
        final FilterCriteria filterCriteria = createFilterCriteria(filter);
        when(filterRepository.findById(filter.getFilterId())).thenReturn(Optional.of(filter));
        when(filterCriteriaRepository.findAllByFilter_FilterId(filter.getFilterId()))
                .thenReturn(List.of(filterCriteria));

        // when
        filterService.deleteFilterAndCriteria(filter.getFilterId());

        // then
        verify(filterCriteriaRepository).deleteAll(List.of(filterCriteria));
        verify(filterRepository).delete(filter);
    }

    @Test
    public void deleteFilterAndCriteriaThrowsExceptionIfFilterNotFound() {
        // given
        final Filter filter = createFilter();
        when(filterRepository.findById(filter.getFilterId())).thenReturn(Optional.empty());

        // when -> then
        assertThrows(EntityNotFoundException.class, () -> filterService.deleteFilterAndCriteria(filter.getFilterId()));
    }

    @Test
    public void getFilterOptions() {
        // given
        final CriteriaType criteriaType = new CriteriaType()
                .setCriteriaTypeId(1)
                .setTypeName("typeName")
                .setDataType("dataType");
        final ComparisonCondition comparisonCondition = new ComparisonCondition()
                .setConditionName("test")
                .setConditionId(1293)
                .setCriteriaType(criteriaType);

        when(criteriaTypeRepository.findAll()).thenReturn(List.of(criteriaType));
        when(comparisonConditionRepository.findAll()).thenReturn(List.of(comparisonCondition));

        // when
        final GetFilterOptionsResponse result = filterService.getFilterOptions();

        // then
        assertEquals(criteriaType.getCriteriaTypeId(), result.getCriteriaTypes().getFirst().getCriteriaTypeId());
        assertEquals(criteriaType.getTypeName(), result.getCriteriaTypes().getFirst().getTypeName());
        assertEquals(comparisonCondition.getConditionId(),
                result.getComparisonConditions().getFirst().getConditionId());
        assertEquals(comparisonCondition.getConditionName(),
                result.getComparisonConditions().getFirst().getConditionName());
        assertEquals(comparisonCondition.getCriteriaType().getCriteriaTypeId(),
                result.getComparisonConditions().getFirst().getCriteriaTypeId());
    }

    private static Filter createFilter() {
        final Filter filter = new Filter();
        filter.setFilterId(123);
        filter.setFilterName("Some filter");
        return filter;
    }

    private static FilterCriteria createFilterCriteria(Filter filter) {
        final CriteriaType criteriaType = createCriteriaType();

        final ComparisonCondition comparisonCondition = new ComparisonCondition();
        comparisonCondition.setConditionId(123);
        comparisonCondition.setConditionName("conditionName");
        comparisonCondition.setCriteriaType(criteriaType);

        final FilterCriteria filterCriteria = new FilterCriteria();
        filterCriteria.setCriteriaId(456);
        filterCriteria.setCriteriaValue("criteriaValue");
        filterCriteria.setFilter(filter);
        filterCriteria.setCriteriaType(criteriaType);
        filterCriteria.setComparisonCondition(comparisonCondition);
        return filterCriteria;
    }

    private static CriteriaType createCriteriaType() {
        final CriteriaType criteriaType = new CriteriaType();
        criteriaType.setCriteriaTypeId(789);
        criteriaType.setTypeName("typeName");
        criteriaType.setDataType("dataType");
        return criteriaType;
    }
}
