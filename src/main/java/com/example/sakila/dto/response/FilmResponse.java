package com.example.sakila.dto.response;

import com.example.sakila.dto.response.PartialFilmResponse;
import com.example.sakila.entities.Actor;
import com.example.sakila.entities.Film;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class FilmResponse {

    private final Short id;
    private final String title;
    private final String description;
    private final Year releaseYear;
    private final Short length;
    private final String rating;
    private final List<PartialActorResponse> actors;
    private final LanguageResponse language;
    private final List<CategoryResponse> categories;


    // Method to convert Film entity to FilmResponse DTO
    public static com.example.sakila.dto.response.FilmResponse from(Film film) {
        return new com.example.sakila.dto.response.FilmResponse(
                film.getId(),
                film.getTitle(),
                film.getDescription(),
                film.getReleaseYear(),
                film.getLength(),
                film.getRating(),
                film.getActors()
                        .stream()
                        .map(PartialActorResponse::from) // Convert each actor to PartialActorResponse
                        .toList(), // Collect the actors into a list
                LanguageResponse.from(film.getLanguage()), // Convert language to LanguageResponse DTO
                film.getCategories()
                        .stream()
                        .map(CategoryResponse::from) // Convert each category to CategoryResponse
                        .collect(Collectors.toList()) // Collect categories into a list
        );
    }

}
