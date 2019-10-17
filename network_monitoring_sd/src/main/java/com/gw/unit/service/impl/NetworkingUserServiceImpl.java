package com.gw.unit.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.common.SnowflakeIdWorker;
import com.gw.exception.ServiceException;
import com.gw.mapper.SysUserMapper;
import com.gw.mapper.UtBaseUserRelationMapper;
import com.gw.mapper.UtBaseUserreunitMapper;
import com.gw.mapper.UtUnitBaseinfoMapper;
import com.gw.mapper.UtUnitUserMapper;
import com.gw.mapper.entity.UtBaseUseRelation;
import com.gw.mapper.entity.UtBaseUserreunit;
import com.gw.mapper.entity.UtUnitUser;
import com.gw.unit.data.BaseInfoSelectOutData;
import com.gw.unit.data.NetworkingUserInData;
import com.gw.unit.data.NetworkingUserOutData;
import com.gw.unit.service.NetworkingUserService;
import com.gw.util.DataConvertUtil;
import com.gw.util.Util;
import com.gw.util.UtilConv;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

@Service
public class NetworkingUserServiceImpl implements NetworkingUserService {

	@Autowired
	private UtUnitUserMapper unitUserMapper;
	@Autowired
	private UtUnitBaseinfoMapper baseinfoMapper;
	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;
	@Autowired
	private UtBaseUserreunitMapper utBaseUserreunitMapper;
	@Autowired
	private UtBaseUserRelationMapper utBaseUserRelationMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    @Transactional
    public void addNetworkingUser(Long userId, NetworkingUserInData inData) throws Exception {
        Example example = new Example(UtUnitUser.class);
        example.createCriteria().andEqualTo("account", inData.getAccount());
        //通过account判断当前用户名是否已存在
        List<UtUnitUser> selectByExample = unitUserMapper.selectByExample(example);
        if (Util.isNotEmpty(selectByExample) && selectByExample.size() > 0) {
            throw new ServiceException("该用户名已经存在!");
        }
        UtUnitUser unitUser = new UtUnitUser();
        BeanUtils.copyProperties(inData, unitUser);
        long nextId = snowflakeIdWorker.nextId();
        //id
        unitUser.setId(nextId);
        //用户类型 用户类型（0维保单位，1联网单位，2其他）
        unitUser.setUsertype(1);
        //是否删除
        unitUser.setIsdelete(0);
        unitUser.setAdddate(new Date());
        unitUser.setAdduserid(userId.toString());
        String md5Hex = DigestUtils.md5Hex(unitUser.getPassword());
        unitUser.setPassword(md5Hex);
        unitUser.setCreateDate(new Date());
        //用户角色
        if (Util.isNotEmpty(inData.getUserrole())) {
            unitUser.setUserrole(Integer.parseInt(inData.getUserrole()));
        }
        //性别
        if (Util.isNotEmpty(Integer.parseInt(inData.getSex()))) {
            unitUser.setSex(Integer.parseInt(inData.getSex()));
        }
        //生日
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (Util.isNotEmpty(inData.getBirthday())) {
            unitUser.setBirthday(format.parse(inData.getBirthday()));
        }
        //发证日期
        if (Util.isNotEmpty(inData.getCertificatesdate())) {
            unitUser.setCertificatesdate(format.parse(inData.getCertificatesdate()));
        }
        //过期时间
        if (Util.isNotEmpty(inData.getExpirytime())) {
            unitUser.setExpirytime(UtilConv.str2Date(inData.getExpirytime(), UtilConv.DATE_YYYY_MM_DD_CHN));
        }
        //获取建筑Id
        String relation[] = inData.getRelationID().split(",");
        //单位id
        if (Util.isNotEmpty(inData.getUnitid())) {
            Long unitId = Long.valueOf(inData.getUnitid());
            //设置单位ID
            unitUser.setUnitid(unitId);
            //创建人员单位关联对象
            UtBaseUserreunit re = new UtBaseUserreunit();
            //设置人员单位关联表单位id
            re.setUnitId(unitId);
            //人员单位关联表用户id
            re.setUserId(nextId);
            //存入人员单位关联表
            utBaseUserreunitMapper.insert(re);
            //判断建筑id是否存在
            if ((Util.isNotEmpty(inData.getRelationID()))) {
            // 遍历建筑id
                for (String relationID : relation) {
                    //人员关联建筑区域
                    UtBaseUseRelation utBaseUseRelation = new UtBaseUseRelation();
                    //设置用户id
                    utBaseUseRelation.setUserId(nextId);
                    //设置建筑id
                    utBaseUseRelation.setRelationId(DataConvertUtil.parseLong(relationID));
                    //存进人员关联建筑区域
                    utBaseUserRelationMapper.insert(utBaseUseRelation);
                }
            }
        } else {
            //如果不存在单位id
            Example utBaseUserreunitExample = new Example(UtBaseUserreunit.class);
            //将用户传过来的userid在   人员单位关联对象查询是否存在关联
            utBaseUserreunitExample.createCriteria().andEqualTo("userId", userId);
            List<UtBaseUserreunit> list = utBaseUserreunitMapper.selectByExample(utBaseUserreunitExample);
            //如果存在
            if (Util.isNotEmpty(list)) {
                //获取人员单位关联对象的单位id
                Long unitId = list.get(0).getUnitId();
                //设置单位id
                unitUser.setUnitid(unitId);
                //创建人员单位关联
                UtBaseUserreunit re = new UtBaseUserreunit();
                //人员单位关联 从新创建一个对象 单位id  人员id
                re.setUnitId(unitId);
                re.setUserId(nextId);
                //存入人员单位关联表
                utBaseUserreunitMapper.insert(re);
                //获取区域id
                if ((Util.isNotEmpty(inData.getRelationID()))) {
                    //如果不为空 循环遍历
                    for (String relationID : relation) {
                        //创建用户区域关联表
                        UtBaseUseRelation utBaseUseRelation = new UtBaseUseRelation();
                        //设置用户id
                        utBaseUseRelation.setUserId(nextId);
                        //建筑区域id
                        utBaseUseRelation.setRelationId(DataConvertUtil.parseLong(relationID));
                        //存进人员关联建筑区域表
                        utBaseUserRelationMapper.insert(utBaseUseRelation);
                    }
                } else {
                    throw new ServiceException("请选择建筑区域！");
                }
            } else {
                throw new ServiceException("该登录用户没有关联单位！");
            }
        }

        //获取用户角色 如果选择的是单位管理人 就会在后台用户创建一个用户
        if (inData.getUserrole().equals("1")) {
//            Example example2 = new Example(SysUser.class);
//            example2.createCriteria().andEqualTo("account", inData.getAccount());
//            List<SysUser> list = sysUserMapper.selectByExample(example2);
//            if (Util.isNotEmpty(list)) {
//                throw new ServiceException("该用户名已经存在!");
//            }
//            SysUser sysUser = new SysUser();
//            sysUser.setId(nextId);
//            sysUser.setAccount(inData.getAccount());
//            sysUser.setUserName(inData.getUsername());
//            sysUser.setCreateDate(new Date());
//            sysUser.setPassword(md5Hex);
//            sysUser.setUserHeader(inData.getPhoto());
//            sysUser.setCreateUser(userId.toString());
//            sysUser.setPhone(inData.getMobilephone());
//            sysUser.setBirthday(inData.getBirthday());
//            sysUser.setSex(inData.getSex());
//            sysUserMapper.insert(sysUser);
            //也是后台用户
            unitUser.setIsSysUser("1");
        }
        unitUserMapper.insert(unitUser);
    }

    @Override
    public PageInfo<NetworkingUserOutData> list(Long userId, NetworkingUserInData inData) throws Exception {
        UtUnitUser utUnitUser = unitUserMapper.selectByPrimaryKey(userId);
        if (Util.isNotEmpty(utUnitUser) && Util.isNotEmpty(utUnitUser.getUnitid())) {
            if (utUnitUser.getId() != 1 || utUnitUser.getUnitid() != 1) {

                    inData.setUnitid(utUnitUser.getUnitid().toString());
                }
            }

            PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
            List<NetworkingUserOutData> list = unitUserMapper.selectAllNetworkingUser(inData);
            PageInfo<NetworkingUserOutData> pageInfo = new PageInfo<NetworkingUserOutData>(list);
            return pageInfo;
        }

        @Override
        @Transactional
        public void updateNetworkingUser (Long id, NetworkingUserInData inData) throws Exception {
            //判断当前用户名是否已存在
            Example example = new Example(UtUnitUser.class);
            example.createCriteria().andEqualTo("account", inData.getAccount());
            List<UtUnitUser> selectByExample = unitUserMapper.selectByExample(example);
            if (Util.isNotEmpty(selectByExample) && selectByExample.size() > 0) {
                UtUnitUser utUnitUser = selectByExample.get(0);
                if (!utUnitUser.getId().equals(Long.valueOf(inData.getId()))) {
                    throw new ServiceException("该用户名已经存在!");
                }
            }

            Long userId = Long.valueOf(inData.getId());
            UtUnitUser unitUser = unitUserMapper.selectByPrimaryKey(userId);
            String password = unitUser.getPassword();
            BeanUtils.copyProperties(inData, unitUser);
            unitUser.setUsertype(1);
            unitUser.setPassword(password);
            if (Util.isNotEmpty(inData.getUserrole())) {
                unitUser.setUserrole(Integer.parseInt(inData.getUserrole()));
            } else {
                unitUser.setUserrole(null);
            }
            if (Util.isNotEmpty(Integer.parseInt(inData.getSex()))) {
                unitUser.setSex(Integer.parseInt(inData.getSex()));
            } else {
                unitUser.setSex(null);
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            if (Util.isNotEmpty(inData.getBirthday())) {
                unitUser.setBirthday(format.parse(inData.getBirthday()));
            } else {
                unitUser.setBirthday(null);
            }
            if (Util.isNotEmpty(inData.getCertificatesdate())) {
                unitUser.setCertificatesdate(format.parse(inData.getCertificatesdate()));
            } else {
                unitUser.setCertificatesdate(null);
            }
            if (Util.isNotEmpty(inData.getExpirytime())) {
                unitUser.setExpirytime(UtilConv.str2Date(inData.getExpirytime(), UtilConv.DATE_YYYY_MM_DD_CHN));
            } else {
                unitUser.setExpirytime(null);
            }
            String relation[] = inData.getRelationID().split(",");
            if (Util.isNotEmpty(inData.getUnitid())) {
                Long unitId = Long.valueOf(inData.getUnitid());
                unitUser.setUnitid(unitId);

			Example example2 = new Example(UtBaseUserreunit.class);
			example2.createCriteria().andEqualTo("userId", inData.getId());
			utBaseUserreunitMapper.deleteByExample(example2);
			utBaseUserRelationMapper.deleteByExample(example2) ;
				UtBaseUserreunit utBaseUserreunit = new UtBaseUserreunit();
				utBaseUserreunit.setUserId(Long.valueOf(inData.getId()));
				utBaseUserreunit.setUnitId(Long.valueOf(inData.getUnitid()));
				utBaseUserreunitMapper.insert(utBaseUserreunit);
				if ((Util.isNotEmpty(inData.getRelationID()))) {
                    for (String relationID : relation) {
                        UtBaseUseRelation utBaseUseRelation = new UtBaseUseRelation();
                        utBaseUseRelation.setUserId(Long.valueOf(inData.getId()));
                        utBaseUseRelation.setRelationId(DataConvertUtil.parseLong(relationID));
				utBaseUserRelationMapper.insert(utBaseUseRelation);
                    }
                } else {
                    Example example1 = new Example(UtBaseUseRelation.class);
                    example1.createCriteria().andEqualTo("userId", Long.valueOf(inData.getId()));
                    utBaseUserRelationMapper.deleteByExample(example1);
			}
		} else {
			unitUser.setUnitid(null);
		}

            if ("1".equals(inData.getUserrole())) {
//                Example example2 = new Example(SysUser.class);
//                example2.createCriteria().andEqualTo("account", inData.getAccount());
//                List<SysUser> list = sysUserMapper.selectByExample(example2);
//                if (Util.isNotEmpty(list)) {
//                    SysUser sysUser = list.get(0);
//                    if (!userId.equals(sysUser.getId())) {
//                        throw new ServiceException("该用户名已经存在!");
//                    }
//                }
//                SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
//                sysUser.setAccount(inData.getAccount());
//                sysUser.setUserName(inData.getUsername());
//                sysUser.setUserHeader(inData.getPhoto());
//                sysUser.setPhone(inData.getMobilephone());
//                sysUser.setBirthday(inData.getBirthday());
//                sysUser.setSex(inData.getSex());
//                sysUser.setUpdateDate(new Date());
//                sysUser.setUpdateUser(id.toString());
//                sysUserMapper.updateByPrimaryKey(sysUser);
                unitUser.setIsSysUser("1");
            }
            unitUserMapper.updateByPrimaryKey(unitUser);

        }

        @Override
        @Transactional
        public void deleteNetworkingUser (String id) throws Exception {
            Example example = new Example(UtBaseUserreunit.class);
            example.createCriteria().andEqualTo("userId", Long.valueOf(id));
            utBaseUserreunitMapper.deleteByExample(example);
            unitUserMapper.deleteByPrimaryKey(Long.valueOf(id));
//            sysUserMapper.deleteByPrimaryKey(Long.valueOf(id));
        }

        @Override
        public List<BaseInfoSelectOutData> selectUnitnameAndId () throws Exception {
            List<BaseInfoSelectOutData> list = baseinfoMapper.selectUnitNameAndId();
            return list;
        }

        @Override
        public void resetPas (String id) throws Exception {
            UtUnitUser utUnitUser = unitUserMapper.selectByPrimaryKey(Long.valueOf(id));
            utUnitUser.setPassword(DigestUtils.md5Hex("123456"));
//            SysUser sysUser = sysUserMapper.selectByPrimaryKey(Long.valueOf(id));
//            sysUser.setPassword(DigestUtils.md5Hex("123456"));
            unitUserMapper.updateByPrimaryKey(utUnitUser);
//            sysUserMapper.updateByPrimaryKey(sysUser);
        }

    }
