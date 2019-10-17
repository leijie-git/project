package com.gw.inspect.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.common.SnowflakeIdWorker;
import com.gw.exception.ServiceException;
import com.gw.inspect.data.InspectPlanInData;
import com.gw.inspect.data.InspectPlanOutData;
import com.gw.inspect.service.InspectPlanService;
import com.gw.mapper.UtInspectPlanMapper;
import com.gw.mapper.UtInspectPlandetialMapper;
import com.gw.mapper.UtUnitUserMapper;
import com.gw.mapper.entity.UtInspectPlan;
import com.gw.util.Util;
import com.gw.util.UtilMessage;

@Service
public class InspectPlanServiceImpl implements InspectPlanService {

    @Resource
    private UtInspectPlanMapper utInspectPlanMapper;

    @Resource
    private UtInspectPlandetialMapper utInspectPlandetialMapper;

    @Resource
    private UtUnitUserMapper utUnitUserMapper;
    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Override
    public PageInfo<InspectPlanOutData> getList(InspectPlanInData inData) throws Exception {
        PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
        List<InspectPlanOutData> list = utInspectPlanMapper.getList(inData);
        for (InspectPlanOutData data : list) {
            data.setPlanStart(data.getPlanStart().substring(0, data.getPlanStart().indexOf(" ")));
            data.setPlanEnd(data.getPlanEnd().substring(0, data.getPlanEnd().indexOf(" ")));
        }
        PageInfo<InspectPlanOutData> pager = new PageInfo<InspectPlanOutData>(list);
        return pager;
    }

    @Override
    public String add(InspectPlanInData inData) throws Exception {
        UtInspectPlan plan = new UtInspectPlan();
        Long id = snowflakeIdWorker.nextId();
        plan.setId(id);
        plan.setPlanname(inData.getPlanName());
        //监督人（计划责任人）
        plan.setSupervisorID(inData.getSupervisorID());
        plan.setRemark(inData.getRemark());
        plan.setStatus(0);
        if (Util.isNotEmpty(inData.getUnitID())) {
            plan.setUnitid(Long.parseLong(inData.getUnitID()));
        }

        if (Util.StringToDate(inData.getPlanStart()).compareTo(Util.StringToDate(inData.getPlanEnd())) == 0 || Util.StringToDate(inData.getPlanStart()).compareTo(Util.StringToDate(inData.getPlanEnd())) == -1) {
            plan.setPlanstart(getStartOfDay(Util.StringToDate(inData.getPlanStart())));

            plan.setPlanend(getEndOfDay(Util.StringToDate(inData.getPlanEnd())));
        } else {
            throw new ServiceException("开始时间不能大于结束时间");
        }
        plan.setCreatedate(new Date());
        plan.setDefaultuserid(inData.getDefaultUserID());
        plan.setDefaultusername(inData.getDefaultUserName());
        Integer flag = utInspectPlanMapper.insert(plan);
        if (flag < 1) {
            throw new ServiceException(UtilMessage.SAVE_MESSAGE_ERROR);
        }
        return String.valueOf(id);

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


    @Override
    public void update(InspectPlanInData inData) throws Exception {
        UtInspectPlan plan = new UtInspectPlan();
        plan.setId(Long.parseLong(inData.getID()));
        plan.setPlanname(inData.getPlanName());

        plan.setRemark(inData.getRemark());
        //监督人
        plan.setSupervisorID(inData.getSupervisorID());
        plan.setStatus(0);
        if (Util.isNotEmpty(inData.getUnitID())) {
            plan.setUnitid(Long.parseLong(inData.getUnitID()));
        }
//        plan.setPlanstart(Util.StringToDate(inData.getPlanStart()));
//        plan.setPlanend(Util.StringToDate(inData.getPlanEnd()));

        if (Util.StringToDate(inData.getPlanStart()).compareTo(Util.StringToDate(inData.getPlanEnd())) == 0 || Util.StringToDate(inData.getPlanStart()).compareTo(Util.StringToDate(inData.getPlanEnd())) == -1) {
            plan.setPlanstart(getStartOfDay(Util.StringToDate(inData.getPlanStart())));

            plan.setPlanend(getEndOfDay(Util.StringToDate(inData.getPlanEnd())));
        } else {
            throw new ServiceException("开始时间不能大于结束时间");
        }
        plan.setCreatedate(new Date());
        plan.setDefaultuserid(inData.getDefaultUserID());
        plan.setDefaultusername(inData.getDefaultUserName());
        Integer flag = utInspectPlanMapper.updateByPrimaryKey(plan);
        if (flag < 1) {
            throw new ServiceException(UtilMessage.UPDATE_ERROR);
        }

    }

    @Override
    public void remove(String id) throws Exception {
        Integer flag = utInspectPlanMapper.deleteByPrimaryKey(Long.parseLong(id));
        utInspectPlandetialMapper.deleteByPlanID(Long.parseLong(id));
        if (flag < 1) {
            throw new ServiceException(UtilMessage.DEL_MESSAGE_ERROR);
        }
    }

    @Override
    public void planSite(String id) throws Exception {

    }

    @Override
    public String getUnitID(Long id) throws Exception {
        String unitID = utUnitUserMapper.getUnitIdById(id);
        return unitID;
    }

}
