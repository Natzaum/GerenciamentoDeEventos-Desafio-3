package desafio3.ms_ticket_manager.service;

import desafio3.ms_ticket_manager.model.Ticket;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendEmailConfirmation(Ticket ticket) {
        String message = String.format(
                "Hello %s,\n\nYour ticket for the event '%s' has been successfully created.\n\nDetails:\nTicket ID: %s\nBRL Amount: %s\nUSD Amount: %s\n\nThank you!",
                ticket.getCustomerName(),
                ticket.getEventName(),
                ticket.getTicketId(),
                ticket.getBrlamount(),
                ticket.getUsdamount()
        );

        rabbitTemplate.convertAndSend("emailQueue", message);
    }
}
