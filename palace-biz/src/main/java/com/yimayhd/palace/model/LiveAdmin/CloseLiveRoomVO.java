package com.yimayhd.palace.model.LiveAdmin;

import java.io.Serializable;

/**
 * Created by haozhu on 16/10/25.
 */
import java.io.Serializable;

public class CloseLiveRoomVO implements Serializable {
    private static final long serialVersionUID = -2930968242534899307L;
    private long liveRoomId;
    private String closeHours;
    private String closeReason;

    public CloseLiveRoomVO() {
    }

    public long getLiveRoomId() {
        return this.liveRoomId;
    }

    public void setLiveRoomId(long liveRoomId) {
        this.liveRoomId = liveRoomId;
    }

    public String getCloseHours() {
        return this.closeHours;
    }

    public void setCloseHours(String closeHours) {
        this.closeHours = closeHours;
    }

    public String getCloseReason() {
        return this.closeReason;
    }

    public void setCloseReason(String closeReason) {
        this.closeReason = closeReason;
    }
}
