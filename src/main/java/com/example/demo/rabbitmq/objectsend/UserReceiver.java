package com.example.demo.rabbitmq.objectsend;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by think on 2017/8/16.
 */
@Component
@RabbitListener(queues = "userQueue")
public class UserReceiver {

    @RabbitHandler
    public void process(User user) {
        System.out.println("user receive  : " + user.getName()+"/"+user.getPass());
    }

}
