package com.extension.impl.registry;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.extension.spi.anotatation.Activate;
import com.rpc.service.register.ServiceRegistry;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.List;

@Slf4j
@Activate
public class NacosServiceRegistry implements ServiceRegistry {

    private static final String SERVER_ADDER = "127.0.0.1:8848";

    private static NamingService namingService;

    static {
        //连接nacos创建命名服务
        try {
            namingService = NamingFactory.createNamingService(SERVER_ADDER);
        } catch (NacosException e) {
            log.error("连接nacos时发生错误 {}",e);
            e.printStackTrace();
        }
    }

    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress) {
        try {
            namingService.registerInstance(serviceName,inetSocketAddress.getHostName(),inetSocketAddress.getPort());
        } catch (NacosException e) {
            log.error("注册服务错误{}",e);
            e.printStackTrace();
        }
    }

    @Override
    public InetSocketAddress lookupService(String serviceName) {
        try {
            List<Instance> instances = namingService.getAllInstances(serviceName);
            Instance instance = instances.get(0);
            return new InetSocketAddress(instance.getIp(), instance.getPort());
        } catch (NacosException e) {
            log.error("获取服务错误");
            e.printStackTrace();
        }

        return null;
    }
}
