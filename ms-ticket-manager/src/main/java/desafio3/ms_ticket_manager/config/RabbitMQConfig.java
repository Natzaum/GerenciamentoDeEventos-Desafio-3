package desafio3.ms_ticket_manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue emailQueue(){
        return new Queue("emailQueue", true);
    }
}
