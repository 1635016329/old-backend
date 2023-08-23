package com.gsq.backend.service;

import com.gsq.backend.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;

/**
 * @author Gosh
 * @version 1.0
 * @date 2023/7/14 15:08
 * @description 用户业务层测试
 */
@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    void userServiceTest() {
//        User user = new User();
//        user.setRealName("小明");
//        user.setEmail("111111@qq.com");
//        user.setOrg("2222222222222");
//        long userId = userService.userRegister(user);
//        Assertions.assertEquals(3, userId);
    }
}
