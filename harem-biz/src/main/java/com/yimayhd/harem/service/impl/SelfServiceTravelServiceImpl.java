package com.yimayhd.harem.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yimayhd.harem.model.travel.groupTravel.GroupTravel;
import com.yimayhd.harem.model.travel.selfServiceTravel.SelfServiceTravel;
import com.yimayhd.harem.service.SelfServiceTravelService;

public class SelfServiceTravelServiceImpl implements SelfServiceTravelService {

	@Override
	public void save(SelfServiceTravel selfServiceTravel) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(SelfServiceTravel selfServiceTravel) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public SelfServiceTravel getById(long id) throws Exception {
		if (id == 0) {
			return null;
		}
		String json = "{'baseInfo':{'extraInfos':[{'content':'1','title':'a'},{'content':'2','title':'b'},{'content':'3','title':'c'},{'content':'4','title':'d'},{'content':'5','title':'e'},{'content':'6','title':'f'}],'from':{'id':'2','name':'南京'},'highlights':'123','id':0,'name':'跟团游1','productImage':'T1jyJTBCZT1RXrhCrK.jpg','publisher':{'id':'0','name':'Gost'},'publisherType':'2','recommond':'123','tags':[{'id':'2','name':'蜜月之旅'}],'themes':[{'id':'2','name':'亲子'}],'to':{'id':'4','name':'丽江'},'tripImage':'T1jtJTBCZT1RXrhCrK.jpg','type':0},'priceInfo':{'limit':1,'tcs':[{'from':'23123','months':[{'days':[{'adult':{'discount':'1','price':'1','stock':'1'},'children02':{'discount':'1','price':'1','stock':'1'},'children212':{'discount':'1','price':'1','stock':'1'},'date':1448812800000,'srd':'1'},{'adult':{'discount':'1','price':'1','stock':'1'},'children02':{'discount':'1','price':'1','stock':'1'},'children212':{'discount':'1','price':'1','stock':'1'},'date':1448812800000,'srd':'1'}]},{'days':[{'adult':{'discount':'1','price':'1','stock':'1'},'children02':{'discount':'1','price':'1','stock':'1'},'children212':{'discount':'1','price':'1','stock':'1'},'date':1448812800000,'srd':'1'},{'adult':{'discount':'1','price':'1','stock':'1'},'children02':{'discount':'1','price':'1','stock':'1'},'children212':{'discount':'1','price':'1','stock':'1'},'date':1448812800000,'srd':'1'}]}],'name':'123'},{'from':'上海','months':[{'days':[{'adult':{'discount':'1','price':'1','stock':'1'},'children02':{'discount':'1','price':'1','stock':'1'},'children212':{'discount':'1','price':'1','stock':'1'},'date':1448985600000,'srd':'1'},{'adult':{'discount':'1','price':'1','stock':'1'},'children02':{'discount':'1','price':'1','stock':'1'},'children212':{'discount':'1','price':'1','stock':'1'},'date':1448985600000,'srd':'1'}]},{'days':[{'adult':{'discount':'1','price':'1','stock':'1'},'children02':{'discount':'1','price':'1','stock':'1'},'children212':{'discount':'1','price':'1','stock':'1'},'date':1448985600000,'srd':'1'},{'adult':{'discount':'1','price':'1','stock':'1'},'children02':{'discount':'1','price':'1','stock':'1'},'children212':{'discount':'1','price':'1','stock':'1'},'date':1448985600000,'srd':'1'}]}],'name':'套餐2'}]},'tripPackageInfo':{'flights':[{'back':{'airways':'23123','flightNumber':'213','fromAirport':'123','fromCity':'123','noteInfo':'123','off':1449158400000,'planEnd':1449237600000,'planStart':1449213900000,'runTime':'0天 6小时 35 分钟','toAirport':'12321','toCity':'2123'},'go':{'airways':'123','flightNumber':'21321','fromAirport':'231','fromCity':'1231','noteInfo':'123','off':1449072000000,'planEnd':1449111000000,'planStart':1449095400000,'runTime':'0天 4小时 20 分钟','toAirport':'321322','toCity':'2312'}}],'hasFlight':1,'hotels':[{'id':'2','name':'温德姆至尊豪廷大酒店2'},{'id':'3','name':'温德姆至尊豪廷大酒店3'}]}}";
		SelfServiceTravel result = JSONObject.parseObject(json, SelfServiceTravel.class);
		return result;
	}

}
