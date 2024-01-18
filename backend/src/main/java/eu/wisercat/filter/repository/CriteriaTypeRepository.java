package eu.wisercat.filter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.wisercat.filter.model.CriteriaType;

@Repository
public interface CriteriaTypeRepository extends JpaRepository<CriteriaType, Integer> {
}
