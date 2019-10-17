package com.gw.front.forSDOS;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.gw.exception.ServiceException;
import com.gw.mapper.UtLzBjzjalarmMapper;
import com.gw.mapper.UtLzFireequipmentalarmMapper;
import com.gw.util.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ServiceForSDOSImpl implements ServiceForSDOS {

	@Resource
	private UtLzBjzjalarmMapper utLzBjzjalarmMapper;
	@Resource
	private UtLzFireequipmentalarmMapper utLzFireequipmentalarmMapper;
	
	private String url = "http://47.96.127.101:8083/token/analyseToken";

	@Override
	public List<FireOutData> getFireAlarmDatas(FrontSdosInData inData) throws Exception {
		PageHelper.startPage(inData.getPageIndex(), inData.getPageSize());
		String dtstart = inData.getDtstart();
		if (Util.isNotEmpty(dtstart)) {
			inData.setDtstart(UtilConv.date2Str(UtilConv.str2Date(dtstart, UtilConv.DATE_TIME_PAT_14),
					UtilConv.DATE_YYYY_MM_DD_SS));
		}
		String dtend = inData.getDtend();
		if (Util.isNotEmpty(dtend)) {
			inData.setDtend(UtilConv.date2Str(UtilConv.str2Date(dtend, UtilConv.DATE_TIME_PAT_14),
					UtilConv.DATE_YYYY_MM_DD_SS));
		}
		//根据token获取私钥
		TokenOutData keyByToken = getKeyByToken(inData.getToken());
		if(Util.isNotEmpty(keyByToken.getPrivateKey())){
			inData.setPrivateKey(keyByToken.getPrivateKey());
		}
		List<FireOutData> outData = utLzBjzjalarmMapper.getFireAlarmDatas(inData);
		for (FireOutData fireOutData : outData) {
			fireOutData.setValue(dealStatus(fireOutData.getValue()));
		}
		return outData;
	}

	private String dealStatus(String alarmStatus) {
		if (Util.isEmpty(alarmStatus)) {
			return "";
		}
		switch (alarmStatus) {
		case "0":
			return "手动报警";
		case "1":
			return "火警";
		case "2":
			return "故障";
		case "3":
			return "启动";
		case "4":
			return "反馈";
		case "5":
			return "监管";
		case "6":
			return "屏蔽";
		case "7":
			return "恢复";
		case "8":
			return "复位";
		case "9":
			return "用户传输装置操作";
		case "11":
			return "火警恢复";
		case "12":
			return "故障恢复";
		case "13":
			return "停止";
		case "14":
			return "反馈撤销";
		case "15":
			return "监管撤销";
		case "16":
			return "屏蔽撤销";
		case "20":
			return "消音";
		case "21":
			return "解除报警";
		case "22":
			return "自动火警有人";
		case "23":
			return "自动火警超时";
		case "24":
			return "自动火警无人";
		case "25":
			return "确认火警";
		case "26":
			return "紧急火警";
		case "27":
			return "预警";
		case "28":
			return "报警";
		case "30":
			return "传输装置开机";
		case "31":
			return "传输装置关机";
		case "32":
			return "控制器开机";
		case "33":
			return "控制器关机";
		case "34":
			return "RTU开机";
		case "35":
			return "RTU关机";
		default:
			break;
		}
		return "";
	}

	@Override
	public List<RtuCurrentOutData> getRTUAlarmCurrentDatas(FrontSdosInData inData) throws Exception {
		TokenOutData keyByToken = getKeyByToken(inData.getToken());
		if(Util.isNotEmpty(keyByToken.getPrivateKey())){
			inData.setPrivateKey(keyByToken.getPrivateKey());
		}
		List<RtuCurrentOutData> outData = utLzBjzjalarmMapper.getRTUAlarmCurrentDatas(inData);
		return outData;
	}

	@Override
	public List<RtuHistoryOutData> getRTUAlarmHistoryDatas(FrontSdosInData inData) throws Exception {
		PageHelper.startPage(inData.getPageIndex(), inData.getPageSize());
		String dtstart = inData.getDtstart();
		if (Util.isNotEmpty(dtstart)) {
			inData.setDtstart(UtilConv.date2Str(UtilConv.str2Date(dtstart, UtilConv.DATE_TIME_PAT_14),
					UtilConv.DATE_YYYY_MM_DD_SS));
		}
		String dtend = inData.getDtend();
		if (Util.isNotEmpty(dtend)) {
			inData.setDtend(UtilConv.date2Str(UtilConv.str2Date(dtend, UtilConv.DATE_TIME_PAT_14),
					UtilConv.DATE_YYYY_MM_DD_SS));
		}
		TokenOutData keyByToken = getKeyByToken(inData.getToken());
		if(Util.isNotEmpty(keyByToken.getPrivateKey())){
			inData.setPrivateKey(keyByToken.getPrivateKey());
		}
		List<RtuHistoryOutData> outData = utLzBjzjalarmMapper.getRTUAlarmHistoryDatas(inData);
		return outData;
	}
	
	@SuppressWarnings("unchecked")
	private TokenOutData getKeyByToken(String token) throws Exception{
		HttpJson httpGet = HttpClientUtil.httpGet(String.format(ReqApiConst.GET_101_ANALYSETOKEN_URL,token));
		if (httpGet.isSuccess()) {
			String msg = httpGet.getMsg();
			Map<String, Object> map = (Map<String, Object>) JSONObject.parse(msg);
			if(!(boolean)map.get("success")){
				throw new ServiceException((String)map.get("msg"));
			}
			//TokenOutData outData = object.toJavaObject(TokenOutData.class);
			JSONObject jsonObject = (JSONObject) map.get("obj");
			TokenOutData outData = jsonObject.toJavaObject(TokenOutData.class);
			return outData;
		}else{
			throw new ServiceException("Token解析失败！");
		}
	}
}
