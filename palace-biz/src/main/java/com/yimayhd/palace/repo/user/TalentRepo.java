package com.yimayhd.palace.repo.user;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yimayhd.fhtd.logger.annot.MethodLogger;
import com.yimayhd.palace.util.RepoUtils;
import com.yimayhd.user.client.dto.TalentDTO;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.service.TalentService;

/**
 * 
 * @author xiemingna
 *
 */
public class TalentRepo {
	private static final Logger log = LoggerFactory.getLogger("TalentRepo");

	@Resource
	private TalentService talentServiceRef;

	/**
	 * 通过ID查询达人用户信息
	 * 
	 * @param userId
	 * @return TalentDO
	 */
	@MethodLogger
	public BaseResult<TalentDTO> queryTalentInfo(long userId) {
		if (userId <= 0) {
			return null;
		}
		RepoUtils.requestLog(log, "talentServiceRef.getTalentDOById", userId);
		BaseResult<TalentDTO> result = talentServiceRef.queryTalentInfo(userId);
		RepoUtils.requestLog(log, "talentServiceRef.getTalentDOById", result);
		return result;
	}
}
