package com.gw.systemManager.data;


import lombok.Data;
/**
 * 前端传入资源参数
 * @author zfg
 *
 */
@Data
public class SysResourceIndata {
    private Long id;
    private String ids;
    private String name;
    private String url;

    private Integer seq;

    private String resourceType;

    private Long pid;

    private String createDate;

    private String createUser;

    private String updateDate;

    private String updateUser;

    private String type;
}
