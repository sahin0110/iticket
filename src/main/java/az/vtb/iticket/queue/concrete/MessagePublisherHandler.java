package az.vtb.iticket.queue.concrete;

import az.vtb.iticket.aop.annotation.Log;
import az.vtb.iticket.queue.abstraction.MessagePublisher;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@Log
@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class MessagePublisherHandler implements MessagePublisher {
    RabbitTemplate rabbitTemplate;

    @Override
    public void publish(String exchange, String key, Object object) {
        try {
            log.info("Publishing message to exchange: {}, key: {}", exchange, key);
            rabbitTemplate.convertAndSend(exchange, key, object);
            log.info("Message published successfully");
        } catch (Exception ex) {
            log.error("Error publishing message to RabbitMQ", ex);
            throw ex;
        }
    }
}