package com.rpc.service.register;

import com.extension.spi.anotatation.SPI;

import java.net.InetSocketAddress;

/**
 * @author YJL
 */
@SPI("nacos")
public interface ServiceRegistry {

    /**
     * 将一个服务注册待注册表
     * @param serviceName
     * @param inetSocketAddress
     */
    void register(String serviceName, InetSocketAddress inetSocketAddress);

    /**
     * 通过服务名查找服务端地址
     * @param serviceName
     * @return
     */
    InetSocketAddress lookupService(String serviceName);


}
