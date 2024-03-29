package com.polarbookshop.catalogservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.polarbookshop.catalogservice.config.DataConfig;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

@DataJdbcTest
@Import(DataConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("integration")
class BookRepositoryJdbcTests {

  @Autowired private BookRepository bookRepository;

  @Autowired JdbcAggregateTemplate jdbcAggregateTemplate;

  @Test
  void findBookByIsbnWhenExisting() {
    var bookIsbn = "1234554321";
    var book = Book.of(bookIsbn, "Title", "Author", 13.90, "Polarsophia");
    jdbcAggregateTemplate.insert(book);
    Optional<Book> actualBook = bookRepository.findByIsbn(bookIsbn);

    assertThat(actualBook).isPresent();
    assertThat(actualBook.get().isbn()).isEqualTo(bookIsbn);
  }

  @Test
  void whenCreateBookNotAuthenticatedThenNoAuditMetadata() {
    var bookToCreate = Book.of("1234567891", "Title", "Author", 12.90, "Polarsophia");
    var createdBook = bookRepository.save(bookToCreate);

    assertThat(createdBook.createdBy()).isNull();
    assertThat(createdBook.lastModifiedBy()).isNull();
  }

  @Test
  @WithMockUser("bjorn")
  void whenCreateBookAuthenticatedThenAuditMetadata() {
    var bookToCreate = Book.of("1234567891", "Title", "Author", 12.90, "Polarsophia");
    var createdBook = bookRepository.save(bookToCreate);

    assertThat(createdBook.createdBy()).isEqualTo("bjorn");
    assertThat(createdBook.lastModifiedBy()).isEqualTo("bjorn");
  }
}
