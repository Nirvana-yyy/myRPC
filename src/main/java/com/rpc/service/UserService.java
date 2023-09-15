package com.rpc.service;

import com.rpc.common.User;

/**
 * @author YJL
 */
public  interface UserService {

    /**
     * 客户端通过这个接口调用服务端的实现类、
     * @param id
     * @return
     */
    User getUserId(Integer id);

    /**
     * 给这个服务端增加一个功能
     * @param user
     * @return
     */
   Integer insertUserId(User user);

}
