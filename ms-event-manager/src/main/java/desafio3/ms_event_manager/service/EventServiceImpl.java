package desafio3.ms_event_manager.service;

import desafio3.ms_event_manager.dto.EventDTO;
import desafio3.ms_event_manager.model.Event;
import desafio3.ms_event_manager.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public Event createEvent(EventDTO eventDTO) {
        Event event = new Event(eventDTO.getName(), eventDTO.getDescription(), eventDTO.getDate());
        return eventRepository.save(event);
    }
}
