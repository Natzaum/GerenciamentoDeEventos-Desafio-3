package desafio3.ms_ticket_manager.service;

import desafio3.ms_ticket_manager.client.EventClient;
import desafio3.ms_ticket_manager.dto.EventResponseDTO;
import desafio3.ms_ticket_manager.dto.TicketDTO;
import desafio3.ms_ticket_manager.model.Ticket;
import desafio3.ms_ticket_manager.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EventClient eventClient;

    @Autowired
    private EmailSender emailSender;

    @Override
    public Ticket createTicket(TicketDTO ticketDTO) {
        EventResponseDTO event = eventClient.getEventById(ticketDTO.getEventId());
        if (event == null) {
            throw new RuntimeException("Event not found");
        }

        Ticket ticket = new Ticket();
        ticket.setTicketId(generateTicketId());
        ticket.setCustomerName(ticketDTO.getCustomerName());
        ticket.setCpf(ticketDTO.getCpf());
        ticket.setCustomerMail(ticketDTO.getCustomerMail());
        ticket.setEvent(event);
        ticket.setEventId(ticketDTO.getEventId());
        ticket.setEventName(ticketDTO.getEventName());
        ticket.setBrlamount(ticketDTO.getBrlamount());
        ticket.setUsdamount(ticketDTO.getUsdamount());
        ticket.setStatus("pending");

        Ticket savedTicket = ticketRepository.save(ticket);

        emailSender.sendEmailConfirmation(savedTicket);

        return savedTicket;
    }

    @Override
    public Ticket getTicketById(String ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found with ID: " + ticketId));

        if (ticket.getEvent() == null) {
            EventResponseDTO event = eventClient.getEventById(ticket.getEventId());
            ticket.setEvent(event);
        }

        return ticket;
    }

    @Override
    public List<Ticket> getTicketByCpf(String cpf) {
        List<Ticket> tickets = ticketRepository.findByCpf(cpf);
        if (tickets.isEmpty()) {
            throw new RuntimeException("No tickets found for CPF: " + cpf);
        }
        return tickets;
    }

    @Override
    public void cancelTicket(String ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found with ID: " + ticketId));

        if ("cancelled".equals(ticket.getStatus())) {
            throw new RuntimeException("Ticket is already cancelled");
        }

        ticket.setStatus("cancelled");
        ticketRepository.save(ticket);
    }

    @Override
    public void cancelTicketsByCpf(String cpf) {
        List<Ticket> tickets = ticketRepository.findByCpf(cpf);
        if (tickets.isEmpty()) {
            throw new RuntimeException("No tickets found for CPF: " + cpf);
        }

        for (Ticket ticket : tickets) {
            ticket.setStatus("cancelled");
            ticketRepository.save(ticket);
        }
    }

    @Override
    public List<Ticket> getTicketsByEventId(String eventId) {
        return ticketRepository.findByEventId(eventId);
    }

    private String generateTicketId() {
        return String.valueOf(ticketRepository.count() + 1);
    }
}