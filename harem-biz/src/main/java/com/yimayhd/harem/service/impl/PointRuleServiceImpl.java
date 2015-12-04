package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.base.BaseQuery;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.IMallPointRuleVO;
import com.yimayhd.harem.service.PointRuleService;
import com.yimayhd.tradecenter.client.model.param.imall.pointrule.IMallPointRuleDTO;
import com.yimayhd.tradecenter.client.model.query.IMallPointRuleQuery;
import com.yimayhd.tradecenter.client.model.result.TCPageResult;
import com.yimayhd.tradecenter.client.model.result.TCResultDTO;
import com.yimayhd.tradecenter.client.model.result.imall.pointrule.IMallPointRuleResult;
import com.yimayhd.tradecenter.client.service.imall.IMallHaremService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/9.
 */
public class PointRuleServiceImpl implements PointRuleService {
    @Autowired
    private IMallHaremService iMallHaremServiceRef;
    @Override
    public IMallPointRuleResult getSendPointRuleNow() throws Exception {
        IMallPointRuleQuery iMallPointRuleQuery = new IMallPointRuleQuery();
        //TODO 商家ID
        iMallPointRuleQuery.setVendorId(1);
        TCResultDTO<IMallPointRuleResult> tcResultDTO = iMallHaremServiceRef.queryValidRuleBySellerId(iMallPointRuleQuery);
        IMallPointRuleResult iMallPointRuleResult = null;
        if(null != tcResultDTO && tcResultDTO.isSuccess()) {
            iMallPointRuleResult = tcResultDTO.getT();
        }
        return iMallPointRuleResult;

    }

    @Override
    public PageVO<IMallPointRuleResult> getSendPointRuleHistory(BaseQuery baseQuery) throws Exception {

        IMallPointRuleQuery iMallPointRuleQuery = new IMallPointRuleQuery();
        //TODO 商家ID
        iMallPointRuleQuery.setVendorId(1);
        iMallPointRuleQuery.setPageSize(baseQuery.getPageSize());
        iMallPointRuleQuery.setCurrentPage(baseQuery.getPageNumber());
        TCPageResult<IMallPointRuleResult> tcPageResult =  iMallHaremServiceRef.queryRuleRecords(iMallPointRuleQuery);
        PageVO<IMallPointRuleResult> pageVO = null;
        if(null != tcPageResult && tcPageResult.isSuccess()) {
            pageVO = new PageVO<IMallPointRuleResult>(baseQuery.getPageNumber(),baseQuery.getPageSize(),tcPageResult.getTotalCount(),tcPageResult.getList());
        }
        return pageVO;
    }

    @Override
    public boolean add(IMallPointRuleVO iMallPointRuleVO) throws Exception {
        //TODO 商家ID
        IMallPointRuleDTO iMallPointRuleDTO = IMallPointRuleVO.getIMallPointRuleDTO(iMallPointRuleVO);
        iMallPointRuleDTO.setVendorId(1);
        //TODO 元换算分
        TCResultDTO<IMallPointRuleResult> tcResultDTO= iMallHaremServiceRef.addPointRule(iMallPointRuleDTO);
        if(null != tcResultDTO && tcResultDTO.isSuccess()) {
            return true;
        }
        return false;
    }
}
