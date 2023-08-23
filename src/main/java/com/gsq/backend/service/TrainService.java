package com.gsq.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gsq.backend.model.dto.train.AddTrainRequest;
import com.gsq.backend.model.dto.train.AuditTrainApplicationRequest;
import com.gsq.backend.model.dto.train.UpdateTrainRequest;
import com.gsq.backend.model.entity.Train;

import java.util.List;

/**
* @author 86178
* @description 针对表【train(培训表)】的数据库操作Service
* @createDate 2023-07-31 10:03:02
*/
public interface TrainService extends IService<Train> {
    /**
     * 添加项目培训
     * @param addTrainRequest
     * @return
     */
    Boolean addProjectTrain(AddTrainRequest addTrainRequest);

    /**
     * 根据项目培训id删除项目
     * @param trainId
     * @return
     */
    Boolean delProjectTrainById(Long trainId);

    /**
     * 修改项目培训信息
     * @param updateTrainRequest
     * @return
     */
    Boolean updateProjectTrain(UpdateTrainRequest updateTrainRequest);

    /**
     * 获取项目培训列表
     */
    List<Train> getTrainList();

    /**
     * 根据项目名申请培训
     * @param projectName
     * @return
     */
    Boolean applyTrainByProjectName(String projectName);

    /**
     * 审核培训申请
     * @param auditTrainApplicationRequest
     * @return
     */
    Boolean auditTrainApplication(AuditTrainApplicationRequest auditTrainApplicationRequest);
}
