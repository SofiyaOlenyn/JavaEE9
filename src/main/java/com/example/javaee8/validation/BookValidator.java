package com.example.javaee8.validation;

import com.example.javaee8.model.Book;
import com.example.javaee8.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class BookValidator {
    private final BookRepository bookRepository;

    public boolean isNewBookValid(final Book book) {

        if (book.getIsbn().isBlank() || book.getAuthor().isBlank() || book.getTitle().isBlank()) {
            throw new IllegalArgumentException();
        }
        if (bookRepository.findByIsbn(book.getIsbn()) == null) {
            throw new IllegalArgumentException("Book already exists");
        }

        return true;
    }

}
