package com.example.backend.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "filters")
public class Filter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long filter_id;

    @Column(name = "filter_name", nullable = false, length = 255)
    private String filter_name;

    @Column(name = "created_at", nullable = false)
    private LocalDate created_at;

    public Filter() {
    }

    public Filter(String filter_name, LocalDate created_at) {
        this.filter_name = filter_name;
        this.created_at = created_at;
    }

    public Long getFilterId() {
        return filter_id;
    }

    public void setFilterId(Long filter_id) {
        this.filter_id = filter_id;
    }

    public String getFilterName() {
        return filter_name;
    }

    public void setFilterName(String filter_name) {
        this.filter_name = filter_name;
    }

    public LocalDate getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(LocalDate created_at) {
        this.created_at = created_at;
    }
}
