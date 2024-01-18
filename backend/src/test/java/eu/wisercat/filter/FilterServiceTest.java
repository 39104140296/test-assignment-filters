package eu.wisercat.filter;

import eu.wisercat.filter.controller.request.CreateFilterRequest;
import eu.wisercat.filter.controller.request.UpdateFilterRequest;
import eu.wisercat.filter.controller.response.GetFilterOptionsResponse;
import eu.wisercat.filter.dto.ComparisonConditionDTO;
import eu.wisercat.filter.dto.CriteriaTypeDTO;
import eu.wisercat.filter.dto.FilterCriteriaDTO;
import eu.wisercat.filter.dto.FilterDTO;
import eu.wisercat.filter.model.*;
import eu.wisercat.filter.repository.ComparisonConditionRepository;
import eu.wisercat.filter.repository.CriteriaTypeRepository;
import eu.wisercat.filter.repository.FilterCriteriaRepository;
import eu.wisercat.filter.repository.FilterRepository;
import eu.wisercat.filter.security.UserDetailsService;
import eu.wisercat.filter.service.FilterService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FilterServiceTest {

    @Mock
    private UserDetailsService userDetailsService;

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

    private User user;

    private Filter filter;

    private FilterCriteria filterCriteria;

    private CriteriaType criteriaType;

    private ComparisonCondition comparisonCondition;

    @BeforeEach
    public void setup() {
        user = new User()
                .setId(new Random().nextInt(Integer.MAX_VALUE))
                .setUsername("user")
                .setPassword("password")
                .setCreatedAt(new Date());
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
                .setUser(user)
                .setName("Some filter");
        filterCriteria = new FilterCriteria()
                .setId(new Random().nextInt(Integer.MAX_VALUE))
                .setUser(user)
                .setValue("1500")
                .setFilter(filter)
                .setCriteriaType(criteriaType)
                .setComparisonCondition(comparisonCondition);
        closeable = MockitoAnnotations.openMocks(this);
        when(userDetailsService.getLoggedInUser()).thenReturn(user);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void getAllFilters() {
        // given
        when(filterRepository.findAllByUserId(user.getId())).thenReturn(List.of(filter));

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
        when(filterRepository.findById(filter.getId())).thenReturn(Optional.of(filter));
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
        when(filterRepository.findById(filter.getId())).thenReturn(Optional.of(filter));

        // when
        final List<FilterCriteriaDTO> result = filterService.getFilterCriteria(filter.getId());

        // then
        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    public void getFilterCriteriaReturnsEmptyListWhenNoCriteriaExistForFilter() {
        // given
        final Integer filterId = filter.getId();
        when(filterRepository.findById(filterId)).thenReturn(Optional.of(filter));
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
