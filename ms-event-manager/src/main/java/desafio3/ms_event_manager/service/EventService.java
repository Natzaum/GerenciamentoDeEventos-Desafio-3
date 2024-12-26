package desafio3.ms_event_manager.service;

import desafio3.ms_event_manager.dto.EventDTO;
import desafio3.ms_event_manager.model.Event;

public interface EventService {
    Event createEvent(EventDTO eventDTO);
    Event getEventById(String id);
}
