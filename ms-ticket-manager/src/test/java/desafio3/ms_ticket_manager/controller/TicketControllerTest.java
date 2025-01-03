package desafio3.ms_ticket_manager.controller;

import desafio3.ms_ticket_manager.dto.TicketDTO;
import desafio3.ms_ticket_manager.model.Ticket;
import desafio3.ms_ticket_manager.service.TicketServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TicketControllerTest {

    @Mock
    private TicketServiceImpl ticketService;

    @InjectMocks
    private TicketController ticketController;

    private MockMvc mockMvc;
    private TicketDTO ticketDTO;
    private Ticket ticket;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(ticketController).build();

        ticketDTO = new TicketDTO();
        ticketDTO.setCustomerName("John Doe");
        ticketDTO.setCpf("12345678900");
        ticketDTO.setCustomerMail("john.doe@example.com");
        ticketDTO.setEventId("1");
        ticketDTO.setEventName("Concert");
        ticketDTO.setBrlamount("100");
        ticketDTO.setUsdamount("20");

        ticket = new Ticket();
        ticket.setTicketId("1");
        ticket.setCustomerName("John Doe");
        ticket.setCpf("12345678900");
        ticket.setCustomerMail("john.doe@example.com");
        ticket.setEventId("1");
        ticket.setEventName("Concert");
        ticket.setBrlamount("100");
        ticket.setUsdamount("20");
        ticket.setStatus("pending");
    }

    @Test
    public void testCreateTicket() throws Exception {
        when(ticketService.createTicket(any(TicketDTO.class))).thenReturn(ticket);

        mockMvc.perform(MockMvcRequestBuilders.post("/tickets/create-ticket")
                        .contentType("application/json")
                        .content("{\"customerName\":\"John Doe\", \"cpf\":\"12345678900\", \"customerMail\":\"john.doe@example.com\", \"eventId\":\"1\", \"eventName\":\"Concert\", \"brlamount\":\"100\", \"usdamount\":\"20\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ticketId").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerName").value("John Doe"));
    }

    @Test
    public void testCreateTicketBadRequest() throws Exception {
        when(ticketService.createTicket(any(TicketDTO.class))).thenThrow(new RuntimeException("Event not found"));

        mockMvc.perform(MockMvcRequestBuilders.post("/tickets/create-ticket")
                        .contentType("application/json")
                        .content("{\"customerName\":\"John Doe\", \"cpf\":\"12345678900\", \"customerMail\":\"john.doe@example.com\", \"eventId\":\"1\", \"eventName\":\"Concert\", \"brlamount\":\"100\", \"usdamount\":\"20\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testGetTicketById() throws Exception {
        when(ticketService.getTicketById("1")).thenReturn(ticket);

        mockMvc.perform(MockMvcRequestBuilders.get("/tickets/get-ticket/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ticketId").value("1"));
    }

    @Test
    public void testGetTicketsByCpf() throws Exception {
        when(ticketService.getTicketByCpf("12345678900")).thenReturn(Collections.singletonList(ticket));

        mockMvc.perform(MockMvcRequestBuilders.get("/tickets/get-ticket-by-cpf/12345678900"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].ticketId").value("1"));
    }

    @Test
    public void testCancelTicket() throws Exception {
        doNothing().when(ticketService).cancelTicket("1");

        mockMvc.perform(MockMvcRequestBuilders.delete("/tickets/cancel-ticket/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Ticket with ID 1 has been cancelled"));
    }

    @Test
    public void testCancelTicketsByCpf() throws Exception {
        doNothing().when(ticketService).cancelTicketsByCpf("12345678900");

        mockMvc.perform(MockMvcRequestBuilders.delete("/tickets/cancel-ticket-cpf/12345678900"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Tickets for CPF 12345678900 have been cancelled"));
    }

    @Test
    public void testGetTicketsByEventId() throws Exception {
        when(ticketService.getTicketsByEventId("1")).thenReturn(Collections.singletonList(ticket));

        mockMvc.perform(MockMvcRequestBuilders.get("/tickets/check-tickets-by-event/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].ticketId").value("1"));
    }

    @Test
    public void testGetTicketsByEventIdNotFound() throws Exception {
        when(ticketService.getTicketsByEventId("1")).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/tickets/check-tickets-by-event/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
