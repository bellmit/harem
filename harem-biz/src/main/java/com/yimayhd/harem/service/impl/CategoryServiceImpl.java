package com.yimayhd.harem.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.harem.model.CategoryVO;
import com.yimayhd.harem.service.CategoryRPCService;
import com.yimayhd.harem.service.CategoryService;
import com.yimayhd.ic.client.model.domain.item.CategoryDO;

/**
 * Created by Administrator on 2015/11/25.
 */
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRPCService categoryRPCService;

	@Override
	public List<CategoryDO> getCategoryDOList() throws Exception {
		return categoryRPCService.getCategoryList();
	}

	@Override
	public List<CategoryDO> getCategoryDOList(long parentId) throws Exception {
		return categoryRPCService.getCategoryList(parentId);
	}

	@Override
	public CategoryVO getCategoryById(long id) throws Exception {
		CategoryDO category = categoryRPCService.getCategoryById(id);
		if (category != null) {
			return CategoryVO.getCategoryVO(category);
		} else {
			return null;
		}
	}
}
