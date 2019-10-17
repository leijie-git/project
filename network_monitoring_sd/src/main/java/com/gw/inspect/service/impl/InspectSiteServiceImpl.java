package com.gw.inspect.service.impl;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.gw.inspect.data.InspectInData;
import com.gw.inspect.data.InspectSiteClassOutData;
import com.gw.inspect.service.InspectSiteClassService;
import com.gw.inspect.service.SiteClassDetialService;
import com.gw.mapper.UtBaseSiteclassMapper;
import com.gw.mapper.UtBaseSiteclassdetialMapper;
import com.gw.mapper.entity.*;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.common.SnowflakeIdWorker;
import com.gw.exception.ServiceException;
import com.gw.inspect.data.InspectSiteInData;
import com.gw.inspect.data.InspectSiteOutData;
import com.gw.inspect.service.InspectSiteService;
import com.gw.mapper.UtInspectSiteMapper;
import com.gw.mapper.UtInspectTaskMapper;
import com.gw.util.Util;
import com.gw.util.UtilMessage;

import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

@Service
public class InspectSiteServiceImpl implements InspectSiteService {

	@Autowired
	InspectSiteService inspectSiteService;
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	@Resource
	private UtInspectSiteMapper utInspectSiteMapper;
	@Resource
	private SiteClassDetialService siteClassDetialService;
	@Resource
	private UtInspectTaskMapper utInspectTaskMapper;
	@Resource
	private UtBaseSiteclassdetialMapper utBaseSiteclassdetialMapper;
	@Resource
	private UtBaseSiteclassMapper utBaseSiteclassMapper;
	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;


	@Override
	public PageInfo<InspectSiteOutData> getList(InspectSiteInData inData) throws Exception {
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<InspectSiteOutData> list = utInspectSiteMapper.getList(inData);
		for (InspectSiteOutData data : list) {
			if (data.getTaskEnd() != "" && data.getTaskEnd() != null) {
				data.setTaskEnd(data.getTaskEnd().substring(0, data.getTaskEnd().indexOf(" ")));
			}
			if (data.getTaskStart() != "" && data.getTaskStart() != null) {
				data.setTaskStart(data.getTaskStart().substring(0, data.getTaskStart().indexOf(" ")));
			}
		}
		PageInfo<InspectSiteOutData> pager = new PageInfo<InspectSiteOutData>(list);
		return pager;
	}

	@Override
	public void add(InspectSiteInData inData) throws Exception {
		UtInspectSite site = new UtInspectSite();
		site.setId(snowflakeIdWorker.nextId());
		site.setInspectcount(Integer.parseInt(inData.getInspectCount()));
		site.setInspectcycletype(Integer.parseInt(inData.getInspectCycleType()));
		site.setNfccode(inData.getNFCCode());
		site.setBuildID(Long.parseLong(inData.getBuildID()));
		site.setBuildAreaID(Long.parseLong(inData.getBuildAreaID()));
		site.setExecutorID(inData.getExecutorID());
		site.setQrCode(inData.getQrCode());
		site.setExecutorName(inData.getExecutorName());
		int i = utInspectSiteMapper.getCodeByUnitid(inData.getUnitID(), inData.getSiteCode());
		if (i == 0) {
			site.setSitecode(inData.getSiteCode());
		} else {
			throw new ServiceException(UtilMessage.SITE_CODE_ERROR);
		}
		site.setSitedesc(inData.getSiteDesc());
		site.setSitename(inData.getSiteName());
		if (Util.isNotEmpty(inData.getTaskStart())) {
			site.setTaskstart(Util.StringToDate(inData.getTaskStart()));
		}
		if (Util.isNotEmpty(inData.getTaskEnd())) {
			site.setTaskend(Util.StringToDate(inData.getTaskEnd()));
		}
		if (Util.isNotEmpty(inData.getUnitID())) {
			site.setUnitid(Long.parseLong(inData.getUnitID()));
		}
		Integer flag = utInspectSiteMapper.insert(site);
		if (flag < 1) {
			throw new ServiceException(UtilMessage.SAVE_MESSAGE_ERROR);
		}
	}

	@Override
	public void update(InspectSiteInData inData) throws Exception {
		UtInspectSite site = new UtInspectSite();
		site.setId(Long.parseLong(inData.getID()));
		site.setInspectcount(Integer.parseInt(inData.getInspectCount()));
		//site.setInspectcycle(Integer.parseInt(inData.getInspectCycle()));
		site.setInspectcycletype(Integer.parseInt(inData.getInspectCycleType()));
		site.setNfccode(inData.getNFCCode());
		site.setRemark(inData.getRemark());
		site.setSitecode(inData.getSiteCode());
		site.setSitedesc(inData.getSiteDesc());
		site.setSitename(inData.getSiteName());
		site.setBuildID(Long.parseLong(inData.getBuildID()));
		site.setBuildAreaID(Long.parseLong(inData.getBuildAreaID()));
		site.setExecutorID(inData.getExecutorID());
		site.setQrCode(inData.getQrCode());
		site.setExecutorName(inData.getExecutorName());
		if (Util.isNotEmpty(inData.getTaskStart())) {
			site.setTaskstart(Util.StringToDate(inData.getTaskStart()));
		}
		if (Util.isNotEmpty(inData.getTaskEnd())) {
			site.setTaskend(Util.StringToDate(inData.getTaskEnd()));
		}
		if (Util.isNotEmpty(inData.getUnitID())) {
			site.setUnitid(Long.parseLong(inData.getUnitID()));
		}
		Integer flag = utInspectSiteMapper.updateByPrimaryKey(site);
		if (flag < 1) {
			throw new ServiceException(UtilMessage.UPDATE_ERROR);
		}

	}

	@Override
	public void remove(String id) throws Exception {
		String[] ids = id.split(",");
		for (String key : ids) {
			System.out.println("key:" + key);
			Example example = new Example(UtInspectTask.class);
			example.createCriteria().andEqualTo("siteid", key).andIsNull("isinspect");
			List<UtInspectTask> userList = utInspectTaskMapper.selectByExample(example);
			if (Util.isNotEmpty(userList)) {
				throw new ServiceException("当前点位已经生成任务不能删除！");
			}
			Integer flag = utInspectSiteMapper.deleteByPrimaryKey(Long.parseLong(key));
			if (flag < 1) {
				throw new ServiceException(UtilMessage.DEL_MESSAGE_ERROR);
			}
		}
	}

	@Override
	public List<InspectSiteOutData> getArrayList(InspectSiteInData inData) throws Exception {
		List<InspectSiteOutData> list = utInspectSiteMapper.getList(inData);
		return list;
	}

	@Override
	public PageInfo<InspectSiteOutData> planSite(InspectSiteInData inData) throws Exception {
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<InspectSiteOutData> list = utInspectSiteMapper.getPlanSiteList(inData);
		for (InspectSiteOutData data : list) {
			if (data.getTaskEnd() != "" && data.getTaskEnd() != null) {
				data.setTaskEnd(data.getTaskEnd().substring(0, data.getTaskEnd().indexOf(" ")));
			}
			if (data.getTaskStart() != "" && data.getTaskStart() != null) {
				data.setTaskStart(data.getTaskStart().substring(0, data.getTaskStart().indexOf(" ")));
			}
		}
		PageInfo<InspectSiteOutData> pager = new PageInfo<InspectSiteOutData>(list);
		return pager;
	}

	@Override
	public PageInfo<InspectSiteOutData> getSiteList(InspectSiteInData inData) throws Exception {
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<InspectSiteOutData> page = utInspectSiteMapper.getSiteList(inData);
		for (InspectSiteOutData data : page) {
			if (data.getTaskEnd() != "" && data.getTaskEnd() != null) {
				data.setTaskEnd(data.getTaskEnd().substring(0, data.getTaskEnd().indexOf(" ")));
			}
			if (data.getTaskStart() != "" && data.getTaskStart() != null) {
				data.setTaskStart(data.getTaskStart().substring(0, data.getTaskStart().indexOf(" ")));
			}
		}
		PageInfo<InspectSiteOutData> pager = new PageInfo<InspectSiteOutData>(page);
		return pager;
	}

	@Override
	@Transactional
	public void importData(List<InspectInData> interfaceImportDatas, String unitId) throws Exception {
		List<UtInspectSite> utInspectSiteList = new ArrayList<>();
		List<UtBaseSiteclassdetial> utBaseSiteclassdetialList = new ArrayList<>();
		UtBaseSiteclass utBaseSiteclass;
		UtInspectSite site;
		for (InspectInData inData : interfaceImportDatas) {
			List<UtBaseSiteclassdetial> utBaseSiteclassdetials = buildSiteclassdetial(inData);
			long siteClassId = 0;
			if (!utBaseSiteclassdetialList.containsAll(utBaseSiteclassdetials)) {
				//生成UtBaseSiteclass，"未定义n"，
				utBaseSiteclass = new UtBaseSiteclass();
				if (inData.getInspectId() == null || "".equals(inData.getInspectId())) {
					int count = utBaseSiteclassMapper.selectNumber(unitId) + 1;//"未定义" + count
					buildSiteClassParams(unitId, utBaseSiteclass, "未定义" + count);
					utBaseSiteclassMapper.insert(utBaseSiteclass);

					siteClassId = utBaseSiteclass.getId();

					utBaseSiteclassdetialList.addAll(utBaseSiteclassdetials);

					utInspectSiteList.add(buildSiteParams(unitId, utBaseSiteclass.getId(), inData));
				} else {
					InspectSiteClassOutData siteClass = utBaseSiteclassMapper.getBySiteclassname(inData.getInspectId(), unitId);
					if (Util.isNotEmpty(siteClass)) {

						siteClassId = Long.parseLong(siteClass.getID());
						utBaseSiteclassdetialList.addAll(utBaseSiteclassdetials);
						site = buildSiteParams(unitId, siteClassId, inData);
						utInspectSiteList.add(site);
					} else {
						buildSiteClassParams(unitId, utBaseSiteclass, inData.getInspectId());
						siteClassId = utBaseSiteclass.getId();
						utBaseSiteclassMapper.insert(utBaseSiteclass);
						//添加到list
						utBaseSiteclassdetialList.addAll(utBaseSiteclassdetials);
						site = buildSiteParams(unitId, utBaseSiteclass.getId(), inData);
						utInspectSiteList.add(site);
					}
				}
				for (UtBaseSiteclassdetial detail : utBaseSiteclassdetials) {
					detail.setSiteclassid(siteClassId);
				}
				// 生成分类ID并赋值，
				// 生成检查项ID并赋值
			} else {
				utBaseSiteclass = new UtBaseSiteclass();
				if (inData.getInspectId() == null || "".equals(inData.getInspectId())) {
					int count = utBaseSiteclassMapper.selectNumber(unitId);//"未定义" + count
					String s = "未定义" + count;
					InspectSiteClassOutData siteClass = utBaseSiteclassMapper.getBySiteclassname(s, unitId);
					site = buildSiteParams(unitId, utBaseSiteclass.getId(), inData);
					site.setSiteclassid(Long.valueOf(siteClass.getID()));
					utInspectSiteList.add(site);
				} else {
					InspectSiteClassOutData siteClass = utBaseSiteclassMapper.getBySiteclassname(inData.getInspectId(), unitId);
					if (Util.isNotEmpty(siteClass)) {
						siteClassId = Long.parseLong(siteClass.getID());
						site = buildSiteParams(unitId, siteClassId, inData);
						utInspectSiteList.add(site);
					} else {
						buildSiteClassParams(unitId, utBaseSiteclass, inData.getInspectId());
						//添加到list
						site = buildSiteParams(unitId, utBaseSiteclass.getId(), inData);
						utInspectSiteList.add(site);
					}
				}
			}
		}

		//插入检查项的List
		boolean isSuccess = false;
		if (utBaseSiteclassdetialList.size() > 0) {
			isSuccess = utBaseSiteclassdetialMapper.insertDetialList(utBaseSiteclassdetialList) > 0;
			if (!isSuccess) {
				throw new ServiceException(UtilMessage.SAVE_MESSAGE_ERROR);
			}
		}

		//插入点位的List
		for (UtInspectSite utInspectSite : utInspectSiteList) {
			boolean isNull = Util.isNotEmpty(utInspectSite.getSitecode()) && Util.isNotEmpty(utInspectSite.getSitename())
					&& Util.isNotEmpty(utInspectSite.getSitedesc()) && Util.isNotEmpty(utInspectSite.getInspectcount())
					&& Util.isNotEmpty(utInspectSite.getInspectcycletype());
			if (!isNull) {
				utInspectSiteList.remove(utInspectSite);
			}
		}
		if(utInspectSiteList.size()==1) {
			isSuccess = utInspectSiteMapper.insertSitelList(utInspectSiteList) > 0;
			if (!isSuccess) {
				throw new ServiceException(UtilMessage.SAVE_MESSAGE_ERROR);
			}
		}else {
			if (utInspectSiteList.size() > 0) {
				boolean saveSiteList = inspectSiteService.save(utInspectSiteList, "com.gw.mapper.UtInspectSiteMapper.insertSitelList");
				if (saveSiteList == false) {
					throw new ServiceException(UtilMessage.SAVE_MESSAGE_ERROR);
				}
			}
		}
	}

	@Override
	@Transactional
	public boolean save(List autoBatcyList, String dao) {
		boolean flag = false;
		try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);) {
			//由于数据库对于插入字段的限制，在这里对批量插入的数据进行分批处理
			int batchCount = 120;//每批commit的个数
			int batchLastIndex = batchCount - 1;// 每批最后一个的下标
			for (int index = 0; index < autoBatcyList.size() - 1; ) {
				if (batchLastIndex > autoBatcyList.size() - 1) {
					batchLastIndex = autoBatcyList.size() - 1;
					sqlSession.insert(dao, autoBatcyList.subList(index, batchLastIndex + 1));
					sqlSession.commit();
					break;// 数据插入完毕,退出循环
				} else {
					sqlSession.insert(dao, autoBatcyList.subList(index, batchLastIndex + 1));
					sqlSession.commit();
					index = batchLastIndex + 1;// 设置下一批下标
					batchLastIndex = index + (batchCount - 1);
				}
			}

			sqlSession.commit();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}


	/**
	 * @param unitId          单位id
	 * @param utBaseSiteclass 被构建的参数实体
	 * @param siteClassName   类型名称
	 */
	private void buildSiteClassParams(String unitId, UtBaseSiteclass utBaseSiteclass, String siteClassName) {
		utBaseSiteclass.setId(snowflakeIdWorker.nextId());
		utBaseSiteclass.setUnitid(Long.parseLong(unitId));
		utBaseSiteclass.setSiteclassname(siteClassName);

	}

	private UtInspectSite buildSiteParams(String unitId, Long classId, InspectInData inData) {
		UtInspectSite site;//巡查点导入
		site = new UtInspectSite();
		//0：日，1：周，2：月，3：年
		if ("日".equals(inData.getInspectCycleType())) {
			site.setInspectcycletype(0);
		}
		if ("周".equals(inData.getInspectCycleType())) {
			site.setInspectcycletype(1);
		}
		if ("月".equals(inData.getInspectCycleType())) {
			site.setInspectcycletype(2);
		}
		if ("年".equals(inData.getInspectCycleType())) {
			site.setInspectcycletype(3);
		}
		site.setId(snowflakeIdWorker.nextId());
		site.setSiteclassid(classId);
		if (Util.isNotEmpty(inData.getSiteCode())) {
			int i = utInspectSiteMapper.getCodeByUnitid(unitId, inData.getSiteCode());
			if (i == 0) {
				site.setSitecode(inData.getSiteCode());
			} else {
				throw new ServiceException(UtilMessage.SITE_CODE_ERROR);
			}
			site.setSitecode(inData.getSiteCode());
		}
		if (Util.isNotEmpty(inData.getSiteName())) {
			site.setSitename(inData.getSiteName());
		}
		if (Util.isNotEmpty(inData.getSiteDesc())) {
			site.setSitedesc(inData.getSiteDesc());
		}
		if (Util.isNotEmpty(inData.getInspectCount())) {
			site.setInspectcount(Integer.parseInt(inData.getInspectCount()));
		}
		if (Util.isNotEmpty(inData.getNFCCode())) {
			site.setNfccode(inData.getNFCCode());
		}
		site.setUnitid(Long.valueOf(unitId));
		return site;
	}

	/**
	 * 生成检查项持久化参数
	 *
	 * @param inspectInData 请求参数实体
	 * @return
	 * @throws Exception
	 */
	private List<UtBaseSiteclassdetial> buildSiteclassdetial(InspectInData inspectInData) throws Exception {
		List<UtBaseSiteclassdetial> detailList = new ArrayList<>();
		UtBaseSiteclassdetial detail = null;
		Field[] declaredFields = inspectInData.getClass().getDeclaredFields();
		for (int i = 0; i < declaredFields.length; i++) {
			Field f = declaredFields[i];
			f.setAccessible(true); // 设置些属性是可以访问的
			if (f.getName().startsWith("checkInfo") && null != f.get(inspectInData) && !"".equals(f.get(inspectInData))) {
				//插入检查项
				detail = new UtBaseSiteclassdetial();
				BeanUtils.copyProperties(inspectInData, detail);
				detail.setId(snowflakeIdWorker.nextId());
				detail.setCheckinfo((String) f.get(inspectInData));
				detailList.add(detail);
			}
		}
		return detailList;
	}




	/**
	 * 导入
	 * -查询所有点位的名称,位置,执行人,巡查频数,执行周期,周期内起止时间,
	 * 	同时根据计划ID判断点位是否已关联该计划(isPlaned),根据时间及是否关联排序
	 */
	@Override
	public PageInfo<UtInspectSiteOut> selectUtInspectSiteOutList(UtInspectSiteOut utInspectSiteOut) {
		PageHelper.startPage(utInspectSiteOut.getPageNumber(), utInspectSiteOut.getPageSize());
		List<UtInspectSiteOut> list= utInspectSiteMapper.selectUtInspectSiteOutList(utInspectSiteOut);
		PageInfo<UtInspectSiteOut> pager = new PageInfo<UtInspectSiteOut>(list);
		return pager;
	}

}
