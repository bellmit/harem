package com.yimayhd.palace.service.impl;

import com.yimayhd.palace.base.BaseServiceImpl;
import com.yimayhd.palace.mapper.HaMenuMapper;
import com.yimayhd.palace.model.HaMenuDO;
import com.yimayhd.palace.service.HaMenuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 菜单表
 * 
 * @author czf
 */
// @Service
public class HaMenuServiceImpl extends BaseServiceImpl<HaMenuDO> implements HaMenuService {

	@Autowired
	private HaMenuMapper haMenuMapper;

	@Override
	protected void initBaseMapper() {
		setBaseMapper(haMenuMapper);
	}

	@Override
	public List<HaMenuDO> getMenuListByUserId(long id) throws Exception {
		return haMenuMapper.getMenuListByUserId(id);
	}

	@Override
	public List<HaMenuDO> getUrlListByUserId(long id) throws Exception {
		return haMenuMapper.getUrlListByUserId(id);
	}

	@Override
	public List<HaMenuDO> getMenuList() throws Exception {
		return haMenuMapper.getMenuList();
	}
}
