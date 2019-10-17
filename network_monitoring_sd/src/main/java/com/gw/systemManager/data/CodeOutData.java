package com.gw.systemManager.data;


import lombok.Data;

@Data
public class CodeOutData {
	
    private String codeid;

    private String codename;

    private String codevalue;

    private String memo;

    private String sortorder;

    private String isdeleted;

    private String adder;

    private String adddate;

    private String updater;

    private String updatedate;

    private String codegroupid;
    
    private String codegroupname;
    private String codegroupkey;
}
