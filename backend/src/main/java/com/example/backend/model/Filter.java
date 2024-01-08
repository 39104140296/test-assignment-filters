package com.example.backend.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "filters")
public class Filter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer filterId;

    @Column(nullable = false)
    private String filterName;

    @Column(nullable = false)
    private Date createdAt;

    // @OneToMany(mappedBy = "filter", fetch = FetchType.LAZY, cascade =
    // CascadeType.ALL)
    // private List<FilterCriteria> filterCriteriaList;

    @OneToMany(mappedBy = "filter", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<FilterCriteria> filterCriteriaList;

    public Integer getFilterId() {
        return filterId;
    }

    public String getFilterName() {
        return filterName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public List<FilterCriteria> getFilterCriteriaList() {
        return filterCriteriaList;
    }
}