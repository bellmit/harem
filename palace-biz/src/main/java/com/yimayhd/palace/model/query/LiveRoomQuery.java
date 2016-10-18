package com.yimayhd.palace.model.query;

import com.yimayhd.live.client.query.PageQuery;
import java.util.List;
import com.yimayhd.live.client.query.PageQuery;
import com.yimayhd.palace.base.BaseQuery;

/**
 * Created by haozhu on 16/9/23.
 */
public class LiveRoomQuery extends BaseQuery {
    private static final long serialVersionUID = -2250976141426722525L;
    /**
     * 主播ID
     */
    private Long userId;
    /**
     * 主播昵称
     */
    private String nickName;
    /**
     * 直播间ID
     */
    private Long liveRoomId;
    /**
     * 直播间状态
     */
    private Long status;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Long getLiveRoomId() {
        return liveRoomId;
    }

    public void setLiveRoomId(Long liveRoomId) {
        this.liveRoomId = liveRoomId;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }
}

