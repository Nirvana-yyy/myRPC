package com.rpc.server;

import com.rpc.service.*;

/**
 * @author YJL
 */
public class TestServer {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        BlogService blogService = new BlogServiceImpl();
        ServiceProvider serviceProvider = new ServiceProvider("127.0.0.1",8081);
        serviceProvider.provideServiceInterface(userService);
        serviceProvider.provideServiceInterface(blogService);
        RPCserver rpcServer = new NettyRPCServer(serviceProvider);
        rpcServer.start(8081);
    }

}
