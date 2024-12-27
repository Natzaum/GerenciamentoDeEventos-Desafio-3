package desafio3.ms_event_manager.service;

import desafio3.ms_event_manager.dto.EventDTO;
import desafio3.ms_event_manager.model.Event;

import java.util.List;

public interface EventService {
    Event createEvent(EventDTO eventDTO);
    Event getEventById(String id);
    List<Event> getAllEvents();
    List<Event> getAllEventsSortedByName();
}
