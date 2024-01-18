package eu.wisercat.filter.controller.response;

import eu.wisercat.filter.dto.ComparisonConditionDTO;
import eu.wisercat.filter.dto.CriteriaTypeDTO;

import java.util.List;

public class GetFilterOptionsResponse {

    private List<CriteriaTypeDTO> criteriaTypes;
    private List<ComparisonConditionDTO> comparisonConditions;

    public GetFilterOptionsResponse() {
    }

    public GetFilterOptionsResponse(List<CriteriaTypeDTO> criteriaTypes,
                                    List<ComparisonConditionDTO> comparisonConditions) {
        this.criteriaTypes = criteriaTypes;
        this.comparisonConditions = comparisonConditions;
    }

    public List<CriteriaTypeDTO> getCriteriaTypes() {
        return criteriaTypes;
    }

    public GetFilterOptionsResponse setCriteriaTypes(List<CriteriaTypeDTO> criteriaTypes) {
        this.criteriaTypes = criteriaTypes;
        return this;
    }

    public List<ComparisonConditionDTO> getComparisonConditions() {
        return comparisonConditions;
    }

    public GetFilterOptionsResponse setComparisonConditions(List<ComparisonConditionDTO> comparisonConditions) {
        this.comparisonConditions = comparisonConditions;
        return this;
    }
}
