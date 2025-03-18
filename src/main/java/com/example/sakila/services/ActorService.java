package com.example.sakila.services;

import com.example.sakila.dto.request.ActorRequest;
import com.example.sakila.dto.response.ActorResponse;
import com.example.sakila.entities.Actor;
import com.example.sakila.entities.Film;
import com.example.sakila.repos.ActorRepo;
import com.example.sakila.repos.FilmRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service // Marks the class as a Spring service
public class ActorService {

    private final ActorRepo actorRepo; // Actor repository


    @Autowired
    public ActorService(ActorRepo actorRepo) {
        this.actorRepo = actorRepo;
    }

    // List actors, optionally filtered by name
    public List<ActorResponse> listActors(String name) {
        if (name != null && !name.isEmpty()) {
            return actorRepo.findByFullNameContainingIgnoreCase(name)
                    .stream()
                    .map(ActorResponse::from)
                    .toList();
        } else {
            return actorRepo.findAll()
                    .stream()
                    .map(ActorResponse::from)
                    .toList();
        }
    }

    // Get actor by ID
    public ActorResponse getActorById(Short id) {
        Actor actor = actorRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Actor not found"));
        return ActorResponse.from(actor);
    }

    // Create a new actor
    public ActorResponse createActor(ActorRequest actorRequest) {
        Actor actor = new Actor();
        actor.setFirstName(actorRequest.getFirstName());
        actor.setLastName(actorRequest.getLastName());
        Actor savedActor = actorRepo.save(actor);
        return ActorResponse.from(savedActor);
    }

    // Update an existing actor
    public ActorResponse updateActor(Short id, ActorRequest data) {
        Actor actor = actorRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Actor not found"));

        // Update actor details if provided
        if (data.getFirstName() != null) {
            actor.setFirstName(data.getFirstName());
        }

        if (data.getLastName() != null) {
            actor.setLastName(data.getLastName());
        }

        Actor updatedActor = actorRepo.save(actor);
        return ActorResponse.from(updatedActor);
    }

    // Delete actor by ID
    public void deleteActor(Short id) {
        Actor actor = actorRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Actor not found"));
        actorRepo.delete(actor);
    }
}
