package desafio3.ms_event_manager.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "ms-event-manager", url = "http://localhost:8080")
public interface EventClient {
}
