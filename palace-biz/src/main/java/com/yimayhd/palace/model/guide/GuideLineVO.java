package com.yimayhd.palace.model.guide;

import com.yimayhd.ic.client.model.dto.guide.GuideLineDTO;
import com.yimayhd.ic.client.model.dto.guide.GuideLineEntry;
import com.yimayhd.palace.result.BizResultSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haozhu on 16/9/9.
 */
public class GuideLineVO extends BizResultSupport {

    public enum Position {
        out, between, end;
    }

    /**
     * 景点位于线路的位置
     */
    public Position position;

    /**
     * 线路顺序
     */
    List<GuideLineEntry> guideLine;

    public List<GuideLineEntry> getGuideLine() {
        return guideLine;
    }

    public void setGuideLine(ArrayList<GuideLineEntry> guideLine) {
        this.guideLine = guideLine;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
