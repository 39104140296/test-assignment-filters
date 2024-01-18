package com.example.backend.dto;

import com.example.backend.model.CriteriaType;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public class CriteriaTypeDTO {

    @JsonProperty("criteriaTypeId")
    private Integer id;

    private String typeName;

    public CriteriaTypeDTO() {
    }

    public CriteriaTypeDTO(CriteriaType criteriaType) {
        this.id = criteriaType.getId();
        this.typeName = criteriaType.getTypeName();
    }

    public Integer getId() {
        return id;
    }

    public CriteriaTypeDTO setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getTypeName() {
        return typeName;
    }

    public CriteriaTypeDTO setTypeName(String typeName) {
        this.typeName = typeName;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CriteriaTypeDTO that = (CriteriaTypeDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(typeName, that.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, typeName);
    }
}
