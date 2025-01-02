package desafio3.ms_event_manager.service;

import desafio3.ms_event_manager.dto.EventDTO;
import desafio3.ms_event_manager.model.Event;
import desafio3.ms_event_manager.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventServiceImplTest {

    @InjectMocks
    private EventServiceImpl eventService;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private CepService cepService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createEvent_success() {
        EventDTO eventDTO = new EventDTO("Concert", "Music concert",
                LocalDateTime.of(2025, 2, 1, 18, 0), "12345678",
                null, null, null, null);

        Map<String, String> mockCepInfo = Map.of(
                "logradouro", "Avenida Brasil",
                "bairro", "Centro",
                "localidade", "Rio de Janeiro",
                "uf", "RJ"
        );

        when(cepService.getCepInfo("12345678")).thenReturn(mockCepInfo);
        when(eventRepository.save(any(Event.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Event result = eventService.createEvent(eventDTO);

        assertNotNull(result);
        assertEquals("Concert", result.getName());
        assertEquals("12345678", result.getCep());
        assertEquals("Avenida Brasil", result.getLogradouro());
        assertEquals("Centro", result.getBairro());
        assertEquals("Rio de Janeiro", result.getCidade());
        assertEquals("RJ", result.getUf());

        verify(cepService, times(1)).getCepInfo("12345678");
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    void createEvent_invalidCep() {
        EventDTO eventDTO = new EventDTO("Concert", "Music concert",
                LocalDateTime.of(2025, 2, 1, 18, 0), "00000000",
                null, null, null, null);

        when(cepService.getCepInfo("00000000")).thenReturn(Map.of("erro", "true"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> eventService.createEvent(eventDTO));

        assertEquals("Invalid cep", exception.getMessage());
        verify(cepService, times(1)).getCepInfo("00000000");
        verify(eventRepository, never()).save(any(Event.class));
    }

    @Test
    void getEventById_success() {
        Event mockEvent = new Event("1", "Concert", "Music concert",
                LocalDateTime.of(2025, 2, 1, 18, 0),
                "12345678", "Avenida Brasil",
                "Centro", "Rio de Janeiro", "RJ");

        when(eventRepository.findById("1")).thenReturn(Optional.of(mockEvent));

        Event result = eventService.getEventById("1");

        assertNotNull(result);
        assertEquals("Concert", result.getName());
        verify(eventRepository, times(1)).findById("1");
    }

    @Test
    void getEventById_notFound() {
        when(eventRepository.findById("2")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> eventService.getEventById("2"));

        assertEquals("Event not found", exception.getMessage());
        verify(eventRepository, times(1)).findById("2");
    }

    @Test
    void getAllEvents_success() {
        List<Event> mockEvents = Arrays.asList(
                new Event("1", "Concert", "Music concert",
                        LocalDateTime.of(2025, 2, 1, 18, 0),
                        "12345678", "Avenida Brasil",
                        "Centro", "Rio de Janeiro", "RJ"),
                new Event("2", "Festival", "Cultural festival",
                        LocalDateTime.of(2025, 3, 1, 14, 0),
                        "87654321", "Rua das Flores",
                        "Jardim", "São Paulo", "SP")
        );

        when(eventRepository.findAll()).thenReturn(mockEvents);

        List<Event> result = eventService.getAllEvents();

        assertEquals(2, result.size());
        assertEquals("Concert", result.get(0).getName());
        assertEquals("Festival", result.get(1).getName());
        verify(eventRepository, times(1)).findAll();
    }

    @Test
    void deleteEvent_success() {
        Event mockEvent = new Event("1", "Concert", "Music concert",
                LocalDateTime.of(2025, 2, 1, 18, 0),
                "12345678", "Avenida Brasil",
                "Centro", "Rio de Janeiro", "RJ");

        when(eventRepository.findById("1")).thenReturn(Optional.of(mockEvent));
        doNothing().when(eventRepository).deleteById("1");

        assertDoesNotThrow(() -> eventService.deleteEvent("1"));
        verify(eventRepository, times(1)).findById("1");
        verify(eventRepository, times(1)).deleteById("1");
    }

    @Test
    void deleteEvent_notFound() {
        when(eventRepository.findById("2")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> eventService.deleteEvent("2"));

        assertEquals("Event not found", exception.getMessage());
        verify(eventRepository, times(1)).findById("2");
        verify(eventRepository, never()).deleteById(anyString());
    }

    @Test
    void testGetAllEventsSortedByName() {
        Event event1 = new Event();
        event1.setId("2");
        event1.setName("B Event");
        event1.setDescription("Description B");
        event1.setDateTime(LocalDateTime.now());
        event1.setCep("12345");

        Event event2 = new Event();
        event2.setId("1");
        event2.setName("A Event");
        event2.setDescription("Description A");
        event2.setDateTime(LocalDateTime.now());
        event2.setCep("67890");

        List<Event> unsortedEvents = Arrays.asList(event1, event2);

        Mockito.when(eventRepository.findAll()).thenReturn(unsortedEvents);

        List<Event> sortedEvents = eventService.getAllEventsSortedByName();

        assertEquals(2, sortedEvents.size());
        assertEquals("A Event", sortedEvents.get(0).getName());
        assertEquals("B Event", sortedEvents.get(1).getName());
    }

    @Test
    void testUpdateEvent_Success() {
        String eventId = "1";

        Event existingEvent = new Event();
        existingEvent.setId(eventId);
        existingEvent.setName("Old Name");
        existingEvent.setDescription("Old Description");
        existingEvent.setDateTime(LocalDateTime.now());
        existingEvent.setCep("12345");

        EventDTO updatedEventDTO = new EventDTO();
        updatedEventDTO.setName("New Name");
        updatedEventDTO.setDescription("New Description");
        updatedEventDTO.setDateTime(LocalDateTime.now());
        updatedEventDTO.setCep("12345");

        Mockito.when(eventRepository.findById(eventId)).thenReturn(Optional.of(existingEvent));
        Mockito.when(eventRepository.save(Mockito.any(Event.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Event updatedEvent = eventService.updateEvent(eventId, updatedEventDTO);

        assertNotNull(updatedEvent);
        assertEquals("New Name", updatedEvent.getName());
        assertEquals("New Description", updatedEvent.getDescription());
        Mockito.verify(eventRepository, Mockito.times(1)).save(Mockito.any(Event.class));
    }

}
