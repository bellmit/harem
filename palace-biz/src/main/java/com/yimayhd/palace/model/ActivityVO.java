package com.yimayhd.palace.model;

import org.springframework.beans.BeanUtils;

import com.yimayhd.palace.util.DateUtil;
import com.yimayhd.palace.util.NumUtil;
import com.yimayhd.snscenter.client.dto.ActivityInfoDTO;


public class ActivityVO extends ActivityInfoDTO {
  
    private double originalPriceY;//价格元
    private double preferentialPriceY;
    private Long[] tagList;
    private String activityDateStr;
	private String startDateStr;
	private String endDateStr;
    
    
    
    
    public static ActivityInfoDTO getActivityInfoDTO(ActivityVO activityVO) throws Exception{
    	ActivityInfoDTO activityInfoDTO = new ActivityVO();
        BeanUtils.copyProperties(activityVO, activityInfoDTO);
        activityInfoDTO.setOriginalPrice((long) (activityVO.getOriginalPriceY() * 100));
        activityInfoDTO.setPreferentialPrice((long) (activityVO.getPreferentialPriceY() * 100));
        activityInfoDTO.setActivityDate( DateUtil.convertStringToDateUseringFormats(activityVO.getActivityDateStr(), DateUtil.DAY_HORU_FORMAT));
        activityInfoDTO.setStartDate(DateUtil.convertStringToDateUseringFormats(activityVO.getStartDateStr(), DateUtil.DAY_HORU_FORMAT));
        activityInfoDTO.setEndDate(DateUtil.convertStringToDateUseringFormats(activityVO.getEndDateStr(), DateUtil.DAY_HORU_FORMAT));
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
	public String getActivityDateStr() {
		return activityDateStr;
	}
	public void setActivityDateStr(String activityDateStr) {
		this.activityDateStr = activityDateStr;
	}
	public String getStartDateStr() {
		return startDateStr;
	}
	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}
	public String getEndDateStr() {
		return endDateStr;
	}
	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}
	
	
	
    
    
}
