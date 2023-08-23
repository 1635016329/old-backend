package com.gsq.backend.controller;

import com.gsq.backend.common.BaseResponse;
import com.gsq.backend.common.ResultUtils;
import com.gsq.backend.model.entity.Lab;
import com.gsq.backend.service.LabService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Gosh
 * @version 1.0
 * @date 2023/7/20 11:18
 * @description 实验室控制器
 */
@RestController
@RequestMapping("/lab")
@Api(tags = {"实验室控制器"})
@Slf4j
public class LabController {

    @Resource
    private LabService labService;

    @ApiOperation("获取实验室列表")
    @GetMapping("/getLabList")
    public BaseResponse<List<Lab>> getLabList() {
        List<Lab> list = labService.list();
        return ResultUtils.success(list);
    }
}
