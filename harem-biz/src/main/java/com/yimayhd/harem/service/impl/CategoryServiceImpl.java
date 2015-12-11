package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.service.CategoryService;
import com.yimayhd.ic.client.model.domain.item.CategoryDO;
import com.yimayhd.ic.client.model.result.item.CategoryQryResult;
import com.yimayhd.ic.client.model.result.item.CategoryResult;
import com.yimayhd.ic.client.model.result.item.CategoryTreeResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/25.
 */
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private com.yimayhd.ic.client.service.item.CategoryService categoryServiceRef;
    @Override
    public List<CategoryDO> getCategoryDOList() throws Exception {
        CategoryQryResult categoryQryResult = categoryServiceRef.getCategoryList();
        return categoryQryResult.getCategroyDOList();
        /*List<CategoryDO> categoryDOList = new ArrayList<CategoryDO>();
        for (int i = 0; i < 4; i++) {
            CategoryDO categoryDOData = new CategoryDO();
            categoryDOData.setId(i);
            categoryDOData.setName("普通商品" + i);
            categoryDOData.setLeaf(false);
            categoryDOData.setLevel(1);
            categoryDOData.setParentId(0);
            categoryDOList.add(categoryDOData);
        }
        return categoryDOList;*/
    }

    @Override
    public List<CategoryDO> getCategoryDOList(long parentId) throws Exception {
        //TODO
        CategoryQryResult categoryQryResult = categoryServiceRef.getCategoryChildren();
        return categoryQryResult.getCategroyDOList();
        /*List<CategoryDO> categoryDOList = new ArrayList<CategoryDO>();
        for (int i = 0; i < 4; i++) {
            CategoryDO categoryDOData = new CategoryDO();
            categoryDOData.setId(10 + i);
            categoryDOData.setName("详细商品" + i);
            categoryDOData.setLeaf(0 == i % 2 ? false:true);
            categoryDOData.setLevel(2);
            categoryDOData.setParentId(parentId);
            categoryDOList.add(categoryDOData);
        }
        return categoryDOList;*/
    }

    @Override
    public CategoryDO getCategoryById(long id) throws Exception {
        CategoryResult categoryResult = categoryServiceRef.getCategory(id);
        return categoryResult.getCategroyDO();
    }
}
