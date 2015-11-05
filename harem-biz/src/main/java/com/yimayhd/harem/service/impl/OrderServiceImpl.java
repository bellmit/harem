package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.model.Commodity;
import com.yimayhd.harem.model.Order;
import com.yimayhd.harem.service.OrderService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/27.
 */
public class OrderServiceImpl implements OrderService {
    @Override
    public Order getById(long id) throws Exception {
        Order order = new Order();
        order.setOrderId(10086);
        order.setOrderNO("2015102710001");
        List<Commodity> commodityList = new ArrayList<Commodity>();
        Commodity commodity = new Commodity();
        commodity.setOrderId(Long.valueOf(10086));
        commodity.setName("圆明园门票");
        commodity.setPrice(BigDecimal.valueOf(15));
        commodity.setNumber(Long.valueOf(2));
        Commodity commodity2 = new Commodity();
        commodity2.setOrderId(Long.valueOf(10086));
        commodity2.setName("故宫门票");
        commodity2.setPrice(BigDecimal.valueOf(60));
        commodity2.setNumber(Long.valueOf(2));
        commodityList.add(commodity);
        commodityList.add(commodity2);
        order.setCommodityList(commodityList);
        return order;
    }
}
