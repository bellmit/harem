package com.yimayhd.harem.service;

import java.util.List;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.User;
import com.yimayhd.harem.model.query.UserListQuery;

/**
 * @author
 */
public interface UserService {

	/**
	 * 根据俱乐部ID获取俱乐部成员列表
	 * 
	 * @param clubId
	 *            俱乐部ID
	 * @return
	 * @throws Exception
	 */
	List<User> getClubMemberListByClubId(long clubId) throws Exception;

	/**
	 * 获取用户列表
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	List<User> getList(User user) throws Exception;

	/**
	 * 获取用户列表
	 * 
	 * @param query
	 * @return
	 * @throws Exception
	 */
	PageVO<User> getListByPage(UserListQuery query) throws Exception;

	/**
	 * 获取用户信息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	User getById(long id) throws Exception;
}
