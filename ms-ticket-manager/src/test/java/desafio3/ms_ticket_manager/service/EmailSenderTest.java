package desafio3.ms_ticket_manager.service;

import desafio3.ms_ticket_manager.model.Ticket;
import desafio3.ms_ticket_manager.service.EmailSender;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.*;

public class EmailSenderTest {

    @InjectMocks
    private EmailSender emailSender;

    @Mock
    private RabbitTemplate rabbitTemplate;

    public EmailSenderTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendEmailConfirmation() {
        Ticket ticket = new Ticket();
        ticket.setCustomerName("John Doe");
        ticket.setEventName("Test Event");
        ticket.setTicketId("12345");
        ticket.setBrlamount("100.0");
        ticket.setUsdamount("20.0");

        emailSender.sendEmailConfirmation(ticket);

        String expectedMessage = String.format(
                "Hello %s,\n\nYour ticket for the event '%s' has been successfully created.\n\nDetails:\nTicket ID: %s\nBRL Amount: %s\nUSD Amount: %s\n\nThank you!",
                ticket.getCustomerName(),
                ticket.getEventName(),
                ticket.getTicketId(),
                ticket.getBrlamount(),
                ticket.getUsdamount()
        );

        verify(rabbitTemplate, times(1)).convertAndSend("emailQueue", expectedMessage);
    }
}
