package com.yimayhd.commission.controller;

import com.yimayhd.commission.biz.CommissionBiz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created with IntelliJ IDEA.
 * User: zhaoyue
 * Date: 2016/1/11
 * Time: 11:23
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class CommissionController  {

    private static final Logger logger = LoggerFactory.getLogger(CommissionController.class);

    @Autowired
    private CommissionBiz commissionBiz;


}
