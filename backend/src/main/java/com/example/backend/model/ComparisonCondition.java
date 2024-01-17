package com.example.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

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

    public Integer getConditionId() {
        return conditionId;
    }

    public ComparisonCondition setConditionId(Integer conditionId) {
        this.conditionId = conditionId;
        return this;
    }

    public CriteriaType getCriteriaType() {
        return criteriaType;
    }

    public ComparisonCondition setCriteriaType(CriteriaType criteriaType) {
        this.criteriaType = criteriaType;
        return this;
    }

    public String getConditionName() {
        return conditionName;
    }

    public ComparisonCondition setConditionName(String conditionName) {
        this.conditionName = conditionName;
        return this;
    }
}
