package com.gw.inspect.service.impl;

import com.gw.common.SnowflakeIdWorker;
import com.gw.inspect.data.InspectionInData;
import com.gw.inspect.service.InspectConfigService;
import com.gw.mapper.*;
import com.gw.mapper.entity.*;
import com.gw.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Set;


@Service
public class InspectConfigServiceImpl implements InspectConfigService {

	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;
	@Autowired
	private UtInspectSiteMapper utInspectSiteMapper;
	@Autowired
	private UtBaseSiteclassbaseMapper utBaseSiteclassbaseMapper;
	@Autowired
	private UtBaseSiteclassdetialMapper utBaseSiteclassdetialMapper;
	@Autowired
	private UtMaintenanceUnitMapper utMaintenanceUnitMapper;
	@Autowired
	private UtUnitBuildMapper utUnitBuildMapper;
	@Autowired
	private UtUnitBuildareaMapper utUnitBuildareaMapper;
	@Autowired
	private UtInspectPlanMapper utInspectPlanMapper;
	@Autowired
	private UtBaseSiteclassMapper utBaseSiteclassMapper;
	@Autowired
	private UTInspectBaseRelMapper uTInspectBaseRelMapper;
	@Autowired
	private UtUnitUserMapper utUnitUserMapper;

	@Override
	public int add(List<InspectionInData> list) throws Exception {

		//如果list为空 方法直接结束
		int successNum = 0;
		if (Util.isEmpty(list)) {
			return 0;
		}
		//将传过来的list转为set去重复以免提交重复数据
		Set<InspectionInData> InspectionInDataSet = new HashSet<>(list);

		//创建巡查点Set
		Set<UtInspectSite> utInspectSiteSet = new HashSet<>();
		//创建检查对象Set
		Set<UtBaseSiteclassbase> utBaseSiteclassbaseSet = new HashSet<>();
		//创建检查项Set
		Set<UtBaseSiteclassdetial> utBaseSiteclassdetialSet = new HashSet<>();
		//遍历set
		for (InspectionInData inspectionInData : InspectionInDataSet) {
			//属于巡查点对象的字段赋值给巡查点对象
			UtInspectSite utInspectSite = addUtInspectSite(inspectionInData);
			//将utInspectSite存进巡查点Set
			utInspectSiteSet.add(utInspectSite);
			//属于的字段赋值给检查对象
			UtBaseSiteclassbase utBaseSiteclassbase = addUtBaseSiteclassbase(inspectionInData);
			//将utBaseSiteclassbase存进检查对象SET
			utBaseSiteclassbaseSet.add(utBaseSiteclassbase);

			//属于检查项对象的字段赋值给检查项对象
			UtBaseSiteclassdetial utBaseSiteclassdetial = addUtBaseSiteclassdetial(inspectionInData);
			utBaseSiteclassdetialSet.add(utBaseSiteclassdetial);
			//将关联对象存进数据库


		}


		//遍历utInspectSiteSet为他们增加主键ID
		for (UtInspectSite site : utInspectSiteSet) {
			site.setId(snowflakeIdWorker.nextId());
		}
		//遍历 utBaseSiteclassbaseSet为他们增加主键ID
		for (UtBaseSiteclassbase siteClass : utBaseSiteclassbaseSet) {
			siteClass.setId(snowflakeIdWorker.nextId());

		}

		//遍历utBaseSiteclassdetialSet为他们增加主键ID
		for (UtBaseSiteclassdetial siteDetial : utBaseSiteclassdetialSet) {
			siteDetial.setId(snowflakeIdWorker.nextId());
		}
		System.out.println(utInspectSiteSet.size());
		System.out.println(utBaseSiteclassbaseSet.size());
		System.out.println(utBaseSiteclassdetialSet.size());

//判断数据库是否存在该对象 是否继续插入
		for (UtInspectSite set1 : utInspectSiteSet) {

			if (utInspectSiteMapper.selectIdByMaintenanceUnitInData(set1) == null) {
				int i = utInspectSiteMapper.insertSelective(set1);
			}

		}

		//判断数据库是否存在该对象 是否继续插入
		for (UtBaseSiteclassbase set1 : utBaseSiteclassbaseSet) {

			if (utBaseSiteclassbaseMapper.selectIDbyUtBaseSiteclassbase(set1) == null) {
				int i = utBaseSiteclassbaseMapper.insertSelective(set1);
			}
		}


		//判断数据库是否存在该对象 是否继续插入
		for (UtBaseSiteclassdetial set1 : utBaseSiteclassdetialSet) {
			if (utBaseSiteclassdetialMapper.selectIDbyUtBaseSiteclassdetial(set1) == null) {
				int i = utBaseSiteclassdetialMapper.insertSelective(set1);
			}

		}
		//再次遍历 List<InspectionInData> list 分别获取对象ID
		for (InspectionInData InspectionInDataList : list) {

			//创建一个关联对象
			UTInspectBaseRel uTInspectBaseRel = new UTInspectBaseRel();
			uTInspectBaseRel.setLastupdate(new Date(System.currentTimeMillis()));
			//为关联对象设置ID
			uTInspectBaseRel.setId(snowflakeIdWorker.nextId());
			//属于巡查点对象的字段赋值给巡查点对象
			UtInspectSite utInspectSite = addUtInspectSite(InspectionInDataList);


			//根据巡查点对象查出 巡查点ID
			String utInspectSiteID = utInspectSiteMapper.selectIdByMaintenanceUnitInData(utInspectSite);

			uTInspectBaseRel.setSiteID(Long.parseLong(utInspectSiteID));

			//属于的字段赋值给检查对象
			UtBaseSiteclassbase utBaseSiteclassbase = addUtBaseSiteclassbase(InspectionInDataList);
			//根据检查对象查出检查ID
			uTInspectBaseRel.setSiteClassID(Long.parseLong(utBaseSiteclassbaseMapper.selectIDbyUtBaseSiteclassbase(utBaseSiteclassbase)));

			//属于检查项对象的字段赋值给检查项对象
			UtBaseSiteclassdetial utBaseSiteclassdetial = addUtBaseSiteclassdetial(InspectionInDataList);

			//根据检查项对象查出对象id
			uTInspectBaseRel.setSiteClassDetialID(Long.parseLong(utBaseSiteclassdetialMapper.selectIDbyUtBaseSiteclassdetial(utBaseSiteclassdetial)));
			successNum++;
			uTInspectBaseRelMapper.insertSelective(uTInspectBaseRel);

		}


		return successNum;
	}


	//字符串转日期
	public static Date str_Date(String str) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = simpleDateFormat.parse(str);
			System.out.println(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	//检查位置   将inspectionInData里面的个别字段存入检查位置
	public UtInspectSite addUtInspectSite(InspectionInData inspectionInData) {

		//检查位置（检查点）
		UtInspectSite utInspectSite = new UtInspectSite();
		//编号
		utInspectSite.setSitecode(inspectionInData.getSiteCode());
		//名称
		utInspectSite.setSitename(inspectionInData.getSiteName());
		//描述
		utInspectSite.setSitedesc(inspectionInData.getDesc());
		//位置
		utInspectSite.setSiteplace(inspectionInData.getSite());
		//根据单位名称查出单位id 判断是否存在该单位
		if (utMaintenanceUnitMapper.selectUnitIDByUnitName(inspectionInData.getUnit()) != null) {
			long unitID = Long.parseLong(utMaintenanceUnitMapper.selectUnitIDByUnitName(inspectionInData.getUnit()));
			utInspectSite.setUnitid(unitID);
		} else {
			utInspectSite.setUnitid(null);
		}
		//        根据建筑名称查出建筑id 判断是否存在该建筑
		if (utUnitBuildMapper.selectIdByBuildName(inspectionInData.getBuilding()) != null) {

			utInspectSite.setBuildID(utUnitBuildMapper.selectIdByBuildName(inspectionInData.getBuilding()));
		} else {
			utInspectSite.setBuildID(null);
		}

		// 根据区域名称查出区域Id  判断是否存在该区域
		if (utUnitBuildareaMapper.selectIDByBuildAreaName(inspectionInData.getArea()) != null) {
			utInspectSite.setBuildAreaID(utUnitBuildareaMapper.selectIDByBuildAreaName(inspectionInData.getArea()));
		} else {
			utInspectSite.setBuildAreaID(null);
		}

		//执行人  根据执行人名称查出执行人ID
		if (inspectionInData.getPerformName() != null) {
			utInspectSite.setExecutorID(utUnitUserMapper.getIdByAccount(inspectionInData.getPerformName()));

		}
//巡查频数
		utInspectSite.setInspectcount(Integer.valueOf(inspectionInData.getInspectNum()));
		//执行周期
		switch (inspectionInData.getInspectCycle()) {
			case "日":
				utInspectSite.setInspectcycle(0);
				break; //可选
			case "周":
				utInspectSite.setInspectcycle(1);
				break; //可选
			case "月":
				utInspectSite.setInspectcycle(2);
				break; //可选
			case "年":
				utInspectSite.setInspectcycle(3);
				break; //可选
		}

		//周期内执行起止时间
		utInspectSite.setTaskstart(str_Date(inspectionInData.getTaskStart()));
		utInspectSite.setTaskend(str_Date(inspectionInData.getTaskend()));
		return utInspectSite;
	}

	//检查对象   将inspectionInData里面的个别字段存入检查对象
	public UtBaseSiteclassbase addUtBaseSiteclassbase(InspectionInData inspectionInData) {
		//检查对象
		UtBaseSiteclassbase utBaseSiteclassbase = new UtBaseSiteclassbase();
		//对象名称
		utBaseSiteclassbase.setSiteclassname(inspectionInData.getObjectName());
		//位置描述
		utBaseSiteclassbase.setSiteclassdesc(inspectionInData.getSiteDesc());
		return utBaseSiteclassbase;
	}

	//  检查项    将inspectionInData里面的个别字段存入检查项
	public UtBaseSiteclassdetial addUtBaseSiteclassdetial(InspectionInData inspectionInData) {
		UtBaseSiteclassdetial utBaseSiteclassdetial = new UtBaseSiteclassdetial();
		Map map1 = new HashMap<>();
		//检查内容
		utBaseSiteclassdetial.setCheckinfo(inspectionInData.getCheckInfo());
		//检查方式
		utBaseSiteclassdetial.setCheckmethod(inspectionInData.getCheckMethod());
		return utBaseSiteclassdetial;
	}
}

