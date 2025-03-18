package com.example.sakila.dto;

import com.example.sakila.controllers.ActorController;
import com.example.sakila.dto.request.ActorRequest;
import com.example.sakila.dto.response.ActorResponse;
import com.example.sakila.entities.Actor;
import com.example.sakila.services.ActorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

public class ActorControllerTest {

    ActorService service = mock(ActorService.class);
    ActorController controller = new ActorController(service);


    @Test
    public void getActorByIdReturnsActorResponseForAValidActorId(){
        final short id = 1;
        final var actor = new Actor(id, "Muhammad", "Abdullah", "Muhammad Abdullah", List.of());

        final var expectedResponse = ActorResponse.from(actor);
        doReturn(expectedResponse).when(service).getActorById(id);
        final var actualResponse = controller.getActorById(id);

        Assertions.assertEquals(expectedResponse.getId(), actualResponse.getId());
        Assertions.assertEquals(expectedResponse.getFirstName(), actualResponse.getFirstName());
        Assertions.assertEquals(expectedResponse.getLastName(), actualResponse.getLastName());
    }

    @Test
    public void listActorsReturnsListOfActorResponses() {
        final var actors = List.of(
                new Actor((short) 1, "Muhammad", "Abdullah", "Muhammad Abdullah", List.of()),
                new Actor((short) 2, "David", "Tenant", "David Tenant", List.of())
        );

        final var expectedResponse = actors.stream()
                .map(ActorResponse::from)
                .toList();
        doReturn(expectedResponse).when(service).listActors(null);

        final var actualResponse = controller.listActors(null);

        Assertions.assertEquals(expectedResponse.size(), actualResponse.size());
        Assertions.assertEquals(expectedResponse.get(0).getId(), actualResponse.get(0).getId());
        Assertions.assertEquals(expectedResponse.get(1).getId(), actualResponse.get(1).getId());
    }

    @Test
    public void createActorReturnsActorResponseWhenActorIsCreated() {
        final var actorRequest = new ActorRequest("Muhammad", "Abdullah", List.of());
        final var createdActor = new Actor((short) 1, "Muhammad", "Abdullah", "Muhammad Abdullah", List.of());
        final var expectedResponse = ActorResponse.from(createdActor);

        doReturn(expectedResponse).when(service).createActor(actorRequest);

        final var actualResponse = controller.createActor(actorRequest);

        Assertions.assertEquals(expectedResponse.getId(), actualResponse.getId());
        Assertions.assertEquals(expectedResponse.getFirstName(), actualResponse.getFirstName());
        Assertions.assertEquals(expectedResponse.getLastName(), actualResponse.getLastName());
    }

    @Test
    public void updateActorReturnsUpdatedActorResponseWhenActorIsUpdated() {
        final short id = 1;
        final var actorRequest = new ActorRequest("Muhammad", "Abdullah", List.of());

        final var updatedActor = new Actor(id, "Muhammad", "Abdullah", "Muhammad Abdullah", List.of());

        final var expectedResponse = ActorResponse.from(updatedActor);

        doReturn(expectedResponse).when(service).updateActor(id, actorRequest);

        final var actualResponse = controller.updateActor(id, actorRequest);

        Assertions.assertEquals(expectedResponse.getId(), actualResponse.getId());
        Assertions.assertEquals(expectedResponse.getFirstName(), actualResponse.getFirstName());
        Assertions.assertEquals(expectedResponse.getLastName(), actualResponse.getLastName());
    }



    @Test
    public void deleteActorDeletesActorWhenActorIdIsValid() {
        final short id = 1;

        doNothing().when(service).deleteActor(id);

        controller.deleteActor(id);

        verify(service, times(1)).deleteActor(id);
    }



}
