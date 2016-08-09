package com.yimayhd.commission.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commission.biz.MallFansBiz;
import com.yimayhd.marketing.client.model.domain.MallFansDO;
import com.yimayhd.marketing.client.model.enums.DomainType;
import com.yimayhd.marketing.client.model.enums.MallFansSettingType;
import com.yimayhd.marketing.client.model.param.mallfans.MallFansUpdateDTO;
import com.yimayhd.marketing.client.model.result.SpmResult;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.constant.ResponseStatus;

@Controller
@RequestMapping(value="/mallFans")
public class MallFansController {

	private final static Logger logger = LoggerFactory.getLogger(MallFansController.class);
	
	@Autowired
	private MallFansBiz mallFansBiz;
	
	@RequestMapping(value="/setLevel", method=RequestMethod.POST)
	@ResponseBody
	public Object setLevel(Model model, MallFansUpdateDTO updateDTO){
		
		logger.info("MallFansController.setLevel begin,param:" + JSON.toJSONString(updateDTO));
		try{
			if(updateDTO.getUserId() <= 0 || updateDTO.getLevelId() <= 0){
				logger.error("MallFansController.setLevel end because of param error,param:" + JSON.toJSONString(updateDTO));
				return JSON.toJSON(new ResponseVo(ResponseStatus.INVALID_DATA));
			}
			
			updateDTO.setDomainId(DomainType.DOMAIN_MYTHIC_FLOW.getDomainId());
			updateDTO.setSettingType(MallFansSettingType.MANUAL.getSettingType());
			
			SpmResult<MallFansDO> baseResult = mallFansBiz.updateLevel(updateDTO);
			if(baseResult != null && baseResult.isSuccess() && baseResult.getT() != null){
				return JSON.toJSON(new ResponseVo(ResponseStatus.SUCCESS));
			}
		}catch(Exception e){
			logger.error("MallFansController.setLevel exceptions occur,param:{},ex:{}" , JSON.toJSONString(updateDTO), e);
		}
		return JSON.toJSON(new ResponseVo(ResponseStatus.ERROR));
	}
}
