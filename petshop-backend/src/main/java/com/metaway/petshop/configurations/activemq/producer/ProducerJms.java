package com.metaway.petshop.configurations.activemq.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProducerJms {

    private final JmsTemplate jmsTemplate;

    public void send(String topicOrQueue, String message) {
        jmsTemplate.convertAndSend(topicOrQueue, message);
    }

}
