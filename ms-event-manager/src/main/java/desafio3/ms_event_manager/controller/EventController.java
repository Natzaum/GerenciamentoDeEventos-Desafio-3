package desafio3.ms_event_manager.controller;

import desafio3.ms_event_manager.dto.EventDTO;
import desafio3.ms_event_manager.model.Event;
import desafio3.ms_event_manager.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @PostMapping("/create-event")
    public ResponseEntity<Event> createEvent(@RequestBody EventDTO eventDTO) {
        Event event = eventService.createEvent(eventDTO);
        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }
}
