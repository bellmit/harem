package com.yimayhd.gf.biz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yimayhd.commentcenter.client.enums.CategoryStatus;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.domain.CategoryDO;
import com.yimayhd.commentcenter.client.domain.CategoryItemRelationDO;
import com.yimayhd.commentcenter.client.dto.CategoryCheckDTO;
import com.yimayhd.commentcenter.client.dto.CategoryQueryDTO;
import com.yimayhd.commentcenter.client.dto.CategoryRelationDTO;
import com.yimayhd.commentcenter.client.errorcode.ComCenterReturnCodes;
import com.yimayhd.commentcenter.client.result.BasePageResult;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.commentcenter.client.result.CategoryResult;
import com.yimayhd.gf.model.query.GFCategoryVo;
import com.yimayhd.gf.repo.GFCategoryRepo;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.enums.ItemStatus;
import com.yimayhd.ic.client.model.param.item.ItemOptionDTO;
import com.yimayhd.ic.client.model.param.item.ItemQryDTO;
import com.yimayhd.ic.client.model.result.item.ItemPageResult;
import com.yimayhd.ic.client.model.result.item.SingleItemQueryResult;
import com.yimayhd.palace.base.BaseException;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.constant.B2CConstant;
import com.yimayhd.palace.model.CategoryVO;
import com.yimayhd.palace.model.ItemVO;
import com.yimayhd.palace.model.query.CommodityListQuery;
import com.yimayhd.stone.enums.DomainAndAppId;

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
	
	public PageVO<CategoryResult> pageQueryCategory(GFCategoryVo gfCategoryVo) {
		int totalCount = 0;
		List<CategoryResult> list = new ArrayList<CategoryResult>();
		if(null != gfCategoryVo ){
			
			CategoryQueryDTO categoryQueryDTO = new CategoryQueryDTO();
			
			categoryQueryDTO.setDomain(DomainAndAppId.APP_DOMAIN_ID_GF_WEB.getDomainId());
			categoryQueryDTO.setPageSize(gfCategoryVo.getPageSize());
			if(!StringUtils.isBlank(gfCategoryVo.getName())) {
				categoryQueryDTO.setName(gfCategoryVo.getName());
	        }
			categoryQueryDTO.setPageNo(gfCategoryVo.getPageNumber());
			if(0 != gfCategoryVo.getStatus()){
				categoryQueryDTO.setStatus(gfCategoryVo.getStatus());
	        }else{
				categoryQueryDTO.setStatus(CategoryStatus.ONLINE.getStatus());
			}
			
			BasePageResult<CategoryResult> basePageResult = gfCategoryRepo.pageQueryCategory(categoryQueryDTO);
			if(null != basePageResult && basePageResult.isSuccess() && CollectionUtils.isNotEmpty(basePageResult.getList())){//res.getValue()
				totalCount=basePageResult.getTotalCount();
				list = basePageResult.getList();
			}
		}else{
			LOGGER.error("Request {} error: query={}", "GFCategoryBiz.pageQueryCategory",JSON.toJSONString(gfCategoryVo));
		}
		return  new PageVO<CategoryResult>(gfCategoryVo.getPageNumber(), gfCategoryVo.getPageSize(), totalCount, list);
	}
	
	public BaseResult<List<CategoryDO>> getPrimaryCategoryList() {
		BaseResult<List<CategoryDO>> baseResult =  new BaseResult<List<CategoryDO>>();
		try {
			baseResult = gfCategoryRepo.getPrimaryCategoryList(DomainAndAppId.APP_DOMAIN_ID_GF_WEB.getDomainId());
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
			
			if(gfCategoryVo.getParentId()!=0){
				
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
			if(gfCategoryVo.getParentId()!=0){
				
				gfCategoryVo.setLevel(2);
				gfCategoryVo.setLeaf(1);
			}else{
				gfCategoryVo.setLevel(1);
				gfCategoryVo.setLeaf(2);
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
	

	public PageVO<ItemVO> getItemList(CommodityListQuery commodityListQuery) throws Exception{
		ItemQryDTO itemQryDTO = new ItemQryDTO();
        List<Integer> domainList = new ArrayList<Integer>();
        domainList.add(B2CConstant.GF_DOMAIN);
        itemQryDTO.setDomains(domainList);
        itemQryDTO.setPageNo(commodityListQuery.getPageNumber());
        itemQryDTO.setPageSize(commodityListQuery.getPageSize());

        if(!StringUtils.isBlank(commodityListQuery.getCommName())) {
            itemQryDTO.setName(commodityListQuery.getCommName());
        }
        List<Integer> status = new ArrayList<Integer>();
        if(0 != commodityListQuery.getCommStatus()){
            status.add(commodityListQuery.getCommStatus());
        }else{
//            status.add(ItemStatus.create.getValue());
            status.add(ItemStatus.valid.getValue());
//            status.add(ItemStatus.invalid.getValue());下降的商品不出现在列表里面
        }
        itemQryDTO.setStatus(status);
        //
        if (commodityListQuery.getItemType() != 0) {
            itemQryDTO.setItemType(commodityListQuery.getItemType());
        }

        ItemPageResult itemPageResult = gfCategoryRepo.getItemList(itemQryDTO);
        if(null == itemPageResult){
        	LOGGER.error("GFCategoryBiz.getList-ItemQueryService.getItemList result is null and parame: " + JSON.toJSONString(itemQryDTO));
            throw new BaseException("返回结果错误,新增失败 ");
        } else if(!itemPageResult.isSuccess()){
        	LOGGER.error("GFCategoryBiz.getList-ItemQueryService.getItemList error:" + JSON.toJSONString(itemPageResult) + "and parame: " + JSON.toJSONString(itemQryDTO));
            throw new BaseException(itemPageResult.getResultMsg());
        }
        List<ItemDO> itemDOList = itemPageResult.getItemDOList();
        List<ItemVO> itemVOList = new ArrayList<ItemVO>();
        List<Long> listLongs = new ArrayList<Long>();
        
        
        //拼装商品列表的ID集合  去关系表中查询是否已经存在  以便回显
        for(ItemDO itemDO:itemDOList){
        	long itemId = itemDO.getId();
        	listLongs.add(itemId);
        }
        CategoryCheckDTO categoryCheckDTO = new CategoryCheckDTO();
        categoryCheckDTO.setDomain(B2CConstant.GF_DOMAIN);
        categoryCheckDTO.setIdList(listLongs);
    	categoryCheckDTO.setCategoryId(commodityListQuery.getCategory_id());
    	BaseResult<Map<Long, Boolean>> baseResult = gfCategoryRepo.checkItemAndCateRelaByitemIds(categoryCheckDTO);
    	if(null==baseResult||!baseResult.isSuccess()){
    		LOGGER.error("gfCategoryRepo.checkItemAndCateRelaByitemIds error:" + JSON.toJSONString(baseResult) + "and parame: " + JSON.toJSONString(categoryCheckDTO));
            throw new BaseException(itemPageResult.getResultMsg());
    	}
    	Map<Long, Boolean> map = baseResult.getValue();
    	
    	for(ItemDO itemDO:itemDOList){
        	long itemId = itemDO.getId();
        	Boolean check = map.get(itemId);
        	if(check){
        		itemDO.setSource(1);
        	}else{
        		itemDO.setSource(0);
        	}
            itemVOList.add(ItemVO.getItemVO(itemDO,new CategoryVO()));
        }

        PageVO<ItemVO> pageVO = new PageVO<ItemVO>(commodityListQuery.getPageNumber(),commodityListQuery.getPageSize(),itemPageResult.getRecordCount(),itemVOList);
        return pageVO;
	}



	public BaseResult<Boolean> batchBoundProduct(GFCategoryVo gfCategoryVo) {
		BaseResult<Boolean> baseResult = new BaseResult<Boolean>();
		if(gfCategoryVo.getId()<=0 || gfCategoryVo.getItemIdList().size()<0||gfCategoryVo==null){
			baseResult.setErrorCode(ComCenterReturnCodes.C_CONTENT_CAN_NOT_BE_NULL);
			return baseResult;
		}
		baseResult = gfCategoryRepo.batchBoundProduct(gfCategoryVo);
		if(baseResult.isSuccess()){
			return baseResult;
		}
		return baseResult;
	}

	
	public PageVO<ItemVO> getCategoryRelationList(
			CommodityListQuery commodityListQuery) throws Exception{
		
		CategoryRelationDTO categoryRelationDTO = new CategoryRelationDTO();
		
        categoryRelationDTO.setDomain(B2CConstant.GF_DOMAIN);
        categoryRelationDTO.setId(commodityListQuery.getId());
        categoryRelationDTO.setPageNo(commodityListQuery.getPageNumber());
        categoryRelationDTO.setPageSize(commodityListQuery.getPageSize());
//        ItemPageResult itemPageResult = gfCategoryRepo.getItemList(itemQryDTO);
        
        BasePageResult<CategoryItemRelationDO> basePageResult = gfCategoryRepo.getCategoryRelationPageList(categoryRelationDTO);
        
        if(null == basePageResult){
        	LOGGER.error("GFCategoryBiz.getCategoryRelationList result is null and parame: " + JSON.toJSONString(categoryRelationDTO));
            throw new BaseException("返回结果错误,新增失败 ");
        } else if(!basePageResult.isSuccess()){
        	LOGGER.error("GFCategoryBiz.getCategoryRelationList error:" + JSON.toJSONString(basePageResult) + "and parame: " + JSON.toJSONString(categoryRelationDTO));
            throw new BaseException(basePageResult.getResultMsg());
        }
        
        List<CategoryItemRelationDO> categoryItemRelationDOs = basePageResult.getList();
        List<ItemVO> itemVOList = new ArrayList<ItemVO>();
        for (CategoryItemRelationDO categoryItemRelationDO : categoryItemRelationDOs) {
        	Long itemId = categoryItemRelationDO.getItemId();
        	
        	ItemOptionDTO itemOptionDTO = new ItemOptionDTO();
        	SingleItemQueryResult singleItemQueryResult = gfCategoryRepo.querySingleItem(itemId,itemOptionDTO);
        	
        	ItemDO itemDO = singleItemQueryResult.getItemDO();
        	itemVOList.add(ItemVO.getItemVO(itemDO,new CategoryVO()));
        	
		}
        
        PageVO<ItemVO> pageVO = new PageVO<ItemVO>(commodityListQuery.getPageNumber(),commodityListQuery.getPageSize(),basePageResult.getTotalCount(),itemVOList);
        return pageVO;
	}

	public BaseResult<Boolean> batchDelProduct(GFCategoryVo gfCategoryVo) {
		BaseResult<Boolean> baseResult = new BaseResult<Boolean>();
		if(gfCategoryVo.getId()<=0 || gfCategoryVo.getItemIdList().size()<0||gfCategoryVo==null){
			baseResult.setErrorCode(ComCenterReturnCodes.C_CONTENT_CAN_NOT_BE_NULL);
			return baseResult;
		}
		baseResult = gfCategoryRepo.batchDelProduct(gfCategoryVo);
		if(baseResult.isSuccess()){
			return baseResult;
		}
		return baseResult;
	}


}
