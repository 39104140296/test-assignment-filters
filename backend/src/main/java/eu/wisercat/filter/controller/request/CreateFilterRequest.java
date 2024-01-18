package eu.wisercat.filter.controller.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

import eu.wisercat.filter.dto.FilterCriteriaDTO;

public class CreateFilterRequest {

    @NotEmpty
    private String filterName;

    @Valid
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
