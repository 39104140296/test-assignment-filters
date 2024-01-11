package com.example.backend.dto;

import java.util.List;

public class CreateFilterDTO {
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