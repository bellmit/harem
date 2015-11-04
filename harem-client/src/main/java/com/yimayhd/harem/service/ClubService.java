package com.yimayhd.harem.service;

import com.yimayhd.harem.model.Club;

import java.util.List;

/**
 * Created by Administrator on 2015/11/2.
 */
public interface ClubService {
    /**
     * 获取俱乐部列表(可带查询条件)
     * @return 俱乐部列表
     */
    List<Club> getList(Club clubVO)throws Exception;
    /**
     * 获取俱乐部详情
     * @return 俱乐部详情
     */
    Club getById(long id)throws Exception;

}
