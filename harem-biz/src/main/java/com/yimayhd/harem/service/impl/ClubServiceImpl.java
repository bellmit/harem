package com.yimayhd.harem.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.dto.TagRelationInfoDTO;
import com.yimayhd.commentcenter.client.enums.BaseStatus;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.commentcenter.client.service.ComCenterService;
import com.yimayhd.harem.base.BaseException;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.Club;
import com.yimayhd.harem.model.ClubAdd;
import com.yimayhd.harem.model.query.ClubListQuery;
import com.yimayhd.harem.service.ClubService;
import com.yimayhd.harem.util.RepoUtils;
import com.yimayhd.snscenter.client.domain.ClubInfoDO;
import com.yimayhd.snscenter.client.domain.result.ClubDO;
import com.yimayhd.snscenter.client.dto.ClubDOInfoDTO;
import com.yimayhd.snscenter.client.result.BasePageResult;
import com.yimayhd.snscenter.client.result.BaseResult;
import com.yimayhd.snscenter.client.service.SnsCenterService;

/**
 * Created by Administrator on 2015/11/2.
 */
public class ClubServiceImpl implements ClubService {

	private static final Logger log = LoggerFactory.getLogger(ClubServiceImpl.class);

	@Autowired
	SnsCenterService snsCenterService;

	@Autowired
	ComCenterService comCenterService;

	@Override
	public PageVO<ClubDO> pageQueryClub(ClubDOInfoDTO query) throws Exception {
		RepoUtils.requestLog(log, "snsCenterService.getClubInfoListByQuery", query);
		BasePageResult<ClubDO> res = snsCenterService.getClubInfoListByQuery(query);
		int totalCount = 0;
		List<ClubDO> itemList = new ArrayList<ClubDO>();
		if (null != res && res.isSuccess()) {
			totalCount = res.getTotalCount();
			if (CollectionUtils.isNotEmpty(res.getList())) {
				itemList.addAll(res.getList());
			}
			log.info("Request {} success", "snsCenterService.getClubInfoListByQuery");
		} else {
			log.error("Request {} error: query={} result:", "snsCenterService.getClubInfoListByQuery",JSON.toJSONString(query),JSON.toJSONString(res));
			throw new BaseException("get page club list failure");
		}
		return new PageVO<ClubDO>(query.getPageNo(), query.getPageSize(), totalCount, itemList);
	}

	@Override
	public Club getById(long id) throws Exception {
		Club clubData = new Club();
		int i = 3;
		clubData.setId(id);
		clubData.setName("俱乐部" + i);// 交易编号
		clubData.setLogoUrl("/123");
		clubData.setJoinStatus(1);
		clubData.setShowStatus(i / 2 + 1);
		clubData.setJoinNum(Long.valueOf(50 + i));
		clubData.setLimitNum(Long.valueOf(100 + i));
		clubData.setManageUserName("王武" + i);
		clubData.setManageUserLogoUrl("/456");
		clubData.setHasActivityNum(Long.valueOf(30 * i));

		return clubData;
	}

	@Override
	public ClubAdd add(ClubAdd clubAdd, List<Long> themeIds) throws Exception {
		if (null == clubAdd || CollectionUtils.isEmpty(themeIds)) {
			throw new Exception("add error,Parameters [clubAdd or themeIds ] cannot be empty");
		}
		BaseResult<ClubInfoDO> res = snsCenterService.addClubInfo(clubAdd);
		if (null != res && res.isSuccess() && null != res.getValue()) {
			clubAdd.setId(res.getValue().getId());
			clubAdd.setCreateTime(res.getValue().getCreateTime());
			TagRelationInfoDTO tagRelationInfoDTO = new TagRelationInfoDTO();
			tagRelationInfoDTO.setList(themeIds);
			tagRelationInfoDTO.setOutId(res.getValue().getId());
			tagRelationInfoDTO.setTagType(TagType.CLUBTAG.getType());
			tagRelationInfoDTO.setOrderTime(res.getValue().getCreateTime());

			com.yimayhd.commentcenter.client.result.BaseResult<Boolean> resTag = comCenterService
					.addTagRelationInfo(tagRelationInfoDTO);
			if (null != resTag && resTag.isSuccess()) {
				return clubAdd;
			} else {
				log.error(" add,Club related theme failure,clubAdd=" + JSON.toJSONString(clubAdd)
						+ " ,tagRelationInfoDTO=" + JSON.toJSONString(tagRelationInfoDTO));
			}
		}
		return null;
	}

	@Override
	public boolean modify(ClubDOInfoDTO club) throws Exception {
		BaseResult<Boolean> res = null;
		if (null != res && res.isSuccess()) {
			return res.getValue();
		}
		return false;
	}

	@Override
	public boolean batchUpOrDownStatus(List<Long> ids, int status) throws Exception {
		if (CollectionUtils.isEmpty(ids)) {
			throw new Exception("Parameters [ids] cannot be empty");
		}
		ClubDOInfoDTO clubDOInfoDTO = new ClubDOInfoDTO();
		clubDOInfoDTO.setIdList(ids);
		if (status == BaseStatus.AVAILABLE.getType()) {
			clubDOInfoDTO.setState(BaseStatus.DELETED.getType());
		} else {
			clubDOInfoDTO.setState(BaseStatus.AVAILABLE.getType());
		}
		BaseResult<Boolean> res = snsCenterService.updateClubStateByIdList(clubDOInfoDTO);
		if (null != res && res.isSuccess()) {
			return true;
		}
		return false;
	}
	
	public static void main(String[] args){
		
		System.out.println(String.format("Request {} error: query={} result:", "snsCenterService.getClubInfoListByQuery","**********","$$$$$$$$$$"));
	}
}
