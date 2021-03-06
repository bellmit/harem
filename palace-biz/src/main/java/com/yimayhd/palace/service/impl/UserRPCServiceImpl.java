package com.yimayhd.palace.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.membercenter.client.domain.TravelKaVO;
import com.yimayhd.membercenter.client.result.MemPageResult;
import com.yimayhd.membercenter.client.service.MerchantService;
import com.yimayhd.membercenter.client.vo.MerchantPageQueryVO;
import com.yimayhd.palace.base.BaseException;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.User;
import com.yimayhd.palace.model.query.TradeMemberQuery;
import com.yimayhd.palace.repo.TravelKaRepo;
import com.yimayhd.palace.service.UserRPCService;
import com.yimayhd.palace.util.PhoneUtil;
import com.yimayhd.snscenter.client.domain.SnsTravelSpecialtyDO;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.domain.UserDOPageQuery;
import com.yimayhd.user.client.result.BasePageResult;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.service.UserService;

/**
 * @author czf
 */
public class UserRPCServiceImpl implements UserRPCService {
	private static Logger log = LoggerFactory.getLogger(UserRPCServiceImpl.class);

	@Autowired
	private MerchantService merchantServiceRef;
	@Autowired
	private UserService userServiceRef;
	@Autowired
	private TravelKaRepo travelKaRepo;

	@Override
	public List<User> getClubMemberListByClubId(long clubId) {
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
	public PageVO<UserDO> getMemberByUserId(long sellerId, TradeMemberQuery tradeMemberQuery) throws Exception {
		// 查询条件转换
		PageVO<UserDO> pageVO = new PageVO<UserDO>(tradeMemberQuery.getPageNumber(), tradeMemberQuery.getPageSize(), 0);
		MerchantPageQueryVO merchantPageQueryVO = new MerchantPageQueryVO();
		merchantPageQueryVO.setMerchantUserId(sellerId);
		merchantPageQueryVO.setPageNo(tradeMemberQuery.getPageNumber());
		merchantPageQueryVO.setPageSize(tradeMemberQuery.getPageSize());
		if (!StringUtils.isBlank(tradeMemberQuery.getNickName())) {
			merchantPageQueryVO.setNickName(tradeMemberQuery.getNickName());
		}
		if (!StringUtils.isBlank(tradeMemberQuery.getCityName())) {
			merchantPageQueryVO.setCity(tradeMemberQuery.getCityName());
		}
		if (!StringUtils.isBlank(tradeMemberQuery.getTel())) {
			merchantPageQueryVO.setMobile(tradeMemberQuery.getTel());
		}
		MemPageResult<UserDO> memResult = merchantServiceRef.findPageUsersByMerchant(merchantPageQueryVO);
		List<UserDO> userDOList = null;
		if (null != memResult && memResult.isSuccess()) {
			userDOList = memResult.getList();
			if (CollectionUtils.isNotEmpty(userDOList)) {
				for (UserDO userDO : userDOList) {
					// 用户电话
					userDO.setMobileNo(PhoneUtil.phoneFormat(userDO.getMobileNo()));
				}
				// TODO 总条数
				pageVO = new PageVO<UserDO>(tradeMemberQuery.getPageNumber(), tradeMemberQuery.getPageSize(),
						memResult.getTotalCount(), userDOList);
			}
		} else {
			log.error("检索会员信息失败：sellerId={},query={}", sellerId, JSON.toJSONString(tradeMemberQuery));
			log.error("检索会员信息失败：result={}", JSON.toJSONString(memResult));
			throw new BaseException("检索会员信息失败");
		}
		return pageVO;
	}

	@Override
	public PageVO<UserDO> getUserListByPage(UserDOPageQuery query)throws Exception{
		UserDOPageQuery userDOPageQuery = new UserDOPageQuery();
		userDOPageQuery.setPageSize(query.getPageSize());
		userDOPageQuery.setPageNo(query.getPageNo());
		if(StringUtils.isNotBlank(query.getNickname())){
			userDOPageQuery.setNickname(query.getNickname());
		}
		BasePageResult<UserDO> result = userServiceRef.findPageResultByCondition(userDOPageQuery);
		int totalCount = 0;
		List<UserDO> itemList = new ArrayList<UserDO>();
		if (result != null && result.isSuccess()) {
			totalCount = result.getTotalCount();
			if (CollectionUtils.isNotEmpty(result.getList())) {
				itemList.addAll(result.getList());
			}
		} else {
			log.error("查询用户列表失败：query={}", JSON.toJSONString(query));
			log.error("查询用户列表失败：result={}", JSON.toJSONString(result));
			throw new BaseException("查询用户列表失败");
		}
		return new PageVO<UserDO>(query.getPageNo(), query.getPageSize(), totalCount, itemList);
	}

	@Override
	public UserDO getUserById(long id) {
		BaseResult<UserDO> baseResult = userServiceRef.getUserDOByUserId(id);
		if(baseResult!=null&&baseResult.isSuccess()){
			return baseResult.getValue();
		}else {
			log.error("getUserById：result is null");
			return null;
		}
	}

	@Override
	public  PageVO<UserDO>  getTravelKaListByPage(UserDOPageQuery query) throws Exception{
		UserDOPageQuery userDOPageQuery = new UserDOPageQuery();
		userDOPageQuery.setPageSize(query.getPageSize());
		//userDOPageQuery.setStartIndex(new Long(query.getPageSize()));
		userDOPageQuery.setPageNo(query.getPageNo());
		if(StringUtils.isNotBlank(query.getNickname())){
			userDOPageQuery.setLikeNickname(query.getNickname());
		}
		userDOPageQuery.setOptionsList(query.getOptionsList());
		
		BasePageResult<UserDO> result = userServiceRef.findPageResultByCondition(userDOPageQuery);
		int totalCount = 0;
		List<UserDO> itemList = new ArrayList<UserDO>();
		if (result != null && result.isSuccess()) {
			totalCount = result.getTotalCount();
			if (CollectionUtils.isNotEmpty(result.getList())) {
				itemList.addAll(result.getList());
			}
		} else {
			log.error("查询用户列表失败：query={}", JSON.toJSONString(query));
			log.error("查询用户列表失败：result={}", JSON.toJSONString(result));
			throw new BaseException("查询用户列表失败");
		}
		return new PageVO<UserDO>(query.getPageNo(), query.getPageSize(), totalCount, itemList);
	}

	@Override
	public TravelKaVO getTravelKaById(long id) {
		return travelKaRepo.getTravelKaById(id);
	}

	@Override
	public Map<Long, UserDO> getUserListByIds(List<SnsTravelSpecialtyDO> specialDoList) {
		Map<Long, UserDO> map = new HashMap<Long, UserDO>();
		if(CollectionUtils.isEmpty(specialDoList)){
			return null;
		}
		List<Long> ids = new ArrayList<Long>();
		for(SnsTravelSpecialtyDO snsTravelSpecialtyDO : specialDoList){
			ids.add(snsTravelSpecialtyDO.getCreateId());
		}
//		List<UserDO> userDOList = userServiceRef.getUserInfoList(ids);
		BaseResult<List<UserDO>> baseResult =  userServiceRef.getUserDOList(ids);
		List<UserDO> userDOList = baseResult.getValue();
		if(CollectionUtils.isEmpty(userDOList)){
			return null;
		}
		for(UserDO userDO : userDOList){
			map.put(userDO.getId(), userDO);
		}
		return map;
	}

	@Override
	public UserDO getUserByMobile(String mobile) throws Exception {
		BaseResult<UserDO> userDOResult = userServiceRef.getUserByMobile(mobile);
		if( userDOResult == null || !userDOResult.isSuccess() ){
			return null;
		}
		return userDOResult.getValue();
	}

}
