package com.yimayhd.palace.service.impl;

import com.yimayhd.membercenter.client.dto.UserMenuOptionDTO;
import com.yimayhd.membercenter.client.enums.HaMenuProjectCode;
import com.yimayhd.membercenter.client.query.UserMenuQuery;
import com.yimayhd.membercenter.client.result.MemPageResult;
import com.yimayhd.membercenter.client.service.UserPermissionService;
import com.yimayhd.palace.base.BaseServiceImpl;
import com.yimayhd.palace.mapper.HaMenuMapper;
import com.yimayhd.palace.model.HaMenuDO;
import com.yimayhd.palace.service.HaMenuService;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.session.manager.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 菜单表
 * 
 * @author czf
 */
// @Service
public class HaMenuServiceImpl extends BaseServiceImpl<HaMenuDO> implements HaMenuService {

	private Logger LOGGER = LoggerFactory.getLogger(getClass());
	@Autowired
	private HaMenuMapper haMenuMapper;

	@Autowired
	private UserPermissionService userPermissionService;
	@Autowired
	private SessionManager sessionManager;

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
	/**
	 * 同一权限调用，调用membercenter的权限接口
	 * 根据用户id获取菜单权限列表
	 *
	 * @param  userId 用户id
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<com.yimayhd.membercenter.client.domain.HaMenuDO> getMenuListByUserIdFromCatch(long userId) throws Exception {

		// WebResult<List<com.yimayhd.membercenter.client.domain.HaMenuDO>> result = new WebResult<List<com.yimayhd.membercenter.client.domain.HaMenuDO>>();
		UserMenuQuery userMenuQuery = new UserMenuQuery();
		userMenuQuery.setUserId(userId);
		//userMenuQuery.setDomain(Constant.DOMAIN_JIUXIU);
		userMenuQuery.setProjectCode(HaMenuProjectCode.PALACE.getCode());
		UserMenuOptionDTO dto = new UserMenuOptionDTO();
		dto.setContainUrl(false);
		MemPageResult<com.yimayhd.membercenter.client.domain.HaMenuDO> queryResult = userPermissionService.getMenuListByUserIdFromCatch(userMenuQuery,dto);
		if (queryResult == null || !queryResult.isSuccess() ) {
			LOGGER.error("getMenuListByUserId failed  userId={}",userId);
			//result.setWebReturnCode(WebReturnCode.REMOTE_CALL_FAILED);
			return null ;
		}
		return queryResult.getList();
	}


	/**
	 * 更新用户菜单缓存
	 *
	 * @param token
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean cacheMenuListByUserId(String token) throws Exception {
		UserDO userDO =sessionManager.getUser(token);
		if(userDO!=null) {
			UserMenuQuery userMenuQuery = new UserMenuQuery();
			userMenuQuery.setUserId(userDO.getId());
			//userMenuQuery.setDomain(Constant.DOMAIN_JIUXIU);
			userMenuQuery.setProjectCode(HaMenuProjectCode.PALACE.getCode());
			UserMenuOptionDTO dto = new UserMenuOptionDTO();
			dto.setContainUrl(false);
			boolean queryResult = userPermissionService.catchUserMenu(userMenuQuery, dto);

			return queryResult;
		}else {
			LOGGER.error("cacheMenuListByUserId  getUser(token) failed  token={}",token);
			return false;
		}
	}


}
