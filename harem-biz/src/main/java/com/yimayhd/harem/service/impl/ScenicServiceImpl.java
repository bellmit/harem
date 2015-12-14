package com.yimayhd.harem.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.common.tfs.TfsManager;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.service.ScenicService;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.domain.share_json.NeedKnow;
import com.yimayhd.ic.client.model.param.item.ScenicAddNewDTO;
import com.yimayhd.ic.client.model.query.ScenicPageQuery;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.ic.client.service.item.ResourcePublishService;

/**
 * Created by Administrator on 2015/11/18.
 */
public class ScenicServiceImpl implements ScenicService {

	@Autowired
	private ItemQueryService itemQueryService;
	@Autowired
	private ResourcePublishService resourcePublishServiceRef;
	@Autowired
    private TfsManager tfsManager;

	@Override
	public PageVO<ScenicDO> getList(ScenicPageQuery query) throws Exception {
		int totalCount = 0;
		List<ScenicDO> itemList = new ArrayList<ScenicDO>();
		query.setNeedCount(true);
		ICPageResult<ScenicDO> pageResult = itemQueryService.pageQueryScenic(query);
		if (pageResult != null && pageResult.isSuccess()) {
			totalCount = pageResult.getTotalCount();
			if (CollectionUtils.isNotEmpty(pageResult.getList())) {
				itemList.addAll(pageResult.getList());
			}
		}
		return new PageVO<ScenicDO>(query.getPageNo(), query.getPageSize(), totalCount, itemList);
	}
	
	
	

	@Override
	public ScenicAddNewDTO getById(long id) throws Exception {
	
		ICResult<ScenicDO> scenic = itemQueryService.getScenic(id);
		if(scenic.isSuccess()){
			ScenicAddNewDTO dto = new ScenicAddNewDTO();
			ScenicDO scenicDO = scenic.getModule();
			NeedKnow needKnow = JSON.parseObject(scenicDO.getNeedKnow(), NeedKnow.class);
			dto.setNeedKnow(needKnow);
			dto.setScenic(scenicDO);
			return dto;
		}
		return null;
	}




	@Override
	public boolean updateStatus(int id, int scenicStatus) throws Exception {
		ArrayList<ScenicDO> scenicDOList = new ArrayList<ScenicDO>();
		ScenicDO scenic = new ScenicDO();
		scenic.setId(id);
		scenic.setStatus(scenicStatus);
		scenicDOList.add(scenic);
		ICResult<ScenicDO> result = resourcePublishServiceRef.updateScenic(scenicDOList);
		return result.isSuccess();
	}


	@Override
	public boolean batchupdateStatus(ArrayList<Integer> scenicIdList, int scenicStatus) {
		if(!scenicIdList.isEmpty()){
			ArrayList<ScenicDO> scenicDOList = new ArrayList<ScenicDO>();
			for (Integer id : scenicIdList) {
				ScenicDO scenic = new ScenicDO();
				scenic.setId(id);
				scenic.setStatus(scenicStatus);
				scenicDOList.add(scenic);
			}
			ICResult<ScenicDO> result = resourcePublishServiceRef.updateScenic(scenicDOList);
			return result.isSuccess();
		}
		return false;
	}




	@Override
	public int save(ScenicAddNewDTO addNewDTO) throws Exception {
		
		String extraInfoUrl = tfsManager.saveFile((addNewDTO.getNeedKnow().getExtraInfoUrl()).getBytes("utf-8"), null, "html");
		addNewDTO.getNeedKnow().setExtraInfoUrl(extraInfoUrl);
		ScenicDO scenic = addNewDTO.getScenic();
		if(0==addNewDTO.getScenic().getId()){
			resourcePublishServiceRef.addScenicNew(addNewDTO);
		}else{
			
		}
		
	
		return 0;
	}

	

}
