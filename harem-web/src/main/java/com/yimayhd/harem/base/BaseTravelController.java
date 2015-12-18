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
import com.yimayhd.harem.model.travel.groupTravel.TripTraffic;
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
		if (tagResult != null && tagResult.isSuccess()) {
			put("tags", tagResult.getValue());
		}
		put("departRegions", regionService.getRegions(RegionType.DEPART_REGION));
		put("descRegions", regionService.getRegions(RegionType.DESC_REGION));
		put("ways", TripTraffic.ways());
	}

	protected void initLinePropertyTypes(long categoryId) {
		put("categoryId", categoryId);
		List<CategoryValueDO> persionPropertyValues = new ArrayList<CategoryValueDO>();
		CategoryResult categoryResult = categoryServiceRef.getCategory(categoryId);
		if (categoryResult != null && categoryResult.isSuccess() && categoryResult.getCategroyDO() != null) {
			List<CategoryPropertyValueDO> propertyDOs = categoryResult.getCategroyDO().getSellCategoryPropertyDOs();
			if (CollectionUtils.isNotEmpty(propertyDOs)) {
				put("options", 1);
				for (CategoryPropertyValueDO propertyDO : propertyDOs) {
					if (propertyDO.getId() == LinePropertyType.PERSON.getType()) {
						put("persionProperty", propertyDO.getCategoryPropertyDO());
						List<CategoryValueDO> categoryValueDOs = propertyDO.getCategoryValueDOs();
						if (CollectionUtils.isNotEmpty(categoryValueDOs)) {
							for (CategoryValueDO categoryValueDO : categoryValueDOs) {
								persionPropertyValues.add(categoryValueDO);
							}
						}
					} else if (propertyDO.getId() == LinePropertyType.TRAVEL_PACKAGE.getType()) {
						put("packageProperty", propertyDO.getCategoryPropertyDO());
					} else if (propertyDO.getId() == LinePropertyType.DEPART_DATE.getType()) {
						put("dateProperty", propertyDO.getCategoryPropertyDO());
					}
				}
			} else {
				put("options", 0);
			}
		}
		put("persionPropertyValues", persionPropertyValues);
	}
}
