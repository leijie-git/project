package com.gw.front.forSDOS;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gw.exception.ServiceException;
import com.gw.myAnnotation.PassToken;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@ResponseBody
@RequestMapping("/front/sdos")
public class ControllerForSDOS {

	@Resource
	private ServiceForSDOS serviceForSDOS;

	/**
	 * 报警主机
	 * 
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@PassToken
	@RequestMapping("/getFireAlarmDatas")
	public ResultJson getFireAlarmDatas(HttpServletRequest request, FrontSdosInData inData) throws Exception {
		ResultJson json = new ResultJson();
		try {
			List<FireOutData> outData = serviceForSDOS.getFireAlarmDatas(inData);
			json.setIsSuccess(true);
			json.setData(outData);
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
			json.setMessage(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * RTU当前值
	 * 
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@PassToken
	@RequestMapping("/getRTUAlarmCurrentDatas")
	public ResultJson getRTUAlarmCurrentDatas(HttpServletRequest request, FrontSdosInData inData) throws Exception {
		ResultJson json = new ResultJson();
		try {
			List<RtuCurrentOutData> outData = serviceForSDOS.getRTUAlarmCurrentDatas(inData);
			json.setIsSuccess(true);
			json.setData(outData);
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
			json.setMessage(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 获取RTU历史记录
	 * 
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@PassToken
	@RequestMapping("/getRTUAlarmHistoryDatas")
	public ResultJson getRTUAlarmHistoryDatas(HttpServletRequest request, FrontSdosInData inData) throws Exception {
		ResultJson json = new ResultJson();
		try {
			List<RtuHistoryOutData> outData = serviceForSDOS.getRTUAlarmHistoryDatas(inData);
			json.setIsSuccess(true);
			json.setData(outData);
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
			json.setMessage(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}
}
