package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.model.Trend;
import com.yimayhd.harem.model.User;
import com.yimayhd.harem.service.TrendService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/11/10.
 */
public class TrendServiceImpl implements TrendService{
    @Override
    public List<Trend> getList(Trend trend) throws Exception {
        List<Trend> trendList = new ArrayList<Trend>();
        for (int i = 0; i < 10; i++) {
            Trend trendData = new Trend();
            trendData.setId((long) (i * 10));
            User user = new User();
            user.setName("孙六");
            user.setId((long) 2);
            trendData.setUser(user);
            trendData.setContent("你在北京的寒夜里四季如春");
            List<String> pictureUrlList = new ArrayList<String>();
            pictureUrlList.add("url1");
            pictureUrlList.add("url2");
            trendData.setPictureUrlList(pictureUrlList);
            trendData.setPublishDate(new Date());
            trendData.setTrendStatus(1);
            List<String> auditHistoryList = new ArrayList<String>();
            auditHistoryList.add("违规");
            auditHistoryList.add("恢复");
            trendData.setAuditHistoryList(auditHistoryList);
            trendData.setCommentNum(166);
            trendData.setPraiseNum(278);
            trendData.setIp("192.168.1.100");

            trendList.add(trendData);
        }
        return trendList;
    }

    @Override
    public Trend getById(long id) throws Exception {
        Trend trendData = new Trend();

        trendData.setId(id);
        User user = new User();
        user.setName("孙六");
        user.setId((long) 2);
        trendData.setUser(user);
        trendData.setContent("你在北京的寒夜里四季如春");
        List<String> pictureUrlList = new ArrayList<String>();
        pictureUrlList.add("url1");
        pictureUrlList.add("url2");
        pictureUrlList.add("url3");
        pictureUrlList.add("url4");
        pictureUrlList.add("url5");
        pictureUrlList.add("url6");
        pictureUrlList.add("url7");
        pictureUrlList.add("url8");
        trendData.setPictureUrlList(pictureUrlList);
        trendData.setPublishDate(new Date());
        trendData.setTrendStatus(1);
        List<String> auditHistoryList = new ArrayList<String>();
        auditHistoryList.add("违规");
        auditHistoryList.add("恢复");
        trendData.setAuditHistoryList(auditHistoryList);
        trendData.setCommentNum(166);
        trendData.setPraiseNum(278);
        trendData.setIp("192.168.1.100");

        return trendData;
    }

    @Override
    public Trend add(Trend trend) throws Exception {
        return null;
    }

    @Override
    public void modify(Trend trend) throws Exception {

    }

    @Override
    public void setTrendStatus(List<Long> trendStatusList) throws Exception {
        for(Long id:trendStatusList){
            Trend trend = new Trend();
            trend.setId(id);
            trend.setTrendStatus(2);
            this.modify(trend);
        }
    }
}
