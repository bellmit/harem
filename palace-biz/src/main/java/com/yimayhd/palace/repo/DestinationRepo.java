package com.yimayhd.palace.repo;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yimayhd.fhtd.logger.annot.MethodLogger;
import com.yimayhd.palace.util.RepoUtils;
import com.yimayhd.resourcecenter.domain.DestinationDO;
import com.yimayhd.resourcecenter.model.query.DestinationQueryDTO;
import com.yimayhd.resourcecenter.model.result.RcResult;
import com.yimayhd.resourcecenter.service.DestinationService;

/**
 * 出发地
 * 
 * @author yebin
 *
 */
public class DestinationRepo {
	private static final Logger log = LoggerFactory.getLogger("DestinationRepo");

	@Resource
	private DestinationService destinationService;

	/**
	 * 根据城市的codelist获取城市list
	 * 
	 * @param aDestinationQueryDTO
	 * @return
	 */
	@MethodLogger
	public RcResult<List<DestinationDO>> queryDestinationList(DestinationQueryDTO aDestinationQueryDTO) {
		if (aDestinationQueryDTO == null) {
			return null;
		}
		RepoUtils.requestLog(log, "destinationService.queryDestinationList", aDestinationQueryDTO);
		RcResult<List<DestinationDO>> result = destinationService.queryDestinationList(aDestinationQueryDTO);
		RepoUtils.requestLog(log, "destinationService.queryDestinationList", result);
		return result;
	}
}
