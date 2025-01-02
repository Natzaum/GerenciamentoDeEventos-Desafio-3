package desafio3.ms_ticket_manager.service;

import desafio3.ms_ticket_manager.dto.TicketDTO;
import desafio3.ms_ticket_manager.model.Ticket;

import java.util.List;

public interface TicketService {
    Ticket createTicket(TicketDTO ticketDTO);
    Ticket getTicketById(String id);
    List<Ticket> getTicketByCpf(String cpf);
    void cancelTicket(String ticketId);
}
