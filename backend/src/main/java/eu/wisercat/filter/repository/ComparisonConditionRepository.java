package eu.wisercat.filter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.wisercat.filter.model.ComparisonCondition;

@Repository
public interface ComparisonConditionRepository extends JpaRepository<ComparisonCondition, Integer> {
}
