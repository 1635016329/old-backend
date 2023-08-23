package com.gsq.backend.controller;

import com.gsq.backend.common.BaseResponse;
import com.gsq.backend.common.ErrorCode;
import com.gsq.backend.common.ResultUtils;
import com.gsq.backend.exception.ThrowUtils;
import com.gsq.backend.model.dto.train.AddTrainRequest;
import com.gsq.backend.model.dto.train.AuditTrainApplicationRequest;
import com.gsq.backend.model.dto.train.UpdateTrainRequest;
import com.gsq.backend.model.entity.Train;
import com.gsq.backend.service.TrainApplicationService;
import com.gsq.backend.service.TrainService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Gosh
 * @version 1.0
 * @date 2023/7/31 10:12
 * @description 培训管理控制器
 */
@RestController
@RequestMapping("/train")
@Api(tags = {"培训管理控制器"})
@Slf4j
public class TrainManageController {

    @Resource
    private TrainService trainService;

    @PostMapping("/addProjectTrain")
    @ApiOperation("添加项目培训")
    public BaseResponse<Boolean> addProjectTrain(@RequestBody AddTrainRequest addTrainRequest) {
        Boolean add = trainService.addProjectTrain(addTrainRequest);
        ThrowUtils.throwIf(!add, ErrorCode.SYSTEM_ERROR, "添加项目培训失败");
        return ResultUtils.success(true);
    }

    @PostMapping("/updateProjectTrain")
    @ApiOperation("修改项目培训")
    public BaseResponse<Boolean> updateProjectTrain(@RequestBody UpdateTrainRequest updateTrainRequest) {
        Boolean update = trainService.updateProjectTrain(updateTrainRequest);
        ThrowUtils.throwIf(!update, ErrorCode.SYSTEM_ERROR, "修改项目培训失败");
        return ResultUtils.success(true);
    }

    @PostMapping("/delProjectTrainById")
    @ApiOperation("添加项目培训")
    public BaseResponse<Boolean> delProjectTrainById(Long trainId) {
        Boolean del = trainService.delProjectTrainById(trainId);
        ThrowUtils.throwIf(!del, ErrorCode.SYSTEM_ERROR, "删除项目培训失败");
        return ResultUtils.success(true);
    }

    @GetMapping("/getTrainList")
    @ApiOperation("获取项目培训列表")
    public BaseResponse<List<Train>> getTrainList() {
        List<Train> trainList = trainService.getTrainList();
        return ResultUtils.success(trainList);
    }

    @PostMapping("/applyTrainByProjectName")
    @ApiOperation("根据项目名申请培训")
    public BaseResponse<Boolean> applyTrainByProjectName(String projectName) {
        Boolean result = trainService.applyTrainByProjectName(projectName);
        return ResultUtils.success(result);
    }

    @PostMapping("/auditTrainApplication")
    @ApiOperation("根据培训申请id审核项目")
    public BaseResponse<Boolean> auditTrainApplicationById(@RequestBody AuditTrainApplicationRequest auditTrainApplicationRequest) {
        Boolean result = trainService.auditTrainApplication(auditTrainApplicationRequest);
        return ResultUtils.success(result);
    }


}
