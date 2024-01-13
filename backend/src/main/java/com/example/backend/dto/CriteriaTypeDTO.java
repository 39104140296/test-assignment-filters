package com.example.backend.dto;

public class CriteriaTypeDTO {

    private Integer criteriaTypeId;
    private String typeName;
    // private String dataType;

    public Integer getCriteriaTypeId() {
        return criteriaTypeId;
    }

    public CriteriaTypeDTO setCriteriaTypeId(Integer criteriaTypeId) {
        this.criteriaTypeId = criteriaTypeId;
        return this;
    }

    public String getTypeName() {
        return typeName;
    }

    public CriteriaTypeDTO setTypeName(String typeName) {
        this.typeName = typeName;
        return this;
    }

    // public String getDataType() {
    // return dataType;
    // }

    // public CriteriaTypeDTO setDataType(String dataType) {
    // this.dataType = dataType;
    // return this;
    // }
}
