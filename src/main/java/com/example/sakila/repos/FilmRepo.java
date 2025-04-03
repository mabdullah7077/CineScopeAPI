package com.example.sakila.repos;

import com.example.sakila.entities.Actor;
import com.example.sakila.entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Year;
import java.util.List;

public interface FilmRepo extends JpaRepository<Film, Short> {


    List<Film> findByTitleContainingIgnoreCase(String title);

    List<Film> findByLanguageNameIgnoreCase(String name);

    List<Film> findByCategoriesNameIgnoreCase(String categoryName);

    List<Film> findByReleaseYear(Year releaseYear);

    List<Film> findByRating(String rating);

}
