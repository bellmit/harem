package com.yimayhd.palace.service.impl;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.base.BaseException;
import com.yimayhd.palace.base.BaseQuery;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.convert.booth.BoothDOConverter;
import com.yimayhd.palace.convert.booth.BoothVOConverter;
import com.yimayhd.palace.model.vo.booth.BoothVO;
import com.yimayhd.palace.service.BoothService;
import com.yimayhd.resourcecenter.domain.AppVersionDO;
import com.yimayhd.resourcecenter.domain.BoothDO;
import com.yimayhd.resourcecenter.model.query.AppVersionQuery;
import com.yimayhd.resourcecenter.model.query.BoothQuery;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.model.result.RcResult;
import com.yimayhd.resourcecenter.service.AppVersionClientService;
import com.yimayhd.resourcecenter.service.BoothClientServer;
import org.apache.commons.beanutils.BeanUtils;
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

    @Autowired
    private AppVersionClientService appVersionClientServiceRef;

    @Override
    public PageVO<BoothVO> getList(BaseQuery baseQuery) throws Exception {
        BoothQuery boothQuery = new BoothQuery();
        boothQuery.setPageSize(baseQuery.getPageSize());
        boothQuery.setPageNo(baseQuery.getPageNumber());
        boothQuery.setCode(baseQuery.getBoothCode());
        boothQuery.setName(baseQuery.getBoothName());
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

    public BoothVO saveOrUpdate(BoothVO entity) throws Exception {
        RcResult<Long> result = null;
        long id = entity.getId();
        if(0==id){
            result = boothClientServerRef.insert(BoothDOConverter.getBoothDO(entity));
            if(null == result || !result.isSuccess()){
                log.error("boothClientServerRef.insert result is null and parame: "+JSON.toJSONString(entity)+"|||result=" + JSON.toJSONString(result));
                throw new BaseException("新增操作失败");
            }
            entity.setId(result.getT());
            return entity;
        }else{
            RcResult<BoothDO> resultBoothDO = boothClientServerRef.getBoothById(id);
            if(null == resultBoothDO || !resultBoothDO.isSuccess()){
                log.error("boothClientServerRef.getBoothById result is null and parame: "+id+"|||result=" + JSON.toJSONString(resultBoothDO));
                throw new BaseException("数据错误，根据id查无数据");
            }
            BoothDO boothDO = resultBoothDO.getT();
            boothDO = BoothDOConverter.BoothVOToBoothDO(entity,boothDO);
            RcResult<Boolean> resultUpdate = boothClientServerRef.update(boothDO);
            if(null == resultUpdate || !resultUpdate.isSuccess()){
                log.error("boothClientServerRef.update result is null and parame: "+JSON.toJSONString(boothDO)+"|||result=" + JSON.toJSONString(resultUpdate));
                throw new BaseException("修改操作失败");
            }
            return entity;
        }
    }

    public BoothVO get(long id)throws Exception{
        RcResult<BoothDO>  result = boothClientServerRef.getBoothById(id);
        if(null == result || !result.isSuccess()){
            log.error("boothClientServerRef.getBoothById result is null and parame: "+id+"|||result=" + JSON.toJSONString(result));
            return null;
        }
        BoothDO boothDO = result.getT();
        BoothVO boothVO = new BoothVO();
        BeanUtils.copyProperties(boothVO,boothDO);
        return boothVO;
    }

    public List<AppVersionDO> queryAppVersionList(AppVersionQuery appVersionQuery){
        RcResult<List<AppVersionDO>> result =  appVersionClientServiceRef.queryAppVersionList(appVersionQuery);
        if(result == null || !result.isSuccess()){
            log.error("appVersionClientServiceRef.queryAppVersionList result is null and parame: " + JSON.toJSONString(appVersionQuery) +"|||result="+JSON.toJSONString(result));
            return null;
        }
        return result.getT();
    }

    public PageVO<AppVersionDO> getAppVersionList(AppVersionQuery query)throws Exception{
        query.setNeedCount(true);
        RCPageResult<AppVersionDO> result = appVersionClientServiceRef.getPageAppVersionResult(query);
        if(result == null || !result.isSuccess() ){
            log.error("appVersionClientServiceRef.queryAppVersionList result is null and parame: " + JSON.toJSONString(query) +"|||result="+JSON.toJSONString(result));
            return null;
        }
        if(CollectionUtils.isNotEmpty(result.getList())){
            return new PageVO<AppVersionDO>(query.getPageNo(),query.getPageSize(),result.getTotalCount(),result.getList());
        }
        return new PageVO<AppVersionDO>();
    }

    public AppVersionDO saveOrUpdateAppVersionDO(AppVersionDO entity)throws Exception{
        long id = entity.getId();
        if(0 == id){//add
            //先查code存不存在
            AppVersionQuery query = new AppVersionQuery();
            query.setCode(entity.getCode());
            RcResult<List<AppVersionDO>> queryResult = appVersionClientServiceRef.queryAppVersionList(query);
            if(null == queryResult || !queryResult.isSuccess()){
                throw new Exception("数据异常，新增失败");
            }
            if(CollectionUtils.isNotEmpty(queryResult.getT())){
                throw new Exception("该版本号【"+query.getCode()+"】已经存在");
            }
            RcResult<Integer> integerRcResult = appVersionClientServiceRef.insert(entity);
            if(null == integerRcResult || !integerRcResult.isSuccess() ||integerRcResult.getT() <=0){
                throw new Exception("数据异常，新增失败");
            }
            return entity;
        }
        //update
        RcResult<AppVersionDO>  appVersionDORcResult = appVersionClientServiceRef.selectById(id);
        if(null == appVersionDORcResult || !appVersionDORcResult.isSuccess() || null == appVersionDORcResult.getT() ){
            throw new Exception("数据异常，更新失败");
        }
        AppVersionDO dbAppVersionDO = appVersionDORcResult.getT();
        BeanUtils.copyProperties(entity,dbAppVersionDO);
        RcResult<Integer> integerRcResult = appVersionClientServiceRef.update(dbAppVersionDO);
        if(null == integerRcResult || !integerRcResult.isSuccess() || integerRcResult.getT() <=0 ){
            throw new Exception("数据异常，更新失败");
        }
        return dbAppVersionDO;
    }



}
