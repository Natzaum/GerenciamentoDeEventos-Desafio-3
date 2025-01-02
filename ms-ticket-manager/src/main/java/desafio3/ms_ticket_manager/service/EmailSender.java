package desafio3.ms_ticket_manager.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendEmail(String emailContent){
        amqpTemplate.convertAndSend("emailQueue", emailContent);
    }
}
