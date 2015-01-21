package com.itjoyee.rpc.sample.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author itjoyee
 * 服务容器启动类
 */
public class RpcBootstrap {

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("spring.xml");
    }
}
