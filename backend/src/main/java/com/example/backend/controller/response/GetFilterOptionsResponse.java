package com.example.backend.controller.response;

import com.example.backend.dto.CriteriaTypeDTO;
import com.example.backend.dto.ComparisonConditionDTO;
import java.util.List;

public class GetFilterOptionsResponse {
    private List<CriteriaTypeDTO> criteriaTypes;
    private List<ComparisonConditionDTO> comparisonConditions;

    public List<CriteriaTypeDTO> getCriteriaTypes() {
        return criteriaTypes;
    }

    public void setCriteriaTypes(List<CriteriaTypeDTO> criteriaTypes) {
        this.criteriaTypes = criteriaTypes;
    }

    public List<ComparisonConditionDTO> getComparisonConditions() {
        return comparisonConditions;
    }

    public void setComparisonConditions(List<ComparisonConditionDTO> comparisonConditions) {
        this.comparisonConditions = comparisonConditions;
    }
}
