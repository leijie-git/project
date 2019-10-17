package com.gw.systemManager.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.common.SnowflakeIdWorker;
import com.gw.exception.ServiceException;
import com.gw.mapper.UtUnitNotifyMapper;
import com.gw.mapper.UtUnitUserMapper;
import com.gw.mapper.UtUserNotifyRelMapper;
import com.gw.mapper.entity.UtUnitNotify;
import com.gw.mapper.entity.UtUserNotifyRel;
import com.gw.systemManager.data.NotifyInData;
import com.gw.systemManager.data.NotifyOutData;
import com.gw.systemManager.data.NotifyRelUserOutData;
import com.gw.systemManager.service.NotifyService;
import com.gw.util.Util;
import com.gw.util.UtilConv;
import com.gw.webSocket.AlarmWebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.*;

/**
 * 通知服务层
 *
 * @author SY
 * @data 2018年9月21日
 */

@Service
public class NotifyServiceImpl implements NotifyService {
	private static final Logger LOGGER = LoggerFactory.getLogger(NotifyServiceImpl.class);
	@Autowired
	private UtUnitNotifyMapper notifyMapper;

	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;

	@Autowired
	private UtUserNotifyRelMapper notifyRelMapper;

	@Autowired
	private UtUnitUserMapper userMapper;

	@Override
	public PageInfo<NotifyOutData> pageList(NotifyInData inData) throws Exception {
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<NotifyOutData> list = notifyMapper.getNotifyPageList(inData);
		PageInfo<NotifyOutData> pageInfo = new PageInfo<NotifyOutData>(list);
		return pageInfo;
	}

	@Override
	@Transactional
	public void addNotify(Long sender, NotifyInData inData) throws Exception {
		UtUnitNotify notify = new UtUnitNotify();
		UtUserNotifyRel notifyRel = new UtUserNotifyRel();
		BeanUtils.copyProperties(inData, notify);
		long nextId = snowflakeIdWorker.nextId();
		notify.setId(nextId);
		notify.setSender(sender);
		notify.setSendDate(new Date());
//		if(Util.isNotEmpty(inData.getSendDate())){
//			notify.setSendDate(UtilConv.str2Date(inData.getSendDate(), UtilConv.DATE_YYYY_MM_DD_SS));
//		}
		notifyMapper.insert(notify);
		webSendNetdeviceMsg(inData);
		//添加关联表的数据
		if (Util.isEmpty(inData.getReceiver())) {
			throw new ServiceException("接收人不能为空");
		}
		String[] split = inData.getReceiver().split(",");
		for (String receiver : split) {
			notifyRel.setUnitUserId(Long.valueOf(receiver));
			notifyRel.setNotifyId(nextId);
			notifyRel.setIsRead("0");
			notifyRelMapper.insert(notifyRel);
		}
	}

	private void webSendNetdeviceMsg(NotifyInData inData) throws Exception {
		Map<AlarmWebSocket, String> map = AlarmWebSocket.map;
		if (Util.isEmpty(map)) {
			LOGGER.info("=====无登陆用户");
			return;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(inData.getReceiver() + ",");
		String userIds = sb.toString();
		AlarmWebSocket webSocket;
		for (Map.Entry<AlarmWebSocket, String> entry : map.entrySet()) {
			String user = entry.getValue();
			String userId = user.substring(0, user.indexOf(","));
			if (userIds.contains(userId)) {
				webSocket = entry.getKey();
				webSocket.sendMessage(JSONObject.toJSONString(inData));
				LOGGER.info("========推送前端信息：userId:" + userId + "站内通知信息:" + inData.getContent());
			}
		}
	}


	@Override
	@Transactional
	public void updateNotify(NotifyInData inData) throws Exception {
		UtUnitNotify notify = notifyMapper.selectByPrimaryKey(Long.valueOf(inData.getId()));
		notify.setContent(inData.getContent());
		notify.setTitle(inData.getTitle());
		if (Util.isNotEmpty(inData.getSendDate())) {
			notify.setSendDate(UtilConv.str2Date(inData.getSendDate(), UtilConv.DATE_YYYY_MM_DD_CHN));
		} else {
			notify.setSendDate(null);
		}
		notifyMapper.updateByPrimaryKey(notify);

//		Example example = new Example(UtUserNotifyRel.class);
//		Criteria criteria = example.createCriteria();
//		criteria.andEqualTo("notifyId", Long.valueOf(inData.getId()));
//		notifyRelMapper.deleteByExample(example);
//		
//		UtUserNotifyRel notifyRel = new UtUserNotifyRel();
//		//添加关联表的数据
//		if(Util.isEmpty(inData.getReceiver())){
//			throw new ServiceException("接收人不能为空");
//		}
//		String[] split = inData.getReceiver().split(",");
//		for (String receiver : split) {
//			notifyRel.setUnitUserId(Long.valueOf(receiver));
//			notifyRel.setNotifyId(Long.valueOf(inData.getId()));
//			notifyRel.setIsRead("0");
//			notifyRelMapper.insert(notifyRel);
//		}
	}

	@Override
	@Transactional
	public void deleteNotify(String notifyId) throws Exception {
		Example example = new Example(UtUserNotifyRel.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("notifyId", notifyId);
		List<UtUserNotifyRel> list = notifyRelMapper.selectByExample(example);
		if (Util.isNotEmpty(list)) {
			notifyRelMapper.deleteByExample(example);
		}
		notifyMapper.deleteByPrimaryKey(Long.valueOf(notifyId));
	}

	@Override
	public PageInfo<NotifyRelUserOutData> getAllUserSelect(Integer pageNumber, Integer pageSize, String userIds) throws Exception {
		PageHelper.startPage(pageNumber, pageSize);
		List<NotifyRelUserOutData> list = userMapper.getAllUserSelectList();
		PageInfo<NotifyRelUserOutData> pageInfo = new PageInfo<NotifyRelUserOutData>(list);
		return pageInfo;
	}

	@Override
	public PageInfo<NotifyRelUserOutData> getNotifyRelUser(Integer pageNumber, Integer pageSize, String id, String userIds) throws Exception {
		/*PageHelper.startPage(pageNumber, pageSize);
		List<NotifyRelUserOutData> list = userMapper.getAllUserSelectList();
		Example example = new Example(UtUserNotifyRel.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("notifyId", id);
		List<UtUserNotifyRel> utUserNotifyRels = notifyRelMapper.selectByExample(example);
		//如果关联通知的用户，则设置state为true
		for (UtUserNotifyRel utUserNotifyRel : utUserNotifyRels) {
			for(NotifyRelUserOutData data : list){
				if(utUserNotifyRel.getUnitUserId().equals(Long.valueOf(data.getId()))){
					data.setState(true);
				}
			}
		}
		PageInfo<NotifyRelUserOutData> pageInfo = new PageInfo<NotifyRelUserOutData>(list);
		return pageInfo;*/
		if (Util.isEmpty(id)) {
			if (Util.isEmpty(userIds)) {
				return null;
			}
			/*String[] split = userIds.split(",");
			List<NotifyRelUserOutData> outList = new ArrayList<NotifyRelUserOutData>();
			for (String userId : split) {
				UtUnitUser utUnitUser = userMapper.selectByPrimaryKey(Long.valueOf(userId));
				NotifyRelUserOutData outData = new NotifyRelUserOutData();
				outData.setId(utUnitUser.getId().toString());
				outData.setAccount(utUnitUser.getAccount());
				outData.setUsername(utUnitUser.getUsername());
				outList.add(outData);
			}*/
			String[] split = userIds.split(",");
			List<String> userIdList = Arrays.asList(split);
			PageHelper.startPage(pageNumber, pageSize);

//			StringBuffer sb = new StringBuffer(userIds);
//			sb.append(")");
//			sb.insert(0, "(");
//			userIds = sb.toString();
			List<NotifyRelUserOutData> list = userMapper.getUserListByIds(userIdList);
			PageInfo<NotifyRelUserOutData> pageInfo = new PageInfo<NotifyRelUserOutData>(list);
			return pageInfo;

		}
		PageHelper.startPage(pageNumber, pageSize);
		List<NotifyRelUserOutData> list = notifyRelMapper.getNotifyRelUser(id);
		PageInfo<NotifyRelUserOutData> pageInfo = new PageInfo<NotifyRelUserOutData>(list);
		return pageInfo;
	}

	@Override
	public PageInfo<NotifyRelUserOutData> getAllUserDisSelect(Integer pageNumber, Integer pageSize,
															  String id, String userIds) throws Exception {
		if (Util.isNotEmpty(id)) {
			Example example = new Example(UtUserNotifyRel.class);
			example.createCriteria().andEqualTo("notifyId", id);
			List<UtUserNotifyRel> utUserNotifyRels = notifyRelMapper.selectByExample(example);
			if (Util.isEmpty(userIds)) {
				List<String> idList = new ArrayList<>();
				for (UtUserNotifyRel utUserNotifyRel : utUserNotifyRels) {
					idList.add(utUserNotifyRel.getUnitUserId().toString());
				}
				PageHelper.startPage(pageNumber, pageSize);
				List<NotifyRelUserOutData> allUserDisSelectList = userMapper.getAllUserDisSelectList(idList);
				PageInfo<NotifyRelUserOutData> pageInfo = new PageInfo<NotifyRelUserOutData>(allUserDisSelectList);
				return pageInfo;
			} else {
				String[] split = userIds.split(",");
				List<String> idList = new ArrayList<>();
				Collections.addAll(idList, split);
				for (UtUserNotifyRel utUserNotifyRel : utUserNotifyRels) {
					idList.add(utUserNotifyRel.getUnitUserId().toString());
				}
				PageHelper.startPage(pageNumber, pageSize);
				List<NotifyRelUserOutData> allUserDisSelectList = userMapper.getAllUserDisSelectList(idList);
				PageInfo<NotifyRelUserOutData> pageInfo = new PageInfo<NotifyRelUserOutData>(allUserDisSelectList);
				return pageInfo;
			}
		} else {
			PageHelper.startPage(pageNumber, pageSize);
			if (Util.isEmpty(userIds)) {
				List<NotifyRelUserOutData> allUserSelectList = userMapper.getAllUserSelectList();
				PageInfo<NotifyRelUserOutData> pageInfo = new PageInfo<NotifyRelUserOutData>(allUserSelectList);
				return pageInfo;
			} else {
				String[] split = userIds.split(",");
				List<String> idList = new ArrayList<>();
				Collections.addAll(idList, split);
				List<NotifyRelUserOutData> allUserDisSelectList = userMapper.getAllUserDisSelectList(idList);
				PageInfo<NotifyRelUserOutData> pageInfo = new PageInfo<NotifyRelUserOutData>(allUserDisSelectList);
				return pageInfo;
			}
		}
	}

	@Override
	public void addNotifyRelUser(String id, String receiver) throws Exception {
		if (Util.isEmpty(receiver)) {
			throw new ServiceException("接收人ID不能为空");
		}
		String[] split = receiver.split(",");
		for (String userId : split) {
			UtUserNotifyRel notifyRel = new UtUserNotifyRel();
			notifyRel.setUnitUserId(Long.valueOf(userId));
			notifyRel.setNotifyId(Long.valueOf(id));
			notifyRel.setIsRead("0");
			notifyRelMapper.insert(notifyRel);
		}
	}

	@Override
	public void deleteNotifyRelUser(String notifyId, String userId) throws Exception {
		Example example = new Example(UtUserNotifyRel.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("notifyId", notifyId);
		criteria.andEqualTo("unitUserId", userId);
		notifyRelMapper.deleteByExample(example);
	}

}
