package com.yimayhd.harem.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.yimayhd.commentcenter.client.enums.BaseStatus;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.TravelOfficial;
import com.yimayhd.harem.model.User;
import com.yimayhd.harem.model.query.TravelOfficialListQuery;
import com.yimayhd.harem.service.TravelOfficialService;
import com.yimayhd.snscenter.client.domain.SnsTravelSpecialtyDO;
import com.yimayhd.snscenter.client.domain.TravelJsonDO;
import com.yimayhd.snscenter.client.dto.TravelSpecialAddDTO;
import com.yimayhd.snscenter.client.dto.TravelSpecialDTO;
import com.yimayhd.snscenter.client.result.BasePageResult;
import com.yimayhd.snscenter.client.result.BaseResult;
import com.yimayhd.snscenter.client.service.SnsCenterService;
import com.yimayhd.tradecenter.client.model.domain.imall.IMallRefundRecordDO;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/11/10.
 */
public class TravelOfficialImpl implements TravelOfficialService{

	private Logger log = LoggerFactory.getLogger(getClass());
	
    @Autowired
    private SnsCenterService snsCenterService;


    @Override
    public PageVO<SnsTravelSpecialtyDO> getList(TravelOfficialListQuery travelOfficialListQuery) throws Exception {
        TravelSpecialDTO travelSpecialDTO = new TravelSpecialDTO();
        if(!StringUtils.isEmpty(travelOfficialListQuery.getTitle())){  // 游记名称
            travelSpecialDTO.setTravelName(travelOfficialListQuery.getTravelName());
        }

        if(!StringUtils.isEmpty(travelOfficialListQuery.getStartDate())){
            long startDade = paraseTime(travelOfficialListQuery.getStartDate());
            travelSpecialDTO.setStartTime(startDade);
        }

        if(!StringUtils.isEmpty(travelOfficialListQuery.getEndDate())){
            long endDate = paraseTime(travelOfficialListQuery.getEndDate());
            travelSpecialDTO.setEndTime(endDate);
        }

        if(!StringUtils.isEmpty(travelOfficialListQuery.getPoiContent())){
            travelSpecialDTO.setPoiContent(travelOfficialListQuery.getPoiContent());
        }

        if(!StringUtils.isEmpty(travelOfficialListQuery.getNickName())){
            travelSpecialDTO.setNickName(travelOfficialListQuery.getNickName());
        }

        travelSpecialDTO.setPageSize(travelOfficialListQuery.getPageSize());
        travelSpecialDTO.setPageNo(travelOfficialListQuery.getPageNumber());
        BasePageResult<SnsTravelSpecialtyDO> result = snsCenterService.getTravelSpecialPage(travelSpecialDTO);

        PageVO<SnsTravelSpecialtyDO> pageVO = new PageVO<SnsTravelSpecialtyDO>(travelOfficialListQuery.getPageNumber(), travelOfficialListQuery.getPageSize(),0);;
        if(result != null && result.isSuccess()){
            pageVO = new PageVO<SnsTravelSpecialtyDO>(travelOfficialListQuery.getPageNumber(), travelOfficialListQuery.getPageSize(),result.getTotalCount(),result.getList());
        }
        return pageVO;
    }

    @Override
    public TravelOfficial getById(long id) throws Exception {

        BaseResult<SnsTravelSpecialtyDO> result = snsCenterService.getTravelSpecialInfoBySubjectId(id);
//        SnsTravelSpecialtyDO snsTravelSpecialtyDO = new SnsTravelSpecialtyDO();
        TravelOfficial travelOfficial = new TravelOfficial();
        if(result!= null && result.isSuccess()){
            SnsTravelSpecialtyDO snsTravelSpecialtyDO = result.getValue();
            travelOfficial = convertTravelOfficial(snsTravelSpecialtyDO);
        }
//        TravelOfficial travelOfficialData = new TravelOfficial();
//        travelOfficialData.setId(id);
//        travelOfficialData.setTitle("穿梭在云端的日子【蜜月之旅】");
//        travelOfficialData.setRegionId((long) 100);
//        travelOfficialData.setRegionName("西藏");
//        User user = new User();
//        user.setName("孙六");
//        user.setId((long) 2);
//        travelOfficialData.setUser(user);
//        travelOfficialData.setTravelStatus(1);
//        travelOfficialData.setPublishDate(new Date());
//        travelOfficialData.setPraiseNum(88);
//        travelOfficialData.setCollectionNum(880);
        return travelOfficial;
    }

    @Override
    public TravelOfficial add(TravelOfficial travelOfficial) throws Exception {
    	
    	TravelSpecialAddDTO travelSpecialAddDTO = new TravelSpecialAddDTO();
    	travelSpecialAddDTO.setBackImg(travelOfficial.getBackImg());
    	travelSpecialAddDTO.setGmtCreated(travelOfficial.getPublishDate());
    	travelSpecialAddDTO.setPreface(null);
    	travelSpecialAddDTO.setTitle(travelOfficial.getTitle());
    	travelSpecialAddDTO.setTravelJsonDO(ImgContentJsonToTravelJsonDO(travelOfficial.getImgContentJson()));
    	travelSpecialAddDTO.setUserId(travelOfficial.getCreateId());
    	
    	BaseResult<SnsTravelSpecialtyDO> res = snsCenterService.addTravelSpecialInfo(travelSpecialAddDTO);
    	if(null != res && res.isSuccess() && null != res.getValue()){
    		travelOfficial.setId(res.getValue().getId());
    		return travelOfficial;
    	}
        return null;
    }
    
    
    
    public List<TravelJsonDO> ImgContentJsonToTravelJsonDO(String imgContentJson){
    	if(org.apache.commons.lang3.StringUtils.isEmpty(imgContentJson)){
    		return null;
    	}
    	try {
    		System.out.println(imgContentJson);
    		List<TravelJsonDO> list  = JSON.parseArray(imgContentJson, TravelJsonDO.class);
			return list;
		} catch (Exception e) {
			log.error("ImgContentJsonToTravelJsonDO error,imgContentJson="+JSON.toJSONString(imgContentJson),e);
		}
    	return null;
    }
    
    
    

    @Override
    public boolean modify(TravelOfficial travelOfficial) throws Exception {
    	TravelSpecialDTO travelSpecialDTO = new TravelSpecialDTO();
    	//travelSpecialDTO.setGmtModified(gmtModified);
    	//travelSpecialDTO.set
    	
    	BaseResult<Boolean>  res = snsCenterService.updateTravelUpStateByIds(travelSpecialDTO);
    	if(null != res && res.isSuccess() && res.getValue()){
    		return true;
    	}
		return false;

    }

    private long paraseTime(String time) throws Exception{
        long rs = 0;
        if(!StringUtils.isEmpty(time)){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date =  sdf.parse(time);
            rs = date.getTime();
        }
        return rs;
    }

    private TravelOfficial convertTravelOfficial(SnsTravelSpecialtyDO snsTravelSpecialtyDO){
        TravelOfficial travelOfficialData = new TravelOfficial();
        if(snsTravelSpecialtyDO != null ){
            travelOfficialData.setTitle(snsTravelSpecialtyDO.getTitle() == null? "" : snsTravelSpecialtyDO.getTitle());
            travelOfficialData.setPublishDate(snsTravelSpecialtyDO.getGmtCreated() == null ? null : snsTravelSpecialtyDO.getGmtCreated());
            travelOfficialData.setBackImg(snsTravelSpecialtyDO.getBackImg() == null ? null :snsTravelSpecialtyDO.getBackImg() );;
        }

        return travelOfficialData;
    }

    private SnsTravelSpecialtyDO convertSnsTravelSpecialtyDO(TravelOfficial travelOfficial){
        SnsTravelSpecialtyDO snsTravelSpecialtyDO = new SnsTravelSpecialtyDO();
        if(travelOfficial == null){
            return null;
        }
        snsTravelSpecialtyDO.setTitle(travelOfficial.getTitle()== null ? null : travelOfficial.getTitle());
        return snsTravelSpecialtyDO;
    }

	@Override
	public boolean batchUpOrDownStatus(List<Long> ids, int status) throws Exception{
		if(CollectionUtils.isEmpty(ids)){
			throw new Exception("batchUpOrDownStatus ,parameters [ids] cannot be empty");
		}
		TravelSpecialDTO travelSpecialDTO = new TravelSpecialDTO();
		travelSpecialDTO.setList(ids);
		if(status==BaseStatus.AVAILABLE.getType()){
			travelSpecialDTO.setState(BaseStatus.DELETED.getType());
		}else{
			travelSpecialDTO.setState(BaseStatus.AVAILABLE.getType());
		}
		BaseResult<Boolean> res = snsCenterService.updateTravelUpStateByIds(travelSpecialDTO); 
		if(null != res && res.isSuccess() && res.getValue() ){
			return true;
		}
		return false;
	}
}
