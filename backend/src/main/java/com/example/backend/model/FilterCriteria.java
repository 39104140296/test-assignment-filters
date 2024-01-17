package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

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

    public FilterCriteria setCriteriaId(Integer criteriaId) {
        this.criteriaId = criteriaId;
        return this;
    }

    public Filter getFilter() {
        return filter;
    }

    public FilterCriteria setFilter(Filter filter) {
        this.filter = filter;
        return this;
    }

    public CriteriaType getCriteriaType() {
        return criteriaType;
    }

    public FilterCriteria setCriteriaType(CriteriaType criteriaType) {
        this.criteriaType = criteriaType;
        return this;
    }

    public ComparisonCondition getComparisonCondition() {
        return comparisonCondition;
    }

    public FilterCriteria setComparisonCondition(ComparisonCondition comparisonCondition) {
        this.comparisonCondition = comparisonCondition;
        return this;
    }

    public String getCriteriaValue() {
        return criteriaValue;
    }

    public FilterCriteria setCriteriaValue(String criteriaValue) {
        this.criteriaValue = criteriaValue;
        return this;
    }
}
