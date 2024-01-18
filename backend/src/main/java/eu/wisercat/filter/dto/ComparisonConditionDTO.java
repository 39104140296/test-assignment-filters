package eu.wisercat.filter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.wisercat.filter.model.ComparisonCondition;

import java.util.Objects;

public class ComparisonConditionDTO {

    @JsonProperty("conditionId")
    private Integer id;

    private Integer criteriaTypeId;

    @JsonProperty("conditionName")
    private String name;

    public ComparisonConditionDTO() {
    }

    public ComparisonConditionDTO(ComparisonCondition comparisonCondition) {
        this.id = comparisonCondition.getId();
        this.name = comparisonCondition.getName();
        this.criteriaTypeId = comparisonCondition.getCriteriaType().getId();
    }

    public Integer getId() {
        return id;
    }

    public ComparisonConditionDTO setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getCriteriaTypeId() {
        return criteriaTypeId;
    }

    public ComparisonConditionDTO setCriteriaTypeId(Integer criteriaTypeId) {
        this.criteriaTypeId = criteriaTypeId;
        return this;
    }

    public String getName() {
        return name;
    }

    public ComparisonConditionDTO setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ComparisonConditionDTO that = (ComparisonConditionDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(criteriaTypeId, that.criteriaTypeId)
                && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, criteriaTypeId, name);
    }
}
