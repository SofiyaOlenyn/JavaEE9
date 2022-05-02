package com.example.javaee8.controller;

import com.example.javaee8.model.Book;
import com.example.javaee8.model.User;
import com.example.javaee8.service.BookService;
import com.example.javaee8.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class BookController {

    BookService bookService;
    UserService userService;

    @Autowired
    public BookController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String getMain(Model model) {
        model.addAttribute("books", bookService.returnAllBooks());
        return "main";
    }


    @GetMapping("/book")
    public String addBookGt() {
        return "add-book";
    }

    @ResponseBody
    @PostMapping("/book")
    public ResponseEntity<String> addBookPost( @Valid @RequestBody Book book) {
        int status;
        String body;
        try {
            bookService.saveBook(book);
            status = 200;
            body = "Successfully created";
        } catch (Exception e) {
            status = 400;
            body = e.getMessage();
        }
        return ResponseEntity.status(status).body(body);
    }

    @ResponseBody
    @GetMapping("/get-books")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.status(200).body(bookService.returnAllBooks());
    }

    @ResponseBody
    @GetMapping("/search-books")
    public ResponseEntity<List<Book>> searchBook(@RequestParam(name = "getBy", required = false) String search) {
        if (search == null) {
            return ResponseEntity.status(200)
                    .header("h1", "Getting all books")
                    .body(bookService.returnAllBooks());
        }
        return ResponseEntity.ok().body(bookService.findBooks(search));
    }

    @GetMapping("/book/{isbn}")
    public String getBook(@PathVariable String isbn, Model model) {
        Book book = bookService.getBookByIsbn(isbn);
        model.addAttribute("book", book);
        return "view-book";
    }

    @GetMapping("/add-fav/{isbn}")
    public String addFavoriteBook(@PathVariable String isbn, @AuthenticationPrincipal UserDetails currentUser) {
        Optional<User> user = userService.findUserByUsername(currentUser.getUsername());
        Book book = bookService.getBookByIsbn(isbn);
        userService.addFavoriteBook(user, book);
        return "redirect:/favorite-books";
    }

    @GetMapping("/favorite-books")
    public String getFavouriteBooks(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        Optional<User> user = userService.findUserByUsername(currentUser.getUsername());
        model.addAttribute("books", user.get().getFavoriteBooks());
        return "favoriteBooks";
    }

    @PostMapping("/del-fav")
    public String deleteFavouriteBook(@RequestParam(name = "isbn") String isbn, @AuthenticationPrincipal UserDetails currentUser) {
        Optional<User> user = userService.findUserByUsername(currentUser.getUsername());
        Book book = bookService.getBookByIsbn(isbn);
        userService.removeFromFavorites(user, book);
        return "redirect:/favorite-books";
    }

}
