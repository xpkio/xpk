package io.xpk.web.endpoint;

import io.xpk.domain.obj.XpkUser;
import io.xpk.domain.repo.XpkUserRepository;
import org.apache.commons.lang3.Validate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Objects;

@RestController
public class UserController {

  private final XpkUserRepository xpkUserRepository;

  public UserController(XpkUserRepository xpkUserRepository) {
    this.xpkUserRepository = xpkUserRepository;
  }

  @RequestMapping("/current-user")
  public XpkUser currentUser(Principal principal, HttpServletResponse response) {
    XpkUser user = xpkUserRepository.findByUsername(principal.getName());
    if (user == null) {
      response.setStatus(404);
    }
    return user;
  }

  @GetMapping("/user/{id}")
  public XpkUser user(@PathVariable Long id, HttpServletResponse response) {
    XpkUser user = xpkUserRepository.findOne(id);
    if (user == null) {
      response.setStatus(404);
    }
    return user;
  }

  @PutMapping("/user/{id}")
  public XpkUser putUser(@PathVariable Long id, @RequestBody XpkUser user) {
    Validate.isTrue(Objects.equals(id, user.getId()));
    return xpkUserRepository.save(user);
  }

  @DeleteMapping("/user/{id}")
  public void deleteUser(@PathVariable Long id, HttpServletResponse response) {
    if (xpkUserRepository.exists(id)) {
      xpkUserRepository.delete(id);
      response.setStatus(204);
    } else {
      response.setStatus(404);
    }
  }

  @PostMapping("/user")
  public XpkUser newUser(@RequestBody XpkUser user) {
    Validate.isTrue(user.getId() == null);
    return xpkUserRepository.save(user);
  }
}
