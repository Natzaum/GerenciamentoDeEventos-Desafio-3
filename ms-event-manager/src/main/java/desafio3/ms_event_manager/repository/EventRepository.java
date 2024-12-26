package desafio3.ms_event_manager.repository;

import desafio3.ms_event_manager.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<Event, String> {
}
