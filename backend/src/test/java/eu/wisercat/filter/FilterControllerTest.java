package eu.wisercat.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.wisercat.filter.controller.request.CreateFilterRequest;
import eu.wisercat.filter.controller.request.UpdateFilterRequest;
import eu.wisercat.filter.controller.response.GetFilterOptionsResponse;
import eu.wisercat.filter.dto.ComparisonConditionDTO;
import eu.wisercat.filter.dto.CriteriaTypeDTO;
import eu.wisercat.filter.dto.FilterCriteriaDTO;
import eu.wisercat.filter.dto.FilterDTO;
import eu.wisercat.filter.model.ComparisonCondition;
import eu.wisercat.filter.model.CriteriaType;
import eu.wisercat.filter.model.Filter;
import eu.wisercat.filter.model.FilterCriteria;
import eu.wisercat.filter.service.FilterService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Random;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = {FilterApplication.class})
public class FilterControllerTest {

    public static final String ADMIN_USERNAME = "admin";
    public static final String ADMIN_PASSWORD = "supersecurepassword";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FilterService filterService;

    private CriteriaType criteriaType;

    private ComparisonCondition comparisonCondition;

    private Filter filter;

    private FilterCriteria filterCriteria;

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
    }

    @Test
    public void getAllFilters() throws Exception {
        // given
        final FilterDTO filter1 = new FilterDTO(filter).setId(1);
        final FilterDTO filter2 = new FilterDTO(filter).setId(2);
        final List<FilterDTO> filters = List.of(filter1, filter2);
        when(filterService.getAllFilters()).thenReturn(filters);

        // when -> then
        mockMvc.perform(get("/api/filters")
                .with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD))
                .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(filters)));
    }

    @Test
    public void getAllFiltersIncorrectPasswordThrows401() throws Exception {
        // when -> then
        mockMvc.perform(get("/api/filters")
                .with(httpBasic(ADMIN_USERNAME, "incorrect"))
                .contentType(APPLICATION_JSON))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void getFilterCriteria() throws Exception {
        final FilterCriteriaDTO expected = new FilterCriteriaDTO(filterCriteria);
        when(filterService.getFilterCriteria(filter.getId())).thenReturn(List.of(expected));

        // when -> then
        mockMvc.perform(get("/api/filters/" + filter.getId())
                .with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD))
                .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(List.of(expected))));
    }

    @Test
    public void updateFilter() throws Exception {
        // given
        final UpdateFilterRequest request = new UpdateFilterRequest()
            .setFilterName("New name")
            .setFilterCriteria(List.of(new FilterCriteriaDTO(filterCriteria)));
        final FilterDTO updatedFilter = new FilterDTO(filter.setName(request.getFilterName()));
        when(filterService.updateFilter(filter.getId(), request)).thenReturn(updatedFilter);

        // when -> then
        mockMvc.perform(put("/api/filters/1")
                .with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD))
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());
    }

    @Test
    public void updateFilterThrows404IfFilterNotFound() throws Exception {
        // given
        final UpdateFilterRequest request = new UpdateFilterRequest()
            .setFilterName("New name")
            .setFilterCriteria(List.of(new FilterCriteriaDTO(filterCriteria)));
        when(filterService.updateFilter(filter.getId(), request))
            .thenThrow(new EntityNotFoundException("Filter not found with id: " + filter.getId()));

        // when -> then
        mockMvc.perform(put("/api/filters/" + filter.getId())
                .with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD))
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateFilterThrowsBadRequestIfMissingValidField() throws Exception {
        // given
        final UpdateFilterRequest request = new UpdateFilterRequest()
            .setFilterName(null)
            .setFilterCriteria(List.of(new FilterCriteriaDTO(filterCriteria)));

        // when -> then
        mockMvc.perform(put("/api/filters/" + filter.getId())
                .with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD))
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void createFilter() throws Exception {
        // given
        final CreateFilterRequest request = new CreateFilterRequest()
            .setFilterName(filter.getName())
            .setCriteria(List.of(new FilterCriteriaDTO(filterCriteria)));
        final FilterDTO createdFilter = new FilterDTO(filter);
        when(filterService.createFilter(request)).thenReturn(createdFilter);

        // when -> then
        mockMvc.perform(post("/api/filters")
                .with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD))
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());
    }

    @Test
    public void createFilterThrowsBadRequestIfMissingValidField() throws Exception {
        // given
        final CreateFilterRequest request = new CreateFilterRequest()
            .setFilterName(null)
            .setCriteria(List.of(new FilterCriteriaDTO(filterCriteria)));

        // when -> then
        mockMvc.perform(post("/api/filters")
                .with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD))
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteFilterAndCriteria() throws Exception {
        // when -> then
        mockMvc.perform(delete("/api/filters/1")
                .with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD))
                .contentType(APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void deleteFilterAndCriteriaThrows404IfFilterNotFound() throws Exception {
        // given
        doThrow(new EntityNotFoundException("Filter not found with id: 1"))
            .when(filterService).deleteFilterAndCriteria(anyInt());

        // when -> then
        mockMvc.perform(delete("/api/filters/1")
                .with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD))
                .contentType(APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    public void getFilterOptions() throws Exception {
        final GetFilterOptionsResponse response = new GetFilterOptionsResponse()
            .setCriteriaTypes(List.of(new CriteriaTypeDTO(criteriaType)))
            .setComparisonConditions(List.of(new ComparisonConditionDTO(comparisonCondition)));
        when(filterService.getFilterOptions()).thenReturn(response);

        // when -> then
        mockMvc.perform(get("/api/filters/options")
                .with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD))
                .contentType(APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}
