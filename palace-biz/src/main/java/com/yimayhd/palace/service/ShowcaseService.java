package com.yimayhd.palace.service;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.dto.CategoryQueryDTO;
import com.yimayhd.commentcenter.client.dto.TagInfoPageDTO;
import com.yimayhd.ic.client.model.param.item.ItemQryDTO;
import com.yimayhd.ic.client.model.query.HotelPageQuery;
import com.yimayhd.ic.client.model.query.ScenicPageQuery;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.convert.ShowCaseItem;
import com.yimayhd.palace.model.vo.booth.ShowcaseVO;
import com.yimayhd.resourcecenter.domain.BoothDO;
import com.yimayhd.resourcecenter.domain.OperationDO;
import com.yimayhd.resourcecenter.domain.RegionDO;
import com.yimayhd.resourcecenter.model.enums.RegionType;
import com.yimayhd.resourcecenter.model.enums.ShowcaseStauts;
import com.yimayhd.resourcecenter.model.query.BoothQuery;
import com.yimayhd.resourcecenter.model.query.OperationQuery;
import com.yimayhd.resourcecenter.model.query.RegionQuery;
import com.yimayhd.resourcecenter.model.query.ShowcaseQuery;
import com.yimayhd.resourcecenter.model.resource.vo.OperactionVO;
import com.yimayhd.resourcecenter.model.result.ShowCaseResult;
import com.yimayhd.snscenter.client.domain.SnsSubjectDO;
import com.yimayhd.snscenter.client.domain.SnsTopicDO;
import com.yimayhd.snscenter.client.dto.SubjectInfoDTO;
import com.yimayhd.snscenter.client.dto.topic.TopicQueryDTO;
import com.yimayhd.snscenter.client.dto.topic.TopicQueryListDTO;
import com.yimayhd.snscenter.client.result.topic.TopicResult;
import com.yimayhd.snscenter.client.result.ugc.UgcResult;
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
    PageVO<ComTagDO> getTagListByTagType(TagInfoPageDTO tagInfoPageDTO);


    public List<OperationDO> getAllOperactions() ;

    public BoothDO getBoothInfoByBoothCode(String code) throws Exception;

    //商品列表
    public PageVO<ShowCaseItem> getItemByItemOptionDTO(ItemQryDTO itemQryDTO) ;

    public PageVO<ShowCaseItem> getHotelList(HotelPageQuery hotelPageQuery) ;

    public PageVO<ShowCaseItem> getScenicList(ScenicPageQuery ccenicPageQuery) ;


    //达人 美食
    public PageVO<ShowCaseItem> getMerchants(MerchantPageQuery merchantPageQuery, MerchantOption merchantOption) ;

    public List<OperactionVO> getAllOperations() ;

    /**
     * 分页查询UGC列表
     * @param subjectInfoDTO 入参
     * @return
     */
    public PageVO<ShowCaseItem> getUgcPageList(SubjectInfoDTO subjectInfoDTO);

    /**
     * 分页查询话题列表
     * @param topicQueryListDTO 入参
     * @return
     */
    public PageVO<ShowCaseItem> getTopicPageList(TopicQueryListDTO topicQueryListDTO);

    /**
     * 查看话题详情
     * @param topicQueryDTO
     * @return
     */
    public SnsTopicDO getTopicDetailInfo(TopicQueryDTO topicQueryDTO);

    /**
     * 查询话题详情
     * @parameter
     * @return
     * @throws
     */
    public SnsSubjectDO getSubjectInfo(SubjectInfoDTO subjectInfoDTO);

    /**
     * 查询booth列表
     * @parameter
     * @return
     * @throws
     */
    public PageVO<ShowCaseItem> getBoothPageList(BoothQuery boothQuery);

    /**
     * 查询booth列表
     * @parameter
     * @return
     * @throws
     */
    public PageVO<ShowCaseItem> getCategoryList(CategoryQueryDTO categoryQueryDTO);

}
