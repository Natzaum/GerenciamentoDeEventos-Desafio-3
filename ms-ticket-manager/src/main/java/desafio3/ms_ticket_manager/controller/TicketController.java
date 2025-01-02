package desafio3.ms_ticket_manager.controller;

import desafio3.ms_ticket_manager.dto.TicketDTO;
import desafio3.ms_ticket_manager.model.Ticket;
import desafio3.ms_ticket_manager.service.TicketServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
