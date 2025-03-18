package com.example.sakila.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ActorRequest {

    @NotNull // Ensures firstName is not null
    @Size(min = 1, max = 45) // Limits the size of firstName
    private String firstName;

    @NotNull // Ensures lastName is not null
    @Size(min = 1, max = 45) // Limits the size of lastName
    private String lastName;

    private List<Short> filmIds; // List of associated film IDs
}