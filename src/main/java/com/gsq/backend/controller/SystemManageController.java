package com.gsq.backend.controller;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.read.listener.PageReadListener;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.gson.Gson;
import com.gsq.backend.annotation.AuthCheck;
import com.gsq.backend.common.BaseResponse;
import com.gsq.backend.common.DeleteRequest;
import com.gsq.backend.common.ErrorCode;
import com.gsq.backend.common.ResultUtils;
import com.gsq.backend.constant.UserConstant;
import com.gsq.backend.exception.BusinessException;
import com.gsq.backend.exception.ThrowUtils;
import com.gsq.backend.model.dto.user.UserFile;
import com.gsq.backend.model.entity.Lab;
import com.gsq.backend.model.entity.User;
import com.gsq.backend.service.LabService;
import com.gsq.backend.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * @author Gosh
 * @version 1.0
 * @date 2023/7/24 8:51
 * @description 系统管理控制器
 */
@RestController
@RequestMapping("/system")
@Api(tags = {"系统管理控制器"})
@Slf4j
public class SystemManageController {

    @Resource
    private LabService labService;

    @Resource
    private UserService userService;

    @ApiOperation("获取实验室列表")
    @GetMapping("/geLabList")
    public BaseResponse<List<Lab>> getLabList() {
        List<Lab> list = labService.list();
        return ResultUtils.success(list);
    }

    @ApiOperation("添加单个实验室")
    @PostMapping("/addLab")
    public BaseResponse<Boolean> addLab(@RequestBody Lab lab) {
        boolean save = labService.save(lab);
        ThrowUtils.throwIf(!save, new BusinessException(ErrorCode.PARAMS_ERROR));
        return ResultUtils.success(true);
    }

    @ApiOperation("根据id删除实验室信息")
    @PostMapping("/delLabById")
    public BaseResponse<Boolean> addLab(Long id) {
        boolean remove = labService.removeById(id);
        ThrowUtils.throwIf(!remove, new BusinessException(ErrorCode.PARAMS_ERROR));
        return ResultUtils.success(true);
    }

    @ApiOperation("根据人员档案excel表格生成人员列表")
    @PostMapping("/getUserListByExcel")
    public BaseResponse<Map<String, Object>> getUserList(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        ArrayList<UserFile> userList = new ArrayList<>();
        EasyExcelFactory.read(multipartFile.getInputStream(), UserFile.class, new PageReadListener<UserFile>(dataList -> {
            for (UserFile demoData : dataList) {
                log.info("读取到一条数据{}", new Gson().toJson(demoData));
                userList.add(demoData);
            }
        }) {
        }).sheet().doRead();

        // 检查邮箱是否重复
        Map<String, UserFile> emailMap = new HashMap<>();
        HashSet<UserFile> duplicateUsers = new LinkedHashSet<>();

        for (UserFile userFile : userList) {
            String email = userFile.getEmail();
            if (emailMap.containsKey(email)) {
                UserFile user  = emailMap.get(email);
                duplicateUsers.add(user);
                duplicateUsers.add(userFile);
            } else {
                emailMap.put(email, userFile);
            }
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("userList", userList);
        map.put("duplicateUserList", duplicateUsers);

        return ResultUtils.success(map);
    }

    @ApiOperation("批量添加用户")
    @PostMapping("/addBatchUser")
    public BaseResponse<Boolean> addBatchUser(@RequestBody List<UserFile> userFiles) {
        List<User> userList = new ArrayList<>();
        for (UserFile userFile : userFiles) {
            User user = User.parseUser(userFile);
            userList.add(user);
        }
        boolean save = userService.saveBatch(userList);
        ThrowUtils.throwIf(!save, new BusinessException(ErrorCode.SYSTEM_ERROR, "批量导入用户失败"));
        return ResultUtils.success(true);
    }

    @ApiOperation("获取所有用户信息")
    @PostMapping("/getUserList")
    public BaseResponse<List<User>> getUserList() {
        List<User> userList = userService.list();
        return ResultUtils.success(userList);
    }

    @ApiOperation("根据用户id删除用户")
    @PostMapping("/deleteUser")
    public BaseResponse<Boolean> deleteProjectById(@RequestBody DeleteRequest deleteRequest) {
        Long id = deleteRequest.getId();
        User user = userService.getById(id);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        boolean remove = userService.removeById(id);
        ThrowUtils.throwIf(!remove, new RuntimeException("删除用户失败"));
        return ResultUtils.success(true);
    }

    @ApiOperation("新增单个用户")
    @PostMapping("/addUser")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addUser(@RequestBody User user) {
        user.setUserPassword("123456");
        boolean save = userService.save(user);
        ThrowUtils.throwIf(!save, new RuntimeException("添加用户失败"));
        Long userId = user.getUserId();
        return ResultUtils.success(userId);
    }

    @ApiOperation("更新单个用户信息")
    @PostMapping("/updateUser")
    public BaseResponse<Boolean> updateProject(@RequestBody User user) {
        Long userId = user.getUserId();
        User exist = userService.getById(userId);
        if (exist == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("user_id", userId)
                .set("real_name", user.getUsername())
                .set("email", user.getEmail())
                .set("user_role", user.getUserRole())
                .set("user_password", user.getUserPassword())
                .set("org", user.getOrg());
        boolean update = userService.update(userUpdateWrapper);
        return ResultUtils.success(update);
    }



}
