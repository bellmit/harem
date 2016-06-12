package com.yimayhd.palace.util;


import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yimayhd.resourcecenter.domain.OperationDetailDO;

import java.util.List;

/**
 * @author create by yushengwei on 2016/6/12
 * @Description
 * @return $returns
 */
public class HtmlUtil {
    public static String appendHtml(List<OperationDetailDO> listOperationDetail){
        StringBuilder sb = new StringBuilder();
        for (OperationDetailDO od :listOperationDetail) {
            sb.append("<input name='chooseService' type='radio' ").append("operationId='").append(od.getOperationId()).append("'")
              .append(" jumpType='").append(od.getJumpType()).append("'").append(" value='").append(od.getShowValue())
              .append("'> ").append(od.getShowName()).append(" ");
        }
        System.out.print("html="+sb.toString());
        return sb.toString();
    }
}
