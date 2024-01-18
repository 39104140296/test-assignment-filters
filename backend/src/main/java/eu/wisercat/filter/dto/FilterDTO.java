package eu.wisercat.filter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import eu.wisercat.filter.model.Filter;

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
