package com.itjoyee.rpc.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

import com.itjoyee.rpc.common.RpcRequest;
import com.itjoyee.rpc.common.RpcResponse;
import com.itjoyee.rpc.registry.ServiceDiscovery;

/**
 * RPC 代理（用于创建 RPC 服务代理）
 *
 * @author itjoyee
 * @since 1.0.0
 */
public class RpcProxy {

    private String serverAddress;
    private ServiceDiscovery serviceDiscovery;

    public RpcProxy(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public RpcProxy(ServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    @SuppressWarnings("unchecked")
    public <T> T create(Class<?> interfaceClass) {
        return (T) Proxy.newProxyInstance(
            interfaceClass.getClassLoader(),
            new Class<?>[]{interfaceClass},
            new InvocationHandler() {
            	@Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                   
            		RpcRequest request = new RpcRequest();
                    request.setRequestId(UUID.randomUUID().toString());
                    request.setClassName(method.getDeclaringClass().getName());
                    request.setMethodName(method.getName());
                    request.setParameterTypes(method.getParameterTypes());
                    request.setParameters(args);

                    if (serviceDiscovery != null) {
                        serverAddress = serviceDiscovery.discover();
                    }

                    String[] array = serverAddress.split(":");
                    String host = array[0];
                    int port = Integer.parseInt(array[1]);

                    // 
                    RpcClient client = new RpcClient(host, port);
                    RpcResponse response = client.send(request);

                    if (response.isError()) {
                        throw response.getError();
                    } else {
                        return response.getResult();
                    }
                }
            }
        );
    }
}
