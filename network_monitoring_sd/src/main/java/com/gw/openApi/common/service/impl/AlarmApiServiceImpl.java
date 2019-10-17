package com.gw.openApi.common.service.impl;

import com.gw.exception.ServiceException;
import com.gw.front.history.data.FrontHistoryInData;
import com.gw.front.history.service.FrontHistoryService;
import com.gw.mapper.UtLzBjzjalarmMapper;
import com.gw.mapper.UtUnitBaseinfoMapper;
import com.gw.openApi.common.data.in.CheckedAlarmVo;
import com.gw.openApi.common.data.out.HistoryAlarmCount;
import com.gw.openApi.common.service.IAlarmApiService;
import com.gw.util.Util;
import com.gw.util.UtilConst;
import com.gw.util.UtilConv;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class AlarmApiServiceImpl implements IAlarmApiService {
    @Resource
    private FrontHistoryService frontHistoryService;

    @Resource
    private UtLzBjzjalarmMapper bjzjalarmMapper;
    @Resource
    private UtUnitBaseinfoMapper unitBaseinfoMapper;

    @Override
    public Object getAlarmList(CheckedAlarmVo alarmInData) throws Exception {
        if(!alarmInData.isAccountChecked()){
            throw new ServiceException(UtilConst.ERRO_ACCOUNT_PASSWORD);
        }
        if (Util.isEmpty(alarmInData.getStartDate()) && Util.isEmpty(alarmInData.getEndDate())) {
            buildDefaultDate(alarmInData);
        }
        if(0==alarmInData.getSysType()){//0,报警主机
            return frontHistoryService.getFireHistoryList(buildAlarmQueryParam(alarmInData));
        }else{//rtu:1,防排烟;2,水系统
            return frontHistoryService.getInterfaceAlarmList(buildAlarmQueryParam(alarmInData));
        }
    }

    @Override
    public List<HistoryAlarmCount> getAlarmCount(CheckedAlarmVo inData) throws Exception {
        if (!inData.isAccountChecked()) {
            throw new ServiceException(UtilConst.ERRO_ACCOUNT_PASSWORD);
        }
        if (Util.isEmpty(inData.getStartDate()) && Util.isEmpty(inData.getEndDate())) {
            buildDefaultDate(inData);
        }
        List<HistoryAlarmCount> alarmCount = bjzjalarmMapper.getHistoryAlarmCount(inData.getStartDate(), inData.getEndDate(), Long.parseLong(inData.getUserId()), inData.getKeyword());
        return alarmCount;
    }

    private FrontHistoryInData buildAlarmQueryParam(CheckedAlarmVo alarmInData) {
        FrontHistoryInData historyInData = new FrontHistoryInData();
        BeanUtils.copyProperties(alarmInData,historyInData);
        historyInData.setUserId(alarmInData.getUserId() + "");
        if(alarmInData.getSysType()==0){//报警系统
            historyInData.setType("3");
            if(alarmInData.getAlarmType() == 0){//默认所有类型
                historyInData.setStatus("");
            }else if(alarmInData.getAlarmType() == 1){//火警
                historyInData.setStatus("1,22,23,24,25,26");
            }else{//其他报警类型
                historyInData.setStatus(alarmInData.getAlarmType()+"");
            }
        }else if(alarmInData.getSysType()==1){//防排烟
            historyInData.setType("9");
        }else if(alarmInData.getSysType()==2){//水系统(灭火系统)
            historyInData.setType("1");
        }

        return historyInData;
    }

    /**
     * 设置查询的默认时间段为当月
     *
     * @param inData
     */
    private void buildDefaultDate(CheckedAlarmVo inData) {
        inData.setStartDate(UtilConv.date2Str(new Date(), UtilConv.YEAR_MONTH) + "/1");
        inData.setEndDate(UtilConv.date2Str(new Date(), UtilConv.YEAR_MONTH) + "/31");
    }

}
