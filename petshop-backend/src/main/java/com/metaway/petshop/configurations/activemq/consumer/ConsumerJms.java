package com.metaway.petshop.configurations.activemq.consumer;

import com.metaway.petshop.utils.PetshopUtil;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerJms {

    @JmsListener(destination = PetshopUtil.CONSUMER_EMAIL)
    public void consumerEmail(String message) {
        System.out.println("Consumidor de email enviado com sucesso: " + message);
    }

}
