package com.mapper;

import com.gw.mapper.ApkInfoMapper;
import com.gw.mapper.entity.ApkInfo;
import com.test.BaseMapperTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @description: AlarmInfoMapper测试类
 *
 **/
public class ApkInfoMapperTest extends BaseMapperTest {

    @Resource
    private ApkInfoMapper apkInfoMapper;

    @Test
    public void findAll() throws Exception {
        ApkInfo info = apkInfoMapper.selectLastApkInfo();
        System.out.println(info);
        List<ApkInfo> apkInfos = apkInfoMapper.selectAll();
        System.out.println(apkInfos.size());
    }

    @Test
    public void findNull(){
        apkInfoMapper.selectByPrimaryKey(null);
    }
}
