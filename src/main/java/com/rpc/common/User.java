package com.rpc.common;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author YJL
 */
@Data
@Builder
public class User implements Serializable {
    //客户端和服务端共有的

    private Integer id;
    private String username;
    private Boolean sex;

}
