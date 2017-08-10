package io.xpk.domain.repo;

import io.xpk.domain.obj.XpkUser;
import org.springframework.data.repository.CrudRepository;

public interface XpkUserRepository extends CrudRepository<XpkUser, Long> {

  XpkUser findByUsername(String username);
}
