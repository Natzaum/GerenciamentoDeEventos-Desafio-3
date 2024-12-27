package desafio3.ms_event_manager.service;

import desafio3.ms_event_manager.dto.EventDTO;
import desafio3.ms_event_manager.exception.EventNotFoundException;
import desafio3.ms_event_manager.model.Event;
import desafio3.ms_event_manager.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public Event createEvent(EventDTO eventDTO) {
        Event event = new Event(eventDTO.getName(), eventDTO.getDescription(), eventDTO.getDate());
        return eventRepository.save(event);
    }

    @Override
    public Event getEventById(String id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent()) {
            return event.get();
        } else {
            throw new RuntimeException("Event not found");
        }
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public List<Event> getAllEventsSortedByName() {
        List<Event> events = eventRepository.findAll();
        events.sort(Comparator.comparing(Event::getName));
        return events;
    }

    @Override
    public Event updateEvent(String id, EventDTO eventDTO) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            Event existingEvent = optionalEvent.get();
            existingEvent.setName(eventDTO.getName());
            existingEvent.setDescription(eventDTO.getDescription());
            existingEvent.setDate(eventDTO.getDate());
            return eventRepository.save(existingEvent);
        } else {
            throw new RuntimeException("Event not found");
        }
    }
}
