package com.yimayhd.gf.repo;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.domain.CategoryDO;
import com.yimayhd.commentcenter.client.domain.CategoryItemRelationDO;
import com.yimayhd.commentcenter.client.dto.CategoryCheckDTO;
import com.yimayhd.commentcenter.client.dto.CategoryQueryDTO;
import com.yimayhd.commentcenter.client.dto.CategoryRelationDTO;
import com.yimayhd.commentcenter.client.dto.CategoryRelationDelDTO;
import com.yimayhd.commentcenter.client.dto.CategoryRelationSaveDTO;
import com.yimayhd.commentcenter.client.dto.CategorySaveDTO;
import com.yimayhd.commentcenter.client.dto.CategoryUpdateDTO;
import com.yimayhd.commentcenter.client.enums.BaseStatus;
import com.yimayhd.commentcenter.client.errorcode.ComCenterReturnCodes;
import com.yimayhd.commentcenter.client.result.BasePageResult;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.commentcenter.client.result.CategoryResult;
import com.yimayhd.commentcenter.client.service.ComCategoryService;
import com.yimayhd.gf.model.query.GFCategoryVo;
import com.yimayhd.ic.client.model.param.item.ItemOptionDTO;
import com.yimayhd.ic.client.model.param.item.ItemQryDTO;
import com.yimayhd.ic.client.model.result.ICErrorCode;
import com.yimayhd.ic.client.model.result.item.ItemPageResult;
import com.yimayhd.ic.client.model.result.item.SingleItemQueryResult;
import com.yimayhd.ic.client.service.item.ItemBizQueryService;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.palace.base.BaseException;
import com.yimayhd.palace.constant.B2CConstant;
import com.yimayhd.palace.util.RepoUtils;
import com.yimayhd.stone.enums.DomainAndAppId;

public class GFCategoryRepo {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GFCategoryRepo.class);
	
	@Autowired
	private ComCategoryService comCategoryService;
	
	@Autowired
	private ItemBizQueryService itemBizQueryServiceRef;
	
	@Autowired
    private ItemQueryService itemQueryServiceRef;

	public BaseResult<List<CategoryDO>> getCategoryList(){
		BaseResult<List<CategoryDO>> baseResult =  new BaseResult<List<CategoryDO>>();
		try {
			baseResult = comCategoryService.getCategoryList();
		} catch (Exception e) {
			baseResult.setErrorCode(ComCenterReturnCodes.READ_DB_FAILED);
			return baseResult;
		}
		return baseResult;
		
	}
	
	public BasePageResult<CategoryResult> pageQueryCategory(CategoryQueryDTO categoryQueryDTO) {
		BasePageResult<CategoryResult> basePageResult =  new BasePageResult<CategoryResult>();
		try {
			if(categoryQueryDTO==null){
				basePageResult.setErrorCode(ComCenterReturnCodes.C_CONTENT_CAN_NOT_BE_NULL);
				return basePageResult;
			}
			basePageResult = comCategoryService.pageQueryCategory(categoryQueryDTO);
		} catch (Exception e) {
			basePageResult.setErrorCode(ComCenterReturnCodes.READ_DB_FAILED);
			return basePageResult;
		}
		return basePageResult;
	}
	
	public BaseResult<List<CategoryDO>> getPrimaryCategoryList(int domainId) {
		BaseResult<List<CategoryDO>> baseResult =  new BaseResult<List<CategoryDO>>();
		try {
			baseResult = comCategoryService.getPrimaryCategoryList(domainId);
		} catch (Exception e) {
			baseResult.setErrorCode(ComCenterReturnCodes.READ_DB_FAILED);
			return baseResult;
		}
		return baseResult;
	}
	
	public BaseResult<CategoryDO> saveCategoryDO(GFCategoryVo gfCategoryVo){
		BaseResult<CategoryDO> baseResult = new BaseResult<CategoryDO>();
		try {
			if(gfCategoryVo==null){
				baseResult.setErrorCode(ComCenterReturnCodes.C_CONTENT_CAN_NOT_BE_NULL);
				return baseResult;
			}
			
			CategorySaveDTO categorySaveDTO = new CategorySaveDTO();
			
			categorySaveDTO.setDomain(DomainAndAppId.APP_DOMAIN_ID_GF_WEB.getDomainId());
			categorySaveDTO.setLeaf(gfCategoryVo.getLeaf());
			categorySaveDTO.setLevel(gfCategoryVo.getLevel());
			categorySaveDTO.setName(gfCategoryVo.getName());
			categorySaveDTO.setPriority(gfCategoryVo.getPriority());
			categorySaveDTO.setSellerId(gfCategoryVo.getSellerId());
			categorySaveDTO.setStatus(gfCategoryVo.getStatus());
			categorySaveDTO.setParentId(gfCategoryVo.getParentId());
			
			baseResult = comCategoryService.saveCategoryDO(categorySaveDTO);
			if(baseResult.isSuccess()){
				return baseResult;
			}
		} catch (Exception e) {
			LOGGER.error("saveCategoryDO:gfCategoryVo={}",JSON.toJSONString(gfCategoryVo),e);
			baseResult.setErrorCode(ComCenterReturnCodes.SYSTEM_EXCEPTION_CODE);
			return baseResult;
		}
		return baseResult;
	}

	public BaseResult<CategoryDO> updateCategory(GFCategoryVo gfCategoryVo){
		
		BaseResult<CategoryDO> baseResult = new BaseResult<CategoryDO>();
		try {
			if(gfCategoryVo==null){
				baseResult.setErrorCode(ComCenterReturnCodes.C_CONTENT_CAN_NOT_BE_NULL);
				return baseResult;
			}
			
			CategoryUpdateDTO categoryUpdateDTO = new CategoryUpdateDTO();
			
			categoryUpdateDTO.setDomain(DomainAndAppId.APP_DOMAIN_ID_GF_WEB.getDomainId());
			categoryUpdateDTO.setId(gfCategoryVo.getId());
			categoryUpdateDTO.setLeaf(gfCategoryVo.getLeaf());
			categoryUpdateDTO.setLevel(gfCategoryVo.getLevel());
			categoryUpdateDTO.setName(gfCategoryVo.getName());
			categoryUpdateDTO.setPriority(gfCategoryVo.getPriority());
			categoryUpdateDTO.setSellerId(gfCategoryVo.getSellerId());
			categoryUpdateDTO.setStatus(gfCategoryVo.getStatus());
			categoryUpdateDTO.setParentId(gfCategoryVo.getParentId());
			baseResult = comCategoryService.updateCategory(categoryUpdateDTO);
			if(baseResult.isSuccess()){
				return baseResult;
			}
		} catch (Exception e) {
			LOGGER.error("updateCategory:gfCategoryVo={}",JSON.toJSONString(gfCategoryVo),e);
			baseResult.setErrorCode(ComCenterReturnCodes.SYSTEM_EXCEPTION_CODE);
			return baseResult;
		}
		return baseResult;
		
	}
	
	public BaseResult<CategoryDO> getCategoryById(long id){
		
		BaseResult<CategoryDO> baseResult = new BaseResult<CategoryDO>();
		try {
			if(id<=0){
				baseResult.setErrorCode(ComCenterReturnCodes.C_CONTENT_CAN_NOT_BE_NULL);
				return baseResult;
			}
			
			baseResult = comCategoryService.getCategoryById(id);
			if(baseResult.isSuccess()){
				return baseResult;
			}
		} catch (Exception e) {
			LOGGER.error("getCategoryRelationList:id={}",JSON.toJSONString(id),e);
			baseResult.setErrorCode(ComCenterReturnCodes.SYSTEM_EXCEPTION_CODE);
			return baseResult;
		}
		return baseResult;
	}
	
	
	public ItemPageResult getItemList(ItemQryDTO itemQryDTO) {
		if (itemQryDTO == null) {
			throw new BaseException("参数为null,查询商品列表失败 ");
		}
		RepoUtils.requestLog(LOGGER, "itemBizQueryServiceRef.getItem", itemQryDTO);
		ItemPageResult itemPageResult = itemQueryServiceRef.getItem(itemQryDTO);
		RepoUtils.resultLog(LOGGER, "itemBizQueryServiceRef.getItem", itemPageResult);
		return itemPageResult;
	}

	
	public BaseResult<Boolean> batchBoundProduct(GFCategoryVo gfCategoryVo) {
		BaseResult<Boolean> baseResult = new BaseResult<Boolean>();
		try {
			if(CollectionUtils.isEmpty(gfCategoryVo.getItemIdList())){
				baseResult.setErrorCode(ComCenterReturnCodes.C_CONTENT_CAN_NOT_BE_NULL);
				return baseResult;
			}
			CategoryRelationSaveDTO categoryRelationSaveDTO = new CategoryRelationSaveDTO();
			
			categoryRelationSaveDTO.setItemIdList(gfCategoryVo.getItemIdList());
			categoryRelationSaveDTO.setCategoryId(gfCategoryVo.getId());
			categoryRelationSaveDTO.setDomain(B2CConstant.GF_DOMAIN);
			categoryRelationSaveDTO.setStatus(BaseStatus.AVAILABLE.getType());
			categoryRelationSaveDTO.setParentId(gfCategoryVo.getParentId());
			baseResult = comCategoryService.batchSaveCateRelation(categoryRelationSaveDTO);
			if(!baseResult.isSuccess()){
				LOGGER.error("batchEnableStatus return value is null !returnValue :"+ JSON.toJSONString(gfCategoryVo));
				baseResult.setErrorCode(ComCenterReturnCodes.WRITE_DB_FAILED);
				return baseResult;
			}
		} catch (Exception e) {
			baseResult.setErrorCode(ComCenterReturnCodes.SYSTEM_EXCEPTION_CODE);
			return baseResult;
		}
		return baseResult;
	}
	
	//不带分业的品类和商品管理列表
	public BaseResult<List<CategoryItemRelationDO>> getCategoryRelationList(long id){
		
		BaseResult<List<CategoryItemRelationDO>> baseResult = new BaseResult<List<CategoryItemRelationDO>>();
		
		try {
			if(id<=0){
				baseResult.setErrorCode(ComCenterReturnCodes.C_CONTENT_CAN_NOT_BE_NULL);
				return baseResult;
			}
			
			CategoryRelationDTO categoryRelationDTO = new CategoryRelationDTO();
			
			categoryRelationDTO.setDomain(DomainAndAppId.APP_DOMAIN_ID_GF_WEB.getDomainId());
			categoryRelationDTO.setId(id);
			
			baseResult = comCategoryService.getCategoryRelationList(categoryRelationDTO);
			if(baseResult.isSuccess()){
				return baseResult;
			}
		} catch (Exception e) {
			LOGGER.error("getCategoryRelationList:id={}",JSON.toJSONString(id),e);
			baseResult.setErrorCode(ComCenterReturnCodes.SYSTEM_EXCEPTION_CODE);
			return baseResult;
		}
		return baseResult;
	}

	//带分页的品类和商品列表
	public BasePageResult<CategoryItemRelationDO> getCategoryRelationPageList(
			CategoryRelationDTO  categoryRelationDTO) {
		BasePageResult<CategoryItemRelationDO> basePageResult = new BasePageResult<CategoryItemRelationDO>();
		if (categoryRelationDTO == null) {
//			throw new BaseException("参数为null,查询商品列表失败 ");
			basePageResult.setErrorCode(ComCenterReturnCodes.C_CONTENT_CAN_NOT_BE_NULL);
			return  basePageResult;
		}
		basePageResult = comCategoryService.getCategoryRelationPageList(categoryRelationDTO);
		RepoUtils.resultLog(LOGGER, "comCategoryService.getCategoryRelationPageList", basePageResult);
		return basePageResult;
	}

	public BaseResult<Boolean> batchDelProduct(GFCategoryVo gfCategoryVo) {
		BaseResult<Boolean> baseResult = new BaseResult<Boolean>();
		try {
			if(CollectionUtils.isEmpty(gfCategoryVo.getItemIdList())){
				baseResult.setErrorCode(ComCenterReturnCodes.C_CONTENT_CAN_NOT_BE_NULL);
				return baseResult;
			}
			CategoryRelationDelDTO categoryRelationDelDTO = new CategoryRelationDelDTO();
			categoryRelationDelDTO.setDomain(B2CConstant.GF_DOMAIN);
			categoryRelationDelDTO.setCategoryId(gfCategoryVo.getId());;
			categoryRelationDelDTO.setIdList(gfCategoryVo.getItemIdList());
			categoryRelationDelDTO.setParentId(gfCategoryVo.getParentId());
			
			baseResult = comCategoryService.delCateRelation(categoryRelationDelDTO);
			if(!baseResult.isSuccess()){
				LOGGER.error("batchEnableStatus return value is null !returnValue :"+ JSON.toJSONString(gfCategoryVo));
				baseResult.setErrorCode(ComCenterReturnCodes.WRITE_DB_FAILED);
				return baseResult;
			}
		} catch (Exception e) {
			baseResult.setErrorCode(ComCenterReturnCodes.SYSTEM_EXCEPTION_CODE);
			return baseResult;
		}
		return baseResult;
	}

	public SingleItemQueryResult querySingleItem(Long itemId,
			ItemOptionDTO itemOptionDTO) {
		SingleItemQueryResult singleItemQueryResult = new SingleItemQueryResult();
		if (itemOptionDTO == null||itemId<=0) {
			singleItemQueryResult.setErrorCode(ICErrorCode.PARAM_ERROR);
			return singleItemQueryResult;
		}
		singleItemQueryResult = itemQueryServiceRef.querySingleItem(itemId,itemOptionDTO);
		RepoUtils.resultLog(LOGGER, "itemBizQueryServiceRef.querySingleItem", singleItemQueryResult);
		return singleItemQueryResult;
	}

	public BaseResult<Map<Long, Boolean>> checkItemAndCateRelaByitemIds(
			CategoryCheckDTO categoryCheckDTO) {
		BaseResult<Map<Long, Boolean>> baseResult = new BaseResult<Map<Long,Boolean>>();
		if (categoryCheckDTO == null) {
			baseResult.setErrorCode(ComCenterReturnCodes.C_CONTENT_CAN_NOT_BE_NULL);
			return baseResult;
		}
		RepoUtils.requestLog(LOGGER, "comCategoryService.checkItemAndCateRelaByitemIds", categoryCheckDTO);
		baseResult = comCategoryService.checkItemAndCateRelaByitemIds(categoryCheckDTO);
		RepoUtils.resultLog(LOGGER, "comCategoryService.checkItemAndCateRelaByitemIds", baseResult);
		return baseResult;
	}


	
}
