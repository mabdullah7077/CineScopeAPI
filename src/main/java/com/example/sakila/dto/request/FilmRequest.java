package com.example.sakila.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Year;
import java.util.List;

@AllArgsConstructor // Generates a constructor with all fields
@Getter // Generates getters for all fields
public class FilmRequest {

    @NotNull // Ensures title is not null
    @Size(max = 128) // Limits the size of title
    private final String title;

    @NotNull // Ensures description is not null
    @Size(max = 500) // Limits the size of description
    private final String description;

    @NotNull // Ensures releaseYear is not null
    private final Year releaseYear;

    @NotNull // Ensures length is not null
    @Min(value = 1)
    @Max(value = 240)
    private final Short length;

    @NotNull // Ensures rating is not null
    @Size(max = 5) // Limits the size of rating
    private final String rating;

    @NotNull // Ensures languageName is not null
    private final String languageName;

    @NotEmpty // Ensures actorIds list is not empty
    private final List<Short> actorIds;

    @NotEmpty // Ensures categoryIds list is not empty
    private final List<Short> categoryIds;
}