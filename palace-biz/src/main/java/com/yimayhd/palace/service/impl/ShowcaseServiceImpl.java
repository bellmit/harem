package com.yimayhd.palace.service.impl;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.error.PalaceReturnCode;
import com.yimayhd.palace.model.vo.VoucherTemplateVO;
import com.yimayhd.palace.model.vo.booth.ShowcaseVO;
import com.yimayhd.palace.result.BizResult;
import com.yimayhd.palace.result.BizResultSupport;
import com.yimayhd.palace.service.RegionService;
import com.yimayhd.palace.service.ShowcaseService;
import com.yimayhd.resourcecenter.domain.OperationDO;
import com.yimayhd.resourcecenter.domain.RegionDO;
import com.yimayhd.resourcecenter.domain.ShowcaseDO;
import com.yimayhd.resourcecenter.model.enums.RegionType;
import com.yimayhd.resourcecenter.model.enums.ShowcaseStauts;
import com.yimayhd.resourcecenter.model.param.ShowCaseDTO;
import com.yimayhd.resourcecenter.model.query.OperationQuery;
import com.yimayhd.resourcecenter.model.query.ShowcaseQuery;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.model.result.RcResult;
import com.yimayhd.resourcecenter.model.result.ShowCaseResult;
import com.yimayhd.resourcecenter.service.OperationClientServer;
import com.yimayhd.resourcecenter.service.RegionClientService;
import com.yimayhd.resourcecenter.service.ShowcaseClientServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Administrator on 2016/4/13.
 */
public class ShowcaseServiceImpl implements ShowcaseService {

    private final  Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired  ShowcaseClientServer showcaseClientServer;

    @Autowired OperationClientServer operationClientServer;

    @Autowired RegionClientService regionClientService;
    public List<ShowcaseVO> getList(long boothId) throws Exception {

        return null;
    }

    @Override
    public PageVO<ShowCaseResult> getShowcaseResult(ShowcaseQuery showcaseQuery) {
        if(null == showcaseQuery){
            LOGGER.error("getShowcaseResult|showcaseClientServer.getShowcaseResult parameter is null");
            return null;
        }
        showcaseQuery.setNeedCount(true);
        RCPageResult<ShowCaseResult> result = showcaseClientServer.getShowcaseResult(showcaseQuery);
        if(null == result || !result.isSuccess()){
            LOGGER.error("getShowcaseResult|showcaseClientServer.getShowcaseResult result is " + JSON.toJSONString(result) +",parameter is "+JSON.toJSONString(showcaseQuery));
            return null;
        }
        PageVO page  = new PageVO<ShowCaseResult>(showcaseQuery.getPageNo(), showcaseQuery.getPageSize(),result.getTotalCount(), result.getList());
        return page;
    }

    @Override
    public ShowcaseVO getById(long id) throws Exception {
        ShowCaseDTO sc = new ShowCaseDTO();
        sc.setShowcaseId(id);
        RcResult<ShowCaseResult> result = showcaseClientServer.getShowcaseResult(sc);
        if(null == result || !result.isSuccess() || null == result.getT().getShowcaseDO()){
            LOGGER.error("getById|showcaseClientServer.getShowcaseResult result is " + JSON.toJSONString(result) +",parameter is "+id);
            return null;
        }
        //TODO:父类不能转子类，这里用BeanUtils.copyProperties，可改进
        ShowcaseDO sdo = result.getT().getShowcaseDO();
        ShowcaseVO svo = new ShowcaseVO();
        BeanUtils.copyProperties(sdo, svo);
        return svo;
    }

    @Override
    public ShowcaseVO add(ShowcaseVO entity) throws Exception {
        RcResult<Boolean> result = showcaseClientServer.insert(entity);
        if(null == result || !result.isSuccess()){
            LOGGER.error("add|showcaseClientServer.update result is " + JSON.toJSONString(result) +",parameter is "+JSON.toJSONString(entity));
            return null;
        }
        return entity;
    }

    @Override
    public ShowcaseVO saveOrUpdate(ShowcaseVO entity) throws Exception {
        BizResult<ShowcaseVO> result = new BizResult<ShowcaseVO>() ;
        ShowcaseVO sv = getById(entity.getId());
        if(null == sv ){
            return add(entity);
        }
        RcResult<Boolean> rcResult = showcaseClientServer.update(entity);
        if(null == rcResult || !rcResult.isSuccess()){
            LOGGER.error("saveOrUpdate|showcaseClientServer.update result is " + JSON.toJSONString(rcResult) +",parameter is "+JSON.toJSONString(entity));
           return null;
        }
        return entity;
    }

    @Override
    public boolean publish(long id, ShowcaseStauts status) throws Exception {
        ShowcaseVO sv = getById(id);
        if(null == sv){
            return false;
        }
        sv.setStatus(status.getStatus());
        if(null == saveOrUpdate(sv)){
            return false;
        }
        return true;
    }

    public PageVO<OperationDO> getPageOperationDO(OperationQuery operationQuery) {
        operationQuery.setNeedCount(true);
        RCPageResult<OperationDO> result = operationClientServer.getOperationResult(operationQuery);
        if(null == result || !result.isSuccess()){
            LOGGER.error("getListOperationDO|showcaseClientServer.getOperationResult result is "
                    + JSON.toJSONString(result) +",parameter is "+JSON.toJSONString(operationQuery));
            return null;
        }
        PageVO page  = new PageVO<OperationDO>(operationQuery.getPageNo(), operationQuery.getPageSize(),
                result.getTotalCount(), result.getList());
        return page;
    }

    public List<OperationDO> getListOperationDO(OperationQuery operationQuery) {
        //TODO:后期加查询list的方法
        operationQuery.setPageNo(1);
        operationQuery.setPageSize(10000);
        return getPageOperationDO(operationQuery)==null?null:getPageOperationDO(operationQuery).getItemList();
    }

    @Override
    public List<RegionDO> getListdestination(RegionType regionType) {
        return regionClientService.getAllRegionListByType(regionType.getType());
    }

    @Override
    public List<OperationDO> getListtheme(OperationQuery operationQuery) {
        return null;
    }
}
