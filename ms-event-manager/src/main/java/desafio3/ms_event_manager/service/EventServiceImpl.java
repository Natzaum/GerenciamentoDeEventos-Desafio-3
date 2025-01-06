package desafio3.ms_event_manager.service;

import desafio3.ms_event_manager.Exception.EventNotFoundException;
import desafio3.ms_event_manager.dto.EventDTO;
import desafio3.ms_event_manager.model.Event;
import desafio3.ms_event_manager.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CepService cepService;

    @Override
    public Event createEvent(EventDTO eventDTO) {
        Map<String, String> cepInfo = cepService.getCepInfo(eventDTO.getCep());
        if (cepInfo == null || cepInfo.containsKey("erro")) {
            throw new RuntimeException("Invalid cep");
        }

        System.out.println("CEP Info: " + cepInfo);

        String logradouro = cepInfo.getOrDefault("logradouro", "Not Available");
        String bairro = cepInfo.getOrDefault("bairro", "Not Available");
        String cidade = cepInfo.getOrDefault("localidade", "Not Available");
        String uf = cepInfo.getOrDefault("uf", "Not Available");

        Event event = new Event();
        event.setName(eventDTO.getName());
        event.setDescription(eventDTO.getDescription());
        event.setDateTime(eventDTO.getDateTime());
        event.setCep(eventDTO.getCep());
        event.setLogradouro(logradouro);
        event.setBairro(bairro);
        event.setCidade(cidade);
        event.setUf(uf);

        return eventRepository.save(event);
    }

    @Override
    public Event getEventById(String id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent()) {
            return event.get();
        } else {
            throw new EventNotFoundException("Event with id: " + id + " not found.");
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
            existingEvent.setDateTime(eventDTO.getDateTime());
            return eventRepository.save(existingEvent);
        } else {
            throw new RuntimeException("Event not found");
        }
    }

    @Override
    public void deleteEvent(String id) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if(optionalEvent.isPresent()) {
            eventRepository.deleteById(id);
        } else{
            throw new RuntimeException("Event not found");
        }
    }
}
