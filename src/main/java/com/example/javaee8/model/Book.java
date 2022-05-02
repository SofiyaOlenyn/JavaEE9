package com.example.javaee8.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Entity
@Table(name = "books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "title can not be empty")
    @Column(name = "title")
    private String title;

    @NotEmpty(message = "author can not be empty")
    @Column(name = "author")
    private String author;

    @NotEmpty(message = "isbn can not be empty")
    @Pattern(regexp = "^(\\d{13})?$", message = "ISBN must contain 13 digits")
    @Column(name = "isbn")
    private String isbn;

    @ManyToMany(mappedBy = "favoriteBooks")
    @JsonIgnore
    private Set<User> likedByUsers;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

}
