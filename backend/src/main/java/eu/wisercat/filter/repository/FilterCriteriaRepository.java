package eu.wisercat.filter.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.wisercat.filter.model.FilterCriteria;

@Repository
public interface FilterCriteriaRepository extends JpaRepository<FilterCriteria, Integer> {

    List<FilterCriteria> findAllByFilterId(Integer filterId);

    void deleteAllByFilterId(Integer filterId);
}
