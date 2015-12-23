package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.model.Club;
import com.yimayhd.harem.service.ClubService;
import com.yimayhd.snscenter.client.domain.ClubInfoDO;
import com.yimayhd.snscenter.client.domain.result.ClubDO;
import com.yimayhd.snscenter.client.domain.result.ClubDOList;
import com.yimayhd.snscenter.client.dto.ClubDOInfoDTO;
import com.yimayhd.snscenter.client.dto.ClubInfoAddDTO;
import com.yimayhd.snscenter.client.result.BasePageResult;
import com.yimayhd.snscenter.client.result.BaseResult;
import com.yimayhd.snscenter.client.service.SnsCenterService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2015/11/2.
 */
public class ClubServiceImpl implements ClubService {
	
	@Autowired SnsCenterService snsCenterService;
	
    @Override
    public List<ClubDO> getList(ClubDOInfoDTO clubVO) throws Exception {
    	BasePageResult<ClubDO> res = snsCenterService.getClubInfoListByQuery(clubVO);
    	if(null != res && res.isSuccess() && CollectionUtils.isNotEmpty(res.getList())){
    		return  res.getList();
    	}
		return null;
    }

    @Override
    public Club getById(long id) throws Exception {
        Club clubData = new Club();
        int i = 3;
        clubData.setId(id);
        clubData.setName("俱乐部" + i);//交易编号
        clubData.setLogoUrl("/123");
        clubData.setJoinStatus(1);
        clubData.setShowStatus(i / 2 + 1);
        clubData.setJoinNum(Long.valueOf(50 + i));
        clubData.setLimitNum(Long.valueOf(100 + i));
        clubData.setManageUserName("王武" + i);
        clubData.setManageUserLogoUrl("/456");
        clubData.setHasActivityNum(Long.valueOf(30*i));

        return clubData;
    }

    @Override
    public ClubInfoDO add(ClubInfoAddDTO clubInfoAddDTO) throws Exception {
    	BaseResult<ClubInfoDO>  res = snsCenterService.addClubInfo(clubInfoAddDTO);
    	if(null !=res && res.isSuccess()){
    		return res.getValue();
    	}
        return null;
    }

    @Override
    public void modify(Club club) throws Exception {

    }
}
