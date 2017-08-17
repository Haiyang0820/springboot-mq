package com.example.demo.rabbitmq;

import com.example.demo.rabbitmq.callback.CallBackSender;
import com.example.demo.rabbitmq.fanout.FanoutSender;
import com.example.demo.rabbitmq.manytomany.Sender1;
import com.example.demo.rabbitmq.manytomany.Sender2;
import com.example.demo.rabbitmq.objectsend.UserSender;
import com.example.demo.rabbitmq.onetomany.HelloSender2;
import com.example.demo.rabbitmq.onetoone.HelloSender1;
import com.example.demo.rabbitmq.topic.TopicSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by think on 2017/8/16.
 */
@RestController
@RequestMapping("/rabbit")
public class RabbitTestController {

    @Autowired
    private HelloSender1 helloSender1;
    @Autowired
    private HelloSender2 helloSender2;
    @Autowired
    private Sender1 sender1;
    @Autowired
    private Sender2 sender2;
    @Autowired
    private UserSender userSender;
    @Autowired
    private TopicSender topicSender;
    @Autowired
    private FanoutSender fanoutSender;
    @Autowired
    private CallBackSender callBackSender;


    @GetMapping("/hello")
    public void hello() {
        helloSender1.send();
    }

    /**
     * 单生产者-多消费者
     */
    @GetMapping("/oneToMany")
    public void oneToMany() {
        for(int i=0;i<10;i++){
            helloSender2.send("hellomsg:"+i);
        }

    }

    /**
     * 多生产者-多消费者
     */
    @GetMapping("/manyToMany")
    public void manyToMany() {
        for(int i=0;i<10;i++){
            sender1.send("hellomsg:"+i);
            sender2.send("hellomsg:"+i);
        }

    }

    /**
     * 实体类传输测试
     */
    @GetMapping("/userTest")
    public void userTest() {
        userSender.send();
    }

    /**
     * topic exchange类型rabbitmq测试
     */
    @GetMapping("/topicTest")
    public void topicTest() {
        topicSender.send();
    }

    /**
     * fanout exchange类型rabbitmq测试
     */
    @GetMapping("/fanoutTest")
    public void fanoutTest() {
        fanoutSender.send();
    }

    @GetMapping("/callback")
    public void callbak() {
        callBackSender.send();
    }


}
