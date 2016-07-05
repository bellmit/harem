package com.yimayhd.palace.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.yimayhd.snscenter.client.domain.SnsSugTopicDO;

/**
 * Created by hongfei.guo on 2016/06/27.
 */
public class SnsSugTopicVO extends SnsSugTopicDO {

	private static final long serialVersionUID = 1L;
	
	public static SnsSugTopicDO getSugTopicDO(SnsSugTopicVO sugTopicVO){
		if(sugTopicVO == null){
    		return null;
    	}
		
		SnsSugTopicDO sugTopicDO = new SnsSugTopicDO();
        BeanUtils.copyProperties(sugTopicVO, sugTopicDO);
        return sugTopicDO;
    }
    
    public static SnsSugTopicVO getSugTopicVO(SnsSugTopicDO sugTopicDO){
    	if(sugTopicDO == null){
    		return null;
    	}
    	
        SnsSugTopicVO sugTopicVO = new SnsSugTopicVO();
        BeanUtils.copyProperties(sugTopicDO, sugTopicVO);
        return sugTopicVO;
    }
    
    public static List<SnsSugTopicVO> getSugTopicVOList(List<SnsSugTopicDO> sugTopicList){
    	
    	List<SnsSugTopicVO> sugTopicVOList = new ArrayList<SnsSugTopicVO>();
    	if(sugTopicList == null){
    		return sugTopicVOList;
    	}
    	
    	for(int i = 0; i < sugTopicList.size(); i++){
    		sugTopicVOList.add(getSugTopicVO(sugTopicList.get(i)));
    	}
        return sugTopicVOList;
    }

}
