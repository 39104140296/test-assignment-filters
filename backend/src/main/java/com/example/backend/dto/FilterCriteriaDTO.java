package com.example.backend.dto;

public class FilterCriteriaDTO {

    private Integer criteriaId;
    private Integer filterId;
    private Integer criteriaTypeId;
    private Integer conditionId;
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

    public Integer getCriteriaTypeId() {
        return criteriaTypeId;
    }

    public FilterCriteriaDTO setCriteriaTypeId(Integer criteriaTypeId) {
        this.criteriaTypeId = criteriaTypeId;
        return this;
    }

    public Integer getConditionId() {
        return conditionId;
    }

    public FilterCriteriaDTO setConditionId(Integer conditionId) {
        this.conditionId = conditionId;
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
