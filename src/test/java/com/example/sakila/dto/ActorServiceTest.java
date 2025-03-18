package com.example.sakila.dto;

import com.example.sakila.dto.request.ActorRequest;
import com.example.sakila.dto.response.ActorResponse;
import com.example.sakila.entities.Actor;
import com.example.sakila.repos.ActorRepo;
import com.example.sakila.repos.FilmRepo;
import com.example.sakila.services.ActorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

public class ActorServiceTest {

    private ActorRepo actorRepo;
    private FilmRepo filmRepo;
    private ActorService actorService;

    @BeforeEach
    void setUp() {

        actorRepo = mock(ActorRepo.class);
        filmRepo = mock(FilmRepo.class);
        actorService = new ActorService(actorRepo);
    }

    @Test
    public void testListActorsReturnsActorsWhenNameIsNull() {

        Actor actor1 = new Actor();
        actor1.setFirstName("John");
        actor1.setLastName("Smith");

        Actor actor2 = new Actor();
        actor2.setFirstName("David");
        actor2.setLastName("Tenant");

        List<Actor> actors = List.of(actor1, actor2);
        when(actorRepo.findAll()).thenReturn(actors);


        List<ActorResponse> actorResponses = actorService.listActors(null);


        assertEquals(2, actorResponses.size());
        verify(actorRepo, times(1)).findAll();
    }

    @Test
    public void testListActorsFiltersByName() {

        Actor actor = new Actor();
        actor.setFirstName("John");
        actor.setLastName("Smith");

        List<Actor> actors = List.of(actor);
        when(actorRepo.findByFullNameContainingIgnoreCase("John")).thenReturn(actors);


        List<ActorResponse> actorResponses = actorService.listActors("John");


        assertEquals(1, actorResponses.size());
        assertEquals("John", actorResponses.get(0).getFirstName());
        verify(actorRepo, times(1)).findByFullNameContainingIgnoreCase("John");
    }

    @Test
    public void testGetActorByIdReturnsActorResponseWhenActorExists() {

        Short actorId = 1;
        Actor actor = new Actor();
        actor.setId(actorId);
        actor.setFirstName("John");
        actor.setLastName("Smith");

        when(actorRepo.findById(actorId)).thenReturn(Optional.of(actor));

        ActorResponse actorResponse = actorService.getActorById(actorId);


        assertEquals("John", actorResponse.getFirstName());
        assertEquals("Smith", actorResponse.getLastName());
        verify(actorRepo, times(1)).findById(actorId);
    }

    @Test
    public void testGetActorByIdThrowsExceptionWhenActorNotFound() {

        Short actorId = 1;
        when(actorRepo.findById(actorId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> actorService.getActorById(actorId));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    public void testCreateActorReturnsActorResponse() {
        ActorRequest actorRequest = new ActorRequest();
        actorRequest.setFirstName("John");
        actorRequest.setLastName("Smith");

        Actor actor = new Actor();
        actor.setFirstName("John");
        actor.setLastName("Smith");

        Actor savedActor = new Actor();
        savedActor.setId((short) 1);
        savedActor.setFirstName("John");
        savedActor.setLastName("Smith");

        when(actorRepo.save(any(Actor.class))).thenReturn(savedActor);

        ActorResponse actorResponse = actorService.createActor(actorRequest);


        assertEquals("John", actorResponse.getFirstName());
        assertEquals("Smith", actorResponse.getLastName());
        verify(actorRepo, times(1)).save(any(Actor.class));
    }

    @Test
    public void testUpdateActorReturnsUpdatedActorResponse() {

        Short actorId = 1;
        Actor actor = new Actor();
        actor.setId(actorId);
        actor.setFirstName("John");
        actor.setLastName("Smith");

        ActorRequest actorRequest = new ActorRequest();
        actorRequest.setFirstName("Updated John");

        when(actorRepo.findById(actorId)).thenReturn(Optional.of(actor));
        when(actorRepo.save(any(Actor.class))).thenReturn(actor);

        ActorResponse actorResponse = actorService.updateActor(actorId, actorRequest);


        assertEquals("Updated John", actorResponse.getFirstName());
        verify(actorRepo, times(1)).save(any(Actor.class));
    }

    @Test
    public void testDeleteActorDeletesActorWhenActorExists() {

        Short actorId = 1;
        Actor actor = new Actor();
        actor.setId(actorId);
        actor.setFirstName("John");
        actor.setLastName("Smith");

        when(actorRepo.findById(actorId)).thenReturn(Optional.of(actor));

        actorService.deleteActor(actorId);

        verify(actorRepo, times(1)).delete(actor);
    }

    @Test
    public void testDeleteActorThrowsExceptionWhenActorNotFound() {

        Short actorId = 1;
        when(actorRepo.findById(actorId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> actorService.deleteActor(actorId));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
}
