package com.polarbookshop.catalogservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "polar")
public record PolarProperties(String greeting, Testdata testdata) implements Greeter {

  record Testdata(boolean enabled) {}
}
