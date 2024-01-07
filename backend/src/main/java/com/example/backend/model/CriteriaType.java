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
    private Long criteria_type_id;

    @Column(name = "type_name", nullable = false, length = 255)
    private String type_name;

    @Column(name = "data_type", nullable = false, length = 255)
    private String data_type;

    public CriteriaType() {
    }

    public CriteriaType(String type_name, String data_type) {
        this.type_name = type_name;
        this.data_type = data_type;
    }

    public Long getCriteriaTypeId() {
        return criteria_type_id;
    }

    public void setCriteriaTypeId(Long criteria_type_id) {
        this.criteria_type_id = criteria_type_id;
    }

    public String getTypeName() {
        return type_name;
    }

    public void setTypeName(String type_name) {
        this.type_name = type_name;
    }

    public String getDataType() {
        return data_type;
    }

    public void setDataType(String data_type) {
        this.data_type = data_type;
    }
}
