package com.example.demo.mq;

import org.springframework.stereotype.Component;
import org.springframework.jms.annotation.JmsListener;

/**
 * Created by think on 2017/8/16.
 */
@Component
public class Consumer {
    @JmsListener(destination = "sample.queue")
    public void receiveQueue(String text) {
        System.out.println(text);
    }
}
