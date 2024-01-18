package eu.wisercat.filter.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "criteria_type")
public class CriteriaType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String typeName;

    @Column(nullable = false)
    private String dataType;

    public Integer getId() {
        return id;
    }

    public CriteriaType setId(Integer id) {
        this.id = id;
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
