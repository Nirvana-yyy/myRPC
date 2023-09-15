package com.extension.impl.serialize;

import com.extension.spi.anotatation.Activate;
import com.rpc.service.pluginservice.SerializerService;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author YJL
 */
@Activate
public class ProtostuffSerializerServiceImpl implements SerializerService {

    private static LinkedBuffer buffer = LinkedBuffer.allocate();

    private static Map<Class<?>, Schema<?>> schemaMap = new ConcurrentHashMap<>();

    @Override
    public  <T>byte[] serializer(T obj){
        Class<T> clazz = (Class<T>) obj.getClass();
        Schema<T> schema = getSchema(clazz);
        byte[] data;
        data = ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        buffer.clear();
        return data;
    }

    @Override
    public  <T> T deserialize(byte[] data, Class<T> clazz) {
        Schema<T> schema = getSchema(clazz);
        T obj = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(data,obj,schema);
        return obj;
    }

    private static <T> Schema<T> getSchema(Class<T> clazz){
        Schema<T> schema = (Schema<T>) schemaMap.get(clazz);
        if (schema == null) {
            //这个schema通过RuntimeSchema进行懒创建并缓存
            //所以可以一直调用Runtime.getSchema(),这个方法是线程安全的
            schema = RuntimeSchema.getSchema(clazz);
            if (schema == null){
                schemaMap.put(clazz,schema);
            }
        }
        return schema;
    }

}
