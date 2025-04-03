package com.example.sakila.controllers;

import com.example.sakila.dto.request.FilmPatchRequest;
import com.example.sakila.dto.request.FilmRequest;
import com.example.sakila.dto.response.FilmResponse;
import com.example.sakila.services.FilmService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="*")
@RestController
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/films") // Get all films with optional filters
    public List<FilmResponse> listFilms(
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) Integer releaseYear,
            @RequestParam(required = false) String rating,
            @RequestParam(required = false) String title) {
        return filmService.listFilms(categoryName, releaseYear, rating, title);
    }

    @GetMapping("/films/{id}") // Get a film by ID
    public FilmResponse getFilmById(@PathVariable Short id) {
        return filmService.getFilmById(id);
    }

    @GetMapping("/films/language") // Get films by language
    public List<FilmResponse> listFilmsByLanguage(@RequestParam String language) {
        return filmService.listFilmsByLanguage(language);
    }

    @PostMapping("/films") // Create a new film
    public FilmResponse createFilm(@Valid @RequestBody FilmRequest data) { // validate film details
        return filmService.createFilm(data);
    }

    @PatchMapping("/films/{id}") // Update a film
    public FilmResponse patchFilm(@PathVariable Short id, @Valid @RequestBody FilmPatchRequest data) {
        return filmService.patchFilm(id, data);
    }

    @DeleteMapping("/films/{id}") // Delete a film by ID
    public void deleteFilm(@PathVariable Short id) {
        filmService.deleteFilm(id);
    }
}
