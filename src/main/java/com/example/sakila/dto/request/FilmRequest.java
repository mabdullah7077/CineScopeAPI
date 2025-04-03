package com.example.sakila.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Year;
import java.util.List;

@AllArgsConstructor
@Getter
public class FilmRequest {

    @NotNull // Ensures title is not null
    @Size(max = 128) // Limits the size of title
    private final String title;

    @NotNull
    @Size(max = 500)
    private final String description;

    @NotNull
    private final Year releaseYear;

    @NotNull
    @Min(value = 1)
    @Max(value = 240)
    private final Short length;

    @NotNull
    @Size(max = 5)
    private final String rating;

    @NotNull
    private final String languageName;

    @NotEmpty
    private final List<Short> actorIds;

    @NotEmpty
    private final List<Short> categoryIds;
}