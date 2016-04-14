package com.yimayhd.palace.service.impl;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.base.BaseException;
import com.yimayhd.palace.base.BaseQuery;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.convert.booth.BoothDOConverter;
import com.yimayhd.palace.convert.booth.BoothVOConverter;
import com.yimayhd.palace.model.vo.booth.BoothVO;
import com.yimayhd.palace.service.BoothService;
import com.yimayhd.resourcecenter.domain.BoothDO;
import com.yimayhd.resourcecenter.model.query.BoothQuery;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.model.result.RcResult;
import com.yimayhd.resourcecenter.service.BoothClientServer;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by czf on 2016/4/13.
 */
public class BoothServiceImpl implements BoothService {

    private static final Logger log = LoggerFactory.getLogger(BoothServiceImpl.class);

    @Autowired
    private BoothClientServer boothClientServerRef;
    @Override
    public PageVO<BoothVO> getList(BaseQuery baseQuery) throws Exception {
        BoothQuery boothQuery = new BoothQuery();
        boothQuery.setPageSize(baseQuery.getPageSize());
        boothQuery.setPageNo(baseQuery.getPageNumber());
        RCPageResult<BoothDO> result = boothClientServerRef.getBoothResult(boothQuery);
        if(null == result){
            log.error("boothClientServerRef.getBoothResult result is null and parame: " + JSON.toJSONString(boothQuery));
            throw new BaseException("返回结果错误,新增失败 ");
        } else if(!result.isSuccess()){
            log.error("boothClientServerRef.getBoothResult error:" + JSON.toJSONString(result) + "and parame: " + JSON.toJSONString(boothQuery));
            throw new BaseException(result.getResultMsg());
        }
        List<BoothVO> BoothVOList = new ArrayList<BoothVO>();
        if(CollectionUtils.isNotEmpty(result.getList())){
            for(BoothDO boothDO : result.getList()){
                BoothVOList.add(BoothVOConverter.getBoothVO(boothDO));
            }
        }
        return new PageVO<BoothVO>(baseQuery.getPageNumber(),baseQuery.getPageSize(),result.getTotalCount(),BoothVOList);
    }

    @Override
    public BoothVO add(BoothVO entity) throws Exception {
        RcResult<Long> result = boothClientServerRef.insert(BoothDOConverter.getBoothDO(entity));
        if(result == null){
            log.error("boothClientServerRef.insert result is null and parame: " + JSON.toJSONString(BoothDOConverter.getBoothDO(entity)));
            throw new BaseException("返回结果错误,新增失败 ");
        } else if(!result.isSuccess()){
            log.error("boothClientServerRef.insert error:" + JSON.toJSONString(result) + "and parame: " + JSON.toJSONString(BoothDOConverter.getBoothDO(entity)));
            throw new BaseException(result.getResultMsg());
        }
        entity.setId(result.getT());
        return entity;
    }
}
