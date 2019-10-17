package com.gw.openApi.common.service.impl;

import com.github.pagehelper.PageInfo;
import com.gw.exception.ServiceException;
import com.gw.front.maintenance.data.FrontMaintenanceInData;
import com.gw.front.maintenance.data.FrontMaintenanceOutData;
import com.gw.front.maintenance.service.FrontMaintenanceService;
import com.gw.openApi.common.data.in.MaintenanceInData;
import com.gw.openApi.common.service.IMaintenanceService;
import com.gw.util.Util;
import com.gw.util.UtilConst;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.util.List;

@Service
public class MaintenanceServiceImpl implements IMaintenanceService {

    @Value("${TL.unitParam.defualtAccountId}")
    private String defaultAccountId;
    @Value("${server.port}")
    private String port;
    @Resource
    private FrontMaintenanceService frontMaintenanceService;
    @Override
    public PageInfo<FrontMaintenanceOutData> getMaintenanceList(MaintenanceInData inData) throws Exception{
        if(Util.isEmpty(defaultAccountId) && !inData.isAccountChecked()){
            throw new ServiceException(UtilConst.ERRO_ACCOUNT_PASSWORD);
        }
        FrontMaintenanceInData frontInData = new FrontMaintenanceInData();
        BeanUtils.copyProperties(inData,frontInData);
        if(Util.isNotEmpty(defaultAccountId) ){
            frontInData.setUserId(defaultAccountId);
        }
        return frontMaintenanceService.getRepairList(frontInData);
    }

    @Override
    public FrontMaintenanceOutData getMaintenanceDetail(String repairId) throws Exception{
            FrontMaintenanceOutData repairDetail = frontMaintenanceService.getRepairDetail(repairId);
            if(null != repairDetail){
            InetAddress addr = InetAddress.getLocalHost();
            String urlPath="http://"+ addr.getHostAddress()+":"+port;
            if (Util.isNotEmpty(repairDetail.getRepairDetailPic())) {
              repairDetail.setRepairDetailPic(urlPath+repairDetail.getRepairDetailPic());
            }
            if(Util.isNotEmpty(repairDetail.getRepairPic())){
                repairDetail.setRepairPic(urlPath + repairDetail.getRepairPic());
            }
            if(Util.isNotEmpty(repairDetail.getRepairPicList())){
                List<String> repairPicList = repairDetail.getRepairPicList();
                for (int i = 0; i<repairPicList.size(); i++) {
                    repairPicList.set(i,urlPath + repairPicList.get(i));
               }
            }
            if(Util.isNotEmpty(repairDetail.getRepairDetailPicList())){
                List<String> repairDetailPicList = repairDetail.getRepairDetailPicList();
                for (int i = 0; i<repairDetailPicList.size(); i++) {
                    repairDetailPicList.set(i,urlPath + repairDetailPicList.get(i));
                }
            }


            }

            return repairDetail;
    }
}
