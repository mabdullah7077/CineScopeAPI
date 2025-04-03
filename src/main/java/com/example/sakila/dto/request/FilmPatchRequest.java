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

    @Size(max = 128)
    private String title;

    @Size(max = 500)
    private String description;

    private Year releaseYear;
    @Min(value = 1)
    private Short length;

    private List<Short> actorIds;

    private Short languageId;

    @Size(max = 5)
    private String rating;

    private List<Short> categoryIds;
}
