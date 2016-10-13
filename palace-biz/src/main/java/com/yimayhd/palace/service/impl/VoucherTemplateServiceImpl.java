package com.yimayhd.palace.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.constant.B2CConstant;
import com.yimayhd.palace.model.query.VoucherListQuery;
import com.yimayhd.palace.model.vo.VoucherTemplateVO;
import com.yimayhd.palace.repo.ItemRepo;
import com.yimayhd.palace.service.VoucherTemplateService;
import com.yimayhd.palace.util.DateUtil;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.service.UserService;
import com.yimayhd.user.session.manager.SessionManager;
import com.yimayhd.voucher.client.domain.VoucherDO;
import com.yimayhd.voucher.client.domain.VoucherTemplateDO;
import com.yimayhd.voucher.client.enums.EntityType;
import com.yimayhd.voucher.client.enums.IssueType;
import com.yimayhd.voucher.client.enums.VoucherType;
import com.yimayhd.voucher.client.param.VoucherDTO;
import com.yimayhd.voucher.client.query.VoucherPageQuery;
import com.yimayhd.voucher.client.query.VoucherTemplateQuery;
import com.yimayhd.voucher.client.result.VcBasePageResult;
import com.yimayhd.voucher.client.result.VcBaseResult;
import com.yimayhd.voucher.client.result.VcResultSupport;
import com.yimayhd.voucher.client.service.VoucherClientService;

import org.apache.commons.collections4.Unmodifiable;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by czf on 2016/1/11.
 */
public class VoucherTemplateServiceImpl implements VoucherTemplateService {

    @Autowired
    private VoucherClientService voucherClientServiceRef;

    @Autowired
    private UserService userService;

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private ItemRepo itemRepo;

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

        voucherTemplateQuery.setVoucherType(voucherListQuery.getVoucherType());
        voucherTemplateQuery.setPageSize(voucherListQuery.getPageSize());
        voucherTemplateQuery.setPageNo(voucherListQuery.getPageNumber());

        voucherTemplateQuery.setDomain(1100);
        voucherTemplateQuery.setNeedCount(true);
        //发卷方式
        if(!StringUtils.isEmpty(voucherListQuery.getIssueType())){
        	voucherTemplateQuery.setIssueType(Integer.parseInt(voucherListQuery.getIssueType()));
        }

        if(!StringUtils.isEmpty(voucherListQuery.getVid())){
            List<Integer> idsList = new ArrayList<Integer>();
            try {
                Integer vid = Integer.parseInt(voucherListQuery.getVid());
                if(vid>0) {
                    idsList.add(vid);
                    voucherTemplateQuery.setIds(idsList);
                } else {
                    // id为0的情况
                    idsList.add(1);
                    voucherTemplateQuery.setIds(idsList);
                }
            } catch (Exception e) {
                // id非数字情况
                idsList.add(1);
                voucherTemplateQuery.setIds(idsList);
            }
        }

        VcBasePageResult<VoucherTemplateDO> result = voucherClientServiceRef.queryVoucherTemplates(voucherTemplateQuery);
        List<VoucherTemplateVO> voucherTemplateVOs = Lists.newArrayList();
        PageVO<VoucherTemplateVO> pageVO = null;
        if (result != null && result.isSuccess() && !CollectionUtils.isEmpty(result.getList())){
            for (VoucherTemplateDO voucherTemplateDO : result.getList()) {
                VoucherTemplateVO voucherVO = new VoucherTemplateVO();
                BeanUtils.copyProperties(voucherTemplateDO, voucherVO);
               // voucherVO.setValue(Math.round(voucherVO.getValue() / 100));
                voucherVO.setValue_((double)voucherVO.getValue() / 100);
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

    public Boolean addFcode(VoucherTemplateVO voucherTemplateVO) throws Exception {
        UserDO userDO = sessionManager.getUser();
        if (userDO != null) {
            voucherTemplateVO.setOperator(userDO.getNickname());
            // 关联创建人iD
            voucherTemplateVO.setUserId(userDO.getId());
        } else {
            new Exception("用户未登陆,请重新登陆");
        }

        // 随机F码
        voucherTemplateVO.setVoucherType(VoucherType.RAND_CODE.getType());
        //关联商品
        voucherTemplateVO.setEntityType(EntityType.ITEM.getType());

        long itemId = voucherTemplateVO.getEntityId();
        if(itemId != 0) {
            Boolean result = itemRepo.changeToFItem(itemId);
            if(!result){
                new Exception("商品编码不存在,请确认后提交!");
            }
        } else {
            new Exception("商品ID不正确,请确认后提交!");
        }
        // 生成方式 预先生成
        voucherTemplateVO.setIssueType(IssueType.PRE_GENERATE_WITH_CODE.getType());

        voucherTemplateVO.setDomain(B2CConstant.GF_DOMAIN);

        voucherTemplateVO.setStartTime(new Date());
        voucherTemplateVO.setEndTime(getEndTime());

        VcBaseResult<Long>  vcBaseResult = voucherClientServiceRef.publishVoucherTemplate(voucherTemplateVO);

        if(!vcBaseResult.isSuccess()){
            new Exception("创建F码活动失败!");
        }
        return true;
    }
    private Date getEndTime() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);
        Date oneyearafter = calendar.getTime();
        return oneyearafter;
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
