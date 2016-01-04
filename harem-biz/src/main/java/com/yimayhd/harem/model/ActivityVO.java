package com.yimayhd.harem.model;

import org.springframework.beans.BeanUtils;

import com.yimayhd.harem.util.NumUtil;
import com.yimayhd.snscenter.client.dto.ActivityInfoDTO;


public class ActivityVO extends ActivityInfoDTO {
  
    private double originalPriceY;//价格元
    private double preferentialPriceY;
    private Long[] tagList;
    
    public static ActivityInfoDTO getActivityInfoDTO(ActivityVO activityVO){
    	ActivityInfoDTO activityInfoDTO = new ActivityVO();
        BeanUtils.copyProperties(activityVO, activityInfoDTO);
        activityInfoDTO.setOriginalPrice((long) (activityVO.getOriginalPriceY() * 100));
        activityInfoDTO.setPreferentialPrice((long) (activityVO.getPreferentialPriceY() * 100));

        return activityInfoDTO;
    }
    public static ActivityVO getActivityVO(ActivityInfoDTO activityInfoDTO){
        ActivityVO activityVO = new ActivityVO();
        BeanUtils.copyProperties(activityInfoDTO,activityVO);
        //分转元
        activityVO.setOriginalPriceY(NumUtil.moneyTransformDouble(activityVO.getOriginalPrice()));
        activityVO.setPreferentialPriceY(NumUtil.moneyTransformDouble(activityVO.getPreferentialPrice()));
        return activityVO;
    }
	public double getOriginalPriceY() {
		return originalPriceY;
	}
	public void setOriginalPriceY(double originalPriceY) {
		this.originalPriceY = originalPriceY;
	}
	public Long[] getTagList() {
		return tagList;
	}
	public void setTagList(Long[] tagList) {
		this.tagList = tagList;
	}
	public double getPreferentialPriceY() {
		return preferentialPriceY;
	}
	public void setPreferentialPriceY(double preferentialPriceY) {
		this.preferentialPriceY = preferentialPriceY;
	}
	
	
    
    
}
