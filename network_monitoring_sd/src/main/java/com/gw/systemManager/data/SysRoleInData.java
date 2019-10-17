package com.gw.systemManager.data;


import lombok.Data;

import java.util.Date;
@Data
public class  SysRoleInData{
    private Long id;

    private String rolename;

    private Integer roletype;

    private Integer sortindex;
    
    private String resourceId;

    private Date adddate;

    private Date updatedate;

    private Boolean isdeleted;

    private Boolean isadmin;

    private String type;

}
