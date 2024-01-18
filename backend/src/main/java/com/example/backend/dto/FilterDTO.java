package com.example.backend.dto;

import com.example.backend.model.Filter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FilterDTO {

    @JsonProperty("filterId")
    private Integer id;

    @JsonProperty("filterName")
    private String name;

    public FilterDTO(Filter filter) {
        this.id = filter.getId();
        this.name = filter.getName();
    }

    public int getId() {
        return id;
    }

    public FilterDTO setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public FilterDTO setName(String name) {
        this.name = name;
        return this;
    }
}
