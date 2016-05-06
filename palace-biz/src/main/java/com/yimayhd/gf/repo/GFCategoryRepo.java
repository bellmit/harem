package com.yimayhd.gf.repo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.domain.CategoryDO;
import com.yimayhd.commentcenter.client.domain.CategoryItemRelationDO;
import com.yimayhd.commentcenter.client.dto.CategoryQueryDTO;
import com.yimayhd.commentcenter.client.dto.CategoryRelationDTO;
import com.yimayhd.commentcenter.client.dto.CategorySaveDTO;
import com.yimayhd.commentcenter.client.dto.CategoryUpdateDTO;
import com.yimayhd.commentcenter.client.errorcode.ComCenterReturnCodes;
import com.yimayhd.commentcenter.client.result.BasePageResult;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.commentcenter.client.service.ComCategoryService;
import com.yimayhd.gf.model.query.GFCategoryVo;
import com.yimayhd.stone.enums.DomainAndAppId;

public class GFCategoryRepo {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GFCategoryRepo.class);
	
	@Autowired
	private ComCategoryService comCategoryService;

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
	
	public BasePageResult<CategoryDO> pageQueryCategory() {
		BasePageResult<CategoryDO> baseResult =  new BasePageResult<CategoryDO>();
		try {
			
			CategoryQueryDTO categoryQueryDTO = new CategoryQueryDTO();
			
			categoryQueryDTO.setDomain(DomainAndAppId.APP_DOMAIN_ID_GF_WEB.getDomainId());
			//由于使用ztree 故暂时不用分页  这里先写死
			categoryQueryDTO.setPageSize(1000);
			baseResult = comCategoryService.pageQueryCategory(categoryQueryDTO);
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

	
}
