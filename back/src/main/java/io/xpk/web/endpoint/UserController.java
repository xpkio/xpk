package io.xpk.web.endpoint;

import io.xpk.domain.obj.XpkUser;
import io.xpk.domain.repo.XpkUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Objects;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

  private final XpkUserRepository xpkUserRepository;
  private final PasswordEncoder passwordEncoder;

  public UserController(XpkUserRepository xpkUserRepository, PasswordEncoder passwordEncoder) {
    this.xpkUserRepository = xpkUserRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @RequestMapping("/self")
  public XpkUser currentUser(Principal principal, HttpServletResponse response) {
    XpkUser user = xpkUserRepository.findByUsername(principal.getName());
    if (user == null) {
      log.warn("Someone's logged in as " + principal.getName() + " but they don't have a corresponding user in the db!");
      response.setStatus(404);
    }
    return user;
  }

  @PreAuthorize("#principal.getName() == #username")
  @GetMapping("/{username}")
  public XpkUser user(@PathVariable String username, Principal principal, HttpServletResponse response) {
    XpkUser user = xpkUserRepository.findByUsername(username);
    if (user == null) {
      response.setStatus(404);
    }
    return user;
  }

  @PreAuthorize("#principal.getName() == #username")
  @PutMapping("/{username}")
  public XpkUser putUser(@PathVariable String username, Principal principal, @RequestBody XpkUser user) {
    Validate.isTrue(Objects.equals(username, user.getUsername()));
    return xpkUserRepository.save(user);
  }

  @PreAuthorize("#principal.getName() == #username")
  @DeleteMapping("/{username}")
  public void deleteUser(@PathVariable String username, Principal principal, HttpServletResponse response) {
    if (xpkUserRepository.existsByUsername(username)) {
      xpkUserRepository.deleteByUsername(username);
      response.setStatus(204);
    } else {
      response.setStatus(404);
    }
  }

  @PostMapping
  public XpkUser newUser(@RequestBody XpkUser user) {
    Validate.isTrue(user.getId() == null, "You can't set the id in a new user request.");
    String salt = new RandomStringGenerator.Builder().withinRange('0', 'z').build().generate(13);
    user.setSalt(salt);
    user.setPassword(passwordEncoder.encode(user.getPassword() + user.getSalt()));
    return xpkUserRepository.save(user);
  }
}
