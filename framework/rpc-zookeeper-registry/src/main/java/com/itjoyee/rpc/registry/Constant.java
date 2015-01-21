package com.itjoyee.rpc.registry;

/**
 * 常量
 * @author itjoyee
 * @since 1.0.0
 */
public interface Constant {

    /**
     * zookeeper客户端session超时时间
     */
    int ZK_SESSION_TIMEOUT = 5000;

    /**
     *zookeeper的znode根路径
     */
    String ZK_REGISTRY_PATH = "/registry";
    
    /**
     * zookeeper上注册的服务的根路径
     */
    String ZK_DATA_PATH = ZK_REGISTRY_PATH + "/services";
}