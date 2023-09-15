package com.rpc.service.pluginservice;


import com.extension.spi.anotatation.SPI;

@SPI("serializer")
public interface SerializerService {

    <T> byte[] serializer(T obj) ;

    <T> T deserialize(byte[] data,Class<T> clazz);
}
