package com.itjoyee.rpc.registry;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务注册
 *
 * @author itjoyee
 * @since 1.0.0
 */
public class ServiceRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRegistry.class);

    private CountDownLatch latch = new CountDownLatch(1);

    private String registryAddress;

    /**
     * 服务注册类
     * @param registryAddress 服务注册的地址
     */
    public ServiceRegistry(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    /**
     * 注册服务
     * @param serviceName 服务名称
     */
    public void register(String serviceName) {
        if (serviceName != null) {
        	// 连接zookeeper
            ZooKeeper zk = connectServer();
            if (zk != null) {
            	// 创建zookeeper节点
                createNode(zk, serviceName);
            }
        }
    }

    private ZooKeeper connectServer() {
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(registryAddress, Constant.ZK_SESSION_TIMEOUT, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (event.getState() == Event.KeeperState.SyncConnected) {
                    	// 发送时间让阻塞线程继续执行
                        latch.countDown();
                    }
                }
            });
            // 阻塞当前线程
            latch.await();
        } catch (IOException | InterruptedException e) {
            LOGGER.error("", e);
        }
        return zk;
    }

    private void createNode(ZooKeeper zk, String data) {
        try {
            byte[] bytes = data.getBytes();
            String path = zk.create(Constant.ZK_DATA_PATH, bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            LOGGER.debug("create zookeeper node ({} => {})", path, data);
        } catch (KeeperException | InterruptedException e) {
            LOGGER.error("", e);
        }
    }
}