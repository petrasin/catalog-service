package com.polarbookshop.catalogservice;

import static org.assertj.core.api.Assertions.assertThat;

import com.polarbookshop.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CatalogServiceApplicationTests {

  @Autowired WebTestClient webTestClient;

  @Test
  void whenPostRequestThenBookCreated() {
    var expectedBook = new Book("1231231231", "Title", "Author", 9.99);

    webTestClient
        .post()
        .uri("/books")
        .bodyValue(expectedBook)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(Book.class)
        .value(
            actualBook -> {
              assertThat(actualBook).isNotNull();
              assertThat(actualBook.isbn()).isEqualTo(expectedBook.isbn());
            });
  }
}