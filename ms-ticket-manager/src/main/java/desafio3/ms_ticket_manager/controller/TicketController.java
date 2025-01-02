package desafio3.ms_ticket_manager.controller;

import desafio3.ms_ticket_manager.dto.TicketDTO;
import desafio3.ms_ticket_manager.model.Ticket;
import desafio3.ms_ticket_manager.service.TicketServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketServiceImpl ticketService;

    @PostMapping("/create-ticket")
    public ResponseEntity<Ticket> createTicket(@RequestBody TicketDTO ticketDTO){
        try{
            Ticket ticket = ticketService.createTicket(ticketDTO);
            return new ResponseEntity<>(ticket, HttpStatus.CREATED);
        } catch (RuntimeException e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-ticket/{id}")
    public Ticket getTicketById(@PathVariable("id") String ticketId) {
        return ticketService.getTicketById(ticketId);
    }

    @GetMapping("/get-ticket-by-cpf/{cpf}")
    public List<Ticket> getTicketsByCpf(@PathVariable("cpf") String cpf) {
        return ticketService.getTicketByCpf(cpf);
    }

    @DeleteMapping("/cancel-ticket/{id}")
    public ResponseEntity<String> cancelTicket(@PathVariable("id") String ticketId) {
        ticketService.cancelTicket(ticketId);
        return ResponseEntity.ok("Ticket with ID " + ticketId + " has been cancelled");
    }

    @DeleteMapping("/cancel-ticket-cpf/{cpf}")
    public ResponseEntity<String> cancelTicketsByCpf(@PathVariable("cpf") String cpf) {
        ticketService.cancelTicketsByCpf(cpf);
        return ResponseEntity.ok("Tickets for CPF " + cpf + " have been cancelled");
    }

    @GetMapping("/check-tickets-by-event/{eventId}")
    public ResponseEntity<List<Ticket>> getTicketsByEventId(@PathVariable("eventId") String eventId) {
        List<Ticket> tickets = ticketService.getTicketsByEventId(eventId);

        if (tickets.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }

        return ResponseEntity.ok(tickets);
    }

}
