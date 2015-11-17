package com.yimayhd.harem.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yimayhd.harem.model.Live;
import com.yimayhd.harem.service.LiveService;

/**
 * Created by Administrator on 2015/11/2.
 */
public class LiveServiceImpl implements LiveService {
 

	@Override
	public List<Live> getList(Live liveListQuery) throws Exception {
		 List<Live> liveList = new ArrayList<Live>();
	        int j = 10;
	        //是否有查询条件
	        for (int i = 0;i <= j;i++){
	            Live liveData = new Live();
	            liveData.setId((long) i);
	            liveData.setCommertNum(22);
	            liveData.setContent("内容");
	            liveData.setGmtCreated(new Date());
	            liveData.setIpAddress("127.1.1.0");
	            liveData.setLikeNum(55);
	            liveData.setName("测试");
	            liveData.setLiveTag("标签");
	            liveData.setPhone("1852120000");
	            liveData.setLocation("xxxx");
	            liveList.add(liveData);
	        }
	        return liveList;
		
	}

	@Override
	public Live getById(long id) throws Exception {
		   Live liveData = new Live();
	        int i = 3;
	        liveData.setId((long) i);
            liveData.setCommertNum(22);
            liveData.setContent("内容");
            liveData.setGmtCreated(new Date());
            liveData.setIpAddress("127.1.1.0");
            liveData.setLikeNum(55);
            liveData.setName("测试");
            liveData.setLiveTag("标签");
            liveData.setPhone("1852120000");
            liveData.setLocation("xxxx");
          
	        return liveData;
		
	}

   
}
