package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "filter_criteria")
public class FilterCriteria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer criteriaId;

    @ManyToOne
    @JoinColumn(name = "filter_id")
    @JsonBackReference
    private Filter filter;

    @ManyToOne
    @JoinColumn(name = "criteria_type_id")
    private CriteriaType criteriaType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "condition_id")
    private ComparisonCondition comparisonCondition;

    @Column(name = "\"value\"", nullable = false)
    private String value;

    public Integer getCriteriaId() {
        return criteriaId;
    }

    public Filter getFilter() {
        return filter;
    }

    public CriteriaType getCriteriaType() {
        return criteriaType;
    }

    public ComparisonCondition getComparisonCondition() {
        return comparisonCondition;
    }

    public String getValue() {
        return value;
    }
}
