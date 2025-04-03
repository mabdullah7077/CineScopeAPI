package com.example.sakila.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "film") // Table name
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private Short id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "release_year")
    private Year releaseYear;

    @Column(name = "length")
    private Short length;

    @Column(name = "rating")
    private String rating;

    @ManyToOne // Many-to-one relationship with Language
    @JoinColumn(name = "language_id") // Foreign key for language
    private Language language;

    @ManyToMany // Many-to-many relationship with Actor
    @JoinTable(
            name = "film_actor",
            joinColumns = {@JoinColumn(name = "film_id")}, // Film FK
            inverseJoinColumns = {@JoinColumn(name = "actor_id")} // Actor FK
    )
    private List<Actor> actors;

    @ManyToMany // Many-to-many relationship with Category
    @JoinTable(
            name = "film_category",
            joinColumns = @JoinColumn(name = "film_id"), // Film FK
            inverseJoinColumns = @JoinColumn(name = "category_id") // Category FK
    )
    private List<Category> categories;

}
