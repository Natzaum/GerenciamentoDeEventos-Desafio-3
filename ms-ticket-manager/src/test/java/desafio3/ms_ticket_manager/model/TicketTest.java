package desafio3.ms_ticket_manager.model;

import desafio3.ms_event_manager.model.Event;
import desafio3.ms_ticket_manager.dto.EventResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TicketTest {

    private Ticket ticket;
    private EventResponseDTO event;

    @BeforeEach
    public void setUp() {
        event = new EventResponseDTO();
        event.setId("1");
        event.setName("Concert");

        ticket = new Ticket("1", "John Doe", "12345678900", "john.doe@example.com",
                "1", "Concert", event, "100", "20", "pending");
    }

    @Test
    public void testTicketConstructor() {
        assertEquals("1", ticket.getTicketId());
        assertEquals("John Doe", ticket.getCustomerName());
        assertEquals("12345678900", ticket.getCpf());
        assertEquals("john.doe@example.com", ticket.getCustomerMail());
        assertEquals("1", ticket.getEventId());
        assertEquals("Concert", ticket.getEventName());
        assertNotNull(ticket.getEvent());
        assertEquals("100", ticket.getBrlamount());
        assertEquals("20", ticket.getUsdamount());
        assertEquals("pending", ticket.getStatus());
    }

    @Test
    public void testSettersAndGetters() {
        ticket.setCustomerName("Jane Doe");
        ticket.setCpf("98765432100");
        ticket.setCustomerMail("jane.doe@example.com");
        ticket.setEventId("2");
        ticket.setEventName("Theater");
        ticket.setBrlamount("150");
        ticket.setUsdamount("30");
        ticket.setStatus("confirmed");

        assertEquals("Jane Doe", ticket.getCustomerName());
        assertEquals("98765432100", ticket.getCpf());
        assertEquals("jane.doe@example.com", ticket.getCustomerMail());
        assertEquals("2", ticket.getEventId());
        assertEquals("Theater", ticket.getEventName());
        assertEquals("150", ticket.getBrlamount());
        assertEquals("30", ticket.getUsdamount());
        assertEquals("confirmed", ticket.getStatus());
    }

}
