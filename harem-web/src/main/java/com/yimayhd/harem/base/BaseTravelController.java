package com.yimayhd.harem.base;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.commentcenter.client.service.ComCenterService;
import com.yimayhd.harem.model.travel.IdNamePair;
import com.yimayhd.ic.client.model.domain.CategoryPropertyDO;
import com.yimayhd.ic.client.model.domain.CategoryPropertyValueDO;
import com.yimayhd.ic.client.model.domain.share_json.LinePropertyType;
import com.yimayhd.ic.client.model.enums.CategoryType;
import com.yimayhd.ic.client.model.enums.LineOwnerType;
import com.yimayhd.ic.client.model.result.item.CategoryResult;
import com.yimayhd.ic.client.service.item.CategoryService;
import com.yimayhd.resourcecenter.model.enums.RegionType;
import com.yimayhd.resourcecenter.service.RegionClientService;

/**
 * 线路基本Controller
 * 
 * @author yebin 2015年11月23日
 *
 */
public abstract class BaseTravelController extends BaseController {
	@Resource
	protected ComCenterService comCenterServiceRef;
	@Resource
	protected RegionClientService regionClientServiceRef;
	@Resource
	protected CategoryService categoryServiceRef;

	protected void initBaseInfo() {
		put("PT_DEFAULT", LineOwnerType.DEFAULT.getType());
		put("PT_MASTER", LineOwnerType.MASTER.getType());
		BaseResult<List<ComTagDO>> tagResult = comCenterServiceRef.selectTagListByTagType(TagType.LINETAG.name());
		List<IdNamePair> tags = new ArrayList<IdNamePair>();
		if (tagResult != null && tagResult.isSuccess()) {
			List<ComTagDO> values = tagResult.getValue();
			if (CollectionUtils.isNotEmpty(values)) {
				for (ComTagDO comTagDO : values) {
					tags.add(new IdNamePair(comTagDO.getId(), comTagDO.getName()));
				}
			}
		}
		put("tags", tags);
		put("linePropertyTypes", LinePropertyType.values());
		put("departRegions", regionClientServiceRef.getRegionDOListByType(RegionType.DEPART_REGION.getType()));
		put("descRegions", regionClientServiceRef.getRegionDOListByType(RegionType.DESC_REGION.getType()));
		CategoryResult categoryResult = categoryServiceRef.getCategory(CategoryType.LINE.getValue());
		if (categoryResult != null && categoryResult.isSuccess()) {
			List<CategoryPropertyValueDO> propertyDOs = categoryResult.getCategroyDO().getKeyCategoryPropertyDOs();
			for (CategoryPropertyValueDO categoryPropertyValueDO : propertyDOs) {
				CategoryPropertyDO categoryPropertyDO = categoryPropertyValueDO.getCategoryPropertyDO();
				if(categoryPropertyDO.getType() == LinePropertyType.PERSON.getType()) {
					categoryPropertyDO.getCategoryValueDOs();
				}
			}
		}
	}
}
