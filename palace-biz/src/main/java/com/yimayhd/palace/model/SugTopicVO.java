package com.yimayhd.palace.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.yimayhd.snscenter.client.domain.SnsSugTopicDO;

/**
 * Created by hongfei.guo on 2016/06/27.
 */
public class SugTopicVO extends SnsSugTopicDO {

	private static final long serialVersionUID = 1L;
	
	public static SnsSugTopicDO getSugTopicDO(SugTopicVO sugTopicVO){
		if(sugTopicVO == null){
    		return null;
    	}
		
		SnsSugTopicDO sugTopicDO = new SnsSugTopicDO();
        BeanUtils.copyProperties(sugTopicVO, sugTopicDO);
        return sugTopicDO;
    }
    
    public static SugTopicVO getSugTopicVO(SnsSugTopicDO sugTopicDO){
    	if(sugTopicDO == null){
    		return null;
    	}
    	
        SugTopicVO sugTopicVO = new SugTopicVO();
        BeanUtils.copyProperties(sugTopicDO, sugTopicVO);
        return sugTopicVO;
    }
    
    public static List<SugTopicVO> getSugTopicVOList(List<SnsSugTopicDO> sugTopicList){
    	
    	List<SugTopicVO> sugTopicVOList = new ArrayList<SugTopicVO>();
    	if(sugTopicList == null){
    		return sugTopicVOList;
    	}
    	
    	for(int i = 0; i < sugTopicList.size(); i++){
    		SugTopicVO sugTopicVO = getSugTopicVO(sugTopicList.get(i));
    		sugTopicVO.setTitle(TopicVO.getTopicTitle2(sugTopicVO.getTitle()));
    		sugTopicVOList.add(sugTopicVO);
    	}
        return sugTopicVOList;
    }

}
