package com.yimayhd.harem.service;

import com.yimayhd.harem.model.Order;

/**
 * Created by Administrator on 2015/10/27.
 */
public interface OrderService {
    Order getById(long id)throws Exception;
}
