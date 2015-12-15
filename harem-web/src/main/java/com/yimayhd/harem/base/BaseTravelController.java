package com.yimayhd.harem.base;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.commentcenter.client.service.ComCenterService;
import com.yimayhd.harem.model.travel.IdNamePair;
import com.yimayhd.harem.service.RegionService;
import com.yimayhd.ic.client.model.domain.CategoryPropertyValueDO;
import com.yimayhd.ic.client.model.domain.CategoryValueDO;
import com.yimayhd.ic.client.model.domain.share_json.LinePropertyType;
import com.yimayhd.ic.client.model.enums.LineOwnerType;
import com.yimayhd.ic.client.model.result.item.CategoryResult;
import com.yimayhd.ic.client.service.item.CategoryService;
import com.yimayhd.resourcecenter.model.enums.RegionType;

/**
 * 线路基本Controller
 * 
 * @author yebin 2015年11月23日
 *
 */
public abstract class BaseTravelController extends BaseController {
	@Resource
	protected ComCenterService comCenterServiceRef;
	@Autowired
	protected RegionService regionService;
	@Resource
	protected CategoryService categoryServiceRef;

	protected void initBaseInfo() throws BaseException {
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
		put("departRegions", regionService.getRegions(RegionType.DEPART_REGION));
		put("descRegions", regionService.getRegions(RegionType.DESC_REGION));
	}
	
	protected void initLinePropertyTypes(long categoryId) {
		put("categoryId", categoryId);
		List<IdNamePair> linePropertyTypes = new ArrayList<IdNamePair>();
		CategoryResult categoryResult = categoryServiceRef.getCategory(categoryId);
		if (categoryResult != null && categoryResult.isSuccess() && categoryResult.getCategroyDO() != null) {
			List<CategoryPropertyValueDO> propertyDOs = categoryResult.getCategroyDO().getSellCategoryPropertyDOs();
			if (CollectionUtils.isNotEmpty(propertyDOs)) {
				for (CategoryPropertyValueDO propertyDO : propertyDOs) {
					if (propertyDO.getId() == LinePropertyType.PERSON.getType()) {
						List<CategoryValueDO> categoryValueDOs = propertyDO.getCategoryValueDOs();
						if (CollectionUtils.isNotEmpty(categoryValueDOs)) {
							for (CategoryValueDO categoryValueDO : categoryValueDOs) {
								linePropertyTypes
										.add(new IdNamePair(categoryValueDO.getId(), categoryValueDO.getText()));
							}
						}
					}
				}
			}
		}
		put("linePropertyTypes", linePropertyTypes);
	}
}
