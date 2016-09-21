package com.yimayhd.palace.model.guide;

import com.yimayhd.palace.model.attachment.AttachmentVO;

import java.util.List;

/**
 * Created by xushubing on 2016/9/2.
 */
public class AttractionFocusVO {
    /**
     * 景点信息
     */
    private GuideAttractionVO guideAttractionVO;

    /**
     * 看点信息
     */
    private List<GuideFocusVO> guideFocusVOList;

    public GuideAttractionVO getGuideAttractionVO() {
        return guideAttractionVO;
    }

    public AttractionFocusVO setGuideAttractionVO(GuideAttractionVO guideAttractionVO) {
        this.guideAttractionVO = guideAttractionVO;
        return this;
    }

    public List<GuideFocusVO> getGuideFocusVOList() {
        return guideFocusVOList;
    }

    public AttractionFocusVO setGuideFocusVOList(List<GuideFocusVO> guideFocusVOList) {
        this.guideFocusVOList = guideFocusVOList;
        return this;
    }
}
