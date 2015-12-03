package com.yimayhd.harem.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yimayhd.harem.model.travel.groupTravel.GroupTravel;
import com.yimayhd.harem.service.GroupTravelService;

public class GroupTravelServiceImpl implements GroupTravelService {

	@Override
	public void save(GroupTravel groupTravel) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(GroupTravel groupTravel) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public GroupTravel getById(long id) throws Exception {
		if (id == 0) {
			return null;
		}
		String json = "{'baseInfo':{"
				+ "'extraInfos':["
				+ "{'content':'1','title':'a'},"
				+ "{'content':'2','title':'b'},"
				+ "{'content':'3','title':'c'},"
				+ "{'content':'4','title':'d'},"
				+ "{'content':'5','title':'e'}"
				+ "{'content':'6','title':'f'}"
				+ "],'from':{'id':'2','name':'南京'},'highlights':'123','id':0,'name':'跟团游1','productImage':'T1jyJTBCZT1RXrhCrK.jpg','publisher':{'id':'0','name':'Gost'},'publisherType':'2','recommond':'123','tags':[{'id':'2','name':'蜜月之旅'}],'themes':[{'id':'2','name':'亲子'}],'to':{'id':'4','name':'丽江'},'tripImage':'T1jtJTBCZT1RXrhCrK.jpg','type':0},'id':0,'priceInfo':{'limit':1,'tcs':[{'endDate':1448985600000,'from':'23123','months':[{'date':1446307200000,'days':[{'adult':{'discount':'1','price':'1','stock':'1'},'children02':{'discount':'1','price':'1','stock':'1'},'children212':{'discount':'1','price':'1','stock':'1'},'date':1448812800000,'srd':'1'}]},{'date':1448899200000,'days':[{'adult':{'discount':'1','price':'1','stock':'1'},'children02':{'discount':'1','price':'1','stock':'1'},'children212':{'discount':'1','price':'1','stock':'1'},'date':1448899200000,'srd':'1'},{'adult':{'discount':'1','price':'1','stock':'1'},'children02':{'discount':'1','price':'1','stock':'1'},'children212':{'discount':'1','price':'1','stock':'1'},'date':1448985600000,'srd':'1'}]}],'name':'123','startDate':1448812800000},{'endDate':1448985600000,'from':'上海','months':[{'date':1446307200000,'days':[{'adult':{'discount':'2','price':'13','stock':'2'},'children02':{'discount':'331','price':'121','stock':'2'},'children212':{'discount':'231','price':'32','stock':'2'},'date':1448812800000,'srd':'113'}]},{'date':1448899200000,'days':[{'adult':{'discount':'2','price':'13','stock':'2'},'children02':{'discount':'331','price':'121','stock':'2'},'children212':{'discount':'231','price':'32','stock':'2'},'date':1448899200000,'srd':'113'},{'adult':{'discount':'2','price':'13','stock':'2'},'children02':{'discount':'331','price':'121','stock':'2'},'children212':{'discount':'231','price':'32','stock':'2'},'date':1448985600000,'srd':'113'}]}],'name':'套餐2','startDate':1448812800000}]},"
				+ "'tripInfo':[{'description':'123','hotels':[{'id':'5','name':'温德姆至尊豪廷大酒店5'},{'id':'4','name':'温德姆至尊豪廷大酒店4'}],'id':1,'restaurant1':[{'id':'1','name':'怡心园1'}],'restaurant2':[{'id':'2','name':'怡心园2'},{'id':'3','name':'怡心园3'}],'restaurant3':[{'id':'5','name':'怡心园5'},{'id':'6','name':'怡心园6'},{'id':'0','name':'123'}],'scenics':[{'id':'1','name':'景点1'},{'id':'3','name':'景点3'}],'traffic':{'from':'北京','to':'上海','way':'0'}},{'description':'123','hotels':[{'id':'7','name':'温德姆至尊豪廷大酒店7'},{'id':'5','name':'温德姆至尊豪廷大酒店5'}],'id':2,'restaurant1':[{'id':'3','name':'怡心园3'},{'id':'5','name':'怡心园5'}],'restaurant2':[{'id':'0','name':'啊'},{'id':'0','name':'dds'}],'restaurant3':[{'id':'0','name':'1'},{'id':'5','name':'怡心园5'}],'scenics':[{'id':'4','name':'景点4'},{'id':'3','name':'景点3'}],'traffic':{'from':'上海','to':'南京','way':'0'}}]}";
		GroupTravel result = JSONObject.parseObject(json, GroupTravel.class);
		return result;
	}

}
