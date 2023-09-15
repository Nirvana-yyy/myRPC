package com.rpc.client;

import com.rpc.common.User;
import com.rpc.service.UserService;

/**
 * @author YJL
 */
public class TestClient {

    public static void main(String[] args) {
        RPCClient rpcClient = new NettyRPCClient();
        ClientProxy clientProxy = new ClientProxy(rpcClient);
        UserService userService = clientProxy.getProxy(UserService.class);
        User userId = userService.getUserId(10);
        System.out.println("从服务器端得到的user为：" + userId);
    }


}
