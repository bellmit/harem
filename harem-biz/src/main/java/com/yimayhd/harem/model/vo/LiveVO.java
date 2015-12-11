package com.yimayhd.harem.model.vo;

import com.yimayhd.harem.model.Live;
import com.yimayhd.harem.model.query.LiveListQuery;

/**
 * Created by Administrator on 2015/11/2.
 */
public class LiveVO {
    private Live live;
    private LiveListQuery liveListQuery;
	public Live getLive() {
		return live;
	}
	public void setLive(Live live) {
		this.live = live;
	}
	public LiveListQuery getLiveListQuery() {
		return liveListQuery;
	}
	public void setLiveListQuery(LiveListQuery liveListQuery) {
		this.liveListQuery = liveListQuery;
	}
   

}
