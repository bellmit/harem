package com.yimayhd.harem.base;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.commentcenter.client.service.ComCenterService;
import com.yimayhd.harem.constant.B2CConstant;
import com.yimayhd.harem.model.travel.groupTravel.TripTraffic;
import com.yimayhd.harem.service.CategoryService;
import com.yimayhd.harem.service.RegionService;
import com.yimayhd.harem.service.UserRPCService;
import com.yimayhd.harem.util.LogUtil;
import com.yimayhd.ic.client.model.domain.CategoryPropertyValueDO;
import com.yimayhd.ic.client.model.domain.CategoryValueDO;
import com.yimayhd.ic.client.model.domain.item.CategoryDO;
import com.yimayhd.ic.client.model.domain.share_json.LinePropertyType;
import com.yimayhd.ic.client.model.enums.LineOwnerType;
import com.yimayhd.resourcecenter.model.enums.RegionType;

/**
 * 线路基本Controller
 * 
 * @author yebin 2015年11月23日
 *
 */
public abstract class BaseTravelController extends BaseController {
	protected Logger log = LoggerFactory.getLogger(getClass());
	@Resource
	protected ComCenterService comCenterServiceRef;
	@Autowired
	protected RegionService regionService;
	@Resource
	protected CategoryService categoryService;
	@Autowired
	protected UserRPCService userService;

	protected void initBaseInfo() throws BaseException {
		put("PT_DEFAULT", LineOwnerType.DEFAULT.getType());
		put("PT_MASTER", LineOwnerType.MASTER.getType());
		LogUtil.requestLog(log, "comCenterServiceRef.selectTagListByTagType", TagType.LINETAG.name());
		BaseResult<List<ComTagDO>> tagResult = comCenterServiceRef.selectTagListByTagType(TagType.LINETAG.name());
		LogUtil.resultLog(log, "comCenterServiceRef.selectTagListByTagType", tagResult);
		put("tags", tagResult.getValue());
		put("departRegions", regionService.getRegions(RegionType.DEPART_REGION));
		put("descRegions", regionService.getRegions(RegionType.DESC_REGION));
		put("ways", TripTraffic.ways());
		put("officialPublisher", userService.getUserById(B2CConstant.SELLERID));
	}

	protected void initLinePropertyTypes(long categoryId) {
		put("categoryId", categoryId);
		List<CategoryValueDO> persionPropertyValues = new ArrayList<CategoryValueDO>();
		CategoryDO category = categoryService.getCategoryDOById(categoryId);
		if (category != null) {
			List<CategoryPropertyValueDO> propertyDOs = category.getSellCategoryPropertyDOs();
			if (CollectionUtils.isNotEmpty(propertyDOs)) {
				put("options", 1);
				for (CategoryPropertyValueDO propertyDO : propertyDOs) {
					if (propertyDO.getCategoryPropertyDO().getId() == LinePropertyType.PERSON.getType()) {
						put("persionProperty", propertyDO.getCategoryPropertyDO());
						List<CategoryValueDO> categoryValueDOs = propertyDO.getCategoryValueDOs();
						if (CollectionUtils.isNotEmpty(categoryValueDOs)) {
							for (CategoryValueDO categoryValueDO : categoryValueDOs) {
								persionPropertyValues.add(categoryValueDO);
							}
						}
					} else if (propertyDO.getCategoryPropertyDO().getId() == LinePropertyType.TRAVEL_PACKAGE
							.getType()) {
						put("packageProperty", propertyDO.getCategoryPropertyDO());
					} else if (propertyDO.getCategoryPropertyDO().getId() == LinePropertyType.DEPART_DATE.getType()) {
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
