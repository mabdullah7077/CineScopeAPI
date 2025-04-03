package com.example.sakila.dto;

import com.example.sakila.dto.request.FilmPatchRequest;
import com.example.sakila.dto.request.FilmRequest;
import com.example.sakila.dto.response.FilmResponse;
import com.example.sakila.entities.Actor;
import com.example.sakila.entities.Category;
import com.example.sakila.entities.Film;
import com.example.sakila.entities.Language;
import com.example.sakila.repos.CategoryRepo;
import com.example.sakila.repos.FilmRepo;
import com.example.sakila.repos.ActorRepo;
import com.example.sakila.repos.LanguageRepo;
import com.example.sakila.services.FilmService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.Year;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class FilmServiceTest {

    private FilmRepo filmRepo;
    private ActorRepo actorRepo;
    private LanguageRepo languageRepo;
    private CategoryRepo categoryRepo;
    private FilmService filmService;

    @BeforeEach
    void setUp() {

        filmRepo = mock(FilmRepo.class);
        actorRepo = mock(ActorRepo.class);
        languageRepo = mock(LanguageRepo.class);
        categoryRepo = mock(CategoryRepo.class);

        filmService = new FilmService(filmRepo, actorRepo, languageRepo, categoryRepo);
    }

    @Test
    public void testListFilmsReturnsFilmsWhenNoFilter() {

        Film film1 = new Film();
        film1.setTitle("Film 1");
        film1.setRating("PG");
        film1.setReleaseYear(Year.of(2020));
        film1.setCategories(List.of());
        film1.setActors(List.of());
        film1.setLanguage(new Language());

        Film film2 = new Film();
        film2.setTitle("Film 2");
        film2.setRating("R");
        film2.setReleaseYear(Year.of(2021));
        film2.setCategories(List.of());
        film2.setActors(List.of());
        film2.setLanguage(new Language());

        List<Film> films = List.of(film1, film2);

        when(filmRepo.findAll()).thenReturn(films);

        List<FilmResponse> filmResponses = filmService.listFilms(null, null, null, null);

        assertEquals(2, filmResponses.size());

        verify(filmRepo, times(1)).findAll();
    }

    @Test
    public void testListFilmsFiltersByTitle() {

        Film film1 = new Film();
        film1.setTitle("Film 1");
        film1.setRating("PG");
        film1.setReleaseYear(Year.of(2020));
        film1.setCategories(List.of());
        film1.setActors(List.of());
        film1.setLanguage(new Language());

        Film film2 = new Film();
        film2.setTitle("Film 2");
        film2.setRating("R");
        film2.setReleaseYear(Year.of(2021));
        film2.setCategories(List.of());
        film2.setActors(List.of());
        film2.setLanguage(new Language());

        when(filmRepo.findByTitleContainingIgnoreCase("Film 1")).thenReturn(List.of(film1));

        List<FilmResponse> filmResponses = filmService.listFilms(null, null, null, "Film 1");

        assertEquals(1, filmResponses.size());
        assertEquals("Film 1", filmResponses.get(0).getTitle());

        verify(filmRepo, times(1)).findByTitleContainingIgnoreCase("Film 1");
    }

    @Test
    public void testListFilmsFiltersByCategory() {

        String categoryName = "Action";
        Category category = new Category();
        category.setName(categoryName);

        Film film1 = new Film();
        film1.setTitle("Film 1");
        film1.setCategories(List.of(category));
        film1.setActors(List.of());
        film1.setLanguage(new Language());

        Film film2 = new Film();
        film2.setTitle("Film 2");
        film2.setCategories(List.of(category));
        film2.setActors(List.of());
        film2.setLanguage(new Language());

        List<Film> films = List.of(film1, film2);

        when(filmRepo.findByCategoriesNameIgnoreCase(categoryName)).thenReturn(films);

        List<FilmResponse> filmResponses = filmService.listFilms(categoryName, null, null, null);

        assertEquals(2, filmResponses.size());
        assertEquals("Film 1", filmResponses.get(0).getTitle());
        assertEquals("Film 2", filmResponses.get(1).getTitle());

        verify(filmRepo, times(1)).findByCategoriesNameIgnoreCase(categoryName);
    }

    @Test
    public void testListFilmsFiltersByReleaseYear() {

        Integer releaseYear = 2020;

        Film film1 = new Film();
        film1.setTitle("Film 1");
        film1.setReleaseYear(Year.of(2020));
        film1.setActors(List.of());
        film1.setLanguage(new Language());
        film1.setCategories(List.of());

        Film film2 = new Film();
        film2.setTitle("Film 2");
        film2.setReleaseYear(Year.of(2020));
        film2.setActors(List.of());
        film2.setLanguage(new Language());
        film2.setCategories(List.of());

        List<Film> films = List.of(film1, film2);

        when(filmRepo.findByReleaseYear(Year.of(releaseYear))).thenReturn(films);

        List<FilmResponse> filmResponses = filmService.listFilms(null, releaseYear, null, null);

        assertEquals(2, filmResponses.size());
        assertEquals("Film 1", filmResponses.get(0).getTitle());
        assertEquals("Film 2", filmResponses.get(1).getTitle());

        verify(filmRepo, times(1)).findByReleaseYear(Year.of(releaseYear));
    }

    @Test
    public void testListFilmsFiltersByRating() {

        String rating = "PG";

        Film film1 = new Film();
        film1.setTitle("Film 1");
        film1.setRating(rating);
        film1.setActors(List.of());
        film1.setLanguage(new Language());
        film1.setCategories(List.of());

        Film film2 = new Film();
        film2.setTitle("Film 2");
        film2.setRating(rating);
        film2.setActors(List.of());
        film2.setLanguage(new Language());
        film2.setCategories(List.of());

        List<Film> films = List.of(film1, film2);

        when(filmRepo.findByRating(rating)).thenReturn(films);

        List<FilmResponse> filmResponses = filmService.listFilms(null, null, rating, null);

        assertEquals(2, filmResponses.size());
        assertEquals("Film 1", filmResponses.get(0).getTitle());
        assertEquals("Film 2", filmResponses.get(1).getTitle());

        verify(filmRepo, times(1)).findByRating(rating);
    }




    @Test
    public void testGetFilmByIdReturnsFilmResponseWhenFilmExists() {

        Short filmId = 1;
        Film film = new Film();
        film.setId(filmId);
        film.setTitle("Film 1");
        film.setActors(List.of());
        film.setCategories(List.of());
        film.setLanguage(new Language());

        when(filmRepo.findById(filmId)).thenReturn(Optional.of(film));

        FilmResponse filmResponse = filmService.getFilmById(filmId);

        assertNotNull(filmResponse);
        assertEquals("Film 1", filmResponse.getTitle());

        verify(filmRepo, times(1)).findById(filmId);
    }


    @Test
    public void testGetFilmByIdThrowsExceptionWhenFilmNotFound() {

        Short filmId = 1;

        when(filmRepo.findById(filmId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> filmService.getFilmById(filmId));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    public void testCreateFilmReturnsFilmResponse() {

        String title = "Film 1";
        String description = "A description of the film.";
        Year releaseYear = Year.of(2025);
        Short length = 120;
        String rating = "PG";
        String languageName = "English";
        Short actorId = 1;
        Short categoryId = 1;

        FilmRequest filmRequest = new FilmRequest(
                title,
                description,
                releaseYear,
                length,
                rating,
                languageName,
                List.of(actorId),
                List.of(categoryId)
        );

        Language language = new Language();
        language.setName("English");

        Actor actor = new Actor();
        actor.setId(actorId);
        actor.setFirstName("John");
        actor.setLastName("Smith");

        Category category = new Category();
        category.setId(categoryId);
        category.setName("Action");

        Film film = new Film();
        film.setTitle("Film 1");
        film.setLanguage(language);
        film.setActors(List.of(actor));
        film.setCategories(List.of(category));

        Film savedFilm = new Film();
        savedFilm.setId((short) 1);
        savedFilm.setTitle("Film 1");
        savedFilm.setLanguage(language);
        savedFilm.setActors(List.of(actor));
        savedFilm.setCategories(List.of(category));

        when(languageRepo.findByNameIgnoreCase(languageName)).thenReturn(Optional.of(language));
        when(actorRepo.findAllById(List.of(actorId))).thenReturn(List.of(actor));
        when(categoryRepo.findAllById(List.of(categoryId))).thenReturn(List.of(category));
        when(filmRepo.save(any(Film.class))).thenReturn(savedFilm);

        FilmResponse filmResponse = filmService.createFilm(filmRequest);

        assertNotNull(filmResponse);
        assertEquals("Film 1", filmResponse.getTitle());

        verify(filmRepo, times(1)).save(any(Film.class));
        verify(languageRepo, times(1)).findByNameIgnoreCase(languageName);
    }

    @Test
    public void testPatchFilmReturnsUpdatedFilmResponse() {

        Short filmId = 1;
        Film film = new Film();
        film.setId(filmId);
        film.setTitle("Film 1");
        film.setDescription("A description of the film");
        film.setReleaseYear(Year.of(2020));
        film.setLength((short) 100);
        film.setRating("PG");
        film.setLanguage(new Language());
        film.setActors(List.of(new Actor()));
        film.setCategories(List.of(new Category()));

        String updatedTitle = "Updated Film";
        String updatedDescription = "Updated description of the film.";
        Year updatedYear = Year.of(2018);
        String updatedRating = "PG-13";
        Short updatedLength = 130;
        FilmPatchRequest filmPatchRequest = new FilmPatchRequest(
                updatedTitle,
                updatedDescription,
                updatedYear,
                updatedLength,
                null,
                null,
                updatedRating,
                null
        );

        when(filmRepo.findById(filmId)).thenReturn(Optional.of(film));
        when(filmRepo.save(any(Film.class))).thenReturn(film);

        FilmResponse filmResponse = filmService.patchFilm(filmId, filmPatchRequest);

        assertNotNull(filmResponse);
        assertEquals(updatedTitle, filmResponse.getTitle());
        assertEquals(updatedDescription, filmResponse.getDescription());
        assertEquals(updatedYear, filmResponse.getReleaseYear());
        assertEquals(updatedLength, filmResponse.getLength());
        assertEquals(updatedRating, filmResponse.getRating());

        verify(filmRepo, times(1)).save(any(Film.class));
    }

    @Test
    public void testDeleteFilmDeletesFilmWhenFilmExists() {

        Short filmId = 1;
        Film film = new Film();
        film.setId(filmId);
        film.setTitle("Film 1");

        when(filmRepo.findById(filmId)).thenReturn(Optional.of(film));

        filmService.deleteFilm(filmId);

        verify(filmRepo, times(1)).delete(film);
    }

    @Test
    public void testListFilmsByLanguageReturnsCorrectFilms() {

        String languageName = "English";
        Language language = new Language();
        language.setId((short) 1);
        language.setName(languageName);

        Film film1 = new Film();
        film1.setTitle("Film 1");
        film1.setLanguage(language);
        film1.setActors(List.of());
        film1.setCategories(List.of());

        Film film2 = new Film();
        film2.setTitle("Film 2");
        film2.setLanguage(language);
        film2.setActors(List.of());
        film2.setCategories(List.of());

        List<Film> films = List.of(film1, film2);

        when(filmRepo.findByLanguageNameIgnoreCase(languageName)).thenReturn(films);

        List<FilmResponse> filmResponses = filmService.listFilmsByLanguage(languageName);

        assertEquals(2, filmResponses.size());
        assertEquals("Film 1", filmResponses.get(0).getTitle());
        assertEquals("Film 2", filmResponses.get(1).getTitle());

        verify(filmRepo, times(1)).findByLanguageNameIgnoreCase(languageName);
    }

    @Test
    public void testDeleteFilmThrowsExceptionWhenFilmNotFound() {

        Short filmId = 1;

        when(filmRepo.findById(filmId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> filmService.deleteFilm(filmId));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    public void testListFilmsFiltersByCategoryReturnsNotFoundWhenNoFilmsMatch() {
        String categoryName = "Non-Existent Category";

        // Mocking the repository to return an empty list
        when(filmRepo.findByCategoriesNameIgnoreCase(categoryName)).thenReturn(List.of());

        // Executing the service method
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            filmService.listFilms(categoryName, null, null, null);
        });

        // Assert that the exception has the expected status code
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

        // Verify that the repository's findByCategoriesNameIgnoreCase method was called once
        verify(filmRepo, times(1)).findByCategoriesNameIgnoreCase(categoryName);
    }

    @Test
    public void testListFilmsFiltersByRatingReturnsNotFoundWhenNoFilmsMatch() {
        String rating = "NC-17";

        // Mocking the repository to return an empty list
        when(filmRepo.findByRating(rating)).thenReturn(List.of());

        // Executing the service method
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            filmService.listFilms(null, null, rating, null);
        });

        // Assert that the exception has the expected status code
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

        // Verify that the repository's findByRating method was called once
        verify(filmRepo, times(1)).findByRating(rating);
    }

    @Test
    public void testListFilmsReturnsNotFoundWhenNoFiltersMatch() {
        String categoryName = "Action";
        Integer releaseYear = 2025;
        String rating = "PG";

        // Mocking all repository methods to return empty results
        when(filmRepo.findByCategoriesNameIgnoreCase(categoryName)).thenReturn(List.of());
        when(filmRepo.findByReleaseYear(Year.of(releaseYear))).thenReturn(List.of());
        when(filmRepo.findByRating(rating)).thenReturn(List.of());

        // Executing the service method
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            filmService.listFilms(categoryName, releaseYear, rating, null);
        });

        // Assert that the exception has the expected status code
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

        // Verify that the repository's methods were called once for each filter
        verify(filmRepo, times(1)).findByCategoriesNameIgnoreCase(categoryName);
        verify(filmRepo, times(1)).findByReleaseYear(Year.of(releaseYear));
        verify(filmRepo, times(1)).findByRating(rating);
    }
    @Test
    public void testUpdateFilmSetsActorsWhenActorIdsAreProvided() {
        Short filmId = 1;
        Film film = new Film();
        film.setId(filmId);
        film.setLanguage(new Language());
        film.setCategories(List.of(new Category()));

        List<Short> actorIds = List.of((short) 1, (short) 2);

        // Create actor entities
        Actor actor1 = new Actor();
        actor1.setId((short) 1);
        Actor actor2 = new Actor();
        actor2.setId((short) 2);

        // Mock repository to return actors by their IDs
        when(actorRepo.findAllById(actorIds)).thenReturn(List.of(actor1, actor2));

        // Update request with actorIds
        FilmPatchRequest filmPatchRequest = new FilmPatchRequest(null, null, null, null, actorIds, null, null, null);

        when(filmRepo.findById(filmId)).thenReturn(Optional.of(film));
        when(filmRepo.save(any(Film.class))).thenReturn(film);

        // Call the service method
        FilmResponse updatedFilmResponse = filmService.patchFilm(filmId, filmPatchRequest);

        // Assert that the actors were correctly set
        assertNotNull(updatedFilmResponse);
        assertEquals(2, updatedFilmResponse.getActors().size());
        assertEquals((short) 1, updatedFilmResponse.getActors().get(0).getId());
        assertEquals((short) 2, updatedFilmResponse.getActors().get(1).getId());

        // Verify that the repository method was called
        verify(actorRepo, times(1)).findAllById(actorIds);
    }

    @Test
    public void testUpdateFilmThrowsExceptionWhenLanguageIdIsInvalid() {
        Short filmId = 1;
        Film film = new Film();
        film.setId(filmId);

        Short languageId = (short) 1; // Non-existing language ID

        // Mock repository to return empty when trying to find language by ID
        when(languageRepo.findById(languageId)).thenReturn(Optional.empty());

        // Update request with languageId
        FilmPatchRequest filmPatchRequest = new FilmPatchRequest(null, null, null, null, null, languageId, null, null);

        when(filmRepo.findById(filmId)).thenReturn(Optional.of(film));

        // Execute and assert the exception
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            filmService.patchFilm(filmId, filmPatchRequest);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());

        // Verify that the repository method was called
        verify(languageRepo, times(1)).findById(languageId);
    }

    @Test
    public void testUpdateFilmSetsCategoriesWhenCategoryIdsAreProvided() {
        Short filmId = 1;
        Film film = new Film();
        film.setId(filmId);
        film.setActors(List.of());
        film.setLanguage(new Language());

        List<Short> categoryIds = List.of((short) 1, (short) 2);

        // Create category entities
        Category category1 = new Category();
        category1.setId((short) 1);
        Category category2 = new Category();
        category2.setId((short) 2);

        // Mock repository to return categories by their IDs
        when(categoryRepo.findAllById(categoryIds)).thenReturn(List.of(category1, category2));

        // Update request with categoryIds
        FilmPatchRequest filmPatchRequest = new FilmPatchRequest(null, null, null, null, null, null, null, categoryIds);

        when(filmRepo.findById(filmId)).thenReturn(Optional.of(film));
        when(filmRepo.save(any(Film.class))).thenReturn(film);

        // Call the service method
        FilmResponse updatedFilmResponse = filmService.patchFilm(filmId, filmPatchRequest);

        // Assert that the categories were correctly set
        assertNotNull(updatedFilmResponse);
        assertEquals(2, updatedFilmResponse.getCategories().size());
        assertEquals((short) 1, updatedFilmResponse.getCategories().get(0).getId());
        assertEquals((short) 2, updatedFilmResponse.getCategories().get(1).getId());

        // Verify that the repository method was called
        verify(categoryRepo, times(1)).findAllById(categoryIds);
    }

}
