package eu.wisercat.filter.repository;

import eu.wisercat.filter.model.Filter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilterRepository extends JpaRepository<Filter, Integer> {

    List<Filter> findAllByUserId(Integer userId);
}
