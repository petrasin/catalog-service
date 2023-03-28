package com.polarbookshop.catalogservice.web;

import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.polarbookshop.catalogservice.config.SecurityConfig;
import com.polarbookshop.catalogservice.domain.BookNotFoundException;
import com.polarbookshop.catalogservice.domain.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BookController.class)
@Import(SecurityConfig.class)
class BookControllerMvcTest {

  @Autowired MockMvc mockMvc;

  @MockBean JwtDecoder jwtDecoder;

  @MockBean BookService bookService;

  @Test
  void whenBookNotExistingThenShouldReturn404() throws Exception {
    String isbn = "0123456789";
    given(bookService.viewBookDetails(isbn)).willThrow(BookNotFoundException.class);

    mockMvc.perform(get("/books/" + isbn)).andExpect(status().isNotFound());
  }

  @Test
  void whenDeleteBookWithEmployeeRoleThenShouldReturn204() throws Exception {
    var isbn = "7373731394";

    mockMvc
        .perform(
            delete("/books/" + isbn)
                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_employee"))))
        .andExpect(status().isNoContent());
  }

  @Test
  void whenDeleteBookWithCustomerRoleThenShouldReturn403() throws Exception {
    var isbn = "7373731394";

    mockMvc
        .perform(
            delete("/books/" + isbn)
                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_customer"))))
        .andExpect(status().isForbidden());
  }

  @Test
  void whenDeleteBookNotAuthenticatedThenShouldReturn401() throws Exception {
    var isbn = "7373731394";

    mockMvc.perform(delete("/books/" + isbn)).andExpect(status().isUnauthorized());
  }
}
