package com.example.sakila.controllers;

import com.example.sakila.dto.request.ActorRequest;
import com.example.sakila.dto.response.ActorResponse;
import com.example.sakila.services.ActorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="*")
@RestController
public class ActorController {

    private final ActorService actorService;

    @Autowired
    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping("/actors") // Get all actors, optionally filtered by name
    public List<ActorResponse> listActors(@RequestParam(required = false) String name) {
        return actorService.listActors(name);
    }

    @GetMapping("/actors/{id}") // Get actor by ID
    public ActorResponse getActorById(@PathVariable Short id) {
        return actorService.getActorById(id);
    }

    @PostMapping("/actors") // Create a new actor
    public ActorResponse createActor(@Valid @RequestBody ActorRequest actorRequest) { // Validate actor details
        return actorService.createActor(actorRequest);
    }

    @PatchMapping("/actors/{id}") // Update an existing actor
    public ActorResponse updateActor(@PathVariable Short id, @Valid @RequestBody ActorRequest data) {
        return actorService.updateActor(id, data);
    }

    @DeleteMapping("/actors/{id}") // Delete an actor by ID
    public void deleteActor(@PathVariable Short id) {
        actorService.deleteActor(id);
    }
}
