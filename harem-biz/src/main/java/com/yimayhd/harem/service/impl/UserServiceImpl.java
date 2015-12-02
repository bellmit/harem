package com.yimayhd.harem.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.User;
import com.yimayhd.harem.model.query.TradeMemberQuery;
import com.yimayhd.harem.model.query.UserListQuery;
import com.yimayhd.harem.service.UserService;
import com.yimayhd.membercenter.client.vo.MerchantPageQueryVO;
import com.yimayhd.user.client.domain.UserDO;

/**
 * @author czf
 */
public class UserServiceImpl implements UserService {
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
	// @Autowired
	// private MerchantService merchantServiceRef;

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
	public List<UserDO> getMemberByUserId(TradeMemberQuery tradeMemberQuery) throws Exception {
		MerchantPageQueryVO merchantPageQueryVO = new MerchantPageQueryVO();
		merchantPageQueryVO.setMerchantUserId(tradeMemberQuery.getMerchantUserId());
		merchantPageQueryVO.setNickName(tradeMemberQuery.getNickName());
		merchantPageQueryVO.setCity(tradeMemberQuery.getCityName());
		merchantPageQueryVO.setMobile(tradeMemberQuery.getTel());
		// MemResult<List<UserDO>> result =
		// merchantServiceRef.findPageUsersByMerchant(merchantPageQueryVO);
		// List<UserDO> userDOList= result.getValue();
		List<UserDO> userDOList = new ArrayList<UserDO>();
		for (int i = 0; i < 5; i++) {
			UserDO userDOData = new UserDO();
			userDOData.setId(i);
			userDOData.setNick("kaka");
			userDOData.setGender(i / 2);
			userDOData.setMobile("110");
			userDOData.setCertId("410123556487468535");
			userDOData.setBirthday(new Date());
			userDOData.setProvince("伊拉克");
			userDOData.setCity("中东");
			userDOData.setGmtCreate(new Date());
			userDOList.add(userDOData);
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
			if (StringUtils.isNotBlank(query.getUserName())) {
				if (user.getName().indexOf(query.getUserName()) != -1) {
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
