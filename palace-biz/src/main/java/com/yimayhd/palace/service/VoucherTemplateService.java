package com.yimayhd.palace.service;

import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.query.VoucherListQuery;
import com.yimayhd.palace.model.vo.VoucherTemplateVO;
import com.yimayhd.voucher.client.domain.VoucherDO;
import com.yimayhd.voucher.client.param.VoucherDTO;
import com.yimayhd.voucher.client.query.VoucherPageQuery;
import com.yimayhd.voucher.client.result.VcBasePageResult;
import com.yimayhd.voucher.client.result.VcBaseResult;
import com.yimayhd.voucher.client.result.VcResultSupport;

/**
 * Created by czf on 2016/1/11.
 */
public interface VoucherTemplateService {
    /**
     * 根据ID查询
     *
     * @param voucherListQuery
     *            查询条件
     * @throws Exception
     */
    PageVO<VoucherTemplateVO> getList(VoucherListQuery voucherListQuery) throws Exception;

    /**
     * 修改优惠券模板
     *
     * @param entity
     *            需要修改的字段和主键
     * @throws Exception
     */
    void modify(VoucherTemplateVO entity) throws Exception;

    /**
     * 添加优惠券模板
     *
     * @return 优惠券模板
     * @param entity
     *            数据实体
     * @throws Exception
     */
    VcBaseResult<Long> add(VoucherTemplateVO entity) throws Exception;
    /**
     * 添加F码
     *
     * @return F码模板
     * @param entity
     *
     * @throws Exception
     */
    Boolean addFcode(VoucherTemplateVO entity) throws Exception;
    /**
     * 根据主键获取优惠券模板
     *
     * @param id
     *            主键long类型的
     * @throws Exception
     */
    VoucherTemplateVO getById(long id) throws Exception;
    /**
     * 上架优惠劵
     * @param voucherDTO
     * @return
     */
    VcResultSupport enableVoucher(VoucherDTO voucherDTO);
    /**
     * 下架优惠劵
     * @param voucherDTO
     * @return
     */
    VcResultSupport disableVoucher(VoucherDTO voucherDTO);
    
    /**
     * 分页导出模版下所有券
     * @param voucherTemplateId
     * @return
     */
    VcBasePageResult<VoucherDO> exportAllVouchers(VoucherPageQuery voucherPageQuery);
}
