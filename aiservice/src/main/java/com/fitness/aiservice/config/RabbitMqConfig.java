
package com.fitness.aiservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Autowired
    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Autowired
    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Autowired
    @Value("${rabbitmq.queue.name}")
    private String queue;

    @Bean
    public DirectExchange fitnessExchange() {
        return new DirectExchange(exchange, true, false);
    }

    @Bean
    public Queue activityQueue() {
        return new Queue(queue, true);
    }

    @Bean
    public Binding activityBinding(
            Queue activityQueue,
            DirectExchange fitnessExchange) {

        return BindingBuilder
                .bind(activityQueue)
                .to(fitnessExchange)
                .with(routingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
