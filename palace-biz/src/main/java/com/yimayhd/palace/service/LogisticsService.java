package com.yimayhd.palace.service;

import com.yimayhd.lgcenter.client.domain.ExpressVO;
import com.yimayhd.lgcenter.client.dto.TaskInfoRequestDTO;
import com.yimayhd.lgcenter.client.result.BaseResult;

import java.util.List;

/**
 * @author create by yushengwei on 2016/3/23
 * @Description
 */
public interface LogisticsService {
   public ExpressVO getLogisticsInfo(TaskInfoRequestDTO taskInfoRequestDTO);


}
