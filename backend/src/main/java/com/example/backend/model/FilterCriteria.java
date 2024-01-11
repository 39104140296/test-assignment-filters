package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "filter_criteria")
public class FilterCriteria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer criteriaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filter_id")
    @JsonBackReference
    private Filter filter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criteria_type_id")
    private CriteriaType criteriaType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "condition_id")
    private ComparisonCondition comparisonCondition;

    @Column(name = "criteria_value", nullable = false)
    private String criteriaValue;

    public Integer getCriteriaId() {
        return criteriaId;
    }

    public void setCriteriaId(Integer criteriaId) {
        this.criteriaId = criteriaId;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public CriteriaType getCriteriaType() {
        return criteriaType;
    }

    public void setCriteriaType(CriteriaType criteriaType) {
        this.criteriaType = criteriaType;
    }

    public ComparisonCondition getComparisonCondition() {
        return comparisonCondition;
    }

    public void setComparisonCondition(ComparisonCondition comparisonCondition) {
        this.comparisonCondition = comparisonCondition;
    }

    public String getCriteriaValue() {
        return criteriaValue;
    }

    public void setCriteriaValue(String criteriaValue) {
        this.criteriaValue = criteriaValue;
    }
}