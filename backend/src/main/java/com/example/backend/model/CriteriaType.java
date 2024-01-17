package com.example.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "criteria_types")
public class CriteriaType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer criteriaTypeId;

    @Column(nullable = false)
    private String typeName;

    @Column(nullable = false)
    private String dataType;

    public Integer getCriteriaTypeId() {
        return criteriaTypeId;
    }

    public CriteriaType setCriteriaTypeId(Integer criteriaTypeId) {
        this.criteriaTypeId = criteriaTypeId;
        return this;
    }

    public String getTypeName() {
        return typeName;
    }

    public CriteriaType setTypeName(String typeName) {
        this.typeName = typeName;
        return this;
    }

    public String getDataType() {
        return dataType;
    }

    public CriteriaType setDataType(String dataType) {
        this.dataType = dataType;
        return this;
    }
}
