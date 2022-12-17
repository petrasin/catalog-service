package com.polarbookshop.catalogservice.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

public record Book(
    @NotBlank(message = "The book ISBN must be defined.")
        @Pattern(
            regexp = "^(?:\\d{10}|\\d{13})$",
            message = "The ISBN format " + "must be " + "valid.")
        String isbn,
    @NotBlank(message = "The book title must be defined.") String title,
    @NotBlank(message = "The book author must be defined.") String author,
    @Positive(message = "The book price must be greater than zero.") Double price) {}
