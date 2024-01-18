package eu.wisercat.filter.repository;

import eu.wisercat.filter.model.CriteriaType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CriteriaTypeRepository extends JpaRepository<CriteriaType, Integer> {
}
