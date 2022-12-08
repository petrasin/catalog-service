package com.polarbookshop.catalogservice.web;

import com.polarbookshop.catalogservice.domain.BookAlreadyExistsException;
import com.polarbookshop.catalogservice.domain.BookNotFoundException;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BookControllerAdvice {

  @ExceptionHandler(BookNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String bookNotFoundHandler(BookNotFoundException e) {
    return e.getMessage();
  }

  @ExceptionHandler(BookAlreadyExistsException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  String bookAlreadyExistsHandler(BookAlreadyExistsException e) {
    return e.getMessage();
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  Map<String, String> handleValidationException(MethodArgumentNotValidException e) {
    return e.getBindingResult().getAllErrors().stream()
        .collect(
            Collectors.toMap(err -> ((FieldError) err).getField(), err -> err.getDefaultMessage()));
  }
}
