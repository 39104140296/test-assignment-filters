package com.example.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "comparison_conditions")
public class ComparisonCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer conditionId;

    @ManyToOne
    @JoinColumn(name = "criteria_type_id", nullable = false)
    private CriteriaType criteriaType;

    @Column(nullable = false)
    private String conditionName;

    // Getters
    public Integer getConditionId() {
        return conditionId;
    }

    public CriteriaType getCriteriaType() {
        return criteriaType;
    }

    public String getConditionName() {
        return conditionName;
    }

    // Setters
    public void setConditionId(Integer conditionId) {
        this.conditionId = conditionId;
    }

    public void setCriteriaType(CriteriaType criteriaType) {
        this.criteriaType = criteriaType;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }
}