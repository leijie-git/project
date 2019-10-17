package com.gw.openApi.common.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 对外接口账号鉴权（通过切面实现）
 */
@ApiModel("鉴权")
@Data
public class CheckAcountBaseData {
    @ApiModelProperty(hidden=true)
    private boolean isAccountChecked=true;
    @ApiModelProperty(hidden=true)
    private String userId;
    @ApiModelProperty("用户账户")
    private String account;
    @ApiModelProperty("用户密码")
    private String password;
}
