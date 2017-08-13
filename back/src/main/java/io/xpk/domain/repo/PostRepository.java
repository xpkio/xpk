package io.xpk.domain.repo;

import io.xpk.domain.obj.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
