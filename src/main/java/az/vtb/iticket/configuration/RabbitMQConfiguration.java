package az.vtb.iticket.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static az.vtb.iticket.mapper.ObjectMapperFactory.OBJECT_MAPPER;
import static az.vtb.iticket.model.constant.QueueConstant.PUBLISHER_DLQ;
import static az.vtb.iticket.model.constant.QueueConstant.PUBLISHER_DLQ_EXCHANGE;
import static az.vtb.iticket.model.constant.QueueConstant.PUBLISHER_DLQ_ROUTING_KEY;
import static az.vtb.iticket.model.constant.QueueConstant.PUBLISHER_EXCHANGE;
import static az.vtb.iticket.model.constant.QueueConstant.PUBLISHER_QUEUE;
import static az.vtb.iticket.model.constant.QueueConstant.PUBLISHER_ROUTING_KEY;

@Configuration
public class RabbitMQConfiguration {
    @Bean
    public Queue publisherQueue() {
        return QueueBuilder.durable(PUBLISHER_QUEUE)
                .withArgument("x-dead-letter-exchange", PUBLISHER_DLQ_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", PUBLISHER_DLQ_ROUTING_KEY)
                .build();
    }

    @Bean
    public Queue publisherDeadLetterQueue() {
        return QueueBuilder.durable(PUBLISHER_DLQ).build();
    }

    @Bean
    public TopicExchange publisherExchange() {
        return new TopicExchange(PUBLISHER_EXCHANGE);
    }

    @Bean
    public TopicExchange publisherDLQExchange() {
        return new TopicExchange(PUBLISHER_DLQ_EXCHANGE);
    }

    @Bean
    public Binding publisherBinding(Queue publisherQueue, TopicExchange publisherExchange) {
        return BindingBuilder
                .bind(publisherQueue)
                .to(publisherExchange)
                .with(PUBLISHER_ROUTING_KEY);
    }

    @Bean
    public Binding publisherDLQBinding(Queue publisherDeadLetterQueue, TopicExchange publisherDLQExchange) {
        return BindingBuilder
                .bind(publisherDeadLetterQueue)
                .to(publisherDLQExchange)
                .with(PUBLISHER_DLQ_ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        var objectMapper = OBJECT_MAPPER.getInstance();
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}