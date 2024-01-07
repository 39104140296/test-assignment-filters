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
@Table(name = "filter_criteria")
public class FilterCriteria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long criteria_id;

    @ManyToOne
    @JoinColumn(name = "filter_id", nullable = false)
    private Filter filter;

    @ManyToOne
    @JoinColumn(name = "criteria_type_id", nullable = false)
    private CriteriaType criteriaType;

    @ManyToOne
    @JoinColumn(name = "condition_id", nullable = false)
    private ComparisonCondition condition;

    @Column(name = "value", length = 255)
    private String value;

    public FilterCriteria() {
    }

    public FilterCriteria(Filter filter, CriteriaType criteriaType, ComparisonCondition condition, String value) {
        this.filter = filter;
        this.criteriaType = criteriaType;
        this.condition = condition;
        this.value = value;
    }

    public Long getCriteriaId() {
        return criteria_id;
    }

    public void setCriteriaId(Long criteria_id) {
        this.criteria_id = criteria_id;
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

    public ComparisonCondition getCondition() {
        return condition;
    }

    public void setCondition(ComparisonCondition condition) {
        this.condition = condition;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
