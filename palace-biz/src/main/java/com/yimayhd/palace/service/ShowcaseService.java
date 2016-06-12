package com.yimayhd.palace.service;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.dto.TagInfoPageDTO;
import com.yimayhd.commentcenter.client.dto.TagRelationDomainDTO;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.ic.client.model.domain.item.ItemInfo;
import com.yimayhd.ic.client.model.param.item.ItemOptionDTO;
import com.yimayhd.ic.client.model.param.item.ItemQryDTO;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.convert.ShowCaseItem;
import com.yimayhd.palace.model.ItemVO;
import com.yimayhd.palace.model.Region;
import com.yimayhd.palace.model.query.CommodityListQuery;
import com.yimayhd.palace.model.vo.booth.ShowcaseVO;
import com.yimayhd.palace.result.BizResult;
import com.yimayhd.resourcecenter.domain.BoothDO;
import com.yimayhd.resourcecenter.domain.OperationDO;
import com.yimayhd.resourcecenter.domain.RegionDO;
import com.yimayhd.resourcecenter.model.enums.RegionType;
import com.yimayhd.resourcecenter.model.enums.ShowcaseStauts;
import com.yimayhd.resourcecenter.model.query.OperationQuery;
import com.yimayhd.resourcecenter.model.query.RegionQuery;
import com.yimayhd.resourcecenter.model.query.ShowcaseQuery;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.model.result.ShowCaseResult;
import com.yimayhd.user.client.enums.MerchantOption;
import com.yimayhd.user.client.query.MerchantPageQuery;

import java.util.List;

/**
 * Created by czf on 2016/4/13.
 */
public interface ShowcaseService {
    /**
     *  根据boothId获取showcase列表
     * @param boothId
     * @return
     * @throws Exception
     */
    List<ShowcaseVO> getList(long boothId)throws Exception;

    /**
     * 根据showcaseId获取showcase实体
     * @param id
     * @return
     * @throws Exception
     */
    ShowcaseVO getById(long id)throws Exception;

    /**
     * 新增showcase
     * @param entity
     * @return
     * @throws Exception
     */
    ShowcaseVO add(ShowcaseVO entity)throws Exception;

    /**
     *  修改showcase
     * @param entity
     * @return
     * @throws Exception
     */
    ShowcaseVO saveOrUpdate(ShowcaseVO entity)throws Exception;

    /**
     *  上下架
     * @return
     * @throws Exception
     */
    boolean publish(long id,ShowcaseStauts status)throws Exception;

    /**
     *  根据查询条件查询showcase列表返回page对象
     * @return
     * @throws Exception
     */
    PageVO<ShowCaseResult> getShowcaseResult(ShowcaseQuery showcaseQuery);

    PageVO<OperationDO> getPageOperationDO(OperationQuery operationQuery);

    //目的地
    List<RegionDO> getListdestination(RegionType regionType);

    PageVO<RegionDO> getRegionDOListByType(RegionQuery regionQuery);
    //主题
    PageVO<ComTagDO> getTagListByTagType(TagInfoPageDTO tagInfoPageDTO)throws Exception;


    public List<OperationDO> getAllOperactions() ;

    public BoothDO getBoothInfoByBoothCode(String code) throws Exception;

    //商品列表
    public PageVO<ShowCaseItem> getItemByItemOptionDTO(ItemQryDTO itemQryDTO) throws Exception ;

    //达人 美食
    public PageVO<ShowCaseItem> getMerchants(MerchantPageQuery merchantPageQuery, MerchantOption merchantOption) throws Exception ;
}
