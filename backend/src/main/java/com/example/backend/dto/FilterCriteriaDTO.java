package com.example.backend.dto;

public class FilterCriteriaDTO {
    private Integer criteriaId;
    private Integer criteriaTypeId;
    private String criteriaTypeName;
    private Integer conditionId;
    private String conditionName;
    private String criteriaValue;

    public Integer getCriteriaId() {
        return criteriaId;
    }

    public void setCriteriaId(Integer criteriaId) {
        this.criteriaId = criteriaId;
    }

    public Integer getCriteriaTypeId() {
        return criteriaTypeId;
    }

    public void setCriteriaTypeId(Integer criteriaTypeId) {
        this.criteriaTypeId = criteriaTypeId;
    }

    public String getCriteriaTypeName() {
        return criteriaTypeName;
    }

    public void setCriteriaTypeName(String criteriaTypeName) {
        this.criteriaTypeName = criteriaTypeName;
    }

    public Integer getConditionId() {
        return conditionId;
    }

    public void setConditionId(Integer conditionId) {
        this.conditionId = conditionId;
    }

    public String getConditionName() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }

    public String getCriteriaValue() {
        return criteriaValue;
    }

    public void setCriteriaValue(String criteriaValue) {
        this.criteriaValue = criteriaValue;
    }
}
