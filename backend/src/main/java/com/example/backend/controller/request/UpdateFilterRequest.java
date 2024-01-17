package com.example.backend.controller.request;

import com.example.backend.dto.FilterCriteriaDTO;
import java.util.List;

public class UpdateFilterRequest {
    private String filterName;
    private List<FilterCriteriaDTO> filterCriteria;

    public String getFilterName() {
        return filterName;
    }

    public UpdateFilterRequest setFilterName(String filterName) {
        this.filterName = filterName;
        return this;
    }

    public List<FilterCriteriaDTO> getFilterCriteria() {
        return filterCriteria;
    }

    public UpdateFilterRequest setFilterCriteria(List<FilterCriteriaDTO> filterCriteria) {
        this.filterCriteria = filterCriteria;
        return this;
    }
}
