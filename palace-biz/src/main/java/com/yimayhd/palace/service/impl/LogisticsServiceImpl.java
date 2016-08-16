package com.yimayhd.palace.service.impl;

import com.alibaba.fastjson.JSON;
import com.yimayhd.lgcenter.client.domain.ExpressVO;
import com.yimayhd.lgcenter.client.dto.TaskInfoRequestDTO;
import com.yimayhd.lgcenter.client.result.BaseResult;
import com.yimayhd.lgcenter.client.service.LgService;
import com.yimayhd.palace.service.LogisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author create by yushengwei on 2016/3/23
 * @Description
 */
public class LogisticsServiceImpl implements LogisticsService {
    protected Logger log = LoggerFactory.getLogger(getClass());

    @Autowired LgService lgService;

    public ExpressVO getLogisticsInfo(TaskInfoRequestDTO taskInfoRequestDTO) {
        BaseResult<ExpressVO> result = lgService.getLogisticsInfo(taskInfoRequestDTO);
        if(null == result || !result.isSuccess()){
            log.error(">>>LogisticsServiceImpl.getLogisticsInfo result is null or notSuccess,Parameters=" + JSON.toJSONString(taskInfoRequestDTO)+"|||result="+JSON.toJSONString(result));
            return null;
        }
        return result.getValue();
    }
}
