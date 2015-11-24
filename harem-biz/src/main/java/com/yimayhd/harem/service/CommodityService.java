package com.yimayhd.harem.service;

import com.yimayhd.ic.client.model.domain.item.ItemDO;

import java.util.List;

/**
 * Created by Administrator on 2015/11/24.
 */
public interface CommodityService {
    List<ItemDO> getList()throws Exception;
}
