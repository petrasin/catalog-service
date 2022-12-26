package com.polarbookshop.catalogservice.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.polarbookshop.catalogservice.domain.Book;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

@JsonTest
class BookJsonTests {

    @Autowired
    JacksonTester<Book> json;

    @Test
    void testSerialize() throws Exception {
        var now = Instant.now();
        var book = new Book(1L, "0123456789", "Title", "Author", 6.66, now, now, 0);
        var jsonContent = json.write(book);
        assertThat(jsonContent).extractingJsonPathNumberValue("@.id").isEqualTo(book.id().intValue());
        assertThat(jsonContent).extractingJsonPathStringValue("@.isbn").isEqualTo(book.isbn());
        assertThat(jsonContent).extractingJsonPathStringValue("@.author").isEqualTo(book.author());
        assertThat(jsonContent).extractingJsonPathStringValue("@.title").isEqualTo(book.title());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.price").isEqualTo(book.price());
        assertThat(jsonContent)
                .extractingJsonPathStringValue("@.createdDate")
                .isEqualTo(now.toString());
        assertThat(jsonContent)
                .extractingJsonPathStringValue("@.lastModifiedDate")
                .isEqualTo(now.toString());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.version").isEqualTo(book.version());
    }

    @Test
    void testDeserialize() throws Exception {
        var instant = Instant.parse("2021-09-07T22:50:37.135029Z");
        var content =
                """
                        {
                            "id": 394,
                            "isbn": "1234567890",
                            "title": "Title",
                            "author": "Author",
                            "price": 9.90,
                            "createdDate": "2021-09-07T22:50:37.135029Z",
                            "lastModifiedDate": "2021-09-07T22:50:37.135029Z",
                            "version": 21
                        }
                        """;
        assertThat(json.parse(content))
                .usingRecursiveComparison()
                .isEqualTo(new Book(394L, "1234567890", "Title", "Author", 9.90, instant, instant, 21));
    }
}
