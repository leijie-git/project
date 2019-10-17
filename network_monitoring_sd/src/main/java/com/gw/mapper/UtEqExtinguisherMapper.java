package com.gw.mapper;

import java.util.List;

import com.gw.front.index.data.UserUnitIDSiteStatus;
import org.apache.ibatis.annotations.Param;

import com.gw.common.BaseMapper;
import com.gw.front.couplet.data.FrontCoupletUnitInfo;
import com.gw.front.maintenance.data.FrontMaintenanceFireOutData;
import com.gw.front.maintenance.data.FrontMaintenanceInData;
import com.gw.front.maintenance.data.FrontMaintenanceStatOutData;
import com.gw.front.unit.data.FrontUnitExtinguisherInData;
import com.gw.front.unit.data.FrontUnitExtinguisherOutData;
import com.gw.mapper.entity.UtEqExtinguisher;

/**
 * 灭火器Mapper层
 *
 * @author SY
 */
public interface UtEqExtinguisherMapper extends BaseMapper<UtEqExtinguisher> {

    /**
     * 灭火器信息统计
     *
     * @param unitId
     * @return
     * @throws Exception
     */
    FrontMaintenanceStatOutData getMaintenanceStat(@Param("unitId") Long unitId, @Param("userId") Long userId) throws Exception;

    /**
     * 查询灭火器列表
     *
     * @param inData
     * @return
     * @throws Exception
     */
    List<FrontMaintenanceFireOutData> getFireExtinguisherList(FrontMaintenanceInData inData) throws Exception;

    /**
     * 查询单位灭火器
     *
     * @param unitId
     * @return
     */
    List<FrontUnitExtinguisherOutData> getExtinguisherByUnitId(@Param("unitId") Long unitId);

    /**
     * 获取所有灭火器列表
     *
     * @param extinguisherPosition
     * @return
     */
    List<FrontUnitExtinguisherOutData> getExtinguisherList(FrontUnitExtinguisherInData inData);

    /**
     * 灭火器信息统计列表
     *
     * @param unitId
     * @return
     * @throws Exception
     */
    List<FrontCoupletUnitInfo> getMaintenanceStatList(@Param("userId") String userId, @Param("keyword") String keyword) throws Exception;

    /**
     * 根据主键id获取灭火器详情
     *
     * @param feId 灭火器id
     */
    FrontMaintenanceFireOutData selectById(@Param("id") Long feId);

    /**
     * 查询单位到期灭火器列表
     *
     * @param
     * @return
     */
    List<FrontUnitExtinguisherOutData> getExpireExtinguisherList();


    /**
    *@描述 统计当前用户的单位下的检查点的状态统计
    *@创建人 Jie.Lei
    *@参数
    *@返回值
    *@创建时间 2019/7/12
    */

    UserUnitIDSiteStatus getUserUnitSiteStatus(String UnitID);

    /**
     *@描述 巡查进度统计 总的巡查个数 已完成个数
     *@创建人 Jie.Lei
     *@参数
     *@返回值
     *@创建时间 2019/7/12
     */

    UserUnitIDSiteStatus getSiteCountAndOkCheckCount(String UnitID);

}