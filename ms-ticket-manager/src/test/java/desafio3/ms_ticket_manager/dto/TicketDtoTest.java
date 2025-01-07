package desafio3.ms_ticket_manager.dto;

import desafio3.ms_event_manager.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TicketDtoTest {

    private TicketDTO ticketDTO;

    @BeforeEach
    public void setUp() {
        EventResponseDTO event = new EventResponseDTO();
        event.setId("1");
        event.setName("Concert");

        ticketDTO = new TicketDTO("John Doe", "12345678900", "john.doe@example.com", "1", "Concert", event, "100", "20");
    }

    @Test
    public void testTicketDTOConstructor() {
        assertEquals("John Doe", ticketDTO.getCustomerName());
        assertEquals("12345678900", ticketDTO.getCpf());
        assertEquals("john.doe@example.com", ticketDTO.getCustomerMail());
        assertEquals("1", ticketDTO.getEventId());
        assertEquals("Concert", ticketDTO.getEventName());
        assertNotNull(ticketDTO.getEvent());
        assertEquals("100", ticketDTO.getBrlamount());
        assertEquals("20", ticketDTO.getUsdamount());
    }

    @Test
    public void testSettersAndGetters() {
        ticketDTO.setCustomerName("Jane Doe");
        ticketDTO.setCpf("98765432100");
        ticketDTO.setCustomerMail("jane.doe@example.com");
        ticketDTO.setEventId("2");
        ticketDTO.setEventName("Theater");
        ticketDTO.setBrlamount("150");
        ticketDTO.setUsdamount("30");

        assertEquals("Jane Doe", ticketDTO.getCustomerName());
        assertEquals("98765432100", ticketDTO.getCpf());
        assertEquals("jane.doe@example.com", ticketDTO.getCustomerMail());
        assertEquals("2", ticketDTO.getEventId());
        assertEquals("Theater", ticketDTO.getEventName());
        assertEquals("150", ticketDTO.getBrlamount());
        assertEquals("30", ticketDTO.getUsdamount());
    }
}
