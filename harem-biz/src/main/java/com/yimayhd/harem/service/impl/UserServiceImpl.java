package com.yimayhd.harem.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yimayhd.harem.util.PhoneUtil;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.client.service.MerchantService;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.User;
import com.yimayhd.harem.model.query.TradeMemberQuery;
import com.yimayhd.harem.model.query.UserListQuery;
import com.yimayhd.harem.service.UserService;
import com.yimayhd.membercenter.client.vo.MerchantPageQueryVO;
import com.yimayhd.user.client.domain.UserDO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author czf
 */
public class UserServiceImpl implements UserService {

	@Autowired
	private MerchantService merchantServiceRef;

	private List<User> table = new ArrayList<User>();

	public UserServiceImpl() {
		for (long i = 0; i < 100; i++) {
			try {
				table.add(getById(i));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	@Override
	public List<User> getClubMemberListByClubId(long clubId) throws Exception {
		List<User> userList = new ArrayList<User>();
		for (int i = 0; i < 2; i++) {
			User user = new User();
			user.setTel("1803926207" + i);
			user.setName("tiny" + i);
			user.setUserName("李四" + i);
			user.setGmtCreated(new Date());
			userList.add(user);
		}
		return userList;
	}

	@Override
	public List<User> getUserList(User user) throws Exception {
		List<User> userList = new ArrayList<User>();
		for (int i = 0; i < 5; i++) {
			User userData = new User();
			userData.setId((long) i);
			userData.setUserName("Gost");
			userData.setRealName("宋江");
			userData.setGender(1);
			userData.setCard("4101831900");
			userData.setAge(18);
			userData.setHomePlace("湖南老家");
			userData.setProvinceId((long) 10010);
			userData.setProvinceName("湖南");
			userData.setCityName("郴州");
			userData.setGmtCreated(new Date());
			userData.setRemark("备注");
			userList.add(userData);
		}
		return userList;
	}

	@Override
	public List<UserDO> getMemberByUserId(long sellerId,TradeMemberQuery tradeMemberQuery) throws Exception {
		//查询条件转换
		MerchantPageQueryVO merchantPageQueryVO = new MerchantPageQueryVO();
		merchantPageQueryVO.setMerchantUserId(sellerId);
		merchantPageQueryVO.setPageNo(tradeMemberQuery.getPageNumber());
		merchantPageQueryVO.setPageSize(tradeMemberQuery.getPageSize());
		if(!StringUtils.isBlank(tradeMemberQuery.getNickName())){
			merchantPageQueryVO.setNickName(tradeMemberQuery.getNickName());
		}
		if(!StringUtils.isBlank(tradeMemberQuery.getCityName())){
			merchantPageQueryVO.setCity(tradeMemberQuery.getCityName());
		}
		if(!StringUtils.isBlank(tradeMemberQuery.getTel())){
			merchantPageQueryVO.setMobile(tradeMemberQuery.getTel());
		}
		MemResult<List<UserDO>> memResult = merchantServiceRef.findPageUsersByMerchant(merchantPageQueryVO);
		List<UserDO> userDOList = null;
		if(null != memResult && memResult.isSuccess()){
			userDOList = memResult.getValue();
			if(CollectionUtils.isNotEmpty(userDOList)){
				for (UserDO userDO :userDOList){
					//用户电话
					userDO.setMobileNo(PhoneUtil.phoneFormat(userDO.getMobileNo()));
				}
			}
		}
		return userDOList;
	}

	@Override
	public PageVO<User> getUserListByPage(UserListQuery query) throws Exception {
		int totalCount = count(query);
		PageVO<User> page = new PageVO<User>(query.getPageNumber(), query.getPageSize(), totalCount);
		List<User> itemList = find(query);
		page.setItemList(itemList);
		return page;
	}

	/**
	 * 查找总数
	 * 
	 * @param query
	 * @return
	 * @throws Exception
	 */
	protected Integer count(UserListQuery query) throws Exception {
		return query(table, query).size();
	}

	protected List<User> find(UserListQuery query) throws Exception {
		int fromIndex = query.getPageSize() * (query.getPageNumber() - 1);
		int toIndex = query.getPageSize() * query.getPageNumber();
		List<User> result = query(table, query);
		if (result.size() > 0) {
			if (toIndex > result.size()) {
				toIndex = result.size();
			}
			result = result.subList(fromIndex, toIndex);
		}
		return result;
	}

	private List<User> query(List<User> users, UserListQuery query) {
		List<User> result = new ArrayList<User>();
		for (User user : users) {
			if (StringUtils.isNotBlank(query.getName())) {
				if (user.getName().indexOf(query.getName()) != -1) {
					result.add(user);
				}
			} else {
				result.add(user);
			}
		}
		return result;
	}

	@Override
	public User getById(long id) throws Exception {
		User userData = new User();
		userData.setId(id);
		userData.setName("用户" + id);
		userData.setUserName("Gost" + id);
		userData.setRealName("宋江" + id);
		userData.setGender(1);
		userData.setCard("4101831900");
		userData.setAge(18);
		userData.setHomePlace("湖南老家");
		userData.setProvinceId((long) 10010);
		userData.setProvinceName("湖南");
		userData.setCityName("郴州");
		userData.setGmtCreated(new Date());
		userData.setRemark("备注");
		return userData;
	}

}
