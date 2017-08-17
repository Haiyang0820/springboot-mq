package com.example.demo.rabbitmq.objectsend;

import java.io.Serializable;

/**
 * Created by think on 2017/8/16.
 */
public class User implements Serializable{

    private String name;
    private String pass;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }


}
