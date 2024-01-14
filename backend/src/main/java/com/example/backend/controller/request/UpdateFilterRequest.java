package com.example.backend.controller.request;

import com.example.backend.dto.FilterCriteriaDTO;
import java.util.List;

public class UpdateFilterRequest {
    private String filterName;
    private List<FilterCriteriaDTO> filterCriteria;

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public List<FilterCriteriaDTO> getFilterCriteria() {
        return filterCriteria;
    }

    public void setFilterCriteria(List<FilterCriteriaDTO> filterCriteria) {
        this.filterCriteria = filterCriteria;
    }
}
