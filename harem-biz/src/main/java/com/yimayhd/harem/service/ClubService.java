package com.yimayhd.harem.service;

import com.yimayhd.harem.model.Club;
import com.yimayhd.snscenter.client.domain.ClubInfoDO;
import com.yimayhd.snscenter.client.domain.result.ClubDO;
import com.yimayhd.snscenter.client.domain.result.ClubDOList;
import com.yimayhd.snscenter.client.dto.ClubDOInfoDTO;
import com.yimayhd.snscenter.client.dto.ClubInfoAddDTO;

import java.util.List;

/**
 * Created by Administrator on 2015/11/2.
 */
public interface ClubService {
    /**
     * 获取俱乐部列表(可带查询条件)
     * @return 俱乐部列表
     */
	List<ClubDO>  getList(ClubDOInfoDTO club)throws Exception;
    /**
     * 获取俱乐部详情
     * @return 俱乐部详情
     */
    Club getById(long id)throws Exception;

    /**
     * 添加俱乐部
     * @param club
     * @return
     * @throws Exception
     */
    ClubInfoDO add(ClubInfoAddDTO clubInfoAddDTO)throws Exception;

    /**
     * 修改俱乐部
     * @param club
     * @throws Exception
     */
    void modify(Club club)throws Exception;

}
