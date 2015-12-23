package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.TravelOfficial;
import com.yimayhd.harem.model.User;
import com.yimayhd.harem.model.query.TravelOfficialListQuery;
import com.yimayhd.harem.service.TravelOfficialService;
import com.yimayhd.snscenter.client.domain.SnsTravelSpecialtyDO;
import com.yimayhd.snscenter.client.dto.TravelSpecialDTO;
import com.yimayhd.snscenter.client.result.BasePageResult;
import com.yimayhd.snscenter.client.result.BaseResult;
import com.yimayhd.snscenter.client.service.SnsCenterService;
import com.yimayhd.tradecenter.client.model.domain.imall.IMallRefundRecordDO;
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
    	SnsTravelSpecialtyDO snsTravelSpecialtyDO = new SnsTravelSpecialtyDO();
    	//标题
    	//发布时间
    	//目的地
    	//创建者
    	//游记封面
    	
    	snsTravelSpecialtyDO.setTitle(travelOfficial.getTitle());
    	snsTravelSpecialtyDO.setGmtCreated(new Date());
    	snsTravelSpecialtyDO.setGmtModified(new Date());
    	snsTravelSpecialtyDO.setCreateId(travelOfficial.getUser().getId());
    	snsTravelSpecialtyDO.setBackImg(travelOfficial.getBackImg());
    	snsTravelSpecialtyDO.setImgContentJson(null);
    	BaseResult<SnsTravelSpecialtyDO> res = snsCenterService.addTravelSpecialInfo(snsTravelSpecialtyDO);
    	if(null != res && res.isSuccess() && null != res.getValue()){
    		travelOfficial.setId(res.getValue().getId());
    		return travelOfficial;
    	}
        return null;
    }

    @Override
    public void modify(TravelOfficial travelOfficial) throws Exception {

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
}
