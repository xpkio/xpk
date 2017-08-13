package io.xpk.domain.repo;

import io.xpk.domain.obj.Spot;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface SpotRepository extends CrudRepository<Spot, Long> {
  Set<Spot> findByIdIn(Set<Long> ids);
}
