package com.yimayhd.palace.model.vo.booth;

import com.yimayhd.resourcecenter.domain.Feature;
import com.yimayhd.resourcecenter.domain.ShowcaseDO;
import com.yimayhd.resourcecenter.util.FeatureUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Created by czf on 2016/4/13.
 */
public class ShowcaseVO extends ShowcaseDO {
    public String boothCode;//boothcode
    public String operationContentZH;//选择内容的中文
    public long  operationDetailId;//选择跳转类型的id[相应operationDetail表的id]
    public boolean isFullScreen;//全屏展示

    public void setOperationContentZH(String operationContentZH) {
        this.operationContentZH = operationContentZH;
    }

    public void setOperationDetailId(long operationDetailId) {
        this.operationDetailId = operationDetailId;
    }

    public String getOperationContentZH() {
        return operationContentZH;
    }

    public void setOperationContentZHs(String feature) {
        String operationContentZHs = "";
        if(StringUtils.isNotEmpty(feature)){
            Map<String, String> map = FeatureUtil.fromString(feature);
            if(null != map ){
                operationContentZHs = map.get("operationContentZH");
            }
        }
        this.operationContentZH = operationContentZHs;
    }

    public long getOperationDetailId() {
        return operationDetailId;
    }

    public void setOperationDetailIds(String feature) {
        long operationDetailIds = 0;
        if(StringUtils.isNotEmpty(feature)){
            Map<String, String> map = FeatureUtil.fromString(feature);
            if(null != map ){
                String id = map.get("operationDetailId");
                operationDetailIds =  StringUtils.isEmpty(id)?0:Long.parseLong(id);
            }
        }
        this.operationDetailId = operationDetailIds;
    }

    public boolean isFullScreen() {
        return isFullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        isFullScreen = fullScreen;
    }

    public String getBoothCode() {
        return boothCode;
    }

    public void setBoothCode(String boothCode) {
        this.boothCode = boothCode;
    }
}
