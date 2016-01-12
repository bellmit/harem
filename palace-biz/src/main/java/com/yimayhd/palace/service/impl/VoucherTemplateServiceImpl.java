package com.yimayhd.palace.service.impl;

import com.google.common.collect.Lists;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.query.VoucherListQuery;
import com.yimayhd.palace.model.vo.VoucherTemplateVO;
import com.yimayhd.palace.service.VoucherTemplateService;
import com.yimayhd.voucher.client.domain.VoucherTemplateDO;
import com.yimayhd.voucher.client.query.VoucherTemplateQuery;
import com.yimayhd.voucher.client.result.BasePageResult;
import com.yimayhd.voucher.client.service.VoucherClientService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by czf on 2016/1/11.
 */
public class VoucherTemplateServiceImpl implements VoucherTemplateService {

    @Autowired
    private VoucherClientService voucherClientServiceRef;

    @Override
    public PageVO<VoucherTemplateVO> getList(VoucherListQuery voucherListQuery) throws Exception {
        VoucherTemplateQuery voucherTemplateQuery = new VoucherTemplateQuery();
        voucherTemplateQuery.setStartTime(voucherListQuery.getBeginDate());
        voucherTemplateQuery.setEndTime(voucherListQuery.getEndDate());
        voucherTemplateQuery.setTitle(voucherListQuery.getTitle());

        BasePageResult<VoucherTemplateDO> result = voucherClientServiceRef.queryVoucherTemplates(voucherTemplateQuery);
        List<VoucherTemplateVO> voucherTemplateVOs = Lists.newArrayList();
        PageVO<VoucherTemplateVO> pageVO = null;
        if (result != null && result.isSuccess() && !CollectionUtils.isEmpty(result.getList())){
            for (VoucherTemplateDO voucherTemplateDO : result.getList()) {
                VoucherTemplateVO voucherVO = new VoucherTemplateVO();
                BeanUtils.copyProperties(voucherTemplateDO, voucherVO);
                voucherTemplateVOs.add(voucherVO);
            }
            pageVO = new PageVO<VoucherTemplateVO>(voucherListQuery.getPageNumber(), voucherListQuery.getPageSize(),
                                                   result.getTotalCount(), voucherTemplateVOs);
        }else{
            pageVO = new PageVO<VoucherTemplateVO>(voucherListQuery.getPageNumber(), voucherListQuery.getPageSize(),
                                                   0, voucherTemplateVOs);
        }

        return pageVO;
    }

    @Override
    public void modify(VoucherTemplateVO entity) throws Exception {

    }

    @Override
    public VoucherTemplateVO add(VoucherTemplateVO entity) throws Exception {
        return null;
    }

    @Override
    public VoucherTemplateVO getById(long id) throws Exception {
        return null;
    }
}
