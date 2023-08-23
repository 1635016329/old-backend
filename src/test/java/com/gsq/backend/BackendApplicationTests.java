package com.gsq.backend;

import com.gsq.backend.mapper.DeviceTypeMapper;
import com.gsq.backend.model.entity.DeviceType;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@MapperScan("com.gsq.backend.mapper")
@SpringBootTest
class BackendApplicationTests {

    @Resource
    private DeviceTypeMapper deviceTypeMapper;

    @Test
    void contextLoads() {
        DeviceType deviceType = new DeviceType();
        deviceType.setTypeName("测试分类1");
        int insert = deviceTypeMapper.insert(deviceType);
        System.out.println(insert);
    }



}
