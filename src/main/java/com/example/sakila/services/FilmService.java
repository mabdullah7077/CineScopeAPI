package com.example.sakila.services;

import com.example.sakila.dto.request.FilmPatchRequest;
import com.example.sakila.dto.request.FilmRequest;
import com.example.sakila.dto.response.FilmResponse;
import com.example.sakila.entities.Actor;
import com.example.sakila.entities.Category;
import com.example.sakila.entities.Film;
import com.example.sakila.entities.Language;
import com.example.sakila.repos.ActorRepo;
import com.example.sakila.repos.CategoryRepo;
import com.example.sakila.repos.FilmRepo;
import com.example.sakila.repos.LanguageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final FilmRepo filmRepo;
    private final ActorRepo actorRepo;
    private final LanguageRepo languageRepo;
    private final CategoryRepo categoryRepo;

    @Autowired
    public FilmService(FilmRepo filmRepo, ActorRepo actorRepo, LanguageRepo languageRepo, CategoryRepo categoryRepo) {
        this.filmRepo = filmRepo;
        this.actorRepo = actorRepo;
        this.languageRepo = languageRepo;
        this.categoryRepo = categoryRepo;
    }

    // List films based on optional filters: category, release year, rating, and title
    public List<FilmResponse> listFilms(String categoryName, Integer releaseYear, String rating, String title) {
        List<Film> films = filmRepo.findAll();

        if (title != null && !title.isEmpty()) {
            films = filmRepo.findByTitleContainingIgnoreCase(title);
        }

        if (categoryName != null && !categoryName.isEmpty()) {
            films = filmRepo.findByCategoriesNameIgnoreCase(categoryName);
        }

        if (releaseYear != null) {
            films = filmRepo.findByReleaseYear(Year.of(releaseYear));
        }

        if (rating != null && !rating.isEmpty()) {
            films = filmRepo.findByRating(rating);
        }

        if (films.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return films.stream()
                .map(FilmResponse::from)
                .collect(Collectors.toList());
    }

    // Get a film by its ID
    public FilmResponse getFilmById(Short id) {
        return filmRepo.findById(id)
                .map(FilmResponse::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // List films by language
    public List<FilmResponse> listFilmsByLanguage(String language) {
        return filmRepo.findByLanguageNameIgnoreCase(language)
                .stream()
                .map(FilmResponse::from)
                .collect(Collectors.toList());
    }

    // Create a new film
    public FilmResponse createFilm(FilmRequest data) {
        // Validate language
        Language language = languageRepo.findByNameIgnoreCase(data.getLanguageName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Language not found"));

        // Fetch actors and categories by IDs
        List<Actor> actors = actorRepo.findAllById(data.getActorIds());
        List<Category> categories = categoryRepo.findAllById(data.getCategoryIds());

        // Create and save the film
        Film film = new Film();
        film.setTitle(data.getTitle());
        film.setDescription(data.getDescription());
        film.setReleaseYear(data.getReleaseYear());
        film.setLength(data.getLength());
        film.setRating(data.getRating());
        film.setLanguage(language);
        film.setActors(actors);
        film.setCategories(categories);

        Film savedFilm = filmRepo.save(film);
        return FilmResponse.from(savedFilm);
    }

    // Update an existing film with patch request
    public FilmResponse patchFilm(Short id, FilmPatchRequest data) {
        Film film = filmRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // Apply updates from the patch request
        if (data.getTitle() != null) film.setTitle(data.getTitle());
        if (data.getDescription() != null) film.setDescription(data.getDescription());
        if (data.getReleaseYear() != null) film.setReleaseYear(data.getReleaseYear());
        if (data.getLength() != null) film.setLength(data.getLength());
        if (data.getRating() != null) film.setRating(data.getRating());

        if (data.getActorIds() != null) {
            List<Actor> actors = actorRepo.findAllById(data.getActorIds());
            film.setActors(actors);
        }

        if (data.getLanguageId() != null) {
            Language language = languageRepo.findById(data.getLanguageId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
            film.setLanguage(language);
        }

        if (data.getCategoryIds() != null) {
            List<Category> categories = categoryRepo.findAllById(data.getCategoryIds());
            film.setCategories(categories);
        }

        Film updatedFilm = filmRepo.save(film);
        return FilmResponse.from(updatedFilm);
    }

    // Delete a film by ID
    public void deleteFilm(Short id) {
        Film film = filmRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        filmRepo.delete(film);
    }
}
