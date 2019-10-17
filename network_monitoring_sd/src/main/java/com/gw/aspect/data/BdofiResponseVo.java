package com.gw.aspect.data;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;

@Data
public class BdofiResponseVo {
    private int ret_code;
    private String ret_msg;
    private List<JSONObject> data;
}
