package desafio3.ms_ticket_manager.service;

import desafio3.ms_ticket_manager.client.EventClient;
import desafio3.ms_ticket_manager.dto.TicketDTO;
import desafio3.ms_ticket_manager.model.Ticket;
import desafio3.ms_ticket_manager.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EventClient eventClient;

    public Ticket createTicket(TicketDTO ticketDTO){
        if(eventClient.getEventById(ticketDTO.getEventId()) == null){
            throw new RuntimeException("Event not found");
        }

        Ticket ticket = new Ticket();
        ticket.setTicketId(generateTicketId());
        ticket.setCustomerName(ticketDTO.getCustomerName());
        ticket.setCpf(ticketDTO.getCpf());
        ticket.setCustomerMail(ticketDTO.getCustomerMail());
        ticket.setEventId(ticketDTO.getEventId());
        ticket.setEventName(ticketDTO.getEventName());
        ticket.setBRLamount(ticketDTO.getBRLamount());
        ticket.setUSDamount(ticketDTO.getUSDamount());
        ticket.setStatus("pending");

        return ticketRepository.save(ticket);
    }

    private String generateTicketId(){
        return String.valueOf (ticketRepository.count() + 1);
    }
}
