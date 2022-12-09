package com.polarbookshop.catalogservice.web;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.polarbookshop.catalogservice.domain.BookNotFoundException;
import com.polarbookshop.catalogservice.domain.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BookController.class)
class BookControllerTest {

  @Autowired MockMvc mockMvc;

  @MockBean BookService bookService;

  @Test
  void whenBookNotExistingThenShouldReturn404() throws Exception {
    String isbn = "0123456789";
    given(bookService.viewBookDetails(isbn)).willThrow(BookNotFoundException.class);

    mockMvc.perform(get("/books/" + isbn)).andExpect(status().isNotFound());
  }
}
