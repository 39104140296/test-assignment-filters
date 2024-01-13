package com.example.backend.controller.request;

import com.example.backend.dto.FilterCriteriaDTO;
import java.util.List;

public class CreateFilterRequest {
    private String filterName;
    private List<FilterCriteriaDTO> criteria;

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public List<FilterCriteriaDTO> getCriteria() {
        return criteria;
    }

    public void setCriteria(List<FilterCriteriaDTO> criteria) {
        this.criteria = criteria;
    }
}
