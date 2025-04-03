package com.example.sakila.dto;

import com.example.sakila.controllers.LanguageController;
import com.example.sakila.dto.request.LanguageRequest;
import com.example.sakila.dto.response.LanguageResponse;
import com.example.sakila.services.LanguageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

public class LanguageControllerTest {

    LanguageService service = mock(LanguageService.class);
    LanguageController controller = new LanguageController(service);

    @Test
    public void createLanguageReturnsLanguageResponseWhenLanguageIsCreated() {
        final var request = new LanguageRequest((short) 1 , "English");
        final var expectedResponse = new LanguageResponse((short) 1, "English");

        doReturn(expectedResponse).when(service).createLanguage(request);

        final var actualResponse = controller.createLanguage(request);

        Assertions.assertEquals(expectedResponse.getId(), actualResponse.getId());
        Assertions.assertEquals(expectedResponse.getName(), actualResponse.getName());
    }

    @Test
    public void getLanguageByIdReturnsLanguageResponse() {
        final short id = 1;
        final var expectedResponse = new LanguageResponse(id, "English");

        doReturn(expectedResponse).when(service).getLanguageById(id);

        final var actualResponse = controller.getLanguageById(id);

        Assertions.assertEquals(expectedResponse.getId(), actualResponse.getId());
        Assertions.assertEquals(expectedResponse.getName(), actualResponse.getName());
    }

    @Test
    public void updateLanguageReturnsUpdatedLanguageResponse() {
        final short id = 1;
        final var request = new LanguageRequest(id, "French");
        final var expectedResponse = new LanguageResponse(id, "French");

        doReturn(expectedResponse).when(service).updateLanguage(id, request);

        final var actualResponse = controller.updateLanguage(id, request);

        Assertions.assertEquals(expectedResponse.getId(), actualResponse.getId());
        Assertions.assertEquals(expectedResponse.getName(), actualResponse.getName());
    }

    @Test
    public void deleteLanguageDeletesLanguageWhenIdIsValid() {
        final short id = 1;

        doNothing().when(service).deleteLanguage(id);

        controller.deleteLanguage(id);

        verify(service, times(1)).deleteLanguage(id);
    }

    @Test
    public void getAllLanguagesReturnsListOfLanguageResponses() {
        final var languages = List.of(
                new LanguageResponse((short) 1, "English"),
                new LanguageResponse((short) 2, "Spanish")
        );

        doReturn(languages).when(service).getAllLanguages();

        final var actualResponse = controller.getAllLanguages();

        Assertions.assertEquals(languages.size(), actualResponse.size());
        Assertions.assertEquals(languages.get(0).getId(), actualResponse.get(0).getId());
        Assertions.assertEquals(languages.get(1).getName(), actualResponse.get(1).getName());
    }
}

