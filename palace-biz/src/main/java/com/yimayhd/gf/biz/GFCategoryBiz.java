package com.yimayhd.gf.biz;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.domain.CategoryDO;
import com.yimayhd.commentcenter.client.domain.CategoryItemRelationDO;
import com.yimayhd.commentcenter.client.errorcode.ComCenterReturnCodes;
import com.yimayhd.commentcenter.client.result.BasePageResult;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.gf.model.query.GFCategoryVo;
import com.yimayhd.gf.repo.GFCategoryRepo;

public class GFCategoryBiz {

	private static final Logger LOGGER = LoggerFactory.getLogger(GFCategoryBiz.class);
	
	@Autowired
	private GFCategoryRepo gfCategoryRepo;
	
	
	
	public BaseResult<List<CategoryDO>> getCategoryList(){
		BaseResult<List<CategoryDO>> baseResult =  new BaseResult<List<CategoryDO>>();
		try {
			baseResult = gfCategoryRepo.getCategoryList();
		} catch (Exception e) {
			baseResult.setErrorCode(ComCenterReturnCodes.READ_DB_FAILED);
			return baseResult;
		}
		return baseResult;
		
	}
	
	public BasePageResult<CategoryDO> pageQueryCategory() {
		BasePageResult<CategoryDO> baseResult =  new BasePageResult<CategoryDO>();
		try {
			baseResult = gfCategoryRepo.pageQueryCategory();
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
			
			if(gfCategoryVo.getParentId()!=null){
				
				gfCategoryVo.setLevel(2);
				gfCategoryVo.setLeaf(1);
			}else{
				gfCategoryVo.setLevel(1);
				gfCategoryVo.setLeaf(2);
			}
			
			baseResult = gfCategoryRepo.saveCategoryDO(gfCategoryVo);
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
			
			baseResult = gfCategoryRepo.updateCategory(gfCategoryVo);
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
			
			baseResult = gfCategoryRepo.getCategoryById(id);
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
			
			baseResult = gfCategoryRepo.getCategoryRelationList(id);
			if(baseResult.isSuccess()){
				return baseResult;
			}
		} catch (Exception e) {
			LOGGER.error("getCategoryRelationList:gfCategoryVo={}",JSON.toJSONString(id),e);
			baseResult.setErrorCode(ComCenterReturnCodes.SYSTEM_EXCEPTION_CODE);
			return baseResult;
		}
		return baseResult;
	}

}
