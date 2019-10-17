package com.gw.openApi.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.exception.ServiceException;
import com.gw.front.couplet.data.FrontCoupletInData;
import com.gw.front.couplet.data.FrontInterFaceStatusOutData;
import com.gw.front.history.data.FrontHisSDDeviceStatusOutData;
import com.gw.front.history.data.FrontHistoryInData;
import com.gw.mapper.UtEqEquipmentMapper;
import com.gw.mapper.UtEqEquipmentdetialgwMapper;
import com.gw.mapper.UtUnitNetdeviceMapper;
import com.gw.openApi.common.data.in.UnitDeviceInData;
import com.gw.openApi.common.data.out.DeviceRunningData;
import com.gw.openApi.common.data.out.EquipmentBaseData;
import com.gw.openApi.common.data.out.InterfaceRunningData;
import com.gw.openApi.common.data.out.UnitDeviceOutData;
import com.gw.openApi.common.service.IDeviceService;
import com.gw.util.Util;
import com.gw.util.UtilConst;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeviceServiceImpl implements IDeviceService{
    @Value("${raw.data.database}")
    private String databaseName;

    @Value("${TL.unitParam.defualtAccountId}")
    private String defaultAcountId;

    @Autowired
    private UtUnitNetdeviceMapper utUnitNetdeviceMapper;
    @Autowired
    private UtEqEquipmentMapper utEqEquipmentMapper;

    @Autowired
    private UtEqEquipmentdetialgwMapper utEqEquipmentdetialgwMapper;


    @Override
    public Object getDeviceList(FrontHistoryInData inData) throws Exception {
        Object obj = null;
        if(Util.isNotEmpty(defaultAcountId)){
            inData.setUserId(defaultAcountId);
            inData.setDatabase(databaseName);
            List<FrontHisSDDeviceStatusOutData> list = utUnitNetdeviceMapper.getSDDeviceStatusList(inData);
            obj =  buildEquipmentOutData(list);
        }else if(inData.isAccountChecked()){
            PageHelper.startPage(inData.getPageNumber(),inData.getPageSize());
            inData.setDatabase(databaseName);
            List<FrontHisSDDeviceStatusOutData> list = utUnitNetdeviceMapper.getSDDeviceStatusList(inData);
            if(Util.isNotEmpty(list)){
                obj = new PageInfo<>(list);
            }
        }
        return obj;
    }

    @Override
    public DeviceRunningData getEquipmentRunningInfo(EquipmentBaseData inData) throws Exception {
        if(Util.isEmpty(inData)){
            throw new ServiceException(UtilConst.ERRO_KEYPARAM_NULL);
        }
        if(Util.isEmpty(defaultAcountId) && !inData.isAccountChecked()){
            throw new ServiceException(UtilConst.ERRO_ACCOUNT_PASSWORD);
        }
        DeviceRunningData equipmentRunningData= new DeviceRunningData();
        List<FrontInterFaceStatusOutData> interfaceValueList = utEqEquipmentdetialgwMapper.getInterfaceValueList(buildInterValueParam(inData));
        if(null!= interfaceValueList &&interfaceValueList.size()!= 0){
            equipmentRunningData.setNetDeviceId(Long.parseLong(interfaceValueList.get(0).getNetDeviceId()));
        }
        Map<String,List<InterfaceRunningData>> intfaceData = new HashMap<>();
        InterfaceRunningData interfaceRunningData = null;
        String ioType = null;
        List<InterfaceRunningData> list = null;
        for (FrontInterFaceStatusOutData outData : interfaceValueList) {
            if(null != outData.getIoType()){
                interfaceRunningData = new InterfaceRunningData();
                BeanUtils.copyProperties(outData,interfaceRunningData);
                ioType = outData.getIoType();
                if("数字量".equals(ioType)&& Util.isNotEmpty(outData.getCurrentValue())){
                    double current = Double.parseDouble(outData.getCurrentValue());
                    double good = Util.isNotEmpty(outData.getDigitalNormal())?Double.parseDouble(outData.getDigitalNormal()):0.0;
                    if(current == good){
                        interfaceRunningData.setCurrentValue(outData.getGoodName());
                    }else{
                        interfaceRunningData.setCurrentValue(outData.getBadName());
                    }
                }
                if(null == intfaceData.get(ioType)){
                    list = new ArrayList<>();
                    list.add(interfaceRunningData);
                    intfaceData.put(ioType,list);
                }else{
                    intfaceData.get(ioType).add(interfaceRunningData);
                }
            }
        }
        equipmentRunningData.setInterfaceStatusList(intfaceData);
        return equipmentRunningData;
    }

    @Override
    public PageInfo<UnitDeviceOutData> getUnitDeviceList(UnitDeviceInData deviceInData) throws Exception {
        if(Util.isEmpty(defaultAcountId) && deviceInData.isAccountChecked()){
            throw new ServiceException(UtilConst.ERRO_ACCOUNT_PASSWORD);
        }
        PageHelper.startPage(deviceInData.getPageNumber(),deviceInData.getPageSize());
        List<UnitDeviceOutData> list = utEqEquipmentMapper.getUnitEquipmentList(deviceInData);
        for (UnitDeviceOutData outData:list) {
            if(outData.getDeviceType() == 1){
                outData.setDeviceTypeName("报警主机");
            }else if(outData.getDeviceType() == 2){
                outData.setDeviceTypeName("传输装置");
            }else if(outData.getDeviceType() == 3){
                outData.setDeviceTypeName("RTU");
            }
        }
       PageInfo<UnitDeviceOutData> pagedDeviceData = new PageInfo<>(list);
        return pagedDeviceData;
    }

    @Override
    public Map<String, String> getDeviceTypeList() {
        Map<String,String> map = new HashMap<>();
        map.put("1","报警主机");
        map.put("2","传输装置");
        map.put("3","RTU");
        return map;
    }



    private Map<String ,List<FrontHisSDDeviceStatusOutData>> addDeviceDataToMap(Map<String ,List<FrontHisSDDeviceStatusOutData>>map, String key,FrontHisSDDeviceStatusOutData outData){
        if(Util.isEmpty(map.get(key))){
            map.put(key,new ArrayList<FrontHisSDDeviceStatusOutData>());
        }
        map.get(key).add(outData);
        return map;
    }
    private String validateDeviceStatus(String originalStatus) {
        StringBuffer deviceStatus = new StringBuffer();
        if(Util.isNotEmpty(originalStatus)){
            int val = Integer.parseInt(originalStatus);
            if ((val & 0x01) == 0) {
                deviceStatus.append("测试状态,");
            }else {
                deviceStatus.append("正常监视状态,");
            }
            if ((val & 0x02) == 0) {
                deviceStatus.append("无火警,");
            }else {
                deviceStatus.append("有火警,");
            }
            if ((val & 0x04) == 0) {
                deviceStatus.append("无故障,");
            }else {
                deviceStatus.append("故障,");
            }
            if ((val & 0x08) == 0) {
                deviceStatus.append("主电正常,");
            }else {
                deviceStatus.append("主电故障,");
            }
            if ((val & 0x010) == 0) {
                deviceStatus.append("备电正常,");
            }else {
                deviceStatus.append("备电故障,");
            }
            if ((val & 0x020) == 0) {
                deviceStatus.append("通信信道正常,");
            }else {
                deviceStatus.append("与监控中心通信信道故障,");
            }
            if ((val & 0x040) == 0) {
                deviceStatus.append("监测连接线路正常,");
            }else {
                deviceStatus.append("监测连接线路故障,");
            }
        }else{
            deviceStatus.append("暂无状态,");
        }
        return deviceStatus.toString().substring(0,deviceStatus.length()-1);
    }

    private FrontCoupletInData buildInterValueParam(EquipmentBaseData inData) {
        FrontCoupletInData data =new FrontCoupletInData();
        data.setDatabase(databaseName);
        if(null != inData.getEqId()){
            data.setDeviceId(inData.getEqId());
        }
        if(null != inData.getEqIds()){
            data.setEqIds(inData.getEqIds());
        }
        if(null != inData.getNetDeviceId()){
            data.setNetDeviceId(inData.getNetDeviceId());
        }
        return data;
    }

    private Map<String, List<FrontHisSDDeviceStatusOutData>> buildEquipmentOutData(List<FrontHisSDDeviceStatusOutData> list) {
        Map<String ,List<FrontHisSDDeviceStatusOutData>> map = new HashMap<>();
        for (FrontHisSDDeviceStatusOutData outData:list) {
            List<EquipmentBaseData> baseEquipmentList = utEqEquipmentMapper.getBaseEquipmentList(outData.getId());
            if("主机".equals(outData.getDeviceType())){
                buildOutData(map, "报警主机",outData, baseEquipmentList);
            }else if("用户传输装置".equals(outData.getDeviceType())){
                outData.setOriginalStatus(validateDeviceStatus(outData.getOriginalStatus()));
                buildOutData(map, "报警主机传输装置",outData, baseEquipmentList);
            }else if("RTU".equals(outData.getDeviceType())&&"防排烟系统".equals(outData.getEqSystemName())){
                buildOutData(map, "防排烟监控装置",outData, baseEquipmentList);
            }else if("RTU".equals(outData.getDeviceType())&&"灭火系统".equals(outData.getEqSystemName())){
                List<String> _pumpEqIds = null;
                List<String> _tankEqIds = null;
                List<String> _tailEqIds = null;
                List<String> _pressureEqIds = null;
                List<String> _otherEqIds = null;

                for (EquipmentBaseData data:baseEquipmentList) {
                    if("泵控制柜".equals(data.getEqClassName())){
                        _pumpEqIds = _pumpEqIds ==null? new ArrayList<>():_pumpEqIds;
                        _pumpEqIds.add(data.getEqId());
                    }else if("水箱".equals(data.getEqClassName())){
                        _tankEqIds = _tankEqIds ==null? new ArrayList<>():_tankEqIds;
                        _tankEqIds.add(data.getEqId());
                    }else if("压力".equals(data.getEqClassName())){
                        if(data.getEqName()!=null && data.getEqName().lastIndexOf("末端")>-1){
                            _tailEqIds = _tailEqIds ==null? new ArrayList<>():_tailEqIds;
                            _tailEqIds.add(data.getEqId());
                        }else if(data.getEqName()!=null && data.getEqName().lastIndexOf("压力")>-1){
                            _pressureEqIds = _pressureEqIds ==null? new ArrayList<>():_pressureEqIds;
                            _pressureEqIds.add(data.getEqId());
                        }else{
                            _otherEqIds = _otherEqIds ==null? new ArrayList<>():_otherEqIds;
                            _otherEqIds.add(data.getEqId());
                        }
                    }else{
                        _otherEqIds = _otherEqIds ==null? new ArrayList<>():_otherEqIds;
                        _otherEqIds.add(data.getEqId());
                    }
                }
                if(null!=_pumpEqIds){
                    outData.setEqIds(_pumpEqIds);
                    addDeviceDataToMap(map,"水泵房监控装置",outData);
                }
                if(null!=_tankEqIds){
                    outData.setEqIds(_pumpEqIds);
                    addDeviceDataToMap(map,"液位仪",outData);
                }
                if(null!=_tailEqIds){
                    outData.setEqIds(_tailEqIds);
                    addDeviceDataToMap(map,"末端压力装置",outData);
                }
                if(null != _pressureEqIds){
                    outData.setEqIds(_pressureEqIds);
                    addDeviceDataToMap(map,"实验消火栓压力",outData);
                }
                if(null!=_otherEqIds){
                    outData.setEqIds(_otherEqIds);
                    addDeviceDataToMap(map,"其他",outData);
                }
            }else{
                addDeviceDataToMap(map,"其他",outData);
            }
        }
        return map;
    }

    private void buildOutData(Map<String, List<FrontHisSDDeviceStatusOutData>> map,String key, FrontHisSDDeviceStatusOutData outData, List<EquipmentBaseData> baseEquipmentList) {
        addEqIdsToDevice(outData,baseEquipmentList);
        addDeviceDataToMap(map,key,outData);
    }

    private void addEqIdsToDevice(FrontHisSDDeviceStatusOutData outData, List<EquipmentBaseData> baseEquipmentList) {
        List<String> eqList = new ArrayList<>();
        for (EquipmentBaseData baseData:baseEquipmentList) {
            eqList.add(baseData.getEqId());
        }
        outData.setEqIds(eqList);
    }

}
