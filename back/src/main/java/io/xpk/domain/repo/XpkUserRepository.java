package io.xpk.domain.repo;

import io.xpk.domain.obj.XpkUser;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface XpkUserRepository extends CrudRepository<XpkUser, Long> {

  XpkUser findByUsername(String username);

  Boolean existsByUsername(String username);

  void deleteByUsername(String username);
}
