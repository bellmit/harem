package com.yimayhd.harem.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.exception.NoticeException;
import com.yimayhd.harem.service.ScenicService;
import com.yimayhd.harem.service.TfsService;
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
	private static final Logger log = LoggerFactory.getLogger(ScenicServiceImpl.class);
	@Autowired
	private ItemQueryService itemQueryService;
	@Autowired
	private ResourcePublishService resourcePublishServiceRef;
	@Autowired
	private TfsService tfsService;

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
		} else {
			log.error("itemQueryService.pageQueryScenic return value is null !returnValue :"
					+ JSON.toJSONString(pageResult));
		}
		return new PageVO<ScenicDO>(query.getPageNo(), query.getPageSize(), totalCount, itemList);
	}

	@Override
	public ScenicAddNewDTO getById(long id) throws Exception {

		ICResult<ScenicDO> scenic = itemQueryService.getScenic(id);
		if (scenic.isSuccess()) {
			ScenicAddNewDTO dto = new ScenicAddNewDTO();
			ScenicDO scenicDO = scenic.getModule();
			NeedKnow needKnow = JSON.parseObject(scenicDO.getNeedKnow(), NeedKnow.class);
			needKnow.setExtraInfoUrl(tfsService.readHtml5(needKnow.getExtraInfoUrl()));
			dto.setNeedKnow(needKnow);
			dto.setScenic(scenicDO);
			return dto;
		} else {
			log.error("itemQueryService.getScenic return value is null !returnValue :" + JSON.toJSONString(scenic));
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
		if (!result.isSuccess()) {
			log.error("resourcePublishServiceRef.updateScenic return value is null !returnValue :"
					+ JSON.toJSONString(result));
		}
		return result.isSuccess();
	}

	@Override
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

	@Override
	public ICResult<ScenicDO> save(ScenicAddNewDTO addNewDTO) throws Exception {
		ICResult<ScenicDO> addScenicNew = null;
		addNewDTO.getNeedKnow().setExtraInfoUrl(tfsService.publishHtml5(addNewDTO.getNeedKnow().getExtraInfoUrl()));
		if (0 == addNewDTO.getScenic().getId()) {
			addScenicNew = resourcePublishServiceRef.addScenicNew(addNewDTO);
		} else {
			addScenicNew = resourcePublishServiceRef.updateScenicNew(addNewDTO);
		}
		if (!addScenicNew.isSuccess()) {
			log.error("resourcePublishServiceRef.updateScenic return value is null !returnValue :"
					+ JSON.toJSONString(addScenicNew));
			throw new NoticeException(addScenicNew.getResultMsg());
		}
		return addScenicNew;
	}
}
