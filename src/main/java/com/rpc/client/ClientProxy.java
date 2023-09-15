package com.rpc.client;

import com.github.rholder.retry.*;
import com.rpc.common.RPCRequest;
import com.rpc.common.RPCResponse;
import com.rpc.util.MyRetryListener;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.UUID;
import java.util.concurrent.*;

@Data
@AllArgsConstructor
@Slf4j
public class ClientProxy  implements InvocationHandler{

    /**
     * 接口最大能容忍的调用并发量
     */
    private static final int MAX_SEMAPHORE = 10;

    private static final Semaphore SEMAPHORE = new Semaphore(MAX_SEMAPHORE);

    private RPCClient client;

    private static Retryer<RPCResponse> retryer;


    static{
        retryer = RetryerBuilder
                .<RPCResponse>newBuilder()
                .retryIfExceptionOfType(TimeoutException.class)
                .withWaitStrategy(WaitStrategies.fixedWait(3,TimeUnit.SECONDS))
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))
                .withRetryListener(new MyRetryListener())
                .build();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args){
        //尝试获取，获取不到就快速失败
        if (!SEMAPHORE.tryAcquire()) {
            return RPCResponse.builder()
                    .code(500)
                    .message("请求频率超过限制" + MAX_SEMAPHORE)
                    .data("请求频率超过限制" + MAX_SEMAPHORE)
                    .build().getData();
        }

        RPCRequest request = RPCRequest.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .params(args)
                .requestId(UUID.randomUUID().toString())
                .paramsType(method.getParameterTypes())
                .build();


        RPCResponse response = null;
        try {
            response = retryer.call(() -> {
                CompletableFuture future = client.sendRequest(request);
                RPCResponse rpcResponse = (RPCResponse) future.get(3,TimeUnit.SECONDS);
                return rpcResponse;
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (RetryException e) {
            e.printStackTrace();
        }finally {
            //释放锁
            SEMAPHORE.release();
        }
        return response.getData();
    }




    public <T>T getProxy(Class<T> clazz){
        try {
            Object o = new ByteBuddy()
                    .subclass(Object.class)
                    .implement(clazz)
                    .method(ElementMatchers.anyOf(clazz.getMethods()))
                    .intercept(InvocationHandlerAdapter.of(this))
                    .make()
                    .load(this.getClass().getClassLoader())
                    .getLoaded()
                    .getDeclaredConstructor()
                    .newInstance();
            return  (T)o;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
