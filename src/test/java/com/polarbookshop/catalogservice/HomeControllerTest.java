package com.polarbookshop.catalogservice;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.polarbookshop.catalogservice.config.PolarProperties;
import com.polarbookshop.catalogservice.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(HomeController.class)
@Import(SecurityConfig.class)
class HomeControllerTest {

  @Autowired MockMvc mockMvc;

  @MockBean PolarProperties polarProperties;

  @Test
  void homeShouldReturnWelcomeMessage() throws Exception {
    doReturn("Welcome to the book catalog!").when(polarProperties).getGreeting();

    mockMvc
        .perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(content().string("Welcome to the book catalog!"));
  }
}
