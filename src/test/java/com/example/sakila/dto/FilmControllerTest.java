package com.example.sakila.dto;

import com.example.sakila.controllers.FilmController;
import com.example.sakila.dto.request.FilmPatchRequest;
import com.example.sakila.dto.request.FilmRequest;
import com.example.sakila.dto.response.FilmResponse;
import com.example.sakila.entities.Film;
import com.example.sakila.entities.Language;
import com.example.sakila.services.FilmService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Year;
import java.util.List;

import static org.mockito.Mockito.*;

public class FilmControllerTest {

    FilmService service = mock(FilmService.class);
    FilmController controller = new FilmController(service);

    @Test
    public void getFilmByIdReturnsFilmResponse() {
        final short id = 1;

        final var releaseYear = Year.of(2001);
        final var language = new Language((short) 1, "English");
        final var film = new Film(id, "Film Title", "Description", releaseYear, (short) 120, "PG", language, List.of(), List.of());

        final var expectedResponse = FilmResponse.from(film);
        doReturn(expectedResponse).when(service).getFilmById(id);

        final var actualResponse = controller.getFilmById(id);

        Assertions.assertEquals(expectedResponse.getId(), actualResponse.getId());
        Assertions.assertEquals(expectedResponse.getTitle(), actualResponse.getTitle());
        Assertions.assertEquals(expectedResponse.getDescription(), actualResponse.getDescription());
        Assertions.assertEquals(expectedResponse.getReleaseYear(), actualResponse.getReleaseYear());
        Assertions.assertEquals(expectedResponse.getLength(), actualResponse.getLength());
        Assertions.assertEquals(expectedResponse.getRating(), actualResponse.getRating());
        Assertions.assertEquals(expectedResponse.getLanguage(), actualResponse.getLanguage());
        Assertions.assertEquals(expectedResponse.getCategories(), actualResponse.getCategories());
        Assertions.assertEquals(expectedResponse.getActors(), actualResponse.getActors());
    }

    @Test
    public void listFilmsReturnsListOfFilmResponses() {
        final var releaseYear = Year.of(2001);
        final var language = new Language((short) 1, "English");

        final var films = List.of(
                new Film((short) 1, "Film Title 1", "Description 1", releaseYear, (short) 120, "PG", language, List.of(), List.of()),
                new Film((short) 2, "Film Title 2", "Description 2", releaseYear, (short) 130, "R", language, List.of(), List.of())
        );

        final var expectedResponse = films.stream()
                .map(FilmResponse::from)
                .toList();
        doReturn(expectedResponse).when(service).listFilms(null, null, null, null);

        final var actualResponse = controller.listFilms(null, null, null, null);

        Assertions.assertEquals(expectedResponse.size(), actualResponse.size());
        Assertions.assertEquals(expectedResponse.get(0).getId(), actualResponse.get(0).getId());
        Assertions.assertEquals(expectedResponse.get(1).getId(), actualResponse.get(1).getId());
    }

    @Test
    public void createFilmReturnsFilmResponseWhenFilmIsCreated() {
        final var releaseYear = Year.of(2022);
        final var language = new Language((short) 1, "English");
        final var filmRequest = new FilmRequest("New Film", "Description", releaseYear, (short) 110, "PG", language.getName(), List.of(), List.of());
        final var createdFilm = new Film((short) 1, "New Film", "Description", releaseYear, (short) 110, "PG", language, List.of(), List.of());
        final var expectedResponse = FilmResponse.from(createdFilm);

        doReturn(expectedResponse).when(service).createFilm(filmRequest);

        final var actualResponse = controller.createFilm(filmRequest);

        Assertions.assertEquals(expectedResponse.getId(), actualResponse.getId());
        Assertions.assertEquals(expectedResponse.getTitle(), actualResponse.getTitle());
        Assertions.assertEquals(expectedResponse.getDescription(), actualResponse.getDescription());
        Assertions.assertEquals(expectedResponse.getReleaseYear(), actualResponse.getReleaseYear());
        Assertions.assertEquals(expectedResponse.getLength(), actualResponse.getLength());
        Assertions.assertEquals(expectedResponse.getRating(), actualResponse.getRating());
        Assertions.assertEquals(expectedResponse.getLanguage(), actualResponse.getLanguage());
        Assertions.assertEquals(expectedResponse.getCategories(), actualResponse.getCategories());
        Assertions.assertEquals(expectedResponse.getActors(), actualResponse.getActors());
    }

    @Test
    public void patchFilmReturnsUpdatedFilmResponseWhenFilmIsUpdated() {
        final short id = 1;
        final var releaseYear = Year.of(2022);
        final var language = new Language((short) 1, "English");
        final var filmPatchRequest = new FilmPatchRequest("Updated Title", "Updated Description", releaseYear, (short) 110, null, null, null, null);

        final var updatedFilm = new Film(id, "Updated Title", "Updated Description", releaseYear, (short) 110, "PG", language, List.of(), List.of());
        final var expectedResponse = FilmResponse.from(updatedFilm);

        doReturn(expectedResponse).when(service).patchFilm(id, filmPatchRequest);

        final var actualResponse = controller.patchFilm(id, filmPatchRequest);

        Assertions.assertEquals(expectedResponse.getId(), actualResponse.getId());
        Assertions.assertEquals(expectedResponse.getTitle(), actualResponse.getTitle());
        Assertions.assertEquals(expectedResponse.getDescription(), actualResponse.getDescription());
        Assertions.assertEquals(expectedResponse.getReleaseYear(), actualResponse.getReleaseYear());
        Assertions.assertEquals(expectedResponse.getLength(), actualResponse.getLength());
        Assertions.assertEquals(expectedResponse.getRating(), actualResponse.getRating());
        Assertions.assertEquals(expectedResponse.getLanguage(), actualResponse.getLanguage());
        Assertions.assertEquals(expectedResponse.getCategories(), actualResponse.getCategories());
        Assertions.assertEquals(expectedResponse.getActors(), actualResponse.getActors());
    }


    @Test
    public void deleteFilmDeletesFilmWhenFilmIdIsValid() {
        final short id = 1;

        doNothing().when(service).deleteFilm(id);

        controller.deleteFilm(id);

        verify(service, times(1)).deleteFilm(id);
    }
}
