package com.gw.login.service.data;

import java.util.List;

import com.gw.systemManager.data.SysResourceOutData;

import lombok.Data;

@Data
public class GetSessionInfoOutData {
    // 用户ID
    private Long id;

    // 登录名
    private String account;
    
    //姓名
    private String userName;

    // 未加密的用户密码
    private String password;
    
    //头像
    private String headUrl;
    
    //电话
    private String phone;

    // 用户可以访问的资源地址列表
    private List<SysResourceOutData> resourceList;
    
    //单位id
    private String unitId;
    //token
    private String token;
}
