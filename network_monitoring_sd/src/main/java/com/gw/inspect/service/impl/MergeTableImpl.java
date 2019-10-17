package com.gw.inspect.service.impl;

import com.gw.common.SnowflakeIdWorker;
import com.gw.inspect.service.MergeTableService;
import com.gw.mapper.SysUserMapper;
import com.gw.mapper.UtUnitUserMapper;
import com.gw.mapper.entity.SysUser;
import com.gw.mapper.entity.UtUnitUser;
import com.gw.util.CodecUtil;
import com.gw.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Service
public class  MergeTableImpl implements MergeTableService {
	@Resource
	private  SysUserMapper sysUserMapper;
	@Resource
	private UtUnitUserMapper utUnitUserMapper;
	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;
	@Override
	//SYS_USER    合并到   UT_Unit_User
	public String mergeTabble () {
		int count=0;
		int nocouunt=0;

		//获取sysUser中的所有数据
		List<SysUser> sysUsers = sysUserMapper.selectAll();
		//获取UtUnitUser中所有的数据
		List<UtUnitUser> utUnitUsers = utUnitUserMapper.selectAll();
		//判断sysUser中的账户密码 在UtUnitUser中是否存在 如果存在不做任何操作 如果不存在将不存在的数据insert到UtUnitUser中
		for(SysUser suer:sysUsers){
			//将sysuser的账号密码取出来  放进UtUnitUser去查询是否存在
			Example example = new Example(UtUnitUser.class);
			example.createCriteria().andEqualTo("account",suer.getAccount()).andEqualTo("password",suer.getPassword());
			if(Util.isNotEmpty(utUnitUserMapper.selectByExample(example))){
				List<UtUnitUser> utUnitUsers1 = utUnitUserMapper.selectByExample(example);
				UtUnitUser utUnitUser = utUnitUsers1.get(0);
				if(suer.getCreateDate()!=null){
					utUnitUser.setCreateDate(suer.getCreateDate());
				}
				if(suer.getUpdateDate()!=null){
					utUnitUser.setUpdateDate(suer.getUpdateDate());
				}
//				if(suer.getSex().equals("男")){
//					utUnitUser.setSex(1);
//				}else{
//					utUnitUser.setSex(0);
//				}
				utUnitUser.setUserHeader(suer.getUserHeader());

				utUnitUser.setAddress(suer.getAddress());

				utUnitUser.setEthnicGroup(suer.getEthnicGroup());

				utUnitUser.setSign(suer.getSign());

				utUnitUser.setRemark(suer.getRemark());

				utUnitUser.setCreateUser(suer.getCreateUser());

				utUnitUser.setUsername(suer.getUserName());

				utUnitUser.setIsSysUser("1");


				utUnitUserMapper.updateByPrimaryKey(utUnitUser);


				//统计有多少有的
				count++;
			}else {
				UtUnitUser sys=new UtUnitUser();
//id
				sys.setId(suer.getId());
				//用户名
				sys.setUsername(suer.getUserName());
				//账户
				sys.setAccount(suer.getAccount());
				//密码加密
				sys.setPassword(CodecUtil.encryptMD5(suer.getPassword()));
				//创建时间
				sys.setCreateDate(new Date());
				//创建人
				sys.setCreateUser(suer.getCreateUser());
				//用户头像
				sys.setUserHeader(suer.getUserHeader());
				//手机号码
				sys.setMobilephone(suer.getPhone());
				//居住地址
				sys.setAddress(suer.getAddress());
				//备注
				sys.setRemark(suer.getRemark());
				//签名
				sys.setSign(suer.getSign());
				//名族
				sys.setEthnicGroup(suer.getEthnicGroup());
				//性别
				if(suer.getSex().equals("男")){
					sys.setSex(1);
				}
				if(suer.getSex().equals("女")){
					sys.setSex(0);
				}

//				//生日
//				if(suer.getBirthday()!=null) {
//
//					DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
//
//					try {
//						format1.parse(suer.getBirthday());
//					} catch (ParseException e) {
//						e.printStackTrace();
//					}
					System.out.println("sys"+sys);
					nocouunt=utUnitUserMapper.insert(sys);
					nocouunt++;
				}


		}
		System.out.println("已经存在的有 "+count);
		System.out.println("需要合并的有 "+nocouunt);

		return "合表成功 合并了"+nocouunt+"========="+"更改了(已经存在)"+"count";
	}





}
