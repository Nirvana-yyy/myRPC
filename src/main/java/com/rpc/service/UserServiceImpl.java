package com.rpc.service;

import com.rpc.common.User;

import java.util.Random;
import java.util.UUID;

public class UserServiceImpl implements UserService{
    @Override
    public User getUserId(Integer id) {
        // 模拟从数据库中取用户的行为
        Random random = new Random();
        User user = User.builder()
                .username(UUID.randomUUID().toString())
                .id(id)
                .sex(random.nextBoolean()).build();
        return user;
    }
    @Override
    public Integer insertUserId(User user) {
            System.out.println("输入数据成功："+user);
            return 1;
    }
}

