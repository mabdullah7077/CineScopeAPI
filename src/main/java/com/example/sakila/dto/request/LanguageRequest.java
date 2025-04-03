package com.example.sakila.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LanguageRequest {

    private Short languageId;

    @NotNull
    @Size(max = 20)
    private final String name;
}
