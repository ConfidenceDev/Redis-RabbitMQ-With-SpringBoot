package com.confidencedev.listener;

import com.confidencedev.config.MQConfig;
import com.confidencedev.model.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    @RabbitListener(queues = MQConfig.QUEUE)
    public void listener(Message message){
        System.out.println(message);
    }
}
