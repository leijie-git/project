package com.gw.openApi.common.data.out;

import com.gw.mapper.entity.UtMaintenanceUnit;
import com.gw.mapper.entity.UtUnitBaseinfo;
import lombok.Data;

@Data
public class UserOut {
    private String userId;
    private String sysUserId;
    private String userName;
    private String account;
    private String userType;
    private String userRole;
    private String email;
    private String mobilePhone;
    private String unitID;
    private String unitName;
    private UtUnitBaseinfo unitBaseinfo;
    private UtMaintenanceUnit maintenanceUnit;
}
