package desafio3.ms_ticket_manager.client;

import desafio3.ms_event_manager.model.Event;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-event-manager", url = "http://localhost:8080")
public interface EventClient {
    @GetMapping("/events/get-event/{id}")
    Event getEventById(@PathVariable("id") String id);
}
