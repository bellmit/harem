package com.yimayhd.palace.service.impl;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.vo.VoucherTemplateVO;
import com.yimayhd.palace.model.vo.booth.ShowcaseVO;
import com.yimayhd.palace.service.ShowcaseService;
import com.yimayhd.resourcecenter.model.query.ShowcaseQuery;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.model.result.ShowCaseResult;
import com.yimayhd.resourcecenter.service.ShowcaseClientServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Administrator on 2016/4/13.
 */
public class ShowcaseServiceImpl implements ShowcaseService {

    private final  Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired  ShowcaseClientServer showcaseClientServer;

    public List<ShowcaseVO> getList(long boothId) throws Exception {

        return null;
    }

    @Override
    public PageVO<ShowCaseResult> getShowcaseResult(ShowcaseQuery showcaseQuery) {
        if(null == showcaseQuery){
            LOGGER.error("getShowcaseResult|showcaseClientServer.getShowcaseResult parameter is null");
            return null;
        }
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
        return null;
    }

    @Override
    public ShowcaseVO add(ShowcaseVO entity) throws Exception {
        return null;
    }

    @Override
    public ShowcaseVO modify(ShowcaseVO entity) throws Exception {
        return null;
    }
}
