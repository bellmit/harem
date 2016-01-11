package com.yimayhd.commission.biz;

import com.yimayhd.commission.repo.CommissionRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: zhaoyue
 * Date: 2016/1/11
 * Time: 13:40
 * To change this template use File | Settings | File Templates.
 */
public class CommissionBiz {

    private static final Logger logger = LoggerFactory.getLogger(CommissionBiz.class);

    @Autowired
    private CommissionRepo commissionRepo;



}
