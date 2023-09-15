package com.rpc.service;

import com.rpc.common.Blog;

public class BlogServiceImpl implements BlogService{
    @Override
    public Blog getBlogById(Integer id) {
        Blog blog = Blog.builder()
                .userId(22)
                .title("我的博客")
                .id(id)
                .build();
        System.out.println("客户端查询了"+id+ "博客");
        return blog;
    }
}
