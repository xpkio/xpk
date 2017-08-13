package io.xpk.web.endpoint;

import io.xpk.domain.obj.Post;
import io.xpk.domain.repo.PostRepository;
import org.apache.commons.lang3.Validate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PostController {

  private final PostRepository postRepository;

  public PostController(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  @PostMapping(value = "/post")
  @MessageMapping("/")
  @SendTo("/spot/")
  public Post post(Post post) {
    Validate.isTrue(post.getId() == null);
    postRepository.save(post);
    return post;
  }
}
