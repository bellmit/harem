package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.model.SendPointRule;
import com.yimayhd.harem.service.SendPointRuleService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/11/9.
 */
public class SendPointRuleServiceImpl implements SendPointRuleService {
    @Override
    public SendPointRule getSendPointRuleNow() throws Exception {
        SendPointRule sendPointRule = new SendPointRule();
        sendPointRule.setId((long) 1);
        sendPointRule.setRatio((long) 10000);
        return sendPointRule;
    }

    @Override
    public List<SendPointRule> getSendPointRuleHistory() throws Exception {
        List<SendPointRule> sendPointRuleList = new ArrayList<SendPointRule>();
        for (int i = 0; i < 10; i++) {
            SendPointRule sendPointRuleData = new SendPointRule();
            sendPointRuleData.setRatio((long) (5000 * (i + 1)));
            sendPointRuleData.setAvaiablepPeriod(60 * (i + 1));
            sendPointRuleData.setEffectiveDate(new Date());
            sendPointRuleData.setOperaName("留名");
            sendPointRuleList.add(sendPointRuleData);
        }
        return sendPointRuleList;
    }

    @Override
    public SendPointRule add(SendPointRule sendPointRule) throws Exception {
        return null;
    }
}
