package io.xpk.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class UserController {

  @RequestMapping
  public String temp() {
    return "hello world!";
  }
}
