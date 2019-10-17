package com.gw.mapper;

import com.gw.common.BaseMapper;
import com.gw.inspect.data.InspectBaseRelInData;
import com.gw.inspect.data.InspectTaskInListData;
import com.gw.inspect.data.UTInspectBaseRelOutData;
import com.gw.mapper.entity.UTInspectBaseRel;

import java.util.List;

public interface UTInspectBaseRelMapper extends BaseMapper<UTInspectBaseRel> {

	List<UTInspectBaseRelOutData> getList(InspectBaseRelInData inspectBaseRelInData) throws Exception;

	List<InspectTaskInListData> getSiteAndSiteCalssList(InspectTaskInListData inspectTaskInListData) throws Exception;



}