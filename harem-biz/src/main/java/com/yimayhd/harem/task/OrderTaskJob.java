package com.yimayhd.harem.task;

import org.springframework.stereotype.Component;


/**
 * 订单的定时任务处理
 *
 * @author liuhaiming
 */
@Component("orderTaskJob")
public class OrderTaskJob {
    /*private static final Logger log = LoggerFactory.getLogger(OrderTaskJob.class.getName());

    @Autowired
    private OrderService orderService;

    *//**
     * 每日零点清空order_seq表，并生成一个随机串
     *
     * @throws Exception
     *//*
    @Scheduled(cron = "0 0 0 * * ?")
    public void resetOrderSeq() throws Exception {
        orderService.modifyOrderSeq();
        log.info("零点重置order_seq表");
    }*/
}
