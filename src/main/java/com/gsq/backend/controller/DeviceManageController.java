package com.gsq.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gsq.backend.common.BaseResponse;
import com.gsq.backend.common.ErrorCode;
import com.gsq.backend.common.FileUtils;
import com.gsq.backend.common.ResultUtils;
import com.gsq.backend.config.FileConfigProperties;
import com.gsq.backend.constant.UserConstant;
import com.gsq.backend.exception.BusinessException;
import com.gsq.backend.exception.ThrowUtils;
import com.gsq.backend.model.dto.device.AddDeviceRequest;
import com.gsq.backend.model.dto.device.DeviceMaintenanceDTO;
import com.gsq.backend.model.dto.device.UpdateDeviceRequest;
import com.gsq.backend.model.entity.*;
import com.gsq.backend.model.vo.DeviceUsedVO;
import com.gsq.backend.model.vo.DeviceVO;
import com.gsq.backend.model.vo.UserVO;
import com.gsq.backend.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Gosh
 * @version 1.0
 * @date 2023/7/19 8:50
 * @description 设备管理控制器
 */
@RestController
@RequestMapping("/device")
@Api(tags = {"设备管理控制器"})
@Slf4j
public class DeviceManageController {

    @Resource
    private DeviceTypeService deviceTypeService;

    @Resource
    private DeviceService deviceService;

    @Resource
    private LabService labService;

    @Resource
    private FileConfigProperties fileConfigProperties;

    @Resource
    private DeviceMaintenanceService deviceMaintenanceService;

    @Resource
    private DeviceOrderService deviceOrderService;

    @Resource
    private UserService userService;

    @ApiOperation("新建项目")
    @PostMapping("/addType")
    public BaseResponse<Boolean> addDeviceType(String typeName) {
        DeviceType deviceType = new DeviceType();
        deviceType.setTypeName(typeName);
        boolean save = deviceTypeService.save(deviceType);
        ThrowUtils.throwIf(!save, new BusinessException(ErrorCode.SYSTEM_ERROR, "分类添加失败"));
        return ResultUtils.success(true);
    }

    @ApiOperation("更新单个设备分类")
    @PostMapping("/updateSingleDeviceType")
    public BaseResponse<Boolean> updateSingleDeviceType(@RequestBody DeviceType deviceType) {
        DeviceType deviceTypeCheck = deviceTypeService.getById(deviceType.getDeviceTypeId());
        ThrowUtils.throwIf(deviceTypeCheck == null, new BusinessException(ErrorCode.NOT_FOUND_ERROR));
        boolean update = deviceTypeService.updateById(deviceType);
        ThrowUtils.throwIf(!update, new BusinessException(ErrorCode.SYSTEM_ERROR, "分类更新失败"));
        return ResultUtils.success(true);
    }

    @ApiOperation("根据设备分类id删除分类")
    @PostMapping("/deleteDeviceTypeById")
    public BaseResponse<Boolean> deleteDeviceTypeById(Long id) {
        DeviceType deviceTypeCheck = deviceTypeService.getById(id);
        ThrowUtils.throwIf(deviceTypeCheck == null, new BusinessException(ErrorCode.NOT_FOUND_ERROR));
        boolean remove = deviceTypeService.removeById(id);
        ThrowUtils.throwIf(!remove, new BusinessException(ErrorCode.SYSTEM_ERROR, "分类删除失败"));
        return ResultUtils.success(true);
    }

    @ApiOperation("获取设备分类列表")
    @PostMapping("/getDeviceTypeList")
    public BaseResponse<List<DeviceType>> getDeviceTypeList() {
        List<DeviceType> deviceTypeList = deviceTypeService.list();
        return ResultUtils.success(deviceTypeList);
    }

    @ApiOperation("获取设备列表")
    @GetMapping("/getDeviceList")
    public BaseResponse<List<DeviceVO>> getDeviceVOList() {
        List<Device> deviceList = deviceService.list();
        ArrayList<DeviceVO> deviceVOList = new ArrayList<>();
        for (Device device : deviceList) {
            Lab lab = labService.getById(device.getLabId());
            DeviceType deviceType = deviceTypeService.getById(device.getDeviceTypeId());
            deviceVOList.add(DeviceVO.parseDeviceVO(device, deviceType, lab));
        }
        return ResultUtils.success(deviceVOList);
    }

    @ApiOperation("获取正在使用中设备列表")
    @GetMapping("/getDeviceUsedList")
    public BaseResponse<List<DeviceUsedVO>> getDeviceUsedList() {
        QueryWrapper<Device> deviceQueryWrapper = new QueryWrapper<>();
        deviceQueryWrapper.eq("is_in_use", 1);
        List<Device> deviceList = deviceService.list(deviceQueryWrapper);
        ArrayList<DeviceUsedVO> deviceUsedVOList = new ArrayList<>();
        for (Device device : deviceList) {
            QueryWrapper<DeviceOrder> deviceOrderQueryWrapper = new QueryWrapper<>();
            deviceOrderQueryWrapper.eq("order_state", 3)
                    .eq("device_id", device.getDeviceId());
            DeviceOrder deviceOrder = deviceOrderService.getOne(deviceOrderQueryWrapper);
            User user = userService.getById(deviceOrder.getUserId());
            DeviceUsedVO deviceUsedVO = new DeviceUsedVO();
            deviceUsedVO.setDeviceId(device.getDeviceId());
            deviceUsedVO.setDeviceName(device.getDeviceName());
            deviceUsedVO.setUserVO(UserVO.parseUserVO(user));
            deviceUsedVO.setStartTime(deviceOrder.getStartTime());
            deviceUsedVO.setEndTime(deviceOrder.getEndTime());
            deviceUsedVOList.add(deviceUsedVO);
        }
        return ResultUtils.success(deviceUsedVOList);
    }

    @ApiOperation("设备使用说明书上传")
    @PostMapping("/uploadPDF")
    public BaseResponse<String> uploadPDF(@RequestParam("pdf") MultipartFile multipartFile) {
        String fileType = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
        if (!fileType.equals("pdf")) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件类型错误，请上传pdf文件");
        }
        long maxSize = 5 * 1024 * 1024;
        if (multipartFile.getSize() > maxSize) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件上传大小不能超过5M");
        }
        UUID uuid = UUID.randomUUID();
        boolean upload = FileUtils.uploadToServer(multipartFile, fileConfigProperties.getUploadPath(), uuid.toString() + ".pdf");
        ThrowUtils.throwIf(!upload, new BusinessException(ErrorCode.SYSTEM_ERROR, "文件上传失败"));
        return ResultUtils.success(uuid.toString());
    }

    @ApiOperation("根据设备id下载设备使用说明书")
    @GetMapping("/downloadPDFById/{deviceId}")
    public void downloadPDFById(@PathVariable Long deviceId , HttpServletResponse response) throws IOException {
        //1 根据设备id查找到设备说明书url
        //2 根据说明书url拿到说明书uuid+.pdf
        //3 找到该文件返回
        Device device = deviceService.getById(deviceId);
        if (device == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "该设备不存在");
        }
        String url = device.getOperationInstructionUrl();
        if (url == null || url.length() < 1) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "该设备暂时没有使用说明书，请联系管理员");
        }
        String uuid = url.substring(url.lastIndexOf("/") + 1);
        String filePath = fileConfigProperties.getPdfPath() + uuid + ".pdf";
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            response.setContentType("image/jpeg/png/pdf");
            String fileName = device.getDeviceName() + "-设备使用说明书" + ".pdf";
            String encodedFilename = StringUtils.cleanPath(URLEncoder.encode(fileName, "UTF-8"));
            response.setHeader("Content-Disposition", "inline; filename=" + encodedFilename);
            org.springframework.core.io.Resource resource = new UrlResource(path.toUri());
            byte[] data = Files.readAllBytes(path);
            response.getOutputStream().write(data);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    @ApiOperation("根据设备id删除设备信息")
    @GetMapping("/deleteDeviceById")
    public BaseResponse<Boolean> deleteDeviceById(Long deviceId) {
        Device device = deviceService.getById(deviceId);
        if (device == null) {
            return ResultUtils.success(true);
        }
        String url = device.getOperationInstructionUrl();
        if (url != null) {
            String uuid = url.substring(url.lastIndexOf("/") + 1);
            String filePath = fileConfigProperties.getPdfPath() + uuid + ".pdf";
            Path path = Paths.get(filePath);
            try {
                Files.delete(path);
                log.info("文件删除成功：" + filePath);
            } catch (IOException e) {
                log.error("pdf文件删除失败：" + filePath);
                e.printStackTrace();
            }
        }
        boolean remove = deviceService.removeById(deviceId);
        ThrowUtils.throwIf(!remove, new BusinessException(ErrorCode.SYSTEM_ERROR, "设备删除失败"));
        return ResultUtils.success(true);
    }

    @ApiOperation("添加设备")
    @PostMapping("/addDevice")
    public BaseResponse<Boolean> addDevice(@RequestBody AddDeviceRequest addDeviceRequest) {
        Device device = new Device();
        device.setDeviceTypeId(addDeviceRequest.getDeviceTypeId());
        device.setDeviceName(addDeviceRequest.getDeviceName());
        device.setLabId(addDeviceRequest.getLabId());
        device.setDeviceFunc(addDeviceRequest.getDeviceFunc());
        device.setOperationInstructionUrl(fileConfigProperties.getBaseUrl() + addDeviceRequest.getUuid());
        try {
            Path sourcePath = Paths.get(fileConfigProperties.getUploadPath() + addDeviceRequest.getUuid() + ".pdf");
            boolean exists = Files.exists(sourcePath);
            ThrowUtils.throwIf(!exists, new BusinessException(ErrorCode.NOT_FOUND_ERROR));
            Path targetPath = Paths.get(fileConfigProperties.getPdfPath() + addDeviceRequest.getUuid() + ".pdf");
            Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            log.info("pdf文件转移成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "上传pdf文件找不到，请重新上传");
        }
        boolean save = deviceService.save(device);
        ThrowUtils.throwIf(!save, new BusinessException(ErrorCode.SYSTEM_ERROR, "设备添加失败"));
        return ResultUtils.success(true);
    }

    @ApiOperation("更新设备信息")
    @PostMapping("/updateDevice")
    public BaseResponse<Boolean> updateDevice(@RequestBody UpdateDeviceRequest updateDeviceRequest) {
        Long deviceId = updateDeviceRequest.getDeviceId();
        Device device = deviceService.getById(deviceId);
        ThrowUtils.throwIf(device == null, new BusinessException(ErrorCode.PARAMS_ERROR, "该设备不存在"));
        UpdateWrapper<Device> deviceUpdateWrapper = new UpdateWrapper<>();
        //查找pdf文件是否存在(1 temp文件夹，2 pdf 文件夹)
        //1 判断是否更新说明书
        //  判断fileConfigProperties.getBaseUrl() + uuid 是否等于 device.getOperationInstructionUrl()
        //  相等，代表没有更新说明书，
        //  不相等，代表更新了说明书
        //   (1) 根据原url删除原pdf
        //   (2) 将新的pdf从temp文件夹转移到pdf文件夹
        //   (3) 修改该设备说明书url
        if (!device.getOperationInstructionUrl().equals(fileConfigProperties.getBaseUrl() + updateDeviceRequest.getUuid())) {
            String uuidOld = device.getOperationInstructionUrl().substring(device.getOperationInstructionUrl().lastIndexOf("/") + 1);
            Path pdfOldPath = Paths.get(fileConfigProperties.getPdfPath() + uuidOld + ".pdf");
            if (Files.exists(pdfOldPath)) {
                // 删除原pdf
                try {
                    Files.delete(pdfOldPath);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR, "pdf文件删除失败");
                }
            }
            // 添加新pdf
            Path pdfNewSourcePath = Paths.get(fileConfigProperties.getUploadPath() + updateDeviceRequest.getUuid() + ".pdf");
            Path pdfNewTargetPath = Paths.get(fileConfigProperties.getPdfPath() + updateDeviceRequest.getUuid() + ".pdf");
            try {
                Files.move(pdfNewSourcePath, pdfNewTargetPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "新的pdf文件转移失败");
            }
            //修改url
            deviceUpdateWrapper.set("operation_instruction_url", fileConfigProperties.getBaseUrl() + updateDeviceRequest.getUuid());
        }
        deviceUpdateWrapper.eq("device_id", updateDeviceRequest.getDeviceId())
                .set("device_type_id", updateDeviceRequest.getDeviceTypeId())
                .set("device_name", updateDeviceRequest.getDeviceName())
                .set("lab_id", updateDeviceRequest.getLabId())
                .set("device_func", updateDeviceRequest.getDeviceFunc());
        boolean update = deviceService.update(deviceUpdateWrapper);
        ThrowUtils.throwIf(!update, new BusinessException(ErrorCode.SYSTEM_ERROR, "设备信息更新失败"));
        return ResultUtils.success(true);
    }

    @ApiOperation("设备维护登记")
    @PostMapping("/addDeviceMaintenance")
    public BaseResponse<Boolean> addDeviceMaintenance(@RequestBody DeviceMaintenance deviceMaintenance) {
        boolean save = deviceMaintenanceService.save(deviceMaintenance);
        ThrowUtils.throwIf(!save, new BusinessException(ErrorCode.SYSTEM_ERROR, "设备维护信息登记失败"));
        return ResultUtils.success(true);
    }

    @ApiOperation("设备维护信息修改")
    @PostMapping("/updateDeviceMaintenance")
    public BaseResponse<Boolean> updateDeviceMaintenance(@RequestBody DeviceMaintenance deviceMaintenance) {
        boolean update = deviceMaintenanceService.updateById(deviceMaintenance);
        ThrowUtils.throwIf(!update, new BusinessException(ErrorCode.SYSTEM_ERROR, "设备维护信息更新失败"));
        return ResultUtils.success(true);
    }

    @ApiOperation("根据设备维护id删除设备维护记录")
    @PostMapping("/deleteDeviceMaintenanceById")
    public BaseResponse<Boolean> deleteDeviceMaintenanceById(Long deviceMaintenanceId) {
        boolean remove = deviceMaintenanceService.removeById(deviceMaintenanceId);
        ThrowUtils.throwIf(!remove, new BusinessException(ErrorCode.SYSTEM_ERROR, "设备维护记录删除失败"));
        return ResultUtils.success(true);
    }

    @ApiOperation("获取设备维护记录列表(根据开始时间倒序)")
    @GetMapping("/getDeviceMaintenanceList")
    public BaseResponse<List<DeviceMaintenanceDTO>> getDeviceMaintenanceList() {
        QueryWrapper<DeviceMaintenance> deviceMaintenanceQueryWrapper = new QueryWrapper<>();
        deviceMaintenanceQueryWrapper.orderByDesc("start_time");
        List<DeviceMaintenance> deviceMaintenanceList = deviceMaintenanceService.list();
        List<DeviceMaintenanceDTO> deviceMaintenanceDTOList = new ArrayList<>();
        for (DeviceMaintenance deviceMaintenance : deviceMaintenanceList) {
            Device device = deviceService.getById(deviceMaintenance.getDeviceId());
            deviceMaintenanceDTOList.add(DeviceMaintenanceDTO.parseDeviceMaintenanceDTO(deviceMaintenance, device.getDeviceName()));
        }
        return ResultUtils.success(deviceMaintenanceDTOList);
    }
}
