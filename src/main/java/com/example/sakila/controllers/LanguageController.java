package com.example.sakila.controllers;

import com.example.sakila.dto.request.LanguageRequest;
import com.example.sakila.dto.response.LanguageResponse;
import com.example.sakila.services.LanguageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/languages")
public class LanguageController {

    private final LanguageService languageService;

    @Autowired
    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LanguageResponse createLanguage(@Valid @RequestBody LanguageRequest languageRequest) {
        return languageService.createLanguage(languageRequest);
    }

    @GetMapping("/{id}")
    public LanguageResponse getLanguageById(@PathVariable Short id) {
        return languageService.getLanguageById(id);
    }

    @PutMapping("/{id}")
    public LanguageResponse updateLanguage(@PathVariable Short id, @Valid @RequestBody LanguageRequest languageRequest) {
        return languageService.updateLanguage(id, languageRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLanguage(@PathVariable Short id) {
        languageService.deleteLanguage(id);
    }

    @GetMapping
    public List<LanguageResponse> getAllLanguages() {
        return languageService.getAllLanguages();
    }
}
