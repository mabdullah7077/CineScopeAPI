package com.example.sakila.repos;

import com.example.sakila.entities.Actor;
import com.example.sakila.entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Year;
import java.util.List;

public interface FilmRepo extends JpaRepository<Film, Short> {

    // Find films by title
    List<Film> findByTitleContainingIgnoreCase(String title);

    // Find films by language name
    List<Film> findByLanguageNameIgnoreCase(String name);

    // Find films by category name
    List<Film> findByCategoriesNameIgnoreCase(String categoryName);

    // Find films by release year
    List<Film> findByReleaseYear(Year releaseYear);

    // Find films by rating
    List<Film> findByRating(String rating);

}
