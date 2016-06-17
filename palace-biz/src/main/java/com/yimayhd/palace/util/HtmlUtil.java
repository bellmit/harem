package com.yimayhd.palace.util;


import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.resourcecenter.domain.OperationDetailDO;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author create by yushengwei on 2016/6/12
 * @Description
 * @return $returns
 */
public class HtmlUtil {
    public static String appendHtml(List<OperationDetailDO> listOperationDetail,long hitId){
        StringBuilder sb = new StringBuilder();
        boolean isSingle = false;
        if(null != listOperationDetail && listOperationDetail.size()==1){
            isSingle=true;
        }
        for (OperationDetailDO od :listOperationDetail) {
            sb.append("<input name='chooseService' id='").append(od.getId()).append("' ");
            if(isSingle || hitId == od.getId() ){
                /*System.out.println("od.getId()="+od.getId() +"       hitId="+hitId);*/
                sb.append(" checked ");
            }
            if(Constant.showTypeRadio == od.getShowType()){
                sb.append("type='radio' ");
            }else if (Constant.showTypeCheckBox == od.getShowType()){
                sb.append("type='checkbox' ");
            }
            sb.append("operationId='").append(od.getOperationId()).append("'")
              .append(" jumpType='").append(od.getJumpType()).append("'").append(" value='").append(od.getShowValue())
              .append("'> ").append(od.getShowName()).append(" ");
        }
        //System.out.println("html="+sb.toString());
        return sb.toString();
    }
}