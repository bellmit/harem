package com.yimayhd.harem.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.harem.model.CategoryVO;
import com.yimayhd.harem.service.CategoryService;
import com.yimayhd.harem.util.LogUtil;
import com.yimayhd.ic.client.model.domain.item.CategoryDO;
import com.yimayhd.ic.client.model.result.item.CategoryQryResult;
import com.yimayhd.ic.client.model.result.item.CategoryResult;

/**
 * Created by Administrator on 2015/11/25.
 */
public class CategoryServiceImpl implements CategoryService {
	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private com.yimayhd.ic.client.service.item.CategoryService categoryServiceRef;

	@Override
	public List<CategoryDO> getCategoryDOList() {
		LogUtil.requestLog(log, "categoryServiceRef.getCategoryList");
		CategoryQryResult categoryQryResult = categoryServiceRef.getCategoryList();
		LogUtil.icResultLog(log, "categoryServiceRef.getCategoryList", categoryQryResult);
		// TODO
		return categoryQryResult.getCategroyDOList();
		/*
		 * List<CategoryDO> categoryDOList = new ArrayList<CategoryDO>(); for
		 * (int i = 0; i < 4; i++) { CategoryDO categoryDOData = new
		 * CategoryDO(); categoryDOData.setId(i); categoryDOData.setName("普通商品"
		 * + i); categoryDOData.setLeaf(false); categoryDOData.setLevel(1);
		 * categoryDOData.setParentId(0); categoryDOList.add(categoryDOData); }
		 * return categoryDOList;
		 */
	}

	@Override
	public List<CategoryDO> getCategoryDOList(long parentId) {
		LogUtil.requestLog(log, "categoryServiceRef.getCategoryChildren", parentId);
		CategoryQryResult categoryQryResult = categoryServiceRef.getCategoryChildren();
		LogUtil.icResultLog(log, "categoryServiceRef.getCategoryChildren", categoryQryResult);
		// TODO
		return categoryQryResult.getCategroyDOList();
		/*
		 * List<CategoryDO> categoryDOList = new ArrayList<CategoryDO>(); for
		 * (int i = 0; i < 4; i++) { CategoryDO categoryDOData = new
		 * CategoryDO(); categoryDOData.setId(10 + i);
		 * categoryDOData.setName("详细商品" + i); categoryDOData.setLeaf(0 == i % 2
		 * ? false:true); categoryDOData.setLevel(2);
		 * categoryDOData.setParentId(parentId);
		 * categoryDOList.add(categoryDOData); } return categoryDOList;
		 */
	}

	@Override
	public CategoryDO getCategoryDOById(long id) {
		LogUtil.requestLog(log, "categoryServiceRef.getCategory", id);
		CategoryResult categoryResult = categoryServiceRef.getCategory(id);
		LogUtil.icResultLog(log, "categoryServiceRef.getCategory", categoryResult);
		return categoryResult.getCategroyDO();
	}

	@Override
	public CategoryVO getCategoryVOById(long id) {
		CategoryDO category = getCategoryDOById(id);
		if (category != null) {
			return CategoryVO.getCategoryVO(category);
		} else {
			return null;
		}
	}
}
