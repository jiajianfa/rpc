package com.itjoyee.rpc.sample.server;

import com.itjoyee.rpc.sample.client.HelloService;
import com.itjoyee.rpc.sample.client.Person;
import com.itjoyee.rpc.server.RpcService;

/**
 * @author itjoyee
 * rpc服务
 */
@RpcService(HelloService.class)
public class HelloServiceImpl implements HelloService {

    public String hello(String name) {
        return "Hello! " + name;
    }

    public String hello(Person person) {
        return "Hello! " + person.getFirstName() + " " + person.getLastName();
    }
}
