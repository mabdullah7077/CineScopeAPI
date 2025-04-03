package com.example.sakila.dto;

import com.example.sakila.dto.request.LanguageRequest;
import com.example.sakila.dto.response.LanguageResponse;
import com.example.sakila.entities.Language;
import com.example.sakila.repos.LanguageRepo;
import com.example.sakila.services.LanguageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class LanguageServiceTest {

    private LanguageRepo languageRepo;
    private LanguageService languageService;
    private Language language;

    @BeforeEach
    void setUp() {

        languageRepo = mock(LanguageRepo.class);
        languageService = new LanguageService(languageRepo);
        language = new Language((short) 1, "English");

    }

    @Test
    void testGetWhenLanguageByIdFound() {
        when(languageRepo.findById((short) 1)).thenReturn(Optional.of(language));
        LanguageResponse response = languageService.getLanguageById((short) 1);
        assertEquals(language.getId(), response.getId());
        assertEquals(language.getName(), response.getName());
    }

    @Test
    void testGetWhenLanguageByIdNotFound() {
        when(languageRepo.findById((short) 1)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> languageService.getLanguageById((short) 1));
    }

    @Test
    void testGetAllLanguages() {
        Language language2 = new Language((short) 2, "Spanish");
        when(languageRepo.findAll()).thenReturn(List.of(language, language2));
        List<LanguageResponse> responses = languageService.getAllLanguages();
        assertEquals(2, responses.size());
        assertEquals("English", responses.get(0).getName());
        assertEquals("Spanish", responses.get(1).getName());
    }

    @Test
    void testCreateLanguage() {
        LanguageRequest request = new LanguageRequest((short) 2,"French");
        Language newLanguage = new Language((short) 3, request.getName());
        when(languageRepo.save(any(Language.class))).thenReturn(newLanguage);
        LanguageResponse response = languageService.createLanguage(request);
        assertEquals((short) 3, response.getId());
        assertEquals("French", response.getName());
    }

    @Test
    void testUpdateWhenLanguageFound() {
        LanguageRequest request = new LanguageRequest((short) 2,"German");
        when(languageRepo.findById((short) 1)).thenReturn(Optional.of(language));
        when(languageRepo.save(any(Language.class))).thenReturn(language);
        LanguageResponse response = languageService.updateLanguage((short) 1, request);
        assertEquals("German", response.getName());
    }

    @Test
    void testUpdateWhenLanguageNotFound() {
        LanguageRequest request = new LanguageRequest((short) 2, "German");
        when(languageRepo.findById((short) 1)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> languageService.updateLanguage((short) 1, request));
    }

    @Test
    void testDeleteWhenLanguageFound() {
        when(languageRepo.findById((short) 1)).thenReturn(Optional.of(language));
        doNothing().when(languageRepo).delete(language);
        assertDoesNotThrow(() -> languageService.deleteLanguage((short) 1));
        verify(languageRepo, times(1)).delete(language);
    }

    @Test
    void testDeleteWhenLanguageNotFound() {
        when(languageRepo.findById((short) 1)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> languageService.deleteLanguage((short) 1));
    }
}
