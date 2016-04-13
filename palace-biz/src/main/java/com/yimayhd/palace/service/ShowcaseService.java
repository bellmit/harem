package com.yimayhd.palace.service;

import com.yimayhd.palace.model.vo.booth.ShowcaseVO;

import java.util.List;

/**
 * Created by czf on 2016/4/13.
 */
public interface ShowcaseService {
    /**
     *  根据boothId获取showcase列表
     * @param boothId
     * @return
     * @throws Exception
     */
    List<ShowcaseVO> getList(long boothId)throws Exception;

    /**
     * 根据showcaseId获取showcase实体
     * @param id
     * @return
     * @throws Exception
     */
    ShowcaseVO getById(long id)throws Exception;

    /**
     * 新增showcase
     * @param entity
     * @return
     * @throws Exception
     */
    ShowcaseVO add(ShowcaseVO entity)throws Exception;

    /**
     *  修改showcase
     * @param entity
     * @return
     * @throws Exception
     */
    ShowcaseVO modify(ShowcaseVO entity)throws Exception;
}
