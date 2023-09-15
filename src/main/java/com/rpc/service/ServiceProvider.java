package com.rpc.service;

import com.extension.spi.extension.ExtensionLoader;
import com.rpc.service.register.ServiceRegistry;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * 存放服务接口名与服务端对应的实现类（本质是hashmap）
 * 服务启动时要暴露其相关的实现类
 * @author YJL
 */
public class ServiceProvider {

    /**
     * 一个类可能实现多个接口
     */
    private Map<String,Object> interfaceProvider;

    private ServiceRegistry serviceRegistry;

    private String host;

    private int port;

    public ServiceProvider(String host,int port){
        this.host = host;
        this.port = port;
        this.interfaceProvider = new HashMap<>();
        this.serviceRegistry = ExtensionLoader.getExtensionLoader(ServiceRegistry.class).getActivate("nacos");

    }

    public ServiceProvider() {
        this.interfaceProvider = new HashMap<>();
    }

    public void provideServiceInterface(Object service){
        Class<?>[] interfaces = service.getClass().getInterfaces();
        for (Class<?> clazz : interfaces) {
            interfaceProvider.put(clazz.getName(),service);
            serviceRegistry.register(clazz.getName(), new InetSocketAddress(host,port));
        }

    }

    public Object getService(String interfaceName){
        return interfaceProvider.get(interfaceName);
    }


}
