package com.yimayhd.palace.service;

import com.yimayhd.palace.model.vo.booth.BoothVO;

import java.util.List;

/**
 * Created by czf on 2016/4/13.
 */
public interface BoothService {
    /**
     * 获取banner列表
     * @return
     * @throws Exception
     */
    List<BoothVO> getList()throws Exception;
}
