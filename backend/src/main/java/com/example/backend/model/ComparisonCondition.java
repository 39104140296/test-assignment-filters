package com.example.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "comparison_conditions")
public class ComparisonCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer conditionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criteria_type_id", nullable = false)
    private CriteriaType criteriaType;

    @Column(nullable = false)
    private String conditionName;

    // Getters and setters
    public Integer getConditionId() {
        return conditionId;
    }

    public void setConditionId(Integer conditionId) {
        this.conditionId = conditionId;
    }

    public CriteriaType getCriteriaType() {
        return criteriaType;
    }

    public void setCriteriaType(CriteriaType criteriaType) {
        this.criteriaType = criteriaType;
    }

    public String getConditionName() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }
}