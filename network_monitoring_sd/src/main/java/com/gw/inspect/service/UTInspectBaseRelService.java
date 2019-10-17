package com.gw.inspect.service;

import com.github.pagehelper.PageInfo;
import com.gw.inspect.data.InspectBaseRelInData;
import com.gw.inspect.data.InspectTaskInListData;
import com.gw.inspect.data.UTInspectBaseRelOutData;

import java.util.List;


public interface UTInspectBaseRelService {

	PageInfo<UTInspectBaseRelOutData> getList(InspectBaseRelInData inData) throws Exception;

	void addAll(List<InspectTaskInListData> inData) throws Exception;

	void updateAll(List<InspectTaskInListData> inData,String unitId) throws Exception;

	void remove(String id) throws Exception;

	List<InspectTaskInListData> getRelList(InspectBaseRelInData inData) throws Exception;
}
