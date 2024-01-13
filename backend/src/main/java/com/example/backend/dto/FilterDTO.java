package com.example.backend.dto;

public class FilterDTO {

    private int filterId;
    private String filterName;

    public int getFilterId() {
        return filterId;
    }

    public FilterDTO setFilterId(int filterId) {
        this.filterId = filterId;
        return this;
    }

    public String getFilterName() {
        return filterName;
    }

    public FilterDTO setFilterName(String filterName) {
        this.filterName = filterName;
        return this;
    }
}
