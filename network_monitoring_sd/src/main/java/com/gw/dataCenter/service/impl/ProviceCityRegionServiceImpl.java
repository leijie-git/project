package com.gw.dataCenter.service.impl;

import com.gw.common.SnowflakeIdWorker;
import com.gw.dataCenter.data.ProviceCityRegionInData;
import com.gw.dataCenter.data.ProviceCityRegionOutData;
import com.gw.dataCenter.data.RegionImportVo;
import com.gw.dataCenter.service.ProviceCityRegionService;
import com.gw.exception.ServiceException;
import com.gw.mapper.UtBaseProvicecityregionMapper;
import com.gw.mapper.entity.UtBaseProvicecityregion;
import com.gw.util.UtilMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ProviceCityRegionServiceImpl implements ProviceCityRegionService{

	private static final Logger LOGGER = LoggerFactory.getLogger(ProviceCityRegionServiceImpl.class);
	@Resource
	private UtBaseProvicecityregionMapper utBaseProvicecityregionMapper;
	
	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;
	@Override
	public List<Map<String, Object>> getAllProvice(String name) {
		return utBaseProvicecityregionMapper.getAllProvice(name);
	}

	@Override
	public void delete(String id) throws Exception {
		Integer flag = utBaseProvicecityregionMapper.deleteByPrimaryKey(Long.parseLong(id));
		if(flag<1) {
			throw new ServiceException(UtilMessage.DEL_MESSAGE_ERROR);
		}
	}

	@Override
	public void add(ProviceCityRegionInData inData) throws Exception {
		UtBaseProvicecityregion region = new UtBaseProvicecityregion();
		region.setId(snowflakeIdWorker.nextId());
		if(inData.getPid()!=null&&inData.getPid()!="") {
			region.setParentid(Long.parseLong(inData.getPid()));
		}
		region.setRegioncode(inData.getRegionCode());
		region.setRegionname(inData.getRegionName());
		region.setType(Integer.parseInt(inData.getType()));
		
		Integer flag = utBaseProvicecityregionMapper.insert(region);
		if(flag<1) {
			throw new ServiceException(UtilMessage.ADD_ERROR);
		}
	}

	@Override
	public void update(ProviceCityRegionInData inData) throws Exception {
		UtBaseProvicecityregion region = new UtBaseProvicecityregion();
		region.setId(Long.parseLong(inData.getId()));
		if(inData.getPid()!=null&&inData.getPid()!="") {
			region.setParentid(Long.parseLong(inData.getPid()));
		}
		region.setRegioncode(inData.getRegionCode());
		region.setRegionname(inData.getRegionName());
		region.setType(Integer.parseInt(inData.getType()));
		
		Integer flag = utBaseProvicecityregionMapper.updateByPrimaryKey(region);
		if(flag<1) {
			throw new ServiceException(UtilMessage.ADD_ERROR);
		}
		
	}

	@Override
	public List<ProviceCityRegionOutData> listProviceByID(String type) {
		List<ProviceCityRegionOutData> list = utBaseProvicecityregionMapper.getListProvice(type);
		return list;
	}

	@Override
	public ProviceCityRegionOutData edit(String id) {
		ProviceCityRegionOutData data = utBaseProvicecityregionMapper.getProviceByID(id);
		return data;
	}

	@Override
	public List<RegionImportVo> importRegion(List<RegionImportVo> regionImportVos) {
		UtBaseProvicecityregion region = new UtBaseProvicecityregion();
		int count = 12000;
		int type = 3;
		if (null != regionImportVos) {
			Example example4 = new Example(UtBaseProvicecityregion.class);
			example4.createCriteria().andEqualTo("type", type + 1);
			utBaseProvicecityregionMapper.deleteByExample(example4);
			addRegionData(3, count, regionImportVos);
			LOGGER.info("剩余区县:" + regionImportVos.size() + "; 明细:" + regionImportVos);
		}
		return null;
	}

	/**
	 * 增量添加行政区划数据
	 *
	 * @param level           上级区划等级:1,省;2,市;3,区;4,乡镇/街道
	 * @param startId         起始id
	 * @param regionImportVos 导入的原始数据
	 */
	private void addRegionData(int level, int startId, List<RegionImportVo> regionImportVos) {
		UtBaseProvicecityregion region = new UtBaseProvicecityregion();
		// 查询上级区划列表
		List<UtBaseProvicecityregion> utBaseProvicecityregions = getUpperRegions(level);
		String[] code;
		String[] name;
		for (UtBaseProvicecityregion province : utBaseProvicecityregions) {
			//遍历导入的数据
			for (int i = regionImportVos.size() - 1; i >= 0; i--) {
				code = new String[]{regionImportVos.get(i).getProvinceNo(), regionImportVos.get(i).getCityNo(), regionImportVos.get(i).getAreaNo(), regionImportVos.get(i).getTownNo()};
				name = new String[]{regionImportVos.get(i).getProvinceName(), regionImportVos.get(i).getCityName(), regionImportVos.get(i).getAreaName(), regionImportVos.get(i).getTownName()};
				//上级区划与导入数据的名称/编号均相同,则插入本级区划
				if (province.getRegioncode().equals(code[level - 1]) && province.getRegionname().equals(name[level - 1])) {
					startId++;
					region.setId((long) startId);
					region.setRegioncode(regionImportVos.get(i).getTownNo());
					region.setType(level + 1);
					region.setRegionname(regionImportVos.get(i).getTownName());
					region.setParentid(province.getId());
					utBaseProvicecityregionMapper.insert(region);
					regionImportVos.remove(i);
				}
			}
		}
	}

	private List<UtBaseProvicecityregion> getUpperRegions(int level) {
		Example example = new Example(UtBaseProvicecityregion.class);
		example.createCriteria().andEqualTo("type", level);
		return utBaseProvicecityregionMapper.selectByExample(example);
	}


}
