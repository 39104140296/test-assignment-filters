package com.example.backend.controller.request;

import com.example.backend.dto.FilterCriteriaDTO;
import java.util.List;

public class CreateFilterRequest {
    private String filterName;
    private List<FilterCriteriaDTO> criteria;

    public String getFilterName() {
        return filterName;
    }

    public CreateFilterRequest setFilterName(String filterName) {
        this.filterName = filterName;
        return this;
    }

    public List<FilterCriteriaDTO> getCriteria() {
        return criteria;
    }

    public CreateFilterRequest setCriteria(List<FilterCriteriaDTO> criteria) {
        this.criteria = criteria;
        return this;
    }
}
