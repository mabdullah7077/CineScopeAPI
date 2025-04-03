package com.example.sakila.dto.response;

import com.example.sakila.entities.Actor;
import com.example.sakila.entities.Film;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class ActorResponse {

    private final Short id;
    private final String firstName;
    private final String lastName;
    private final List<PartialFilmResponse> films;

    // Converts an Actor entity to an ActorResponse DTO
    public static ActorResponse from(Actor actor) {
        return new ActorResponse(
                actor.getId(),
                actor.getFirstName(),
                actor.getLastName(),
                actor.getFilms()
                        .stream()
                        .map(PartialFilmResponse::from) // Convert films to PartialFilmResponse
                        .toList()
        );
    }

    // Returns the full name of the actor
    public String getFullName(){
        return firstName + " " + lastName;
    }
}
