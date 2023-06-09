package com.asan.wallet.services;


import com.asan.wallet.configuration.amqp.MessagingConfig;
import com.asan.wallet.models.requestrespons.RabbitRequest;
import com.asan.wallet.models.requestrespons.Request;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitService {


    @Autowired
    private RabbitTemplate template;

    public void sendToRabbit(RabbitRequest request) {
        template.convertAndSend(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY,
               request );
    }
}
