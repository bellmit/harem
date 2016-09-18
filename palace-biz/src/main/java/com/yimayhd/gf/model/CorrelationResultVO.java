package com.yimayhd.gf.model;

import java.io.Serializable;

import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.ItemVO;
import com.yimayhd.resourcecenter.dto.RecommendDTO;

public class CorrelationResultVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 72657124480185867L;
	
	
	private ItemVO masterItemVO;
	private PageVO<ItemVO> pageVO;
	private RecommendDTO recommendDTO;
	public PageVO<ItemVO> getPageVO() {
		return pageVO;
	}
	public void setPageVO(PageVO<ItemVO> pageVO) {
		this.pageVO = pageVO;
	}
	public RecommendDTO getRecommendDTO() {
		return recommendDTO;
	}
	public void setRecommendDTO(RecommendDTO recommendDTO) {
		this.recommendDTO = recommendDTO;
	}
	public ItemVO getMasterItemVO() {
		return masterItemVO;
	}
	public void setMasterItemVO(ItemVO masterItemVO) {
		this.masterItemVO = masterItemVO;
	}
	
	
}
