package com.gw.repairr.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.build.data.BuildAreaOutData;
import com.gw.common.SnowflakeIdWorker;
import com.gw.device.data.AddressRelOutData;
import com.gw.exception.ServiceException;
import com.gw.generatereport.GenerateMaintenanceUserData;
import com.gw.mapper.*;
import com.gw.mapper.entity.UtLzRepair;
import com.gw.mapper.entity.UtLzRepairPlan;
import com.gw.repairr.data.EquipmentListData;
import com.gw.repairr.data.ParameterData;
import com.gw.repairr.data.RepairData;
import com.gw.repairr.service.RepairService;
import com.gw.util.Util;
import com.gw.util.UtilConv;
import com.gw.util.UtilMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

import static com.gw.alarm.AlarmConstants.SPLIT_FALG;

@Service
public class RepairServiceImpl implements RepairService {

    @Resource
    private UtLzRepairPlanMapper utLzRepairPlanMapper;

    @Resource
    private UtLzRepairMapper utLzRepairMapper;

    @Resource
    private UtEqAddressRelMapper addressRelMapper;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Resource
    private UtUnitBuildareaMapper utUnitBuildareaMapper;


    /**
     * 列表
     * @param inData
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo<RepairData> getRepairList(RepairData inData) throws Exception{
        List<RepairData> rep = getUnitList(inData);
        StringBuilder sb = new StringBuilder();
        for (int i = 0;i < rep.size();){
            sb.append(rep.get(i).getUnitID());
            i++;
            if(i<rep.size()){
                sb.append(",");
            }
        }
        inData.setUnitID(sb.toString());
        PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
        if (!Util.isEmpty(inData.getPlanEndTime())){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(sdf.parse(inData.getPlanEndTime()));
            calendar.add(Calendar.DATE,1);
            inData.setPlanEndTime(sdf.format(calendar.getTime()));
        }
        List<RepairData> list = utLzRepairPlanMapper.getRepairList(inData);
        return new PageInfo<RepairData>(list);
    }

    /**
     * 返回维保单位的执行人
     * @param generateMaintenanceUserData
     * @return
     * @throws Exception
     */
    @Override
    public List<GenerateMaintenanceUserData> getMaintenanceUserBynetworkingID(GenerateMaintenanceUserData generateMaintenanceUserData) throws Exception{
        return utLzRepairPlanMapper.getMaintenanceUserBynetworkingID(generateMaintenanceUserData);
    }

    /**
     * 添加
     * @param inData
     * @throws Exception
     */
    @Override
    public void repairAdd(ParameterData inData) throws Exception {
        if(!Util.isEmpty(inData)){
            if(Util.isEmpty(inData.getPlanStartTime()) && Util.isEmpty(inData.getPlanEndTime())){
                throw new Exception("时间必填");
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if(sdf.parse(inData.getPlanStartTime()).compareTo(sdf.parse(inData.getPlanEndTime())) == 1){
                throw new Exception("开始时间不能大于结束时间");
            }
            UtLzRepairPlan utLzRepairPlan = new UtLzRepairPlan();
            BeanUtils.copyProperties(inData,utLzRepairPlan);
            utLzRepairPlan.setUnitID(Long.valueOf(inData.getUnitID()));
            utLzRepairPlan.setId(snowflakeIdWorker.nextId());
            utLzRepairPlan.setIsGenerate(0);
            utLzRepairPlan.setLastupdate(new Date());
            utLzRepairPlan.setCreateTime(new Date());
            utLzRepairPlan.setPlanStartTime(getStartOfDay(sdf.parse(inData.getPlanStartTime())));
            utLzRepairPlan.setPlanEndTime(getEndOfDay(sdf.parse(inData.getPlanEndTime())));
            utLzRepairPlanMapper.insert(utLzRepairPlan);
        }
    }

    /**
     * 修改
     * @param inData
     * @throws Exception
     */
    @Override
    public void repairModify(ParameterData inData) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(sdf.parse(inData.getPlanStartTime()).compareTo(sdf.parse(inData.getPlanEndTime())) == 1){
            throw new Exception("开始时间不能大于结束时间");
        }

        UtLzRepairPlan utLzRepairPlan = new UtLzRepairPlan();
        utLzRepairPlan.setUnitID(Long.valueOf(inData.getUnitID()));
        BeanUtils.copyProperties(inData,utLzRepairPlan);
        utLzRepairPlan.setId(Long.valueOf(inData.getId()));
        utLzRepairPlan.setLastupdate(new Date());
        utLzRepairPlan.setPlanStartTime(getStartOfDay(sdf.parse(inData.getPlanStartTime())));
        utLzRepairPlan.setPlanEndTime(getEndOfDay(sdf.parse(inData.getPlanEndTime())));
        Integer flag = utLzRepairPlanMapper.updateByPrimaryKeySelective(utLzRepairPlan);
        if(flag < 1) {
            throw new ServiceException(UtilMessage.MODIFY_DATA_FAILED);
        }
    }

    /**
     * 删除
     * @param ids
     * @throws Exception
     */
    @Override
    public void repairDelete(String ids) throws Exception{
        String[] split = ids.split(SPLIT_FALG);
        for (String id : split){
            Integer flag = utLzRepairPlanMapper.deleteByPrimaryKey(Long.parseLong(id));
            if(flag < 1) {
                throw new ServiceException(UtilMessage.DEL_MESSAGE_ERROR);
            }
        }
    }

    /**
     * 重置
     * @param ids
     * @throws Exception
     */
    @Override
    public void repairReset(String ids) throws Exception{
        String[] split = ids.split(SPLIT_FALG);
        for (String id : split){
            UtLzRepair utLzRepair = new UtLzRepair();
            utLzRepair.setBaseid(Long.valueOf(id));
            utLzRepairMapper.delete(utLzRepair);

            //修改计划表生成任务状态
            UtLzRepairPlan utLzRepairPlan = new UtLzRepairPlan();
            utLzRepairPlan.setId(Long.valueOf(id));
            utLzRepairPlan.setIsGenerate(0);
            utLzRepairPlanMapper.updateByPrimaryKeySelective(utLzRepairPlan);
        }
    }

    /**
     * 生成维保任务
     * @param inData
     * @throws Exception
     */
    @Override
    public void repairGenerate(ParameterData inData) throws Exception{

        String ids = inData.getId();
        String[] split = ids.split(SPLIT_FALG);
        for (String id : split){

            UtLzRepairPlan planDetails = utLzRepairPlanMapper.selectByPrimaryKey(Long.valueOf(id));

            List<EquipmentListData> equipmentList = utLzRepairPlanMapper.getEquipmentList(planDetails.getUnitID());

            for (int i = 0;i < equipmentList.size();i++){

                List<AddressRelOutData> list = addressRelMapper.getAllAddressRel(
                        equipmentList.get(i).getID().toString(), equipmentList.get(i).getUnitName());
                for (AddressRelOutData addressRelOutData : list) {
                    String buildAreaId = addressRelOutData.getBuildareaid();
                    if (buildAreaId != null) {
                        BuildAreaOutData unitidByBuildAreaId = utUnitBuildareaMapper.getUnitidByBuildAreaId(buildAreaId);
                        addressRelOutData.setUnitid(unitidByBuildAreaId.getUnitID());
                        addressRelOutData.setUnitname(unitidByBuildAreaId.getUnitName());
                    }
                }

                //生成设备设施维保任务
                UtLzRepair repair = new UtLzRepair();
                String dealcode = "F" + UtilConv.date2Str(new Date(), UtilConv.DATE_TIME_FULL_PAT_17);
                repair.setRepaircode(dealcode);
                repair.setId(snowflakeIdWorker.nextId());
                repair.setUnitid(planDetails.getUnitID());                      //单位id  计划表
                repair.setBaddesc("定期维保");                                  //问题描述
                repair.setCreatetime(new Date());
                repair.setBaseid(planDetails.getId());                          //原始数据主键   计划表主键id
                repair.setDatafrom(3);
                repair.setIswbfk(1);
                repair.setWbclr(inData.getCreateUserId());                      //创建人id
                repair.setRepairsite(equipmentList.get(i).getBuildingName());   //需维修位置
                repair.setDealstatus(1);
                repair.setExecutorID(planDetails.getExecutorID());
                repair.setTaskStartTime(getStartOfDay(planDetails.getPlanStartTime()));
                repair.setTaskEndTime(getEndOfDay(planDetails.getPlanEndTime()));
                repair.setEqid(equipmentList.get(i).getID());
                repair.setEqname(equipmentList.get(i).getEqName());
                Integer flag = utLzRepairMapper.insert(repair);
                if (flag < 1) {
                    throw new ServiceException("生成维修失败！");
                }

                //生成点位的维保任务
                for (int j = 0; j < list.size();j++){
                    UtLzRepair repair2 = new UtLzRepair();
                    String dealcode2 = "F" + UtilConv.date2Str(new Date(), UtilConv.DATE_TIME_FULL_PAT_17);
                    repair2.setRepaircode(dealcode2);
                    repair2.setId(snowflakeIdWorker.nextId());
                    repair2.setUnitid(Long.valueOf(list.get(j).getUnitid()));    //单位id
                    repair2.setBaddesc("定期维保");                              //问题描述
                    repair2.setCreatetime(new Date());
                    repair2.setBaseid(planDetails.getId());                      //原始数据主键   计划表主键id
                    repair2.setDatafrom(3);
                    repair2.setIswbfk(1);
                    repair2.setWbclr(inData.getCreateUserId());                  //创建人id
                    repair2.setRepairsite(list.get(j).getAdress());              //需维修位置，点位真实地址
                    repair2.setDealstatus(1);
                    repair2.setExecutorID(planDetails.getExecutorID());//执行人id
                    repair2.setTaskStartTime(getStartOfDay(planDetails.getPlanStartTime()));
                    repair2.setTaskEndTime(getEndOfDay(planDetails.getPlanEndTime()));

                    repair2.setEqid(Long.valueOf(list.get(i).getId()));
                    repair2.setEqname(list.get(i).getName());
                    Integer flag2 = utLzRepairMapper.insert(repair2);
                    if (flag2 < 1) {
                        throw new ServiceException("生成维修失败！");
                    }
                }
            }
            //修改计划表生成任务状态
            UtLzRepairPlan utLzRepairPlan = new UtLzRepairPlan();
            utLzRepairPlan.setId(Long.valueOf(id));
            utLzRepairPlan.setIsGenerate(1);
            utLzRepairPlanMapper.updateByPrimaryKeySelective(utLzRepairPlan);
        }
    }

    @Override
    public Integer repairGenerateNumber(ParameterData inData) throws Exception{
        Integer number=0;
        String ids = inData.getId();
        String[] split = ids.split(SPLIT_FALG);
        for (String id : split){
            UtLzRepairPlan planDetails = utLzRepairPlanMapper.selectByPrimaryKey(Long.valueOf(id));

            List<EquipmentListData> equipmentList = utLzRepairPlanMapper.getEquipmentList(planDetails.getUnitID());
            for (int i = 0;i < equipmentList.size();i++){
                List<AddressRelOutData> list = addressRelMapper.getAllAddressRel(
                        equipmentList.get(i).getID().toString(), equipmentList.get(i).getUnitName());
                //生成点位的维保任务
                number += list.size();
            }
            number += equipmentList.size();
        }
        return number;
    }

    public List<RepairData> getUnitList(RepairData inData) throws Exception{
        List<RepairData> list = utLzRepairPlanMapper.getUnitList(inData);
        return list;
    }

    // 获得某天最大时间 2018-03-20 23:59:59
    public static Date getEndOfDay(Date date) {
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(date);
        calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
        calendarEnd.set(Calendar.MINUTE, 59);
        calendarEnd.set(Calendar.SECOND, 59);
        //防止mysql自动加一秒,毫秒设为0
        calendarEnd.set(Calendar.MILLISECOND, 0);
        return calendarEnd.getTime();
    }

    // 获得某天最小时间 2018-03-20 00:00:00
    public static Date getStartOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

}
