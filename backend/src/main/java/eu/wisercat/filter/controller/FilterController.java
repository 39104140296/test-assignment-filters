package eu.wisercat.filter.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.wisercat.filter.controller.request.CreateFilterRequest;
import eu.wisercat.filter.controller.request.UpdateFilterRequest;
import eu.wisercat.filter.controller.response.GetFilterOptionsResponse;
import eu.wisercat.filter.dto.FilterCriteriaDTO;
import eu.wisercat.filter.dto.FilterDTO;
import eu.wisercat.filter.service.FilterService;

@RestController
@RequestMapping("/api/filters")
public class FilterController {

    private final FilterService filterService;

    public FilterController(FilterService filterService) {
        this.filterService = filterService;
    }

    @GetMapping
    public List<FilterDTO> getAllFilters() {
        return filterService.getAllFilters();
    }

    @GetMapping("/{filterId}")
    public List<FilterCriteriaDTO> getFilterCriteria(@PathVariable Integer filterId) {
        return filterService.getFilterCriteria(filterId);
    }

    @PutMapping("/{filterId}")
    public FilterDTO updateFilter(@PathVariable Integer filterId,
            @Valid @RequestBody UpdateFilterRequest request) {
        return filterService.updateFilter(filterId, request);
    }

    @PostMapping
    public FilterDTO createFilter(@Valid @RequestBody CreateFilterRequest request) {
        return filterService.createFilter(request);
    }

    @DeleteMapping("/{filterId}")
    public void deleteFilter(@PathVariable Integer filterId) {
        filterService.deleteFilterAndCriteria(filterId);
    }

    @GetMapping("/options")
    public GetFilterOptionsResponse getFilterOptions() {
        return filterService.getFilterOptions();
    }
}
