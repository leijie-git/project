package com.gw.login.service.data;

import lombok.Data;

@Data
public class GetLoginInData {
    // 员工id
    private String empId;

    // 员工名称
    private String empName;

    // 员工账号
    private String loginAccount;

    // 密码
    private String password;

    // 新密码
    private String passwordNew;

    // 手机
    private String phone;
}
