package com.yimayhd.palace.model.guide;

import com.yimayhd.ic.client.model.dto.guide.GuideLineDTO;
import com.yimayhd.ic.client.model.dto.guide.GuideLineEntry;
import com.yimayhd.ic.client.model.domain.guide.GuideAttractionDO;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by haozhu on 16/9/2.
 */
public class AttractionListGuideLineVO {

    /**
     * 景点列表
     */
    List<GuideAttractionDO> guideAttractionDOList;

    /**
     * 线路列表
     */
    List<GuideLineEntry> guideLine;

    public List<GuideLineEntry> getGuideLine() {
        return guideLine;
    }

    public com.yimayhd.palace.model.guide.AttractionListGuideLineVO setGuideLine(List<GuideLineEntry> guideLine) {
        this.guideLine = guideLine;
        return this;
    }

    public List<GuideAttractionDO> getGuideAttractionDOList() {
        return guideAttractionDOList;
    }

    public com.yimayhd.palace.model.guide.AttractionListGuideLineVO setGuideAttractionDOList(List<GuideAttractionDO> guideAttractionDOList) {
        this.guideAttractionDOList = guideAttractionDOList;
        return this;
    }
}
