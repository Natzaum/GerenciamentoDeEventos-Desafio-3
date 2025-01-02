package desafio3.ms_ticket_manager.repository;

import desafio3.ms_ticket_manager.model.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TicketRepository extends MongoRepository<Ticket, String> {

}
