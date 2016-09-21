package com.yimayhd.palace.repo.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.yimayhd.fhtd.logger.annot.MethodLogger;
import com.yimayhd.palace.util.PhoneUtil;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.service.UserService;

/**
 * 
 * @author wzf
 *
 */
public class UserRepo {
	private static final Logger logger = LoggerFactory.getLogger("UserRepo");

	@Resource
	private UserService userServiceRef;

	/**
	 * 通过ID查询用户信息
	 * 
	 * @param userId
	 * @return
	 */
	@MethodLogger
	public UserDO getUser(long userId) {
		if (userId <= 0) {
			return null;
		}
		return userServiceRef.getUserDOById(userId);
	}
	public UserDO getUser(long userId, boolean maskMobile) {
		if (userId <= 0) {
			return null;
		}
		return userServiceRef.getUserDOById(userId, maskMobile);
	}

	/**
	 * 根据手机号码查询用户
	 * @param mobile
	 * @return
	 */
	@MethodLogger
	public UserDO getUserByMobile(String mobile) {
		BaseResult<UserDO> userDOResult = userServiceRef.getUserByMobile(mobile);
		if( userDOResult == null || !userDOResult.isSuccess() ){
			return null;
		}
		return userDOResult.getValue();
	}
	
	@MethodLogger(isPrintResult=false)
	public List<UserDO> getUsers(List<Long> userIds) {
		if( CollectionUtils.isEmpty(userIds) ){
			return null ;
		}
		BaseResult<List<UserDO>> userDOResult = userServiceRef.getUserDOList(userIds);
		if( userDOResult == null || !userDOResult.isSuccess() ){
			logger.error("getUserDOList failed!  userIds={}, Result={}", JSON.toJSONString(userIds), JSON.toJSONString(userDOResult));
			return null;
		}
		List<UserDO> users = userDOResult.getValue();
		return users;
	}

	public String findMobileByUserId(Long id){
		BaseResult<String> mobile =  userServiceRef.findMobileByUserId(id);
		if(mobile.isSuccess()){
			return mobile.getValue();
		} else {
			return "";
		}
	}
	public Map<String, Long> getUserIdsByMobiles(List<String> mobiles){
		BaseResult<Map<String, Long>> result =  userServiceRef.getUserIdsByMobilesV2(mobiles);
		if( result == null || !result.isSuccess()){
			logger.error("getUserIdsByMobilesV2  mobiles={}, result={}", JSON.toJSONString(mobiles), JSON.toJSONString(result));
			return null ;
		}
		Map<String, Long> map = result.getValue();
		return map ;
	}
	public Map<Long, String> getMobilesByUserIds(List<Long> userIds){
		BaseResult<List<UserDO>> result =  userServiceRef.getUserDOList(userIds);
		if( result == null || !result.isSuccess()){
			logger.error("getUserDOList  mobiles={}, result={}", JSON.toJSONString(userIds), JSON.toJSONString(result));
			return null ;
		}
		List<UserDO> users = result.getValue();
		Map<Long, String> map = new HashMap<Long, String>();
		for( UserDO user : users ){
			long id = user.getId() ;
			String mobile = user.getUnmaskMobile() ;
			map.put(id, PhoneUtil.getMobileWithoutPrefix(mobile)) ;
		}
		return map ;
	}

}
