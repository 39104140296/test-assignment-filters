package eu.wisercat.filter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.wisercat.filter.model.FilterCriteria;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;

public class FilterCriteriaDTO {

    @JsonProperty("criteriaId")
    private Integer id;

    @NotNull
    private CriteriaTypeDTO criteriaType;

    @NotNull
    private ComparisonConditionDTO comparisonCondition;

    @NotEmpty
    private String criteriaValue;

    public FilterCriteriaDTO() {
    }

    public FilterCriteriaDTO(FilterCriteria filterCriteria) {
        this.id = filterCriteria.getId();
        this.criteriaValue = filterCriteria.getValue();
        this.criteriaType = new CriteriaTypeDTO(filterCriteria.getCriteriaType());
        this.comparisonCondition = new ComparisonConditionDTO(filterCriteria.getComparisonCondition());
    }

    public Integer getId() {
        return id;
    }

    public FilterCriteriaDTO setId(Integer id) {
        this.id = id;
        return this;
    }

    public CriteriaTypeDTO getCriteriaType() {
        return criteriaType;
    }

    public FilterCriteriaDTO setCriteriaType(CriteriaTypeDTO criteriaType) {
        this.criteriaType = criteriaType;
        return this;
    }

    public ComparisonConditionDTO getComparisonCondition() {
        return comparisonCondition;
    }

    public FilterCriteriaDTO setComparisonCondition(ComparisonConditionDTO comparisonCondition) {
        this.comparisonCondition = comparisonCondition;
        return this;
    }

    public String getCriteriaValue() {
        return criteriaValue;
    }

    public FilterCriteriaDTO setCriteriaValue(String criteriaValue) {
        this.criteriaValue = criteriaValue;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        FilterCriteriaDTO that = (FilterCriteriaDTO) o;
        return Objects.equals(id, that.id)
                && Objects.equals(criteriaType, that.criteriaType)
                && Objects.equals(comparisonCondition, that.comparisonCondition)
                && Objects.equals(criteriaValue, that.criteriaValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, criteriaType, comparisonCondition, criteriaValue);
    }
}
