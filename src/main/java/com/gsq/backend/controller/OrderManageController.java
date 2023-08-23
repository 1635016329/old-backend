package com.gsq.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gsq.backend.annotation.AuthCheck;
import com.gsq.backend.annotation.LoginCheck;
import com.gsq.backend.common.BaseResponse;
import com.gsq.backend.common.ErrorCode;
import com.gsq.backend.common.ResultUtils;
import com.gsq.backend.constant.UserConstant;
import com.gsq.backend.exception.BusinessException;
import com.gsq.backend.exception.ThrowUtils;
import com.gsq.backend.model.dto.device.DeviceOrderDTO;
import com.gsq.backend.model.entity.*;
import com.gsq.backend.model.vo.DeviceVO;
import com.gsq.backend.model.vo.UserVO;
import com.gsq.backend.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Gosh
 * @version 1.0
 * @date 2023/7/25 9:15
 * @description 预约管理控制器
 */
@RestController
@RequestMapping("/order")
@Api(tags = {"设备预约管理控制器"})
@Slf4j
public class OrderManageController {

    @Resource
    private DeviceOrderService deviceOrderService;

    @Resource
    private UserService userService;

    @Resource
    private MailService mailService;

    @Resource
    private DeviceService deviceService;

    @Resource
    private DeviceTypeService deviceTypeService;

    @Resource
    private ProjectUserService projectUserService;

    @Resource
    private LabService labService;

    @Resource
    private ProjectDeviceService projectDeviceService;

    @GetMapping("/getDeviceOrderList")
    @ApiOperation("获取项目预约列表(管理员)")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<List<DeviceOrderDTO>> getDeviceOrderList() {
        // todo 可拓展为根据条件查询，比如查询未被审核的，审核通过的，审核不通过的
        QueryWrapper<DeviceOrder> deviceOrderQueryWrapper = new QueryWrapper<>();
        //根据申请时间倒序排列
        deviceOrderQueryWrapper.orderByDesc("create_time");
        List<DeviceOrder> deviceOrderList = deviceOrderService.list(deviceOrderQueryWrapper);
        ArrayList<DeviceOrderDTO> deviceOrderDTOList = new ArrayList<>();
        for (DeviceOrder deviceOrder : deviceOrderList) {
            Device device = deviceService.getById(deviceOrder.getDeviceId());
            DeviceVO deviceVO = DeviceVO.parseDeviceVO(device, deviceTypeService.getById(device.getDeviceTypeId()), labService.getById(device.getLabId()));
            UserVO userVO = UserVO.parseUserVO(userService.getById(deviceOrder.getUserId()));
            DeviceOrderDTO deviceOrderDTO = new DeviceOrderDTO();
            deviceOrderDTO.setUserVO(userVO);
            deviceOrderDTO.setDeviceVO(deviceVO);
            deviceOrderDTO.setStartTime(deviceOrder.getStartTime());
            deviceOrderDTO.setEndTime(deviceOrder.getEndTime());
            deviceOrderDTO.setCreateTime(deviceOrder.getCreateTime());
            deviceOrderDTO.setOrderState(deviceOrder.getOrderState());
            deviceOrderDTOList.add(deviceOrderDTO);
        }
        return ResultUtils.success(deviceOrderDTOList);
    }

    @PostMapping("/addDeviceOrder")
    @ApiOperation("设备预约申请(用户)")
    public BaseResponse<Boolean> addDeviceOrder(@RequestBody DeviceOrder deviceOrder) {
        // 1. 检查申请设备在申请时间段内是否被预约(1,2 条注意考虑并发，需要加锁)
        // 2. 添加申请记录
        // 3. 向管理员发邮件，说XXX提交了预约申请，请及时审核
        // 判断结束时间和开始世纪那的插值是否大于15分钟
        // todo 后面添加设置必须提前多久预约
        long userTime = deviceOrder.getEndTime().getTime() - deviceOrder.getStartTime().getTime();
        if (userTime < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "开始时间晚于结束时间");
        }
        if (userTime / (1000 * 60) < 15) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "预约使用时间必须大于等于15分钟");
        }
        QueryWrapper<DeviceOrder> deviceOrderQueryWrapper = new QueryWrapper<>();
        deviceOrderQueryWrapper.eq("device_id", deviceOrder.getDeviceId())
                .and(w -> w.and(w1 -> w1.gt("start_time", deviceOrder.getStartTime()).lt("start_time", deviceOrder.getEndTime()))
                        .or(w1 -> w1.gt("end_time", deviceOrder.getStartTime()).lt("end_time", deviceOrder.getEndTime())));
        long order = deviceOrderService.count(deviceOrderQueryWrapper);
        ThrowUtils.throwIf(order > 0, new BusinessException(ErrorCode.PARAMS_ERROR, "该时间段已被预约"));
        boolean save = deviceOrderService.save(deviceOrder);
        ThrowUtils.throwIf(!save, new BusinessException(ErrorCode.SYSTEM_ERROR, "添加设备预约申请失败，请重试"));
        // todo 暂时写死，后续修改为管理员邮箱
        Boolean send = mailService.sendTextMail("1635016329@qq.com", "设备预约", "XXX提交了设备预约申请，请及时审核！！！");
        ThrowUtils.throwIf(!send, new BusinessException(ErrorCode.SYSTEM_ERROR, "邮件发送失败"));
        return ResultUtils.success(true);
    }


    @PostMapping("/checkDeviceOrderById")
    @ApiOperation("根据设备预约id审核设备预约申请(管理员)")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> checkDeviceOrder(Boolean result, Long orderId) {
        // 1. 审核通过
        // 2. 审核不通过
        // 3. 发邮件告诉申请人审核结果
        // 未审核：0  审核通过：1  审核不通过：2 todo 后面封装为枚举
        ThrowUtils.throwIf(result == null, new BusinessException(ErrorCode.PARAMS_ERROR, "审核结果不能为空"));
        if (Boolean.TRUE.equals(result)) {
            UpdateWrapper<DeviceOrder> deviceOrderUpdateWrapper = new UpdateWrapper<>();
            deviceOrderUpdateWrapper.eq("order_id", orderId)
                    .set("audit_result", 1);
            boolean update = deviceOrderService.update(deviceOrderUpdateWrapper);
            ThrowUtils.throwIf(!update, new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库错误"));
        }
        if (Boolean.FALSE.equals(result)) {
            UpdateWrapper<DeviceOrder> deviceOrderUpdateWrapper = new UpdateWrapper<>();
            deviceOrderUpdateWrapper.eq("order_id", orderId)
                    .set("audit_result", 2);
            boolean update = deviceOrderService.update(deviceOrderUpdateWrapper);
            ThrowUtils.throwIf(!update, new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库错误"));
        }
        DeviceOrder deviceOrder = deviceOrderService.getById(orderId);

        User user = userService.getById(deviceOrder.getUserId());
        Boolean send = mailService.sendTextMail(user.getEmail(), "设备预约审核结果", result ? "设备预约审核通过" : "设备预约申请被驳回");// todo 可以备注审核不通过原因
        ThrowUtils.throwIf(!send, new BusinessException(ErrorCode.SYSTEM_ERROR, "邮件发送失败"));

        return ResultUtils.success(true);
    }

    @GetMapping("/getOrderListByUserId")
    @ApiOperation("根据项目id获取该项目所有设备的预约记录")
    public BaseResponse<Map<String, Object>> getOrderListByUserId(Long projectId) {
        HashMap<String, Object> map = new HashMap<>();
        ProjectDevice projectDevice = projectDeviceService.getById(projectId);
        for (String deviceId : projectDevice.getDeviceIds().split(",")) {
            QueryWrapper<DeviceOrder> deviceOrderQueryWrapper = new QueryWrapper<>();
            deviceOrderQueryWrapper.orderByDesc("create_time")
                    .eq("device_id", deviceId);
            List<DeviceOrder> deviceOrderList = deviceOrderService.list(deviceOrderQueryWrapper);
            ArrayList<DeviceOrderDTO> deviceOrderDTOList = new ArrayList<>();
            for (DeviceOrder deviceOrder : deviceOrderList) {
                Device device = deviceService.getById(deviceOrder.getDeviceId());
                DeviceVO deviceVO = DeviceVO.parseDeviceVO(device, deviceTypeService.getById(device.getDeviceTypeId()), labService.getById(device.getLabId()));
                UserVO userVO = UserVO.parseUserVO(userService.getById(deviceOrder.getUserId()));
                DeviceOrderDTO deviceOrderDTO = new DeviceOrderDTO();
                deviceOrderDTO.setUserVO(userVO);
                deviceOrderDTO.setDeviceVO(deviceVO);
                deviceOrderDTO.setStartTime(deviceOrder.getStartTime());
                deviceOrderDTO.setEndTime(deviceOrder.getEndTime());
                deviceOrderDTO.setCreateTime(deviceOrder.getCreateTime());
                deviceOrderDTO.setOrderState(deviceOrder.getOrderState());
                deviceOrderDTOList.add(deviceOrderDTO);
            }
            map.put(deviceService.getById(deviceId).getDeviceName(), deviceOrderDTOList);
        }
        return ResultUtils.success(map);
    }




}
