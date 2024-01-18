package eu.wisercat.filter.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.wisercat.filter.model.Filter;

@Repository
public interface FilterRepository extends JpaRepository<Filter, Integer> {

    List<Filter> findAllByUserId(Integer userId);
}
