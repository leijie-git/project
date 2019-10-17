package com.gw.wechat.service.impl;

import com.gw.apppush.PushMsgManage;
import com.gw.common.SnowflakeIdWorker;
import com.gw.exception.ServiceException;
import com.gw.front.login.data.FrontUnitUserOutData;
import com.gw.mapper.*;
import com.gw.mapper.entity.*;
import com.gw.util.*;
import com.gw.wechat.data.TrunSingleInData;
import com.gw.wechat.service.AppService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class AppServiceImpl implements AppService {

	@Resource
	private SnowflakeIdWorker snowflakeIdWorker;
	@Resource
	private UtUnitUserMapper utUnitUserMapper;
	@Resource
	private UtMaintenanceLoginLogMapper utMaintenanceLoginLogMapper;
	@Resource
	private UtInspectTaskMapper utInspectTaskMapper;
	@Resource
	private ApkInfoMapper apkInfoMapper;
	@Resource
	private TaskTurnSingleMapper taskTurnSingleMapper;

	@Value("${cbs.imagesPath}")
	private String uploadPath;

	@Override
	public FrontUnitUserOutData login(HttpServletRequest request, FrontUnitUserOutData inData) throws Exception {
		String password = inData.getPassword();
		String md5Hex = DigestUtils.md5Hex(password);
		Example example = new Example(UtUnitUser.class);
		example.createCriteria().andEqualTo("account", inData.getAccount()).andEqualTo("password", md5Hex)
				.andEqualTo("isdelete", "0");
		List<UtUnitUser> userList = utUnitUserMapper.selectByExample(example);
		if (Util.isEmpty(userList)) {
			throw new ServiceException(UtilConst.ERRO_ACCOUNT_PASSWORD);
		}

		UtUnitUser user = userList.get(0);
		String channelId = inData.getChannelId();
		String phoneName = inData.getPhoneName();
		//清空channelid除了自己
		utUnitUserMapper.updateChannelId(channelId, inData.getAccount());
		if (Util.isNotEmpty(channelId) && !channelId.equals(user.getChannelid())) {
			user.setChannelid(channelId);
			user.setPhoneName(phoneName);
			utUnitUserMapper.updateByPrimaryKey(user);
		}

		// 保存登录记录
		UtMaintenanceLoginLog log = new UtMaintenanceLoginLog();
		String ip = UtilConv.getIpAddr(request);
		log.setLoginIp(ip);
		log.setLoginAddr(UtilConv.baiduGetCityCode(ip));
		Date loginDate = new Date();
		log.setLoginDate(loginDate);
		log.setLoginOutDate(new Date(loginDate.getTime() + 1800000));// 默认退出时间半个小时以后
		log.setLoginName(inData.getAccount());
		long nextId = snowflakeIdWorker.nextId();
		log.setId(nextId);
		log.setDataFrom("3");// App登录记录
		utMaintenanceLoginLogMapper.insert(log);
		// request.getSession().setAttribute("utMaintenanceLoginLog", log);

		FrontUnitUserOutData outData = new FrontUnitUserOutData();
		outData.setAccount(user.getAccount());
		outData.setUsertype(user.getUsertype() + "");
		outData.setUsername(user.getUsername());
		outData.setUserrole(user.getUserrole() + "");
		outData.setEmail(user.getEmail());
		outData.setMobilephone(user.getMobilephone());
//		outData.setOpenid(user.getOpenid());
		outData.setSex(user.getSex());
		outData.setUnitid(user.getUnitid() + "");
		// outData.setUserid(user.getUserid()+"");
		outData.setId(user.getId() + "");
		outData.setPassword(md5Hex);
		outData.setToken(TokenUtil.getToken(outData.getId(), outData.getPassword()));
		outData.setIspushfault(user.getIspushfault());
		outData.setChannelId(channelId);
		return outData;
	}

	@Override
	public void logout(FrontUnitUserOutData inData) throws Exception {
        Example example = new Example(UtUnitUser.class);
        example.createCriteria().andEqualTo("account", inData.getAccount()).andEqualTo("isdelete", "0");
        UtUnitUser utUnitUser = utUnitUserMapper.selectOneByExample(example);
        utUnitUser.setChannelid("");
        //清除数据库对应account的channelId
        utUnitUserMapper.updateByPrimaryKey(utUnitUser);
	}

	/**
	 * apk上传
	 *
	 * @param multipartFile 文件
	 * @return url
	 */

	@Override
	public String uploadApk(MultipartFile multipartFile) throws Exception {

		if (Util.isEmpty(multipartFile)) {
			throw new ServiceException(UtilConst.ERRO_SYSTEM);
		}

		String fileName = multipartFile.getOriginalFilename();

		// UUID
		String uuid = UUID.randomUUID().toString().replace("-", "");
		String apkRealPathDir = uploadPath + "/apk/" + uuid + "/";

		// 根据真实路径创建目录
		File picSaveFile = new File(apkRealPathDir);
		if (!picSaveFile.exists())
			picSaveFile.mkdirs();

		// 拼成完整的文件保存路径加文件
		String filePath = apkRealPathDir + fileName;
		File file = new File(filePath);

		//保存文件
		multipartFile.transferTo(file);

		// 返回图片的存放路径
		return filePath;
	}

	/**
	 * 新增apkInfo
	 *
	 * @param apkInfo apk基础信息
	 */
	@Override
	public void addApkInfo(ApkInfo apkInfo) throws Exception {
		if (apkInfoMapper.insertSelective(apkInfo) < 1)
			throw new ServiceException(UtilMessage.SAVE_MESSAGE_ERROR);
	}

	/**
	 * 获取最新apk基础信息
	 *
	 * @return apk基础信息
	 */
	@Override
	public ApkInfo getLastApkInfo() throws Exception {
		return apkInfoMapper.selectLastApkInfo();
	}

	/**
	 * 发起转单
	 *
	 * @param trunSingleInData
	 * @throws Exception
	 */
	@Override
	@Transactional
	public void turnSingle(TrunSingleInData trunSingleInData) throws Exception {
		updateStatus(trunSingleInData);
	}


	private void updateStatus(TrunSingleInData trunSingleInData) {
		UtInspectTask utInspectTask = new UtInspectTask();
		utInspectTask.setId(Long.valueOf(trunSingleInData.getTaskId()));
		//转单人Id
		utInspectTask.setChangeuserid(Long.parseLong(trunSingleInData.getChangeUserId()));
		//接单人Id
		utInspectTask.setReceiveuserid(Long.parseLong(trunSingleInData.getReceiveUserId()));
		//转单状态
		utInspectTask.setReceivestatus((trunSingleInData.getReceiveStatus()));
		if (trunSingleInData.getIsChange() == 1) {
			utInspectTask.setChangetime(new Date());
		}
		if (trunSingleInData.getIsChange() == 4) {
			utInspectTask.setReceivetime(new Date());
		}
		Integer flag = utInspectTaskMapper.updateByPrimaryKeySelective(utInspectTask);
		if (flag < 1) {
			throw new ServiceException(UtilMessage.IS_CHANGE_ERROR);
		}
		//任务详情
		UtInspectTask utInspect = utInspectTaskMapper.selectByPrimaryKey(Long.valueOf(trunSingleInData.getTaskId()));
		//转单人详细信息
		UtUnitUser utUnitUser = utUnitUserMapper.selectByPrimaryKey(Long.valueOf(utInspect.getInspectuserid()));
		//监督人详细信息
		UtUnitUser utUnitSupervisorUser = utUnitUserMapper.selectByPrimaryKey(Long.valueOf(utInspect.getSupervisorid()));
		//接单人详细信息
		UtUnitUser utUnitReceiveUser = utUnitUserMapper.selectByPrimaryKey(Long.valueOf(utInspect.getReceiveuserid()));
		String content = "";
		//更新log记录表
		TaskTurnSingle taskTurnSingle = new TaskTurnSingle();
		taskTurnSingle.setTask_id(utInspect.getId());
		taskTurnSingle.setChange_id(utUnitUser.getId());
		taskTurnSingle.setReceive_id(utUnitReceiveUser.getId());
		taskTurnSingle.setTask_status(trunSingleInData.getReceiveStatus());
		if (trunSingleInData.getIsChange() == 1) {
			taskTurnSingle.setChange_time(new Date());
			//构建推送消息--推送至监督人
			content = utUnitSupervisorUser.getUsername() + "您监督的" + utUnitUser.getUsername() + "的申请转单，请登录app重新下载任务";
			PushMsgManage.pushMsg(utUnitSupervisorUser.getPhoneName(), "转单申请通知", content, utUnitSupervisorUser.getChannelid());
		} else if (trunSingleInData.getIsChange() == 2) {
			//构建推送消息--推送至接单人
			content = utUnitReceiveUser.getUsername() + "您有一条来自" + utUnitUser.getUsername() + "的申请转单，请登录app重新下载任务";
			PushMsgManage.pushMsg(utUnitReceiveUser.getPhoneName(), "转单通知", content, utUnitReceiveUser.getChannelid());
		} else if (trunSingleInData.getIsChange() == 3 || trunSingleInData.getIsChange() == 5) {
			//构建推送消息--推送至转单人
			content = utUnitUser.getUsername() + "您的申请转单被驳回，请登录app查看";
			PushMsgManage.pushMsg(utUnitUser.getPhoneName(), "转单驳回通知", content, utUnitUser.getChannelid());
		} else {
			taskTurnSingle.setChange_time(utInspect.getChangetime());
			taskTurnSingle.setReceive_time(new Date());
			//构建推送消息--推送至转单人
			content = utUnitUser.getUsername() + "您的转单申请已被通过，请登录app重新下载任务";
			PushMsgManage.pushMsg(utUnitUser.getPhoneName(), "转单成功通知", content, utUnitUser.getChannelid());
		}
		taskTurnSingleMapper.insert(taskTurnSingle);
	}


}
