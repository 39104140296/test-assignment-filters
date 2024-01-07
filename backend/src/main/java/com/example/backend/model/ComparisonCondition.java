package com.example.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    private Long condition_id;

    @ManyToOne
    @JoinColumn(name = "criteria_type_id", nullable = false)
    private CriteriaType criteriaType;

    @Column(name = "condition_name", nullable = false, length = 255)
    private String condition_name;

    public ComparisonCondition() {
    }

    public ComparisonCondition(CriteriaType criteriaType, String condition_name) {
        this.criteriaType = criteriaType;
        this.condition_name = condition_name;
    }

    public Long getConditionId() {
        return condition_id;
    }

    public void setConditionId(Long condition_id) {
        this.condition_id = condition_id;
    }

    public CriteriaType getCriteriaType() {
        return criteriaType;
    }

    public void setCriteriaType(CriteriaType criteriaType) {
        this.criteriaType = criteriaType;
    }

    public String getConditionName() {
        return condition_name;
    }

    public void setConditionName(String condition_name) {
        this.condition_name = condition_name;
    }
}
