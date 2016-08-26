package com.yimayhd.palace.repo;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.palace.util.RepoUtils;
import com.yimayhd.resourcecenter.domain.MediaDO;
import com.yimayhd.resourcecenter.model.query.MediaPageQuery;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.service.MediaClientService;
import com.yimayhd.solrsearch.client.base.SolrsearchPageResult;
import com.yimayhd.solrsearch.client.domain.SolrHotelDO;
import com.yimayhd.solrsearch.client.domain.SolrScenicDO;
import com.yimayhd.solrsearch.client.domain.query.SolrsearchDTO;
import com.yimayhd.solrsearch.client.service.SolrsearchService;

/**
 * 资源搜索
 * 
 * @author xiemingna
 *
 */
public class SolrsearchRepo {
	private static final Logger log = LoggerFactory.getLogger("DestinationRepo");

	@Resource
	private SolrsearchService solrsearchServiceRef;
	@Autowired
	private MediaClientService mediaClientServiceRef;
	/**
	 * 
	 * @param queryHotelListByPage
	 * @return
	 */
	public SolrsearchPageResult<SolrHotelDO> queryHotelListByPage(SolrsearchDTO solrsearchDTO) {
		if (solrsearchDTO == null) {
			return null;
		}
		RepoUtils.requestLog(log, "destinationService.queryDestinationList", solrsearchDTO);
		SolrsearchPageResult<SolrHotelDO> result = solrsearchServiceRef.queryHotelListByPage(solrsearchDTO);
		RepoUtils.requestLog(log, "destinationService.queryDestinationList", result);
		return result;
	}
	public SolrsearchPageResult<SolrScenicDO> queryScenicListByPage(SolrsearchDTO solrsearchDTO) {
		if (solrsearchDTO == null) {
			return null;
		}
		RepoUtils.requestLog(log, "destinationService.queryDestinationList", solrsearchDTO);
		SolrsearchPageResult<SolrScenicDO> result = solrsearchServiceRef.queryScenicListByPage(solrsearchDTO);
		RepoUtils.requestLog(log, "destinationService.queryDestinationList", result);
		return result;
	}
	
	public RCPageResult<MediaDO> queryAudioPageResult(MediaPageQuery mediaPageQuery) {
		//MediaPageQuery mediaPageQuery = new MediaPageQuery();
		RepoUtils.requestLog(log, "mediaClientServiceRef.query", mediaPageQuery);
		RCPageResult<MediaDO> pageResult = mediaClientServiceRef.query(mediaPageQuery);
		RepoUtils.requestLog(log, "mediaClientServiceRef.query", pageResult);
		return pageResult;
	}
}
