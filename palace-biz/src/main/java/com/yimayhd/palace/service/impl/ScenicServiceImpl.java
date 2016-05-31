package com.yimayhd.palace.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.dto.ComentEditDTO;
import com.yimayhd.commentcenter.client.enums.PictureText;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.commentcenter.client.result.PicTextResult;
import com.yimayhd.commission.convert.PictureTextConverter;
import com.yimayhd.ic.client.model.domain.PicturesDO;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.domain.TicketDO;
import com.yimayhd.ic.client.model.domain.share_json.NeedKnow;
import com.yimayhd.ic.client.model.domain.share_json.TextItem;
import com.yimayhd.ic.client.model.enums.BaseStatus;
import com.yimayhd.ic.client.model.enums.PictureOutType;
import com.yimayhd.ic.client.model.param.item.PictureUpdateDTO;
import com.yimayhd.ic.client.model.param.item.ScenicAddDTO;
import com.yimayhd.ic.client.model.param.item.ScenicAddNewDTO;
import com.yimayhd.ic.client.model.param.item.ScenicDTO;
import com.yimayhd.ic.client.model.param.item.ScenicUpdateDTO;
import com.yimayhd.ic.client.model.query.PicturesPageQuery;
import com.yimayhd.ic.client.model.query.ScenicPageQuery;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.ic.client.model.result.ICResultSupport;
import com.yimayhd.ic.client.model.result.item.ItemPubResult;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.ic.client.service.item.ResourcePublishService;
import com.yimayhd.ic.client.service.item.ScenicPublishService;
import com.yimayhd.palace.base.BaseException;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.model.PictureVO;
import com.yimayhd.palace.model.ScenicAddVO;
import com.yimayhd.palace.model.ScenicVO;
import com.yimayhd.palace.model.TicketVO;
import com.yimayhd.palace.model.line.pictxt.PictureTextVO;
import com.yimayhd.palace.model.query.ScenicListQuery;
import com.yimayhd.palace.repo.CommentRepo;
import com.yimayhd.palace.repo.PictureTextRepo;
import com.yimayhd.palace.service.ScenicService;
import com.yimayhd.palace.service.TfsService;
import com.yimayhd.palace.util.DateUtil;

/**
 * Created by Administrator on 2015/11/18.
 */
public class ScenicServiceImpl implements ScenicService {
	private final static int PIC_PAGE_SIZE = 500;
	private final static int PIC_PAGE_NO = 1;
	private static final Logger log = LoggerFactory.getLogger(ScenicServiceImpl.class);
	@Autowired
	private ItemQueryService itemQueryService;
	@Autowired
	private ResourcePublishService resourcePublishServiceRef;
	@Autowired
	private TfsService tfsService;
	@Autowired
	private CommentRepo		commentRepo;
	@Autowired
	private PictureTextRepo	pictureTextRepo;
	@Autowired
	private ScenicPublishService scenicPublishService;

	@Override
	public PageVO<ScenicDO> getList(ScenicListQuery scenicListQuery) throws Exception {
		int totalCount = 0;
		ScenicPageQuery pageQuery = new ScenicPageQuery();
		pageQuery.setNeedCount(true);
		if(scenicListQuery.getPageNumber()!=null){
			int pageNumber =scenicListQuery.getPageNumber();
			int pageSize = scenicListQuery.getPageSize();
			pageQuery.setPageNo(pageNumber);
			pageQuery.setPageSize(pageSize);
		}
		//景区名称
		if (!StringUtils.isBlank(scenicListQuery.getTags())) {
			pageQuery.setTags(scenicListQuery.getTags());			
		}
		//景区名称
		if (!StringUtils.isBlank(scenicListQuery.getName())) {
			pageQuery.setName(scenicListQuery.getName());			
		}
		//景区状态
		if (scenicListQuery.getStatus() != null) {			
			pageQuery.setStatus(scenicListQuery.getStatus());
		}
		//区域-省
		if (scenicListQuery.getLocationProvinceId() != null) {		
			pageQuery.setLocationProvinceId(scenicListQuery.getLocationProvinceId());
		}
		//区域-市
		if (scenicListQuery.getLocationCityId() != null) {		
			pageQuery.setLocationCityId(scenicListQuery.getLocationCityId());
		}
		//开始时间
		if (!StringUtils.isBlank(scenicListQuery.getStartTime())) {
			Date startTime = DateUtil.parseDate(scenicListQuery.getStartTime());
			pageQuery.setStartTime(startTime);
		}
		//结束时间
		if (!StringUtils.isBlank(scenicListQuery.getEndTime())) {
			Date endTime = DateUtil.parseDate(scenicListQuery.getEndTime());
			pageQuery.setEndTime(DateUtil.add23Hours(endTime));
		}
		//景区等级
		if (null!=scenicListQuery.getLevel()) {			
			pageQuery.setLevel(scenicListQuery.getLevel() );
		}
		
		if (scenicListQuery.getDomain() != 0) {			
			pageQuery.setDomain(scenicListQuery.getDomain());
		}
		
		List<ScenicDO> itemList = new ArrayList<ScenicDO>();
		
		ICPageResult<ScenicDO> pageResult = itemQueryService.pageQueryScenic(pageQuery);
		if (pageResult != null && pageResult.isSuccess()) {
			totalCount = pageResult.getTotalCount();
			if (CollectionUtils.isNotEmpty(pageResult.getList())) {
				itemList.addAll(pageResult.getList());
			}
		} else {
			log.error("itemQueryService.pageQueryScenic return value is null !returnValue :"
					+ JSON.toJSONString(pageResult));
		}
		return new PageVO<ScenicDO>(pageQuery.getPageNo(), pageQuery.getPageSize(), totalCount, itemList);
	}

	@Override
	public ScenicVO getById(long id) throws Exception {

		ICResult<ScenicDO> scenic = itemQueryService.getScenic(id);
		if(scenic == null){
			log.error("ScenicServiceImpl.getById-itemQueryService.getScenic result is null and parame: " + id);
			throw new BaseException("返回结果为空，获取景区资源失败");
		}else if(!scenic.isSuccess()){
			log.error("ScenicServiceImpl.getById-itemQueryService.getScenic error:" + JSON.toJSONString(scenic) + "and parame: " + id);
			throw new BaseException("返回结果错误，获取景区资源失败，" + scenic.getResultMsg());
		}	
			//ScenicAddNewDTO dto = new ScenicAddNewDTO();
			ScenicDO scenicDO = scenic.getModule();
			NeedKnow needKnow = scenicDO.getNeedKnow();
			String extraInfoUrl = "";
			if (needKnow != null && StringUtils.isNotBlank(needKnow.getExtraInfoUrl())) {
				extraInfoUrl = tfsService.readHtml5(needKnow.getExtraInfoUrl());
				needKnow.setExtraInfoUrl(extraInfoUrl);
			}
			List<String> pictures = scenicDO.getPictures();
			if(pictures !=null && pictures.size()!=0){
				scenicDO.setCoverUrl(StringUtils.join(pictures.toArray(),"|"));
			}
			//获取图片
			PicturesPageQuery picturesPageQuery = new PicturesPageQuery();
			picturesPageQuery.setOutId(id);
			picturesPageQuery.setPageNo(PIC_PAGE_NO);
			picturesPageQuery.setPageSize(PIC_PAGE_SIZE);
			picturesPageQuery.setStatus(BaseStatus.AVAILABLE.getType());
			picturesPageQuery.setOutType(PictureOutType.SCENIC.getValue());
			ICPageResult<PicturesDO> icPageResult = itemQueryService.queryPictures(picturesPageQuery);
			if(icPageResult == null){
				log.error("ScenicServiceImpl.getById-itemQueryService.queryPictures result is null and parame: " + JSON.toJSONString(picturesPageQuery));
				throw new BaseException("返回结果为空，获取景区资源图片失败");
			}else if(!icPageResult.isSuccess()){
				log.error("ScenicServiceImpl.getById-itemQueryService.queryPictures error:" + JSON.toJSONString(icPageResult) + "and parame: " + JSON.toJSONString(picturesPageQuery) + "and id:" + id);
				throw new BaseException("返回结果错误，获取景区资源图片失败，" + icPageResult.getResultMsg());
			}
			List<PicturesDO> picturesDOList = icPageResult.getList();
			List<PictureVO> pictureVOList = new ArrayList<PictureVO>();
			if(CollectionUtils.isNotEmpty(picturesDOList)){
				for(PicturesDO picturesDO : picturesDOList){
					PictureVO pictureVO = new PictureVO();
					pictureVO.setId(picturesDO.getId());
					pictureVO.setName(picturesDO.getName());
					pictureVO.setValue(picturesDO.getPath());
					pictureVO.setIsTop(picturesDO.isTop());
					pictureVOList.add(pictureVO);

				}
			}
			ScenicVO scenicVO = ScenicVO.getScenicVO(scenicDO);
			scenicVO.setPictureList(pictureVOList);
			
			
			//dto.setNeedKnow(needKnow);
			//dto.setScenic(scenicDO);
			return scenicVO;
		
	}
	
	@Override
	public ScenicAddVO getDetailById(long id) throws Exception {
		
		ICResult<ScenicDTO> scenic = itemQueryService.getScenicDetail(id);
		if(scenic == null){
			log.error("ScenicServiceImpl.getById-itemQueryService.getScenic result is null and parame: " + id);
			throw new BaseException("返回结果为空，获取景区资源失败");
		}else if(!scenic.isSuccess()){
			log.error("ScenicServiceImpl.getById-itemQueryService.getScenic error:" + JSON.toJSONString(scenic) + "and parame: " + id);
			throw new BaseException("返回结果错误，获取景区资源失败，" + scenic.getResultMsg());
		}	

		ScenicAddVO scenicAddVO = new ScenicAddVO();
		
		ScenicDTO scenicDTO = scenic.getModule();
		ScenicVO scenicVO = ScenicVO.getScenicVO(scenicDTO.getScenic());
		scenicAddVO.setScenicVO(scenicVO);
		
		List<TicketVO> ticketVOList = ScenicAddVO.transformTicketVOList(scenicDTO.getTicketDOList());
		scenicAddVO.setTicketVOList(ticketVOList);
		// 图文详情
		PicTextResult picTextResult = pictureTextRepo.getPictureText(id, PictureText.ITEM);
		PictureTextVO pictureTextVO = PictureTextConverter.toPictureTextVO(picTextResult);
		scenicAddVO.setPictureText(pictureTextVO);;
		
		return scenicAddVO;
	}

	public boolean enableScenicItem(long id) throws Exception {
		
		ItemPubResult result = resourcePublishServiceRef.enableScenicItem(id);
		if(!result.isSuccess()){
			log.error("enableScenicItem return value is null !returnValue :"
					+ JSON.toJSONString(result));
		}
		return result.isSuccess();
		
	}
	
	public boolean enableScenic(long id) throws Exception {
		
		ItemPubResult result = scenicPublishService.enableScenic(id);
		if(!result.isSuccess()){
			log.error("enableScenic return value is null !returnValue :"
					+ JSON.toJSONString(result));
		}
		return result.isSuccess();
		
	}
	
	
	public boolean disableScenicItem(int id) throws Exception {
		ItemPubResult result = resourcePublishServiceRef.disableScenicItem(id);
		if(!result.isSuccess()){
			log.error("disableScenicItem return value is null !returnValue :"
					+ JSON.toJSONString(result));
		}
		return result.isSuccess();
	}
	
	/*@Override
	public boolean updateStatus(int id, int scenicStatus) throws Exception {
		ArrayList<ScenicDO> scenicDOList = new ArrayList<ScenicDO>();
		ScenicDO scenic = new ScenicDO();
		scenic.setId(id);
		scenic.setStatus(scenicStatus);
		scenicDOList.add(scenic);
		ICResult<ScenicDO> result = resourcePublishServiceRef.updateScenic(scenicDOList);
		if (!result.isSuccess()) {
			log.error("resourcePublishServiceRef.updateScenic return value is null !returnValue :"
					+ JSON.toJSONString(result));
		}
		
		resourcePublishServiceRef.enableScenicItem(arg0)
		
		return result.isSuccess();
	}
*/
/*	@Override
	public boolean batchupdateStatus(ArrayList<Integer> scenicIdList, int scenicStatus) {
		if (!scenicIdList.isEmpty()) {
			ArrayList<ScenicDO> scenicDOList = new ArrayList<ScenicDO>();
			for (Integer id : scenicIdList) {
				ScenicDO scenic = new ScenicDO();
				scenic.setId(id);
				scenic.setStatus(scenicStatus);
				scenicDOList.add(scenic);
			}
			ICResult<ScenicDO> result = resourcePublishServiceRef.updateScenic(scenicDOList);
			if (!result.isSuccess()) {
				log.error("resourcePublishServiceRef.updateScenic return value is null !returnValue :"
						+ JSON.toJSONString(result));
			}
		}
		return false;

	}
*/
	@Override
	public ICResult<ScenicDO> save(ScenicVO scenicVO) throws Exception {

		ICResult<ScenicDO> addScenicNew = null;

		if (0 == scenicVO.getId()) {
			ScenicAddNewDTO addNewDTO = new ScenicAddNewDTO();

			ScenicDO scenicDO = ScenicVO.getScenicDO(scenicVO);
			addNewDTO.setScenic(scenicDO);
			scenicDO.setMemberPrice(scenicDO.getPrice());
			//NeedKnowOb
			List<TextItem> frontNeedKnow = scenicVO.getNeedKnowOb().getFrontNeedKnow();
			List<TextItem> newFrontNeedKnow =new ArrayList<TextItem>();
			if(frontNeedKnow!=null&&!frontNeedKnow.isEmpty()){
				for (int i = 0; i < frontNeedKnow.size(); i++) {
					if(StringUtils.isNotBlank(frontNeedKnow.get(i).getTitle())||StringUtils.isNotBlank(frontNeedKnow.get(i).getContent())){
						newFrontNeedKnow.add(frontNeedKnow.get(i));
					}
				}
				scenicVO.getNeedKnowOb().setFrontNeedKnow(newFrontNeedKnow);
			}
			
			
			addNewDTO.setNeedKnow(scenicVO.getNeedKnowOb());
			scenicDO.setRecommend(scenicVO.getMasterRecommend());
			//购买须知存tfs
			if(org.apache.commons.lang.StringUtils.isNotBlank(addNewDTO.getNeedKnow().getExtraInfoUrl())) {
				addNewDTO.getNeedKnow().setExtraInfoUrl(tfsService.publishHtml5(addNewDTO.getNeedKnow().getExtraInfoUrl()));
			}
			addScenicNew = resourcePublishServiceRef.addScenicNew(addNewDTO);
			if(null == addScenicNew){
				log.error("ScenicServiceImpl.save-ResourcePublishService.addScenicNew result is null and parame: " + JSON.toJSONString(addNewDTO));
				throw new BaseException("修改返回结果为空,修改失败");
			} else if(!addScenicNew.isSuccess()){
				log.error("ScenicServiceImpl.save-ResourcePublishService.addScenicNew error:" + JSON.toJSONString(addScenicNew) + "and parame: " + JSON.toJSONString(addNewDTO) + "and scenicVO:" + JSON.toJSONString(scenicVO));
				throw new BaseException(addScenicNew.getResultMsg());
			}
			
			//图片集insert
			if(org.apache.commons.lang.StringUtils.isNotBlank(scenicVO.getPicListStr())){
				List<PictureVO> pictureVOList = JSON.parseArray(scenicVO.getPicListStr(),PictureVO.class);
				List<PicturesDO> picList = new ArrayList<PicturesDO>();
				for (PictureVO pictureVO:pictureVOList){
					PicturesDO picturesDO = new PicturesDO();
					picturesDO.setPath(pictureVO.getValue());
					picturesDO.setName(pictureVO.getName());
					picturesDO.setOutId(addScenicNew.getModule().getId());
					picturesDO.setOutType(PictureOutType.SCENIC.getValue());
					//TODO picturesDO.setOrderNum(pictureVO.getIndex());
					picturesDO.setIsTop(pictureVO.isTop());
					picList.add(picturesDO);
				}
				ICResult<Boolean> icResult =  resourcePublishServiceRef.addPictures(picList);
				if(null == icResult){
					log.error("ScenicServiceImpl.save-ResourcePublishService.addScenicNew result is null and parame: " + JSON.toJSONString(picList));
					throw new BaseException("景区资源保存成功，图片集保存返回结果为空，保存失败");
				} else if(!icResult.isSuccess()){
					log.error("ScenicServiceImpl.save-ResourcePublishService.addScenicNew error:" + JSON.toJSONString(icResult) + "and parame: " + JSON.toJSONString(picList) + "and scenicVO:" + JSON.toJSONString(scenicVO));
					throw new BaseException("景区资源保存成功，图片集保存失败" + icResult.getResultMsg());
				}
			}
		} else {
			ICResult<ScenicDO> icResult = itemQueryService.getScenic(scenicVO.getId());
			if(null == icResult){
				log.error("ScenicServiceImpl.save-itemQueryService.getScenic result is null and parame: " + scenicVO.getId());
				throw new BaseException("查询结果为空,修改失败 ");
			} else if(!icResult.isSuccess()){
				log.error("ScenicServiceImpl.save-itemQueryService.getScenic error:" + JSON.toJSONString(icResult) + "and parame: " + scenicVO.getId());
				throw new BaseException(icResult.getResultMsg());
			}
			ScenicDO scenicDB = icResult.getModule();
			if(scenicDB == null){
				log.error("ScenicServiceImpl.save-itemQueryService.getScenic result.getModule is null and parame: " + scenicVO.getId());
			}
			ScenicAddNewDTO addNewDTO = new ScenicAddNewDTO();
			ScenicDO scenicDO = ScenicVO.getScenicDO(scenicVO);
			addNewDTO.setScenic(scenicDO);
			scenicDO.setMemberPrice(scenicDO.getPrice());
			//NeedKnowOb
			if(null !=scenicVO.getNeedKnowOb()){
				List<TextItem> frontNeedKnow = scenicVO.getNeedKnowOb().getFrontNeedKnow();
				List<TextItem> newFrontNeedKnow =new ArrayList<TextItem>();
				if(frontNeedKnow!=null&&!frontNeedKnow.isEmpty()){
					for (int i = 0; i < frontNeedKnow.size(); i++) {
						if(StringUtils.isNotBlank(frontNeedKnow.get(i).getTitle())||StringUtils.isNotBlank(frontNeedKnow.get(i).getContent())){
							newFrontNeedKnow.add(frontNeedKnow.get(i));
						}
					}
					scenicVO.getNeedKnowOb().setFrontNeedKnow(newFrontNeedKnow);
				}
				addNewDTO.setNeedKnow(scenicVO.getNeedKnowOb());
				//购买须知存tfs
				if(org.apache.commons.lang.StringUtils.isNotBlank(scenicVO.getNeedKnowOb().getExtraInfoUrl())) {
					addNewDTO.getNeedKnow().setExtraInfoUrl(tfsService.publishHtml5(scenicVO.getNeedKnowOb().getExtraInfoUrl()));
				}
			}else{
				addNewDTO.setNeedKnow(null);
			}
			scenicDO.setRecommend(scenicVO.getMasterRecommend());
			
			//TODO 修改项处理
			addScenicNew = resourcePublishServiceRef.updateScenicNew(addNewDTO);
			if(null == addScenicNew){
				log.error("ScenicServiceImpl.save-ResourcePublishService.updateScenicNew result is null and parame: " + JSON.toJSONString(addNewDTO));
				throw new BaseException("修改返回结果为空,修改失败");
			} else if(!addScenicNew.isSuccess()){
				log.error("ScenicServiceImpl.save-ResourcePublishService.updateScenicNew error:" + JSON.toJSONString(addScenicNew) + "and parame: " + JSON.toJSONString(addNewDTO) + "and scenicVO:" + JSON.toJSONString(scenicVO));
				throw new BaseException(addScenicNew.getResultMsg());
			}
			if(StringUtils.isNotBlank(scenicVO.getPicListStr())){
				scenicVO.setPictureList(JSON.parseArray(scenicVO.getPicListStr(),PictureVO.class));
			if(CollectionUtils.isNotEmpty(scenicVO.getPictureList())) {
				//获取图片
				PicturesPageQuery picturesPageQuery = new PicturesPageQuery();
				picturesPageQuery.setOutId(scenicVO.getId());
				picturesPageQuery.setPageNo(PIC_PAGE_NO);
				picturesPageQuery.setPageSize(PIC_PAGE_SIZE);
				picturesPageQuery.setStatus(BaseStatus.AVAILABLE.getType());
				ICPageResult<PicturesDO> icPageResult = itemQueryService.queryPictures(picturesPageQuery);
				if (icPageResult == null) {
					log.error("ScenicServiceImpl.updateScenic-itemQueryService.queryPictures result is null and parame: " + JSON.toJSONString(picturesPageQuery));
					throw new BaseException("返回结果为空，获取景区资源图片失败");
				} else if (!icPageResult.isSuccess()) {
					log.error("ScenicServiceImpl.updateScenic-itemQueryService.queryPictures error:" + JSON.toJSONString(icPageResult) + "and parame: " + JSON.toJSONString(picturesPageQuery) + "and id:" + scenicVO.getId());
					throw new BaseException("返回结果错误，获取景区资源图片失败，" + icPageResult.getResultMsg());
				}
				List<PicturesDO> picturesDOList = icPageResult.getList();
				//图片集合处理
				PictureUpdateDTO pictureUpdateDTO = new PictureUpdateDTO();
				if(PictureVO.setPictureListPictureUpdateDTO(scenicVO.getId(),PictureOutType.SCENIC,pictureUpdateDTO, picturesDOList,scenicVO.getPictureList()) != null){
					ICResult<Boolean> updatePictrueResult = resourcePublishServiceRef.updatePictures(pictureUpdateDTO);
					if(null == updatePictrueResult){
						log.error("ScenicServiceImpl.updateScenic-ResourcePublishService.updatePictures result is null and parame: " + JSON.toJSONString(pictureUpdateDTO));
						throw new BaseException("景区资源保存成功，图片集保存返回结果为空，保存失败");
					} else if(!updatePictrueResult.isSuccess()){
						log.error("ScenicServiceImpl.updateScenic-ResourcePublishService.updatePictures error:" + JSON.toJSONString(updatePictrueResult) + "and parame: " + JSON.toJSONString(pictureUpdateDTO) + "and scenicVO:" + JSON.toJSONString(scenicVO));
						throw new BaseException("景区资源保存成功，图片集保存失败" + updatePictrueResult.getResultMsg());
					}
				}
			}
			}
			
			
			
			
			
		}
		return addScenicNew;
	}
	
	@Override
	public ICResult<ScenicDO> save(ScenicAddVO scenicAddVO) throws Exception {

		if(scenicAddVO == null){
			return null;
		}
		
		ICResult<ScenicDO> result = new ICResult<ScenicDO>();
		
		ScenicVO scenicVO = scenicAddVO.getScenicVO();
		ScenicDO scenicDO = ScenicVO.getScenicDO(scenicVO);
		scenicDO.setDomain(Constant.DOMAIN_JIUXIU);
		
		List<TicketDO> ticketDOList = ScenicAddVO.transformTicketDOList(scenicAddVO.getTicketListStr());
		
		if (0 == scenicVO.getId()) {
			
			ScenicAddDTO scenicAddDTO = new ScenicAddDTO();
			scenicAddDTO.setScenic(scenicDO);
			scenicAddDTO.setTicketDOList(ticketDOList);
			
			ICResult<ScenicDO> addResult = scenicPublishService.addScenic(scenicAddDTO);
			if(null == addResult){
				log.error("ScenicServiceImpl.save-ScenicPublishService.addScenic result is null and parame: " + JSON.toJSONString(scenicAddDTO));
				throw new BaseException("修改返回结果为空,修改失败");
			} else if(!addResult.isSuccess()){
				log.error("ScenicServiceImpl.save-ScenicPublishService.addScenic error:" + JSON.toJSONString(addResult) + "and parame: " + JSON.toJSONString(scenicAddDTO) + "and scenicAddVO:" + JSON.toJSONString(scenicAddVO));
				throw new BaseException(addResult.getResultMsg());
			}
			
			result = addResult;
		} else {
			ICResult<ScenicDTO> icResult = itemQueryService.getScenicDetail(scenicVO.getId());
			if(null == icResult){
				log.error("ScenicServiceImpl.save-itemQueryService.getScenic result is null and parame: " + scenicVO.getId());
				throw new BaseException("查询结果为空,修改失败 ");
			} else if(!icResult.isSuccess()){
				log.error("ScenicServiceImpl.save-itemQueryService.getScenic error:" + JSON.toJSONString(icResult) + "and parame: " + scenicVO.getId());
				throw new BaseException(icResult.getResultMsg());
			}
			
			ScenicDTO scenicDTO = icResult.getModule();
			if(scenicDTO == null){
				throw new BaseException("查询结果为空,修改失败 ");
			}
			
			ScenicDO scenicDB = icResult.getModule().getScenic();
			if(scenicDB == null){
				log.error("ScenicServiceImpl.save-itemQueryService.getScenic result.getModule is null and parame: " + scenicVO.getId());
				throw new BaseException("查询结果为空,修改失败 ");
			}
			
			ScenicUpdateDTO updateDTO = new ScenicUpdateDTO();
			updateDTO.setId(scenicDO.getId());
			updateDTO.setLocationProvinceId(scenicDO.getLocationProvinceId());
			updateDTO.setLocationCityId(scenicDO.getLocationCityId());
			updateDTO.setLocationTownId(scenicDO.getLocationTownId());
			updateDTO.setLocationText(scenicDO.getLocationText());
			updateDTO.setLocationX(scenicDO.getLocationX());
			updateDTO.setLocationY(scenicDO.getLocationY());
			updateDTO.setTicketDOList(ticketDOList);
			updateDTO.setLevel(scenicDO.getLevel());
			updateDTO.setScenicFeature(scenicDO.getScenicFeature());
			updateDTO.setLogoUrl(scenicDO.getLogoUrl());
			updateDTO.setPictures(scenicDO.getPictures());
			updateDTO.setOpenTime(scenicDO.getOpenTime());
			
			ICResultSupport updateResult = scenicPublishService.updateScenic(updateDTO);
			if(null == updateResult){
				log.error("ScenicServiceImpl.save-ScenicPublishService.updateScenic result is null and parame: " + JSON.toJSONString(updateDTO));
				throw new BaseException("修改返回结果为空,修改失败");
			} else if(!updateResult.isSuccess()){
				log.error("ScenicServiceImpl.save-ScenicPublishService.updateScenic error:" + JSON.toJSONString(updateResult) + "and parame: " + JSON.toJSONString(updateDTO) + "and scenicVO:" + JSON.toJSONString(scenicVO));
				throw new BaseException(updateResult.getResultMsg());
			}
			
			scenicVO.setId(scenicDB.getId());
			result.setSuccess(updateResult.isSuccess());
			result.setResultMsg(updateResult.getResultMsg());
			result.setModule(scenicVO);
		}
		return result;
	}

	@Override
	public boolean batchEnableStatus(ArrayList<Integer> scenicIdList) {
		ItemPubResult result = new ItemPubResult();
		for (Integer id : scenicIdList) {
			result = resourcePublishServiceRef.enableScenicItem(id);
		}
		
		if(!result.isSuccess()){
			log.error("disableScenicItem return value is null !returnValue :"
					+ JSON.toJSONString(result));
		}
		return result.isSuccess();
	}

	@Override
	public boolean batchDisableStatus(ArrayList<Integer> scenicIdList) {
		ItemPubResult result = new ItemPubResult();
		for (Integer id : scenicIdList) {
			result = resourcePublishServiceRef.disableScenicItem(id);
		}
		
		if(!result.isSuccess()){
			log.error("disableScenicItem return value is null !returnValue :"
					+ JSON.toJSONString(result));
		}
		return result.isSuccess();
	}

	@Override
	public List<ComTagDO> getAllLineThemes() {
		try {
			List<ComTagDO> tagsByTagType = commentRepo.getTagsByTagType(TagType.VIEWTAG);
			return tagsByTagType;
		} catch (Exception e) {
			log.error("ScenicService.getAllLineThemes error", e);
		}
		return null;
	}

	@Override
	public void savePictureText(long id, PictureTextVO pictureTextVO)throws Exception {
		ComentEditDTO comentEditDTO = PictureTextConverter.toComentEditDTO(id, PictureText.ITEM, pictureTextVO);
		pictureTextRepo.editPictureText(comentEditDTO);
	}
}
