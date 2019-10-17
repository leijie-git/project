package com.gw.openApi.common.service.impl;

import com.github.pagehelper.PageInfo;
import com.gw.exception.ServiceException;
import com.gw.front.couplet.data.FrontCoupletAlarmInfoOutData;
import com.gw.front.couplet.service.FrontCoupletService;
import com.gw.front.history.data.FrontHistoryAlarmInfoOutData;
import com.gw.front.history.data.FrontHistoryInData;
import com.gw.front.history.data.FrontHistoryInterfaceAlarmData;
import com.gw.front.history.service.FrontHistoryService;
import com.gw.openApi.common.data.in.AlarmInData;
import com.gw.openApi.common.service.IAlarmHistoryService;
import com.gw.util.Util;
import com.gw.util.UtilConst;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class AlarmHistoryServiceImpl implements IAlarmHistoryService {
    @Value("${TL.unitParam.defualtAccountId}")
    private String defaultAcountId;
    @Resource
    private FrontHistoryService frontHistoryService;

    @Resource
    private FrontCoupletService frontCoupletService;

    @Override
    public PageInfo<FrontHistoryAlarmInfoOutData> getMainframeAlarmList(AlarmInData inData) throws Exception {
        PageInfo<FrontHistoryAlarmInfoOutData> fireHistoryList = frontHistoryService.getFireHistoryList(buildAlarmQueryParam(inData));
        return fireHistoryList;
    }

    @Override
    public PageInfo<FrontHistoryInterfaceAlarmData> getRTUAlarmList(AlarmInData inData) throws Exception {
        PageInfo<FrontHistoryInterfaceAlarmData> interfaceAlarmList = frontHistoryService.getInterfaceAlarmList(buildAlarmQueryParam(inData));
        return interfaceAlarmList;
    }

    @Override
    public Map<Integer,String> getAlarmTypeList() {
        Map<Integer,String> map = new HashMap<>();
        map.put(1,"火警");
        map.put(2,"故障");
        map.put(3,"启动");
        map.put(4,"反馈");
        map.put(5,"监管");
        map.put(6,"屏蔽");
        map.put(7,"恢复");
        map.put(8,"复位");

        return map;
    }

    @Override
    public FrontCoupletAlarmInfoOutData getMainframeDealDetail(String alarmId) throws Exception{
       return frontCoupletService.getAlarmInfo(alarmId, "SysUser");
    }

    @Override
    public FrontCoupletAlarmInfoOutData getRtuDealDetail(String alarmId) throws Exception {
        return frontCoupletService.getRTUAlarmInfo(alarmId,"SysUser");
    }

    private FrontHistoryInData buildAlarmQueryParam(AlarmInData inData) {
        FrontHistoryInData historyInData = new FrontHistoryInData();
        BeanUtils.copyProperties(inData,historyInData);
        if(Util.isEmpty(historyInData.getUserId()) && Util.isNotEmpty(defaultAcountId)){
            //用户id为空,默认为特领调用
            historyInData.setUserId(defaultAcountId);
        }else if(!inData.isAccountChecked()){//判断鉴权是否成功,未鉴权直接异常
            throw new ServiceException(UtilConst.ERRO_ACCOUNT_PASSWORD);
        }
        if(inData.getSysType()==0){//报警系统
            historyInData.setType("3");
            if(inData.getAlarmType() == 0){//默认所有类型
                historyInData.setStatus("");
            }else if(inData.getAlarmType() == 1){//火警
                historyInData.setStatus("1,22,23,24,25,26");
            }else{//其他报警类型
                historyInData.setStatus(inData.getAlarmType()+"");
            }
        }else if(inData.getSysType()==1){//防排烟
            historyInData.setType("9");
        }else if(inData.getSysType()==2){//水系统(灭火系统)
            historyInData.setType("1");
        }

        return historyInData;
    }
}
