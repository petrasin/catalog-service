package com.polarbookshop.catalogservice;

import com.polarbookshop.catalogservice.config.Greeter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  private final Greeter greeter;

  public HomeController(Greeter greeter) {
    this.greeter = greeter;
  }

  @GetMapping
  public String hello() {
    return greeter.greeting();
  }
}
