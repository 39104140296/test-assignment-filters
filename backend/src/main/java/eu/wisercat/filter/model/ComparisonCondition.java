package eu.wisercat.filter.model;

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
@Table(name = "comparison_condition")
public class ComparisonCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criteria_type_id", nullable = false)
    private CriteriaType criteriaType;

    @Column(nullable = false)
    private String name;

    public Integer getId() {
        return id;
    }

    public ComparisonCondition setId(Integer id) {
        this.id = id;
        return this;
    }

    public CriteriaType getCriteriaType() {
        return criteriaType;
    }

    public ComparisonCondition setCriteriaType(CriteriaType criteriaType) {
        this.criteriaType = criteriaType;
        return this;
    }

    public String getName() {
        return name;
    }

    public ComparisonCondition setName(String name) {
        this.name = name;
        return this;
    }
}
