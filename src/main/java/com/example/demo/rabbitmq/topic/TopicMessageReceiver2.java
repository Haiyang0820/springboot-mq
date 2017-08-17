package com.example.demo.rabbitmq.topic;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by think on 2017/8/16.
 */
@Component
@RabbitListener(queues = "topic.messages")
public class TopicMessageReceiver2 {

    @RabbitHandler
    public void process(String msg) {
        System.out.println("TopicMessageReceiver2  : " +msg);
    }

}
