package com.example.javaee8.service;

import com.example.javaee8.model.Book;
import com.example.javaee8.repository.BookRepository;
import com.example.javaee8.validation.BookValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookValidator bookValidator;

    @Transactional
    public void saveBook(Book book) {
        log.info("Trying to create new book: {}", book.getIsbn());

        try {
            bookValidator.isNewBookValid(book);
            log.info("New book is created: {}", bookRepository.save(book));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Transactional
    public List<Book> returnAllBooks() {
        log.info("Getting all books");
        return bookRepository.findAll();
    }

    @Transactional
    public List<Book> findBooks(final String string) {
        log.info("Finding book: {}", string);
        return bookRepository.findAllWhereTitleLikeOrAuthorLikeOrIsbnLike(string);
    }

    @Transactional
    public Book getBookByIsbn(String isbn) {
        log.info("Finding book: {}", isbn);
        return bookRepository.findByIsbn(isbn);
    }

}

