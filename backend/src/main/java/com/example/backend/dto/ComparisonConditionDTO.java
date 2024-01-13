package com.example.backend.dto;

public class ComparisonConditionDTO {

    private Integer conditionId;
    private Integer criteriaTypeId;
    private String conditionName;

    public ComparisonConditionDTO() {
    }

    public Integer getConditionId() {
        return conditionId;
    }

    public ComparisonConditionDTO setConditionId(Integer conditionId) {
        this.conditionId = conditionId;
        return this;
    }

    public Integer getCriteriaTypeId() {
        return criteriaTypeId;
    }

    public ComparisonConditionDTO setCriteriaTypeId(Integer criteriaTypeId) {
        this.criteriaTypeId = criteriaTypeId;
        return this;
    }

    public String getConditionName() {
        return conditionName;
    }

    public ComparisonConditionDTO setConditionName(String conditionName) {
        this.conditionName = conditionName;
        return this;
    }
}