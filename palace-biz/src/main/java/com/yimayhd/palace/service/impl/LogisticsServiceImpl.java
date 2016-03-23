package com.yimayhd.palace.service.impl;

import com.yimayhd.lgcenter.client.domain.ExpressVO;
import com.yimayhd.lgcenter.client.dto.TaskInfoRequestDTO;
import com.yimayhd.lgcenter.client.result.BaseResult;
import com.yimayhd.lgcenter.client.service.LgService;
import com.yimayhd.palace.service.LogisticsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author create by yushengwei on 2016/3/23
 * @Description
 */
public class LogisticsServiceImpl implements LogisticsService {

    @Autowired LgService lgService;

    public List<ExpressVO> getLogisticsInfo(TaskInfoRequestDTO taskInfoRequestDTO) {
        BaseResult<ExpressVO> result = lgService.getLogisticsInfo(taskInfoRequestDTO);
        if(null == result || !result.isSuccess()){
            //log
            return null;
        }
        List<ExpressVO> list = new ArrayList<ExpressVO>();
        list.add(result.getValue());
        return list;
    }
}
