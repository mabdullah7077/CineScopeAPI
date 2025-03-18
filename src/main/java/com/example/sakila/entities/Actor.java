package com.example.sakila.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "actor")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Actor {

    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    @Column(name = "actor_id") // Column mapping
    private Short id; // Actor ID

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Formula("concat(first_name, ' ', last_name)") // Concatenate first and last name
    @Setter(AccessLevel.NONE) // No setter for full name
    private String fullName;

    @ManyToMany // Many-to-many relationship with Film
    @JoinTable(
            name = "film_actor",
            joinColumns = {@JoinColumn(name = "actor_id")}, // Actor FK
            inverseJoinColumns = {@JoinColumn(name = "film_id")} // Film FK
    )
    private List<Film> films = new ArrayList<>(); // Films associated with actor

}
