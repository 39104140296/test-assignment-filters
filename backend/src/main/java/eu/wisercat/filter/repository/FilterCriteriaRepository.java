package eu.wisercat.filter.repository;

import eu.wisercat.filter.model.FilterCriteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilterCriteriaRepository extends JpaRepository<FilterCriteria, Integer> {

    List<FilterCriteria> findAllByFilterId(Integer filterId);

    void deleteAllByFilterId(Integer filterId);
}
