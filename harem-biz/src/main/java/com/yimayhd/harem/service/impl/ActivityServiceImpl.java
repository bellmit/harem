package com.yimayhd.harem.service.impl;

import com.taobao.common.tfs.TfsManager;
import com.yimayhd.harem.model.Activity;
import com.yimayhd.harem.model.Club;
import com.yimayhd.harem.service.ActivityService;
import com.yimayhd.harem.service.ClubService;
import com.yimayhd.harem.util.HttpRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/11/2.
 */
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private TfsManager tfsManager;

    @Override
    public List<Activity> getList(Activity activity) throws Exception {
        List<Activity> activityList = new ArrayList<Activity>();
        for (int i = 0; i < 6; i++) {
            Activity activityData = new Activity();

            activityData.setId((long) i);
            activityData.setOutId((long) 100);
            activityData.setTitle("活动标题");
            activityData.setClubName("古迹俱乐部");
            activityData.setMemberCount(32);
            activityData.setOriginalPrice((long) 150000);
            activityData.setPreferentialPrice((long) 10000);
            activityData.setStatus(2);
            activityData.setImage("/1.png");
            activityData.setModifyTime(new Date());
            activityData.setCreaterTime(new Date());
            activityData.setOutType(1);
            activityData.setFavorName("会员优惠");
            activityData.setFavorComent("八折");
            activityData.setRecruitingGroups("招募的对象");
            activityData.setStartDate(new Date());
            activityData.setEndDate(new Date());
            activityData.setPoiContent("西三旗");
            activityData.setCostDesc("全部费用");
            activityData.setActiveRoute("七天七夜");
            activityData.setServiceTel("010-12345678");
            activityData.setContent("游长城");
            activityData.setProductId((long) 33);
            activityData.setIsStatus(1);
            activityData.setClubImg("/2.png");
            activityData.setEnrollBeginDate(new Date());
            activityData.setEnrollEndDate(new Date());
            activityData.setMemberCountLimit(50);
            activityList.add(activityData);


        }
        return activityList;
    }

    @Override
    public Activity getById(long id) throws Exception {
        Activity activity = new Activity();

        activity.setId(id);
        activity.setOutId((long) 100);
        activity.setTitle("活动标题");
        activity.setClubName("古迹俱乐部");
        activity.setMemberCount(32);
        activity.setOriginalPrice((long) 150000);
        activity.setPreferentialPrice((long) 10000);
        activity.setStatus(2);
        activity.setImage("/1.png");
        activity.setModifyTime(new Date());
        activity.setCreaterTime(new Date());
        activity.setOutType(1);
        activity.setFavorName("会员优惠");
        activity.setFavorComent("八折");
        activity.setRecruitingGroups("招募的对象");
        activity.setStartDate(new Date());
        activity.setEndDate(new Date());
        activity.setPoiContent("西三旗");
        activity.setCostDesc("全部费用");
        activity.setActiveRoute("七天七夜");
        activity.setServiceTel("010-12345678");
        activity.setContent("游长城");
        activity.setProductId((long) 33);
        activity.setIsStatus(1);
        activity.setClubImg("/2.png");
        activity.setEnrollBeginDate(new Date());
        activity.setEnrollEndDate(new Date());
        activity.setMemberCountLimit(60);
        List<String> stringList = new ArrayList<String>();
        stringList.add("标签1");
        stringList.add("标签2");
        stringList.add("标签3");
        activity.setTagList(stringList);
        activity.setActivityDetailWebUrl("T1QtJTByYT1RX1qZUK");
        activity.setActivityDetailAppUrl("T1QtJTByYT1RX1qZUK");
        HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
        String resault = httpRequestUtil.sendGet("http://192.168.1.209:7500/v1/tfs/T1QtJTByYT1RX1qZUK");
        activity.setActivityDetailWeb(resault);
        activity.setActivityDetailApp(resault);
        System.out.println(resault);
        return activity;
    }

    @Override
    public Activity add(Activity activity) throws Exception {
        return null;
    }

    @Override
    public void modify(Activity activity) throws Exception {
        System.out.println(activity.getActivityDetailWebUrl());
        System.out.println(activity.getActivityDetailAppUrl());
        System.out.println(activity.getActivityDetailWeb());
        System.out.println(activity.getActivityDetailApp());
        //保存文件到tfs
        String encodeHtml = "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />";
        String encodeHtmlHead = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta http-equiv=\"x-ua-compatible\" content=\"IE=edge,chrome=1\">\n" +
                "    <meta name=\"viewport\" content=\"initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no\">\n" +
                "    <title></title>\n" +
                "</head>\n" +
                "<body>";
        String encodeHtmlFoot = "</body>\n" +
                "</html>";

        String activityDetailWeb = tfsManager.saveFile((encodeHtmlHead + activity.getActivityDetailWeb() + encodeHtmlFoot).getBytes("utf-8"), null, "html");
        String activityDetailApp = tfsManager.saveFile((encodeHtmlHead + activity.getActivityDetailApp() + encodeHtmlFoot).getBytes("utf-8"), null, "html");
        activity.setActivityDetailWebUrl(activityDetailWeb);
        activity.setActivityDetailAppUrl(activityDetailApp);
        System.out.println(activityDetailWeb);
        System.out.println(activityDetailApp);

    }
}
