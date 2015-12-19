package com.yimayhd.harem.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.User;
import com.yimayhd.harem.model.query.TradeMemberQuery;
import com.yimayhd.harem.service.UserRPCService;
import com.yimayhd.harem.util.PhoneUtil;
import com.yimayhd.membercenter.client.domain.TravelKaVO;
import com.yimayhd.membercenter.client.query.TravelkaPageQuery;
import com.yimayhd.membercenter.client.result.MemPageResult;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.client.service.MerchantService;
import com.yimayhd.membercenter.client.service.TravelKaService;
import com.yimayhd.membercenter.client.vo.MerchantPageQueryVO;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.domain.UserDOPageQuery;
import com.yimayhd.user.client.result.BasePageResult;
import com.yimayhd.user.client.service.UserService;

/**
 * @author czf
 */
public class UserRPCServiceImpl implements UserRPCService {
	private static Logger log = LoggerFactory.getLogger(UserRPCServiceImpl.class);

	@Autowired
	private MerchantService merchantServiceRef;
	@Autowired
	private TravelKaService travelKaServiceRef;
	@Autowired
	private UserService userServiceRef;

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
			log.error(MessageFormat.format("sellerId={0},query={1}", sellerId, JSON.toJSONString(tradeMemberQuery)));
			log.error(MessageFormat.format("查询会员列表失败：code={0},msg={1}", memResult.getErrorCode(),
					memResult.getErrorMsg()));
		}
		return pageVO;
	}

	@Override
	public PageVO<UserDO> getUserListByPage(UserDOPageQuery query) {
		BasePageResult<UserDO> result = userServiceRef.findPageResultByCondition(query);
		int totalCount = 0;
		List<UserDO> itemList = new ArrayList<UserDO>();
		if (result != null && result.isSuccess()) {
			totalCount = result.getTotalCount();
			if (CollectionUtils.isNotEmpty(result.getList())) {
				itemList.addAll(result.getList());
			}
		} else {
			log.error(MessageFormat.format("query={0}", JSON.toJSONString(query)));
			log.error(MessageFormat.format("查询用户列表失败：code={0},msg={1}", result.getErrorCode(), result.getErrorMsg()));
		}
		return new PageVO<UserDO>(query.getPageNo(), query.getPageSize(), totalCount, itemList);
	}

	@Override
	public UserDO getUserById(long id) {
		UserDO user = userServiceRef.getUserDOById(id);
		return user;
	}

	@Override
	public PageVO<TravelKaVO> getTravelKaListByPage(TravelkaPageQuery query) {
		MemPageResult<TravelKaVO> travelKaListPage = travelKaServiceRef.getTravelKaListManagerPage(query);
		List<TravelKaVO> result = new ArrayList<TravelKaVO>();
		int totalCount = 0;
		if (travelKaListPage != null & travelKaListPage.isSuccess()) {
			totalCount = travelKaListPage.getTotalCount();
			result = travelKaListPage.getList();
		} else {
			log.error(MessageFormat.format("query={0}", JSON.toJSONString(query)));
			log.error(MessageFormat.format("查询旅游咖列表失败：code={0},msg={1}", travelKaListPage.getErrorCode(),
					travelKaListPage.getErrorMsg()));
		}
		return new PageVO<TravelKaVO>(query.getPageNo(), query.getPageSize(), totalCount, result);
	}

	@Override
	public TravelKaVO getTravelKaById(long id) {
		MemResult<TravelKaVO> travelKaDetail = travelKaServiceRef.getTravelKaDetail(id);
		if (travelKaDetail != null & travelKaDetail.isSuccess()) {
			return travelKaDetail.getValue();
		} else {
			log.error(MessageFormat.format("id={0}", id));
			log.error(MessageFormat.format("查询旅游咖信息失败：code={0},msg={1}", travelKaDetail.getErrorCode(),
					travelKaDetail.getErrorMsg()));
		}
		return null;
	}

}
