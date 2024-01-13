package com.example.backend.dto;

public class FilterCriteriaDTO {

    private Integer criteriaId;
    private Integer filterId;
    private CriteriaTypeDTO criteriaType;
    private ComparisonConditionDTO comparisonCondition;
    private String criteriaValue;

    public Integer getCriteriaId() {
        return criteriaId;
    }

    public FilterCriteriaDTO setCriteriaId(Integer criteriaId) {
        this.criteriaId = criteriaId;
        return this;
    }

    public Integer getFilterId() {
        return filterId;
    }

    public FilterCriteriaDTO setFilterId(Integer filterId) {
        this.filterId = filterId;
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
}
