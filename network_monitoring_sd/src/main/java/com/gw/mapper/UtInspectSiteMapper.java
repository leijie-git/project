package com.gw.mapper;

import java.util.List;

import com.gw.mapper.entity.UtInspectSiteOut;
import org.apache.ibatis.annotations.Param;

import com.gw.common.BaseMapper;
import com.gw.inspect.data.InspectSiteInData;
import com.gw.inspect.data.InspectSiteOutData;
import com.gw.mapper.entity.UtInspectSite;

public interface UtInspectSiteMapper extends BaseMapper<UtInspectSite> {

    /**
     * 获取巡查点列表
     *
     * @param inData
     * @return
     * @throws Exception
     */
    List<InspectSiteOutData> getList(InspectSiteInData inData) throws Exception;

    /**
     * 获取计划对应的点位
     *
     * @param id
     * @return
     */
    List<InspectSiteOutData> getPlanSiteList(InspectSiteInData inData) throws Exception;

    /**
     * 获取计划未包含的点位
     *
     * @param inData
     * @return
     */
    List<InspectSiteOutData> getSiteList(InspectSiteInData inData) throws Exception;

    /**
     * 根据点位id获取点位信息
     *
     * @param site
     * @param planID
     * @return
     */
    InspectSiteOutData getSiteListByID(@Param("site") String site, @Param("planID") String planID);

    /**
     * 获取单位点位
     *
     * @param inData
     * @return
     * @throws Exception
     */
    List<UtInspectSite> getUnitSiteList(@Param("unitID") String unitID) throws Exception;
    //批量增加巡检点对象
    int insertSitelList(List<UtInspectSite> list);

    int getCodeByUnitid(@Param("unitID") String unitID, @Param("siteCode") String siteCode);

    List<InspectSiteOutData> getInspectSiteBySiteclassid(@Param("siteClassid") String siteClassid);




    //根据检查对象查出id
    String selectIdByMaintenanceUnitInData(UtInspectSite tInspectSite);

    //查询所有点位的名称,位置,执行人,巡查频数,执行周期,周期内起止时间,同时根据计划ID判断点位是否已关联该计划(isPlaned),根据时间及是否关联排序

    List<UtInspectSiteOut> selectUtInspectSiteOutList(UtInspectSiteOut utInspectSiteOut);


}