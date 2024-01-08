package com.example.backend.model;

import jakarta.persistence.*;

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

    // Getters
    public Integer getCriteriaTypeId() {
        return criteriaTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getDataType() {
        return dataType;
    }

    // Setters
    public void setCriteriaTypeId(Integer criteriaTypeId) {
        this.criteriaTypeId = criteriaTypeId;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
