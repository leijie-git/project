package com.gw.front.maintenance.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.common.ExportExcel;
import com.gw.exception.ServiceException;
import com.gw.front.index.data.UserUnitIDSiteStatus;
import com.gw.front.maintenance.data.*;
import com.gw.front.maintenance.service.FrontMaintenanceService;
import com.gw.generatereport.GenerateMaintenanceUserData;
import com.gw.generatereport.GenerateReport;
import com.gw.mapper.UtEqExtinguisherMapper;
import com.gw.mapper.UtInspectTaskdetialMapper;
import com.gw.mapper.UtLzRepairMapper;
import com.gw.mapper.UtLzRepairdetialMapper;
import com.gw.mapper.entity.UtLzRepair;
import com.gw.mapper.entity.UtLzRepairInData;

import com.gw.util.Util;
import com.gw.util.UtilConv;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class FrontMaintenanceServiceImpl implements FrontMaintenanceService {

    @Resource
    private GenerateReport generateReport;
    @Resource
    private UtEqExtinguisherMapper utEqExtinguisherMapper;
    @Resource
    private UtLzRepairMapper utLzRepairMapper;
    @Resource
    private UtLzRepairdetialMapper utLzRepairdetialMapper;
    @Resource
    private UtInspectTaskdetialMapper utInspectTaskdetialMapper;

    @Override
    public FrontMaintenanceStatOutData getMaintenanceStat(String unitId, String userId) throws Exception {
        // 灭火器到期
        Long unit = null;
        if (Util.isNotEmpty(unitId)) {
            unit = Long.parseLong(unitId);
        }
        FrontMaintenanceStatOutData outData = utEqExtinguisherMapper.getMaintenanceStat(unit, Long.parseLong(userId));
        if (Util.isEmpty(outData)) {
            outData = new FrontMaintenanceStatOutData();
        }
        FrontMaintenanceInData inData = new FrontMaintenanceInData();
        inData.setUnitId(unitId);
        inData.setUserId(userId);
        // 维保进度
        FrontMaintenanceStatOutData outData1 = utLzRepairMapper.getMaintenanceStatusStat(inData);
        if (Util.isNotEmpty(outData1)) {
            outData.setWbNodeal(outData1.getWbNodeal());
            outData.setWbdealed(outData1.getWbdealed());
        }

        // 巡查进度
        FrontMaintenanceStatOutData outData2 = utInspectTaskdetialMapper.getMaintenanceStatusStat(inData);
        if (Util.isNotEmpty(outData2)) {
            String xcdealed = outData2.getXcdealed();
            if (Util.isEmpty(xcdealed)) {
                xcdealed = "0";
            }
            outData.setXcdealed(xcdealed);
            String totalCount = outData2.getTotalCount();
            if (Util.isNotEmpty(totalCount)) {
                outData.setXcNodeal(Integer.valueOf(totalCount) - Integer.valueOf(xcdealed) + "");
            }
        }
        String nowDate = UtilConv.date2Str(new Date(), UtilConv.DATE_YYYY_MM_DD_CHN);
        Integer xcExpire = utInspectTaskdetialMapper.getInspectEpirationTaskCount(unitId, nowDate);
        outData.setXcExpire(xcExpire);
        return outData;
    }

    @Override
    public PageInfo<FrontMaintenanceFireOutData> getFireExtinguisherList(FrontMaintenanceInData inData, String userId)
            throws Exception {
        PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
        inData.setUserId(userId);
        List<FrontMaintenanceFireOutData> list = utEqExtinguisherMapper.getFireExtinguisherList(inData);
        PageInfo<FrontMaintenanceFireOutData> pager = new PageInfo<>(list);
        return pager;
    }

    @Override
    public void exportFireExtinguisherList(FrontMaintenanceInData inData, HttpServletResponse response, String userId)
            throws Exception {
        inData.setUserId(userId);
        List<FrontMaintenanceFireOutData> list = utEqExtinguisherMapper.getFireExtinguisherList(inData);
        List<FrontMaintenanceFireExport> exportDatas = new ArrayList<>();
        for (FrontMaintenanceFireOutData frontMaintenanceFireOutData : list) {
            FrontMaintenanceFireExport data = new FrontMaintenanceFireExport();
            BeanUtils.copyProperties(frontMaintenanceFireOutData, data);
            exportDatas.add(data);
        }
        String[] header = new String[]{"药剂类型", "放置位置", "出厂时间", "检测时间", "充装时间", "药剂到期时间", "报废时间"};
        ExportExcel<FrontMaintenanceFireExport> export = new ExportExcel<FrontMaintenanceFireExport>();
        export.exportExcel(response, exportDatas, header);
    }

    @Override
    public PageInfo<FrontMaintenanceOutData> getRepairList(FrontMaintenanceInData inData) throws Exception {
        PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
        List<FrontMaintenanceOutData> list = utLzRepairMapper.getRepairList(inData);
        PageInfo<FrontMaintenanceOutData> pager = new PageInfo<>(list);
        return pager;
    }

    @Override
    public void exportRepairList(FrontMaintenanceInData inData, HttpServletResponse response) throws Exception {
        List<FrontMaintenanceOutData> list = utLzRepairMapper.getRepairList(inData);
        List<FrontMaintenanceExport> exportDatas = new ArrayList<>();
        for (FrontMaintenanceOutData frontMaintenanceOutData : list) {
            FrontMaintenanceExport data = new FrontMaintenanceExport();
            BeanUtils.copyProperties(frontMaintenanceOutData, data);
            exportDatas.add(data);
        }
        String[] header = new String[]{"单位编号", "单位名称", "维保人员", "维保内容", "位置", "处理状态", "执行时间", "计划时间"};
        ExportExcel<FrontMaintenanceExport> export = new ExportExcel<FrontMaintenanceExport>();
        export.exportExcel(response, exportDatas, header);
    }

    @Override
    public FrontMaintenanceOutData getRepairDetail(String repairId) throws Exception {
        FrontMaintenanceOutData outData = utLzRepairdetialMapper.getRepairDetail(repairId);
        if (null != outData && Util.isNotEmpty(outData.getRepairPic())) {
            String[] repairPics = outData.getRepairPic().split(",");
            List<String> repairPicList = new ArrayList<String>();
            for (String repairPic : repairPics) {
                repairPicList.add(repairPic);
            }
            outData.setRepairPicList(repairPicList);
        }
        if (null != outData && Util.isNotEmpty(outData.getRepairDetailPic())) {
            String[] repairDetailPics = outData.getRepairDetailPic().split(",");
            List<String> repairDetailPicList = new ArrayList<String>();
            for (String repairDetailPic : repairDetailPics) {
                repairDetailPicList.add(repairDetailPic);
            }
            outData.setRepairDetailPicList(repairDetailPicList);
        }
        return outData;
    }

    @Override
    public PageInfo<FrontMaintenanceTaskOutData> getInspectTaskList(FrontMaintenanceInData inData) throws Exception {
        PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
        List<FrontMaintenanceTaskOutData> list = utInspectTaskdetialMapper.getInspectTaskList(inData);
        PageInfo<FrontMaintenanceTaskOutData> pager = new PageInfo<>(list);
        return pager;
    }

    @Override
    public void exportInspectTaskList(FrontMaintenanceInData inData, HttpServletResponse response) throws Exception {

        List<FrontMaintenanceTaskOutData> list = utInspectTaskdetialMapper.getInspectTaskList(inData);
        List<FrontMaintenanceTaskExport> exportDatas = new ArrayList<>();
        for (FrontMaintenanceTaskOutData frontMaintenanceTaskOutData : list) {
            FrontMaintenanceTaskExport data = new FrontMaintenanceTaskExport();
            BeanUtils.copyProperties(frontMaintenanceTaskOutData, data);
            exportDatas.add(data);
        }
        String[] header = new String[]{"单位编号", "单位名称", "巡查人员", "计划名称", "计划时间", "位置", "处理状态", "处理时间"};
        ExportExcel<FrontMaintenanceTaskExport> export = new ExportExcel<FrontMaintenanceTaskExport>();
        export.exportExcel(response, exportDatas, header);
    }

    @Override
    public List<FrontMaintenanceTaskOutData> getInspectTaskDetail(String taskID) throws Exception {
        List<FrontMaintenanceTaskOutData> outData = utInspectTaskdetialMapper.getInspectTaskDetail(taskID);
        for (FrontMaintenanceTaskOutData data : outData) {
            if (Util.isNotEmpty(data.getPicPath())) {
                List<String> picPathList = new ArrayList<String>();
                String[] picPaths = data.getPicPath().split(",");
                for (String picPath : picPaths) {
                    picPathList.add(picPath);
                }
                data.setPicPathList(picPathList);
            }
        }
        return outData;
    }

    @Override
    public PageInfo<FrontMaintenanceTaskOutData> getInspectEpirationTaskList(FrontMaintenanceInData inData)
            throws Exception {
        PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
        String nowDate = UtilConv.date2Str(new Date(), UtilConv.DATE_YYYY_MM_DD_CHN);
        inData.setNowDate(nowDate);
        List<FrontMaintenanceTaskOutData> list = utInspectTaskdetialMapper.getInspectEpirationTaskList(inData);
        for (FrontMaintenanceTaskOutData data : list) {
            String endTime = data.getTaskEnd();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date sd = df.parse(nowDate);
            Date ed = df.parse(endTime);
            Long days = (ed.getTime() - sd.getTime()) / 3600 / 24 / 1000 + 1;
            data.setLastDay(days.toString());
        }
        PageInfo<FrontMaintenanceTaskOutData> pager = new PageInfo<>(list);
        return pager;
    }

    @Override
    public void exportInspectEpirationTaskList(HttpServletResponse response, FrontMaintenanceInData inData)
            throws Exception {
        List<InspectEpirationTaskExport> exports = new ArrayList<>();
        String nowDate = UtilConv.date2Str(new Date(), UtilConv.DATE_YYYY_MM_DD_CHN);
        inData.setNowDate(nowDate);
        List<FrontMaintenanceTaskOutData> list = utInspectTaskdetialMapper.getInspectEpirationTaskList(inData);
        for (FrontMaintenanceTaskOutData frontMaintenanceTaskOutData : list) {
            String endTime = frontMaintenanceTaskOutData.getTaskEnd();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date sd = df.parse(nowDate);
            Date ed = df.parse(endTime);
            Long days = (ed.getTime() - sd.getTime()) / 3600 / 24 / 1000 + 1;
            frontMaintenanceTaskOutData.setLastDay(days.toString());
            if (Util.isNotEmpty(frontMaintenanceTaskOutData.getInspectCycleType())) {
                switch (frontMaintenanceTaskOutData.getInspectCycleType()) {
                    case "0":
                        frontMaintenanceTaskOutData.setInspectCycleType("日");
                        break;
                    case "1":
                        frontMaintenanceTaskOutData.setInspectCycleType("周");
                        break;
                    case "2":
                        frontMaintenanceTaskOutData.setInspectCycleType("月");
                        break;
                    case "3":
                        frontMaintenanceTaskOutData.setInspectCycleType("年");
                        break;
                    default:
                        break;
                }
            }
            InspectEpirationTaskExport export = new InspectEpirationTaskExport();
            BeanUtils.copyProperties(frontMaintenanceTaskOutData, export);
            exports.add(export);
        }
        String[] header = new String[]{"巡查人员", "巡查位置", "处理状态", "单位名称", "单位编号", "到期时间", "剩余天数", "周期类型"};
        ExportExcel<InspectEpirationTaskExport> exportExcel = new ExportExcel<InspectEpirationTaskExport>();
        exportExcel.exportExcel(response, exports, header);
    }

    @Override
    public void exportInspectMouthReport(FrontMaintenanceInData inData, HttpServletResponse response) throws Exception {
        generateReport.exportInspectReport(inData, response);
    }

    @Override
    public void exportMaintenanceReport(FrontMaintenanceInData inData, HttpServletResponse response) throws Exception {
        generateReport.exportMaintenanceReport(inData, response);

    }

    /**
     * @描述 统计当前用户的单位下的检查点的状态统计
     * @创建人 Jie.Lei
     * @参数
     * @返回值
     * @创建时间 2019/7/12
     */
    @Override
    public UserUnitIDSiteStatus getUserUnitSiteStatus(String UnitID) {
        return utEqExtinguisherMapper.getUserUnitSiteStatus(UnitID);
    }

    /**
     * @描述 巡查进度统计 总的巡查个数 已完成个数
     * @创建人 Jie.Lei
     * @参数
     * @返回值
     * @创建时间 2019/7/12
     */

    @Override
    public UserUnitIDSiteStatus getSiteCountAndOkCheckCount(String UnitID) {
        return utEqExtinguisherMapper.getSiteCountAndOkCheckCount(UnitID);
    }


    /**
     * 通过联网单位Id查出维保人员
     * lei.jie
     */
    @Override
    public List<GenerateMaintenanceUserData> getMaintenanceUserBynetworkingID(GenerateMaintenanceUserData generateMaintenanceUserData) {
        return utLzRepairMapper.getMaintenanceUserBynetworkingID(generateMaintenanceUserData);
    }

    @Override
    public int updateUtLzRepair(UtLzRepairInData utLzRepair) throws Exception {
        UtLzRepair ut = new UtLzRepair();
        //获取任务的开始结束时间
        if (Util.StringToDate(utLzRepair.getTaskStartTime()).compareTo(Util.StringToDate(utLzRepair.getTaskEndTime())) == 0 ||Util.StringToDate(utLzRepair.getTaskStartTime()).compareTo(Util.StringToDate(utLzRepair.getTaskEndTime())) == -1) {
            ut.setTaskStartTime(getStartOfDay(Util.StringToDate(utLzRepair.getTaskStartTime())));
            ut.setTaskEndTime(getEndOfDay(Util.StringToDate(utLzRepair.getTaskEndTime())));
            ut.setExecutorID(Long.parseLong(utLzRepair.getExecutorID()));
            ut.setId(Long.valueOf(utLzRepair.getId()));
            ut.setDealstatus(2);
        } else {
            throw new ServiceException("开始时间不能大于结束时间");
        }
        return utLzRepairMapper.updateByPrimaryKeySelective(ut);
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
