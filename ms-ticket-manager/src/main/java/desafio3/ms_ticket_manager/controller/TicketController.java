package desafio3.ms_ticket_manager.controller;

import desafio3.ms_ticket_manager.dto.TicketDTO;
import desafio3.ms_ticket_manager.model.Ticket;
import desafio3.ms_ticket_manager.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/create-ticket")
    public ResponseEntity<Ticket> createTicket(@RequestBody TicketDTO ticketDTO){
        try{
            Ticket ticket = ticketService.createTicket(ticketDTO);
            return new ResponseEntity<>(ticket, HttpStatus.CREATED);
        } catch (RuntimeException e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
