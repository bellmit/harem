package com.yimayhd.palace.biz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.model.ArticleFoodItemVO;
import com.yimayhd.palace.util.DateUtil;
import com.yimayhd.solrsearch.client.constant.HotelConstant;
import com.yimayhd.solrsearch.client.domain.SolrScenicDO;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemFeature;
import com.yimayhd.ic.client.model.result.item.SingleItemQueryResult;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.ic.client.util.PicUrlsUtil;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.convert.ArticleConverter;
import com.yimayhd.palace.model.ArticleConsultServiceItemVO;
import com.yimayhd.palace.model.ArticleProductItemVO;
import com.yimayhd.palace.model.vo.AudioVO;
import com.yimayhd.palace.repo.CommentRepo;
import com.yimayhd.palace.repo.DestinationRepo;
import com.yimayhd.palace.repo.ItemRepo;
import com.yimayhd.palace.repo.MerchantRepo;
import com.yimayhd.palace.repo.SolrsearchRepo;
import com.yimayhd.palace.repo.user.TalentRepo;
import com.yimayhd.palace.result.BizPageResult;
import com.yimayhd.palace.service.ArticleService;
import com.yimayhd.resourcecenter.domain.DestinationDO;
import com.yimayhd.resourcecenter.domain.MediaDO;
import com.yimayhd.resourcecenter.model.enums.DestinationOutType;
import com.yimayhd.resourcecenter.model.enums.DestinationUseType;
import com.yimayhd.resourcecenter.model.query.DestinationQueryDTO;
import com.yimayhd.resourcecenter.model.query.MediaPageQuery;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.model.result.RcResult;
import com.yimayhd.solrsearch.client.base.SolrsearchPageResult;
import com.yimayhd.solrsearch.client.domain.SolrHotelDO;
import com.yimayhd.solrsearch.client.domain.query.SolrsearchDTO;
import com.yimayhd.user.client.domain.MerchantDO;
import com.yimayhd.user.client.dto.TalentDTO;
import com.yimayhd.user.client.dto.UserDTO;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.service.MerchantService;

/**
 * 达人故事
 *
 * @author xiemingna
 */
public class ArticleBiz {

	private static Logger log = LoggerFactory.getLogger("ArticleBiz");
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ItemQueryService itemQueryService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private ItemRepo itemRepo;
    @Autowired
    private MerchantRepo merchantRepo;
    @Autowired
    private TalentRepo talentRepo;
    @Autowired
    private DestinationRepo destinationRepo;
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private SolrsearchRepo solrsearchRepo;
    @Autowired
    private MerchantService userMerchantServiceRef;

    public ArticleProductItemVO getArticleProductItemVO(ItemDO itemDO) {
        if (itemDO == null) {
            return null;
        }
        long sellerId = itemDO.getSellerId();
        BaseResult<MerchantDO> result = merchantRepo.getMerchantBySellerId(sellerId);
        if (result == null || result.getValue() == null) {
            return null;
        }
        MerchantDO merchantDO = result.getValue();
        ArticleProductItemVO articleProductItemVO = ArticleConverter.ItemDOToArticleProductItemVO(itemDO, merchantDO);
        return articleProductItemVO;
    }

    public ItemDO getItemById(long id) {
        SingleItemQueryResult itemResult = itemRepo.querySingleItem(id);
        if (itemResult == null || !itemResult.isSuccess()) {
            return null;
        }
        ItemDO itemDO = itemResult.getItemDO();
        return itemDO;
    }

    /**
     * 查询达人信息
     *
     * @param userId
     * @return
     */
    public UserDTO queryTalentInfo(long userId) {
        BaseResult<TalentDTO> result = talentRepo.queryTalentInfo(userId);
        if (result == null || !result.isSuccess() || result.getValue() == null) {
            return null;
        }
        TalentDTO talentDTO = result.getValue();
        UserDTO userDTO = talentDTO.getUserDTO();
        return userDTO;
    }

    /**
     * 封装咨询服务
     *
     * @param itemDO
     * @return
     */
    public ArticleConsultServiceItemVO getArticleConsultServiceItemVO(ItemDO itemDO) {
        if (itemDO == null) {
            return null;
        }
        ArticleConsultServiceItemVO articleConsultServiceItemVO = new ArticleConsultServiceItemVO();
        if (PicUrlsUtil.getItemMainPics(itemDO) != null) {
            articleConsultServiceItemVO.setServiceHeadPic(PicUrlsUtil.getItemMainPics(itemDO).get(0));
        }
        articleConsultServiceItemVO.setServiceCurrentPrice(itemDO.getPrice());
        articleConsultServiceItemVO.setServiceOriginalPrice(itemDO.getOriginalPrice());
        articleConsultServiceItemVO.setServiceName(itemDO.getTitle());
        List<String> citys = getCityNameList(itemDO);
        articleConsultServiceItemVO.setServiceCity(citys);
        ItemFeature itemFeature = itemDO.getItemFeature();
        if (itemFeature != null) {
            articleConsultServiceItemVO.setConsultTime(itemFeature.getConsultTime());
        }
        return articleConsultServiceItemVO;

    }

    /**
     * 获得城市列表
     *
     * @param itemDO
     * @return
     */
    private List<String> getCityNameList(ItemDO itemDO) {
        if (itemDO == null) {
            return null;
        }
        List<ComTagDO> comTagDOs = commentRepo.getTagsByOutId(itemDO.getId(), TagType.DESTPLACE);
        if (CollectionUtils.isEmpty(comTagDOs)) {
            return null;
        }
        ArrayList<Integer> cityCodeList = new ArrayList<Integer>();
        for (ComTagDO comTagDO : comTagDOs) {
            String code = comTagDO.getName();
            if (StringUtils.isNumeric(code)) {
                int parseInt = Integer.parseInt(code);
                cityCodeList.add(parseInt);
            }
        }
        ArrayList<String> citys = new ArrayList<String>();
        DestinationQueryDTO destinationQueryDTO = new DestinationQueryDTO();
        destinationQueryDTO.setDomain(Constant.DOMAIN_JIUXIU);
        destinationQueryDTO.setCodeList(cityCodeList);
        destinationQueryDTO.setOutType(DestinationOutType.SERVICE.getCode());
        destinationQueryDTO.setUseType(DestinationUseType.APP_SHOW.getCode());
        RcResult<List<DestinationDO>> result = destinationRepo.queryDestinationList(destinationQueryDTO);
        if (result == null || !result.isSuccess() || CollectionUtils.isEmpty(result.getT())) {
            return null;
        }
        List<DestinationDO> destinationDOs = result.getT();
        if (CollectionUtils.isNotEmpty(destinationDOs)) {
            for (DestinationDO destinationDO : destinationDOs) {
                citys.add(destinationDO.getName());
            }
        }
        return citys;
    }

    public SolrHotelDO getSolrHotelDOById(long id) {
        SolrsearchDTO solrsearchDTO = new SolrsearchDTO();
        List<Long> ids = new ArrayList<Long>();
        ids.add(id);
        solrsearchDTO.setIds(ids);
        solrsearchDTO.setDomainId(Constant.DOMAIN_JIUXIU);
        solrsearchDTO.setBeginDay(new Date());
        solrsearchDTO.setEndDay(new Date());
        solrsearchDTO.setPageNo(1);
        solrsearchDTO.setPageSize(1);
        SolrsearchPageResult<SolrHotelDO> result = solrsearchRepo.queryHotelListByPage(solrsearchDTO);
        if (result == null || CollectionUtils.isEmpty(result.getList())) {
            return null;
        }
        List<SolrHotelDO> list = result.getList();
        return list.get(0);
    }
    public SolrScenicDO getSolrScenicDOById(long id) {
        SolrsearchDTO solrsearchDTO = new SolrsearchDTO();
        List<Long> ids = new ArrayList<Long>();
        ids.add(id);
        solrsearchDTO.setIds(ids);
        solrsearchDTO.setDomainId(Constant.DOMAIN_JIUXIU);
        solrsearchDTO.setBeginDay(new Date());
        solrsearchDTO.setEndDay(new Date());
        solrsearchDTO.setPageNo(1);
        solrsearchDTO.setPageSize(1);
        SolrsearchPageResult<SolrScenicDO> result = solrsearchRepo.queryScenicListByPage(solrsearchDTO);
        if (result == null || CollectionUtils.isEmpty(result.getList())) {
            return null;
        }
        List<SolrScenicDO> list = result.getList();
        return list.get(0);
    }
    
    public BizPageResult<AudioVO> queryAudioPageResult(long id) {
    	BizPageResult<AudioVO> pageResult = new BizPageResult<AudioVO>();
    	MediaPageQuery mediaPageQuery = new MediaPageQuery();
//    	mediaPageQuery.setPageNo(1);
//    	mediaPageQuery.setPageSize(1);
//    	mediaPageQuery.setStarteTime(new Date());
//    	mediaPageQuery.setEndTime(new Date());
//    	mediaPageQuery.set
    	RCPageResult<MediaDO> queryAudioPageResult = solrsearchRepo.queryAudioPageResult(mediaPageQuery);
    	if (queryAudioPageResult == null || (queryAudioPageResult != null && CollectionUtils.isEmpty(queryAudioPageResult.getList()))) {
			log.error("solrsearchRepo.queryAudioPageResult param:MediaPageQuery={},result:{}",JSON.toJSONString(mediaPageQuery),JSON.toJSONString(queryAudioPageResult));
			return null;
    	}
    	List<MediaDO> mediaList = queryAudioPageResult.getList();
    	List<AudioVO> audioList = ArticleConverter.convertMedia2Audio(mediaList);
    	pageResult.setList(audioList);
    	pageResult.setPageNo(queryAudioPageResult.getPageNo());
    	pageResult.setPageSize(queryAudioPageResult.getPageSize());
    	pageResult.setSuccess(true);
    	pageResult.setTotalCount(queryAudioPageResult.getTotalCount());
    	return pageResult;
    }

    /**
     * h5 获取美食vo
     * @param id
     * @return
     */
    public ArticleFoodItemVO getArticleFoodItemVO(long id) {
        if (id <= 0) {
            return null;
        }
        BaseResult<MerchantDO> merchant = userMerchantServiceRef.getMerchantById(id);
        if (merchant == null || merchant.getValue() == null) {
            return null;
        }
        MerchantDO merchantDO = merchant.getValue();
        ArticleFoodItemVO articleFoodItemVO = ArticleConverter.merchantDO2ArticleFoodItemVO(merchantDO);
        return articleFoodItemVO;
    }
}

