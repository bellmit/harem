package com.yimayhd.palace.model.guide;

import com.yimayhd.ic.client.model.dto.guide.AttractionCascadeFocusDTO;
import com.yimayhd.ic.client.model.dto.guide.GuideLineEntry;

import java.util.List;

/**
 * Created by haozhu on 16/9/9.
 */
public class GuideCascadeAttractionVO {

    // 景点列表
    List<AttractionCascadeFocusDTO> attractionDTOList;

    // 线路列表
    List<GuideLineEntry> guideLine;

    public List<AttractionCascadeFocusDTO> getAttractionDTOList () {
        return attractionDTOList;
    }

    public void setAttractionDTOList(List<AttractionCascadeFocusDTO> attractionDTOList) {
        this.attractionDTOList = attractionDTOList;
    }

    public List<GuideLineEntry> getGuideLine () {
        return guideLine;
    }

    public void setGuideLine(List<GuideLineEntry> guideLine) {
        this.guideLine = guideLine;
    }
}
