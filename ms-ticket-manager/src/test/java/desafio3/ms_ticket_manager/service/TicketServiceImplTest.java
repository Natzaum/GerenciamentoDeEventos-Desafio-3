package desafio3.ms_ticket_manager.service;

import desafio3.ms_event_manager.model.Event;
import desafio3.ms_ticket_manager.client.EventClient;
import desafio3.ms_ticket_manager.dto.TicketDTO;
import desafio3.ms_ticket_manager.model.Ticket;
import desafio3.ms_ticket_manager.repository.TicketRepository;
import desafio3.ms_ticket_manager.service.TicketServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TicketServiceImplTest {

    @InjectMocks
    private TicketServiceImpl ticketService;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private EventClient eventClient;

    @Mock
    private EmailSender emailSender;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTicket_EventNotFound() {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setEventId("1");

        when(eventClient.getEventById(anyString())).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> ticketService.createTicket(ticketDTO));
        assertEquals("Event not found", exception.getMessage());
    }

    @Test
    void testGetTicketById_Success() {
        Ticket ticket = new Ticket();
        ticket.setTicketId("1");

        when(ticketRepository.findById(anyString())).thenReturn(Optional.of(ticket));

        Ticket result = ticketService.getTicketById("1");
        assertNotNull(result);
        assertEquals("1", result.getTicketId());
    }

    @Test
    void testGetTicketById_NotFound() {
        when(ticketRepository.findById(anyString())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> ticketService.getTicketById("1"));
        assertEquals("Ticket not found with ID: 1", exception.getMessage());
    }

    @Test
    void testGetTicketByCpf_Success() {
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(new Ticket());

        when(ticketRepository.findByCpf(anyString())).thenReturn(tickets);

        List<Ticket> result = ticketService.getTicketByCpf("12345678901");
        assertFalse(result.isEmpty());
    }

    @Test
    void testGetTicketByCpf_NotFound() {
        when(ticketRepository.findByCpf(anyString())).thenReturn(new ArrayList<>());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> ticketService.getTicketByCpf("12345678901"));
        assertEquals("No tickets found for CPF: 12345678901", exception.getMessage());
    }

    @Test
    void testCancelTicket_Success() {
        Ticket ticket = new Ticket();
        ticket.setTicketId("1");
        ticket.setStatus("pending");

        when(ticketRepository.findById(anyString())).thenReturn(Optional.of(ticket));

        ticketService.cancelTicket("1");

        assertEquals("cancelled", ticket.getStatus());
        verify(ticketRepository, times(1)).save(ticket);
    }

    @Test
    void testCancelTicket_AlreadyCancelled() {
        Ticket ticket = new Ticket();
        ticket.setTicketId("1");
        ticket.setStatus("cancelled");

        when(ticketRepository.findById(anyString())).thenReturn(Optional.of(ticket));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> ticketService.cancelTicket("1"));
        assertEquals("Ticket is already cancelled", exception.getMessage());
    }

    @Test
    void testCancelTicketsByCpf_Success() {
        List<Ticket> tickets = new ArrayList<>();
        Ticket ticket = new Ticket();
        ticket.setStatus("pending");
        tickets.add(ticket);

        when(ticketRepository.findByCpf(anyString())).thenReturn(tickets);

        ticketService.cancelTicketsByCpf("12345678901");

        assertEquals("cancelled", tickets.get(0).getStatus());
        verify(ticketRepository, times(1)).save(ticket);
    }

    @Test
    void testCancelTicketsByCpf_NotFound() {
        when(ticketRepository.findByCpf(anyString())).thenReturn(new ArrayList<>());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> ticketService.cancelTicketsByCpf("12345678901"));
        assertEquals("No tickets found for CPF: 12345678901", exception.getMessage());
    }

    @Test
    void testGetTicketsByEventId() {
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(new Ticket());

        when(ticketRepository.findByEventId(anyString())).thenReturn(tickets);

        List<Ticket> result = ticketService.getTicketsByEventId("1");
        assertFalse(result.isEmpty());
    }
}
