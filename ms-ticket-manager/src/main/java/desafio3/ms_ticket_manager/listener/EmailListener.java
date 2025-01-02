package desafio3.ms_ticket_manager.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EmailListener {

    @RabbitListener(queues = "emailQueue")
    public void processEmailQueue(String message) {
        System.out.println("Sending email with the following message:\n" + message);
    }
}
