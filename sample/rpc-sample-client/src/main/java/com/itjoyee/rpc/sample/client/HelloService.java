package com.itjoyee.rpc.sample.client;

public interface HelloService {

    String hello(String name);

    String hello(Person person);
}
