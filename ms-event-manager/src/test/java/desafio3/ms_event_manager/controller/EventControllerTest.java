package desafio3.ms_event_manager.controller;

import desafio3.ms_event_manager.dto.EventDTO;
import desafio3.ms_event_manager.model.Event;
import desafio3.ms_event_manager.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventController.class)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    private Event event;
    private EventDTO eventDTO;

    @BeforeEach
    void setup() {
        event = new Event("1", "Music Fest", "An amazing music festival", LocalDateTime.now(),
                "12345678", "Rua das Flores", "Centro", "São Paulo", "SP");

        eventDTO = new EventDTO("Music Fest", "An amazing music festival", LocalDateTime.now(),
                "12345678", "Rua das Flores", "Centro", "São Paulo", "SP");
    }

    @Test
    void testCreateEvent() throws Exception {
        Mockito.when(eventService.createEvent(any(EventDTO.class))).thenReturn(event);

        mockMvc.perform(post("/events/create-event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Music Fest\",\"description\":\"An amazing music festival\",\"dateTime\":\"2025-01-03T20:00:00\",\"cep\":\"12345678\",\"logradouro\":\"Rua das Flores\",\"bairro\":\"Centro\",\"cidade\":\"São Paulo\",\"UF\":\"SP\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Music Fest"))
                .andExpect(jsonPath("$.description").value("An amazing music festival"));
    }

    @Test
    void testGetEventById() throws Exception {
        Mockito.when(eventService.getEventById("1")).thenReturn(event);

        mockMvc.perform(get("/events/get-event/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Music Fest"))
                .andExpect(jsonPath("$.description").value("An amazing music festival"));
    }

    @Test
    void testGetAllEvents() throws Exception {
        List<Event> events = Arrays.asList(event);
        Mockito.when(eventService.getAllEvents()).thenReturn(events);

        mockMvc.perform(get("/events/get-all-events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("Music Fest"));
    }

    @Test
    void testUpdateEvent() throws Exception {
        Mockito.when(eventService.updateEvent(anyString(), any(EventDTO.class))).thenReturn(event);

        mockMvc.perform(put("/events/update-event/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Music Fest\",\"description\":\"Updated festival description\",\"dateTime\":\"2025-01-03T20:00:00\",\"cep\":\"12345678\",\"logradouro\":\"Rua das Flores\",\"bairro\":\"Centro\",\"cidade\":\"São Paulo\",\"UF\":\"SP\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("An amazing music festival"));
    }

    @Test
    void testDeleteEvent() throws Exception {
        Mockito.doNothing().when(eventService).deleteEvent("1");

        mockMvc.perform(delete("/events/delete-event/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetAllEventsSortedByName() throws Exception {
        List<Event> sortedEvents = Arrays.asList(event); // Simula uma lista já ordenada
        Mockito.when(eventService.getAllEventsSortedByName()).thenReturn(sortedEvents);

        mockMvc.perform(get("/events/get-all-events/sorted"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("Music Fest"));
    }

    @Test
    void testGetEventById_NotFound() throws Exception {
        Mockito.when(eventService.getEventById(anyString())).thenThrow(new RuntimeException("Event not found"));

        mockMvc.perform(get("/events/get-event/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateEvent_NotFound() throws Exception {
        Mockito.when(eventService.updateEvent(anyString(), any(EventDTO.class))).thenThrow(new RuntimeException("Event not found"));

        mockMvc.perform(put("/events/update-event/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Event\",\"description\":\"Updated description\",\"dateTime\":\"2025-01-03T20:00:00\",\"cep\":\"12345678\",\"logradouro\":\"Rua das Flores\",\"bairro\":\"Centro\",\"cidade\":\"São Paulo\",\"UF\":\"SP\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteEvent_NotFound() throws Exception {
        Mockito.doThrow(new RuntimeException("Event not found")).when(eventService).deleteEvent("999");

        mockMvc.perform(delete("/events/delete-event/999"))
                .andExpect(status().isNotFound());
    }

}
