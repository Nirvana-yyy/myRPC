package com.rpc.common;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author YJL
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Blog implements Serializable {

    private Integer id;
    private Integer userId;
    private String title;


}
