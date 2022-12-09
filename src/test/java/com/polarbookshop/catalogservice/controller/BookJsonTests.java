package com.polarbookshop.catalogservice.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.polarbookshop.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

@JsonTest
class BookJsonTests {

  @Autowired JacksonTester<Book> json;

  @Test
  void testSerialize() throws Exception {
    var book = new Book("0123456789", "Title", "Author", 6.66);
    var jsonContent = json.write(book);
    assertThat(jsonContent).extractingJsonPathStringValue("@.isbn").isEqualTo(book.isbn());
    assertThat(jsonContent).extractingJsonPathStringValue("@.author").isEqualTo(book.author());
    assertThat(jsonContent).extractingJsonPathStringValue("@.title").isEqualTo(book.title());
    assertThat(jsonContent).extractingJsonPathNumberValue("@.price").isEqualTo(book.price());
  }
}
