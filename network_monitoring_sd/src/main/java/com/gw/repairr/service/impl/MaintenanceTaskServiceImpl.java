package com.gw.repairr.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.common.SnowflakeIdWorker;
import com.gw.exception.ServiceException;
import com.gw.mapper.UtLzRepairMapper;
import com.gw.mapper.UtLzRepairPlanMapper;
import com.gw.mapper.entity.UtLzRepair;
import com.gw.mapper.entity.UtLzRepairPlan;
import com.gw.repairr.data.MaintenanceTaskData;
import com.gw.repairr.data.RepairData;
import com.gw.repairr.data.UserListData;
import com.gw.repairr.service.MaintenanceTaskService;
import com.gw.util.Util;
import com.gw.util.UtilConv;
import com.gw.util.UtilMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static com.gw.alarm.AlarmConstants.SPLIT_FALG;

@Service
public class MaintenanceTaskServiceImpl implements MaintenanceTaskService {

    @Resource
    private UtLzRepairMapper utLzRepairMapper;

    @Resource
    private UtLzRepairPlanMapper utLzRepairPlanMapper;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Override
    public PageInfo<MaintenanceTaskData> getList(MaintenanceTaskData inData) throws Exception{
        //获取维保单位关联的联网单位
        inData.setUnitID(getUnitList(inData.getUnitID()));
        PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
        if (!Util.isEmpty(inData.getTaskEndTime())){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(sdf.parse(inData.getTaskEndTime()));
            calendar.add(Calendar.DATE,1);
            inData.setTaskEndTime(sdf.format(calendar.getTime()));
        }
        List<MaintenanceTaskData> list = utLzRepairMapper.getList(inData);
        return new PageInfo<MaintenanceTaskData>(list);
    }

    @Override
    public List<MaintenanceTaskData> getPlanList(MaintenanceTaskData inData) throws Exception{
        //获取维保单位关联的联网单位
        inData.setUnitID(getUnitList(inData.getUnitID()));
        List<MaintenanceTaskData> list = utLzRepairPlanMapper.getPlanList(inData);
        return list;
    }

    @Override
    public void taskAdd(MaintenanceTaskData inData) throws Exception{
        UtLzRepairPlan planDetails = utLzRepairPlanMapper.selectByPrimaryKey(Long.valueOf(inData.getPlanID()));
        //新增维保任务
        UtLzRepair repair = new UtLzRepair();
        String dealcode = "F" + UtilConv.date2Str(new Date(), UtilConv.DATE_TIME_FULL_PAT_17);
        repair.setRepaircode(dealcode);
        repair.setId(snowflakeIdWorker.nextId());
        repair.setUnitid(planDetails.getUnitID());                      //单位id  计划表
        repair.setBaddesc("定期维保");                                  //问题描述
        repair.setCreatetime(new Date());                               //录入时间
        repair.setBaseid(planDetails.getId());                          //原始数据主键   计划表主键id
        repair.setDatafrom(3);
        repair.setIswbfk(1);
        repair.setWbclr(inData.getCreateUserID());                      //创建人id
        repair.setRepairsite(inData.getRepairSite());                   //需维修位置
        repair.setDealstatus(1);
        repair.setExecutorID(planDetails.getExecutorID());              //责任人
        repair.setTaskStartTime(planDetails.getPlanStartTime());
        repair.setTaskEndTime(planDetails.getPlanEndTime());
        repair.setEqname(inData.getEqName());                           //维保对象
        utLzRepairMapper.insert(repair);
        //新增任务后更改状态
        planDetails.setIsGenerate(1);
        utLzRepairPlanMapper.updateByPrimaryKeySelective(planDetails);
    }

    @Override
    public void taskModify(MaintenanceTaskData inData) throws Exception{
        UtLzRepairPlan planDetails = utLzRepairPlanMapper.selectByPrimaryKey(Long.valueOf(inData.getPlanID()));
        //修改维保任务
        UtLzRepair repair = new UtLzRepair();
        repair.setId(Long.valueOf(inData.getID()));
        repair.setUnitid(planDetails.getUnitID());                      //单位id  计划表
        repair.setBaseid(planDetails.getId());                          //原始数据主键   计划表主键id
        repair.setWbclr(inData.getCreateUserID());                      //处理人id
        repair.setRepairsite(inData.getRepairSite());                   //需维修位置
        repair.setExecutorID(planDetails.getExecutorID());              //责任人
        repair.setTaskStartTime(planDetails.getPlanStartTime());        //开始时间
        repair.setTaskEndTime(planDetails.getPlanEndTime());            //结束时间
        repair.setEqname(inData.getEqName());                           //维保对象
        utLzRepairMapper.updateByPrimaryKeySelective(repair);
    }

    @Override
    public void taskDelete(String ids) throws Exception{
        String[] split = ids.split(SPLIT_FALG);
        for (String id : split){
            UtLzRepair utLzRepair = utLzRepairMapper.selectByPrimaryKey(Long.parseLong(id));
            //删除任务
            utLzRepairMapper.deleteByPrimaryKey(Long.parseLong(id));

            UtLzRepair utLzRepair2 = new UtLzRepair();
            utLzRepair2.setBaseid(utLzRepair.getBaseid());
            Integer number = utLzRepairMapper.selectCount(utLzRepair2);
            //修改计划表生成任务状态
            if (number < 1){
                UtLzRepairPlan utLzRepairPlan = new UtLzRepairPlan();
                utLzRepairPlan.setId(utLzRepair.getBaseid());
                utLzRepairPlan.setIsGenerate(0);
                utLzRepairPlanMapper.updateByPrimaryKeySelective(utLzRepairPlan);
            }
        }
    }

    @Override
    public List<UserListData> getMaintenancePlanList(MaintenanceTaskData inData) throws Exception{
        return utLzRepairMapper.getMaintenancePlanList(inData);
    }

    @Override
    public List<UserListData> getMaintenanceUserList(MaintenanceTaskData inData) throws Exception{
        return utLzRepairMapper.getMaintenanceUserList(inData);
    }

    public String getUnitList(String inData) throws Exception{
        RepairData repairData = new RepairData();
        repairData.setUnitID(inData);
        List<RepairData> rep = utLzRepairPlanMapper.getUnitList(repairData);
        StringBuilder sb = new StringBuilder();
        for (int i = 0;i < rep.size();){
            sb.append(rep.get(i).getUnitID());
            i++;
            if(i<rep.size()){
                sb.append(",");
            }
        }
        return sb.toString();
    }

}
