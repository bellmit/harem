package com.yimayhd.harem.repo;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.harem.base.BaseException;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.util.RepoUtils;
import com.yimayhd.snscenter.client.domain.SnsActivityDO;
import com.yimayhd.snscenter.client.dto.ActivityInfoDTO;
import com.yimayhd.snscenter.client.dto.ActivityQueryDTO;
import com.yimayhd.snscenter.client.result.BasePageResult;
import com.yimayhd.snscenter.client.result.BaseResult;
import com.yimayhd.snscenter.client.service.SnsCenterService;

/**
 * 活动
 * 
 * @author yebin
 *
 */
public class ActivityRepo {
	private final Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private SnsCenterService snsCenterServiceRef;

	public PageVO<SnsActivityDO> pageQueryActivities(ActivityQueryDTO activityQueryDTO) {
		RepoUtils.requestLog(log, "snsCenterServiceRef.getActivityPage", activityQueryDTO);
		BasePageResult<SnsActivityDO> scResult = snsCenterServiceRef.getActivityPage(activityQueryDTO);
		RepoUtils.resultLog(log, "snsCenterServiceRef.getActivityPage", scResult);
		int totalCount = scResult.getTotalCount();
		List<SnsActivityDO> itemList = scResult.getList();
		return new PageVO<SnsActivityDO>(activityQueryDTO.getPageNo(), activityQueryDTO.getPageSize(), totalCount,
				itemList);
	}

	public SnsActivityDO getActivityById(long id) {
		RepoUtils.requestLog(log, "snsCenterServiceRef.getActivityPage", id);
		BaseResult<SnsActivityDO> scResult = snsCenterServiceRef.getActivityInfoByActivityId(id);
		RepoUtils.resultLog(log, "snsCenterServiceRef.getActivityPage", scResult);
		return scResult.getValue();
	}

	public SnsActivityDO updateActivityInfo(ActivityInfoDTO activityinfodto) {
		RepoUtils.requestLog(log, "snsCenterServiceRef.updateActivityInfo");
		BaseResult<SnsActivityDO> scResult = snsCenterServiceRef.updateActivityInfo(activityinfodto);
		RepoUtils.resultLog(log, "snsCenterServiceRef.updateActivityInfo", scResult);
		return scResult.getValue();
	}

	public SnsActivityDO addActivityInfo(ActivityInfoDTO activityinfodto) {
		RepoUtils.requestLog(log, "snsCenterServiceRef.addActivityInfo");
		BaseResult<SnsActivityDO> scResult = snsCenterServiceRef.addActivityInfo(activityinfodto);
		RepoUtils.resultLog(log, "snsCenterServiceRef.addActivityInfo", scResult);
		return scResult.getValue();
	}

	public boolean updateActivityStateByIList(List<Long> ids, int state) {
		if (CollectionUtils.isEmpty(ids)) {
			throw new BaseException("snsCenterServiceRef.updateActivityStateByIList ids is empty");
		}
		ActivityQueryDTO dto = new ActivityQueryDTO();
		dto.setActivityIdList(ids);
		dto.setState(state);
		RepoUtils.requestLog(log, "snsCenterServiceRef.updateActivityStateByIList");
		BaseResult<Boolean> scResult = snsCenterServiceRef.updateActivityStateByIList(dto);
		RepoUtils.resultLog(log, "snsCenterServiceRef.updateActivityStateByIList", scResult);
		return scResult.getValue();
	}

}
