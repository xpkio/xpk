package io.xpk.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {

  @RequestMapping("/hello")
  public String temp(Principal user) {
    return "hello, " + user.getName();
  }
}
