package com.gsq.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsq.backend.common.ErrorCode;
import com.gsq.backend.constant.UserConstant;
import com.gsq.backend.exception.BusinessException;
import com.gsq.backend.exception.ThrowUtils;
import com.gsq.backend.model.dto.train.AddTrainRequest;
import com.gsq.backend.model.dto.train.AuditTrainApplicationRequest;
import com.gsq.backend.model.dto.train.UpdateTrainRequest;
import com.gsq.backend.model.entity.Train;
import com.gsq.backend.model.entity.TrainApplication;
import com.gsq.backend.model.entity.User;
import com.gsq.backend.model.enums.AuditStateEnum;
import com.gsq.backend.model.vo.UserVO;
import com.gsq.backend.service.MailService;
import com.gsq.backend.service.TrainApplicationService;
import com.gsq.backend.service.TrainService;
import com.gsq.backend.mapper.TrainMapper;
import com.gsq.backend.service.UserService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author 86178
 * @description 针对表【train(培训表)】的数据库操作Service实现
 * @createDate 2023-07-31 10:03:02
 */
@Service
public class TrainServiceImpl extends ServiceImpl<TrainMapper, Train>
        implements TrainService {

    @Resource
    private TrainApplicationService trainApplicationService;

    @Resource
    private MailService mailService;

    @Resource
    private UserService userService;

    @Override
    public Boolean addProjectTrain(AddTrainRequest addTrainRequest) {
        Train train = new Train();
        try {
            BeanUtils.copyProperties(addTrainRequest, train);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "属性拷贝失败");
        }
        return save(train);
    }

    @Override
    public Boolean delProjectTrainById(Long trainId) {
        return removeById(trainId);
    }

    @Override
    public Boolean updateProjectTrain(UpdateTrainRequest updateTrainRequest) {
        Train exists = getById(updateTrainRequest.getTrainId());
        if (exists == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        Train train = new Train();
        try {
            BeanUtils.copyProperties(updateTrainRequest, train);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "属性拷贝失败");
        }
        return updateById(train);
    }

    @Override
    public List<Train> getTrainList() {
        return list();
    }

    @Override
    public Boolean applyTrainByProjectName(String projectName) {
        // 1. 根据项目名获取培训项目id
        Train train = query().eq("train_name", projectName).one();
        Long trainId = train.getTrainId();
        // 2. 从ThreadLocal中获取用户id
        UserVO userVO = UserConstant.USER_LOGIN.get();
        Long userId = userVO.getUserId();
        // 3. 向数据库保存申请
        TrainApplication trainApplication = new TrainApplication();
        trainApplication.setTrainId(trainId);
        trainApplication.setUserId(userId);
        boolean save = trainApplicationService.save(trainApplication);
        ThrowUtils.throwIf(!save, ErrorCode.SYSTEM_ERROR, "保存申请失败，请重新申请");
        // 4. 给管理员发邮件告知管理员有新的培训申请，请尽快审核
        // todo 这里管理员邮箱暂时写死
        mailService.sendTextMail("914135534@qq.com", "培训申请", "有新的培训申请，请尽快审核");
        return true;
    }

    @Override
    public Boolean auditTrainApplication(AuditTrainApplicationRequest auditTrainApplicationRequest) {
        Long trainApplicationId = auditTrainApplicationRequest.getTrainApplicationId();
        Boolean result = auditTrainApplicationRequest.getResult();
        String rejectReason = auditTrainApplicationRequest.getRejectReason();
        // 1. 审核通过，根据id修改审核状态
        // 2. 审核不通过，根据id修改审核状态，更新不通过原因，根据申请人id查找申请人邮箱，向申请人发送未通过邮件
        TrainApplication trainApplication = trainApplicationService.getById(trainApplicationId);
        ThrowUtils.throwIf(trainApplication == null, ErrorCode.NOT_FOUND_ERROR);
        UpdateWrapper<TrainApplication> trainApplicationUpdateWrapper = new UpdateWrapper<>();
        trainApplicationUpdateWrapper.eq("train_application_id", trainApplicationId);
        if (result) {
            trainApplicationUpdateWrapper.set("state", AuditStateEnum.PASS_AUDIT.getValue());
        } else {
            trainApplicationUpdateWrapper.set("state", AuditStateEnum.REJECT_AUDIT.getValue())
                    .set("reject_reason", rejectReason);
        }
        boolean update = trainApplicationService.update(trainApplicationUpdateWrapper);
        ThrowUtils.throwIf(!update, ErrorCode.SYSTEM_ERROR);
        User user = userService.getById(trainApplication.getUserId());
        String email = user.getEmail();
        if (result) {
            mailService.sendTextMail(email, "申请项目培训审核通过", "您申请的项目培训审核已通过");
        } else {
            mailService.sendTextMail(email, "申请项目培训审核未通过", "您申请的项目培训审核被驳回，原因是：" + rejectReason);
        }
        return true;
    }


}




