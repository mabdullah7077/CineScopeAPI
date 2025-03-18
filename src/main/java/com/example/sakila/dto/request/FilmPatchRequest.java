package com.example.sakila.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Year;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class FilmPatchRequest {

    @Size(max = 128) // Limits the size of title
    private String title;

    @Size(max = 500) // Limits the size of description
    private String description;

    private Year releaseYear; // Release year of the film

    @Min(value = 1) // Ensures length is at least 1
    private Short length;

    private List<Short> actorIds; // List of associated actor IDs

    private Short languageId; // Associated language id

    @Size(max = 5) // Limits the size of rating
    private String rating;

    private List<Short> categoryIds; // List of associated category IDs
}
