package com.example.backend;

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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FilterServiceTest {

    @Mock
    private AutoCloseable closeable;

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

    private Filter filter;

    private FilterCriteria filterCriteria;

    private CriteriaType criteriaType;

    private ComparisonCondition comparisonCondition;

    @BeforeEach
    public void setup() {
        criteriaType = new CriteriaType()
                .setId(new Random().nextInt(Integer.MAX_VALUE))
                .setTypeName("amount")
                .setDataType("NUMBER");
        comparisonCondition = new ComparisonCondition()
                .setId(new Random().nextInt(Integer.MAX_VALUE))
                .setName("More")
                .setCriteriaType(criteriaType);
        filter = new Filter()
                .setId(new Random().nextInt(Integer.MAX_VALUE))
                .setName("Some filter");
        filterCriteria = new FilterCriteria()
                .setId(new Random().nextInt(Integer.MAX_VALUE))
                .setValue("1500")
                .setFilter(filter)
                .setCriteriaType(criteriaType)
                .setComparisonCondition(comparisonCondition);
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void getAllFilters() {
        // given
        when(filterRepository.findAll()).thenReturn(Collections.singletonList(filter));

        // when
        final List<FilterDTO> result = filterService.getAllFilters();

        // then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.getFirst().getId()).isEqualTo(filter.getId());
        assertThat(result.getFirst().getName()).isEqualTo(filter.getName());
    }

    @Test
    public void getFilterCriteria() {
        // given
        when(filterCriteriaRepository.findAllByFilterId(filter.getId())).thenReturn(List.of(filterCriteria));

        // when
        final List<FilterCriteriaDTO> result = filterService.getFilterCriteria(filter.getId());

        // then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.getFirst()).usingRecursiveComparison().isEqualTo(new FilterCriteriaDTO(filterCriteria));
    }

    @Test
    public void getFilterCriteriaReturnsEmptyListIfCriteriaNotFound() {
        // given
        when(filterCriteriaRepository.findAllByFilterId(filter.getId())).thenReturn(List.of());

        // when
        final List<FilterCriteriaDTO> result = filterService.getFilterCriteria(filter.getId());

        // then
        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    public void getFilterCriteriaReturnsEmptyListWhenNoCriteriaExistForFilter() {
        // given
        final Integer filterId = filter.getId();
        when(filterCriteriaRepository.findAllByFilterId(filterId)).thenReturn(List.of());

        // when
        final List<FilterCriteriaDTO> result = filterService.getFilterCriteria(filterId);

        // then
        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    public void updateFilterUpdatesExistingFilterAndCriteria() {
        // given
        final Filter updatedFilter = filter.setName("Updated filter");
        final FilterCriteria updatedFilterCriteria = filterCriteria.setValue("Updated criteria value");
        final FilterCriteriaDTO filterCriteriaDTO = new FilterCriteriaDTO(updatedFilterCriteria);
        final UpdateFilterRequest request = new UpdateFilterRequest()
                .setFilterName(updatedFilter.getName())
                .setFilterCriteria(List.of(filterCriteriaDTO));
        when(filterRepository.findById(filter.getId())).thenReturn(Optional.of(filter));
        when(filterRepository.save(filter)).thenReturn(updatedFilter);
        when(filterCriteriaRepository.findAllByFilterId(filter.getId()))
                .thenReturn(List.of(filterCriteria));
        when(criteriaTypeRepository.findById(filterCriteria.getCriteriaType().getId()))
                .thenReturn(Optional.of(filterCriteria.getCriteriaType()));
        when(comparisonConditionRepository.findById(filterCriteria.getComparisonCondition().getId()))
                .thenReturn(Optional.of(filterCriteria.getComparisonCondition()));

        // when
        final FilterDTO result = filterService.updateFilter(filter.getId(), request);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(new FilterDTO(filter));
        verify(filterCriteriaRepository).deleteAllByFilterId(filter.getId());
        verify(filterCriteriaRepository).save(any(FilterCriteria.class));
    }

    @Test
    public void updateFilterThrowsExceptionWhenFilterNotFound() {
        // given
        final Integer filterId = filter.getId();
        final UpdateFilterRequest request = new UpdateFilterRequest();

        when(filterRepository.findById(filterId)).thenReturn(Optional.empty());

        // when -> then
        assertThrows(EntityNotFoundException.class, () -> filterService.updateFilter(filterId, request));
    }

    @Test
    public void updateFilterThrowsExceptionWhenComparisonConditionNotFound() {
        // given
        final UpdateFilterRequest request = new UpdateFilterRequest()
                .setFilterCriteria(List.of(new FilterCriteriaDTO(filterCriteria)));
        when(filterRepository.findById(filter.getId())).thenReturn(Optional.of(filter));
        when(criteriaTypeRepository.findById(anyInt())).thenReturn(Optional.of(criteriaType));
        when(comparisonConditionRepository.findById(anyInt())).thenReturn(Optional.empty());

        // when -> then
        assertThrows(EntityNotFoundException.class, () -> filterService.updateFilter(filter.getId(), request));
    }

    @Test
    public void createFilterCreatesNewFilterAndCriteria() {
        // given
        final String filterName = "New filter";
        final CreateFilterRequest request = new CreateFilterRequest()
                .setFilterName(filterName)
                .setCriteria(List.of(new FilterCriteriaDTO(filterCriteria)));
        when(filterRepository.save(any(Filter.class))).thenReturn(filter);
        when(criteriaTypeRepository.findById(anyInt())).thenReturn(Optional.of(criteriaType));
        when(comparisonConditionRepository.findById(anyInt())).thenReturn(Optional.of(comparisonCondition));

        // when
        final FilterDTO result = filterService.createFilter(request);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(new FilterDTO(filter));
    }

    @Test
    public void createFilterThrowsExceptionWhenCriteriaTypeNotFound() {
        // given
        final CriteriaTypeDTO criteriaTypeDTO = new CriteriaTypeDTO().setId(1);
        final FilterCriteriaDTO filterCriteriaDTO = new FilterCriteriaDTO().setCriteriaType(criteriaTypeDTO);
        final CreateFilterRequest createRequest = new CreateFilterRequest().setCriteria(List.of(filterCriteriaDTO));
        when(criteriaTypeRepository.findById(anyInt())).thenReturn(Optional.empty());

        // when -> then
        assertThrows(EntityNotFoundException.class, () -> filterService.createFilter(createRequest));
    }

    @Test
    public void createFilterThrowsExceptionWhenComparisonConditionNotFound() {
        // given
        final CreateFilterRequest request = new CreateFilterRequest()
                .setCriteria(List.of(new FilterCriteriaDTO(filterCriteria)));
        when(criteriaTypeRepository.findById(anyInt())).thenReturn(Optional.of(criteriaType));
        when(comparisonConditionRepository.findById(anyInt())).thenReturn(Optional.empty());

        // when -> then
        assertThrows(EntityNotFoundException.class, () -> filterService.createFilter(request));
    }

    @Test
    public void deleteFilterAndCriteria() {
        // given
        when(filterRepository.findById(filter.getId())).thenReturn(Optional.of(filter));

        // when
        filterService.deleteFilterAndCriteria(filter.getId());

        // then
        verify(filterCriteriaRepository).deleteAllByFilterId(filter.getId());
        verify(filterRepository).delete(filter);
    }

    @Test
    public void deleteFilterAndCriteriaThrowsExceptionIfFilterNotFound() {
        // given
        when(filterRepository.findById(filter.getId())).thenReturn(Optional.empty());

        // when -> then
        assertThrows(EntityNotFoundException.class, () -> filterService.deleteFilterAndCriteria(filter.getId()));
    }

    @Test
    public void getFilterOptions() {
        // given
        when(criteriaTypeRepository.findAll()).thenReturn(List.of(criteriaType));
        when(comparisonConditionRepository.findAll()).thenReturn(List.of(comparisonCondition));
        final GetFilterOptionsResponse expected = new GetFilterOptionsResponse()
                .setCriteriaTypes(List.of(new CriteriaTypeDTO(criteriaType)))
                .setComparisonConditions(List.of(new ComparisonConditionDTO(comparisonCondition)));

        // when
        final GetFilterOptionsResponse result = filterService.getFilterOptions();

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }
}
