package com.gw.aspect.util;


import com.alibaba.fastjson.JSONObject;
import com.gw.aspect.data.BdofiResponseVo;
import com.gw.common.Json;
import com.gw.util.HttpClientUtil;
import com.gw.util.HttpJson;
import com.gw.util.JsonUtils;
import org.springframework.util.Base64Utils;

import java.security.interfaces.RSAPublicKey;

public class RpcTokenUtil {
    private static String BDOFI_TOKEN;
    private static Long BDOFI_OUTDATE;
    public static String getBDOFIToken() throws Exception {
        if(null == BDOFI_OUTDATE || System.currentTimeMillis() > BDOFI_OUTDATE){
            Json json = HttpClientUtil.doGet("http://183.136.177.64:39999/BDOFI/public/getPublicKey");
            if(json.isSuccess()){
                BdofiResponseVo bdofiResponseVo = JsonUtils.jsonToPojo(json.getObj().toString(), BdofiResponseVo.class);
                String publicKey = bdofiResponseVo.getData().get(0).getString("publicKey");
                RSAPublicKey pubKey = RSAUtils.restorePublicKey(Base64Utils.decode(publicKey.getBytes()));
                String mi = RSAUtils.encryptByPublicKey("728D6F9733447B9FBCF8BAD3E22896EB", pubKey);
                JSONObject postParams = new JSONObject();
                postParams.put("appId", "1000100040");
                postParams.put("secret",mi);
                postParams.put("publicKey",publicKey);
                HttpJson httpJson = HttpClientUtil.easemobPost("http://183.136.177.64:39999/BDOFI/public/token", "", postParams);
                BdofiResponseVo tokenResponseVo = JsonUtils.jsonToPojo(httpJson.getObj().toString(), BdofiResponseVo.class);
                BDOFI_TOKEN = tokenResponseVo.getData().get(0).getString("token");
                String expired = tokenResponseVo.getData().get(0).getString("expired");
                BDOFI_OUTDATE = System.currentTimeMillis() + Long.parseLong(expired);
            }
        }

        return BDOFI_TOKEN;
    }

}
