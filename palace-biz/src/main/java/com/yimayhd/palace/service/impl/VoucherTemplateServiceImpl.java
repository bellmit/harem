package com.yimayhd.palace.service.impl;

import com.google.common.collect.Lists;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.query.VoucherListQuery;
import com.yimayhd.palace.model.vo.VoucherTemplateVO;
import com.yimayhd.palace.service.VoucherTemplateService;
import com.yimayhd.palace.util.DateUtil;
import com.yimayhd.voucher.client.domain.VoucherDO;
import com.yimayhd.voucher.client.domain.VoucherTemplateDO;
import com.yimayhd.voucher.client.enums.IssueType;
import com.yimayhd.voucher.client.param.VoucherDTO;
import com.yimayhd.voucher.client.query.VoucherPageQuery;
import com.yimayhd.voucher.client.query.VoucherTemplateQuery;
import com.yimayhd.voucher.client.result.VcBasePageResult;
import com.yimayhd.voucher.client.result.VcBaseResult;
import com.yimayhd.voucher.client.result.VcResultSupport;
import com.yimayhd.voucher.client.service.VoucherClientService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.util.CollectionUtils;

import java.util.Date;
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
        if (StringUtils.isNotEmpty(voucherListQuery.getEndDate())){
            Date endDate = DateUtil.formatMaxTimeForDate(voucherListQuery.getEndDate());
            voucherTemplateQuery.setEndTime(DateUtil.date2String(endDate));
        }
        voucherTemplateQuery.setStartTime(voucherListQuery.getBeginDate());
        voucherTemplateQuery.setTitle(voucherListQuery.getTitle());
        voucherTemplateQuery.setStatus(voucherListQuery.getStatus());
        voucherTemplateQuery.setPageSize(voucherListQuery.getPageSize());
        voucherTemplateQuery.setPageNo(voucherListQuery.getPageNumber());
        voucherTemplateQuery.setDomain(1100);
        voucherTemplateQuery.setNeedCount(true);
        //发卷方式
        if(!StringUtils.isEmpty(voucherListQuery.getIssueType())){
        	voucherTemplateQuery.setIssueType(Integer.parseInt(voucherListQuery.getIssueType()));
        }
        

        VcBasePageResult<VoucherTemplateDO> result = voucherClientServiceRef.queryVoucherTemplates(voucherTemplateQuery);
        List<VoucherTemplateVO> voucherTemplateVOs = Lists.newArrayList();
        PageVO<VoucherTemplateVO> pageVO = null;
        if (result != null && result.isSuccess() && !CollectionUtils.isEmpty(result.getList())){
            for (VoucherTemplateDO voucherTemplateDO : result.getList()) {
                VoucherTemplateVO voucherVO = new VoucherTemplateVO();
                BeanUtils.copyProperties(voucherTemplateDO, voucherVO);
                voucherVO.setValue(Math.round(voucherVO.getValue() / 100));
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
        voucherClientServiceRef.updateVoucherTemplate(entity);
    }

    @Override
    public VcBaseResult<Long> add(VoucherTemplateVO entity) throws Exception {
        return voucherClientServiceRef.publishVoucherTemplate(entity);
    }

    @Override
    public VoucherTemplateVO getById(long id) throws Exception {
        VoucherTemplateVO voucherTemplateVO = new VoucherTemplateVO();
        BeanUtils.copyProperties(voucherClientServiceRef.getVoucherTemplateById(id), voucherTemplateVO);
        return voucherTemplateVO;
    }

	@Override
	public VcResultSupport enableVoucher(VoucherDTO voucherDTO) {
		return voucherClientServiceRef.enableVoucher(voucherDTO);
	}

	@Override
	public VcResultSupport disableVoucher(VoucherDTO voucherDTO) {
		return voucherClientServiceRef.disableVoucher(voucherDTO);
	}

	@Override
	public VcBasePageResult<VoucherDO> exportAllVouchers(VoucherPageQuery voucherPageQuery) {
		return voucherClientServiceRef.exportAllVouchers(voucherPageQuery);
	}
}
