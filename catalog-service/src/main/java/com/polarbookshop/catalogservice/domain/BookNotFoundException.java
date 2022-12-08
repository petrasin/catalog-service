package com.polarbookshop.catalogservice.domain;

public class BookNotFoundException extends RuntimeException {

  public BookNotFoundException(String isbn) {
    super("A book with ISBN %s was not found.".formatted(isbn));
  }
}
