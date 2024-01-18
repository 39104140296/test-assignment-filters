package com.example.backend;

import com.example.backend.controller.FilterController;
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
import com.example.backend.service.FilterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FilterController.class)
public class FilterControllerTest {

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
        mockMvc.perform(get("/api/filters").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(filters)));
    }

    @Test
    public void getFilterCriteria() throws Exception {
        final FilterCriteriaDTO expected = new FilterCriteriaDTO(filterCriteria);
        when(filterService.getFilterCriteria(filter.getId())).thenReturn(List.of(expected));

        // when -> then
        mockMvc.perform(get("/api/filters/" + filter.getId())
                .contentType(MediaType.APPLICATION_JSON))
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
                .contentType(MediaType.APPLICATION_JSON)
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
                .contentType(MediaType.APPLICATION_JSON)
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
                .contentType(MediaType.APPLICATION_JSON)
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
                .contentType(MediaType.APPLICATION_JSON)
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
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteFilterAndCriteria() throws Exception {
        // when -> then
        mockMvc.perform(delete("/api/filters/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteFilterAndCriteriaThrows404IfFilterNotFound() throws Exception {
        // given
        doThrow(new EntityNotFoundException("Filter not found with id: 1"))
                .when(filterService).deleteFilterAndCriteria(anyInt());

        // when -> then
        mockMvc.perform(delete("/api/filters/1")
                .contentType(MediaType.APPLICATION_JSON))
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
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
