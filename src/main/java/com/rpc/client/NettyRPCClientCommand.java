package com.rpc.client;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.rpc.common.RPCRequest;

/**
 * 实现熔断
 */
public class NettyRPCClientCommand extends HystrixCommand {

    private NettyRPCClient client;

    private RPCRequest request;

    public NettyRPCClientCommand(NettyRPCClient client,RPCRequest request){
        super(setter());
        this.client = client;
        this.request = request;
    }

    private static Setter setter(){
        HystrixCommandProperties.Setter commandPropertiesSetter = HystrixCommandProperties.Setter()
                //hystrix默认是线程池隔离
                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
                //熔断
                .withCircuitBreakerEnabled(true) //启用熔断，默认true
                .withCircuitBreakerForceClosed(false) //强制关闭熔断开关，默认false
                .withCircuitBreakerForceOpen(false) //强制打开熔断开关，默认false
                .withCircuitBreakerErrorThresholdPercentage(100) //一个采样周期内（默认10s）失败率超过这个值将会打开熔断开关，默认50%
                .withCircuitBreakerRequestVolumeThreshold(20)  //一个采样周期内达到这个请求数才进行失败百分比判定熔断，默认20
                .withCircuitBreakerSleepWindowInMilliseconds(30000)  //一旦熔断后，每隔这个时间窗口允许一次重试，成功则关闭熔断开关，否则继续打开，默认5s
                ;
        return HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("client"))
                .andCommandPropertiesDefaults(commandPropertiesSetter);


    }

    @Override
    protected Object run() throws Exception {
        return this.client.sendRequest(request);
    }
}
