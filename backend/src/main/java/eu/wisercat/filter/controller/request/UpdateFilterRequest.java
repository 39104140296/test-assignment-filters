package eu.wisercat.filter.controller.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Objects;

import eu.wisercat.filter.dto.FilterCriteriaDTO;

public class UpdateFilterRequest {

    @NotEmpty
    private String filterName;

    @Valid
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UpdateFilterRequest that = (UpdateFilterRequest) o;
        return Objects.equals(filterName, that.filterName) &&
                Objects.equals(filterCriteria, that.filterCriteria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filterName, filterCriteria);
    }
}
