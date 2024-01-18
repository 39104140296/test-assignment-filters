package eu.wisercat.filter.repository;

import eu.wisercat.filter.model.ComparisonCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComparisonConditionRepository extends JpaRepository<ComparisonCondition, Integer> {
}
