package com.yimayhd.harem.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.commentcenter.client.enums.BaseStatus;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.TripBo;
import com.yimayhd.harem.service.HotelRPCService;
import com.yimayhd.harem.service.ScenicService;
import com.yimayhd.harem.service.TripService;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.domain.share_json.NeedKnow;
import com.yimayhd.ic.client.model.domain.share_json.TextItem;
import com.yimayhd.ic.client.model.query.HotelPageQuery;
import com.yimayhd.ic.client.model.query.ScenicPageQuery;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.resourcecenter.domain.BoothDO;
import com.yimayhd.resourcecenter.domain.RegionDO;
import com.yimayhd.resourcecenter.domain.RegionIntroduceDO;
import com.yimayhd.resourcecenter.domain.ShowcaseDO;
import com.yimayhd.resourcecenter.model.enums.ColumnType;
import com.yimayhd.resourcecenter.model.enums.RegionStatus;
import com.yimayhd.resourcecenter.model.enums.RegionType;
import com.yimayhd.resourcecenter.model.query.RegionIntroduceQuery;
import com.yimayhd.resourcecenter.model.query.ShowcaseQuery;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.model.result.RcResult;
import com.yimayhd.resourcecenter.model.result.ShowCaseResult;
import com.yimayhd.resourcecenter.service.BoothClientServer;
import com.yimayhd.resourcecenter.service.RegionClientService;
import com.yimayhd.resourcecenter.service.RegionIntroduceClientService;
import com.yimayhd.resourcecenter.service.ShowcaseClientServer;

public class TripServiceImpl implements TripService {

	@Autowired
	private RegionClientService regionClientServiceRef;

	@Autowired
	private ShowcaseClientServer showcaseClientServerRef;

	@Autowired
	private HotelRPCService hotelRPCService;

	@Autowired
	private ScenicService scenicSpotService;

	@Autowired
	private BoothClientServer boothClientServerRef;

	@Autowired
	private ItemQueryService itemQueryServiceRef;

	@Autowired
	private RegionIntroduceClientService RegionIntroduceClientServiceRef;

	public RegionDO saveOrUpdate(TripBo tripBo) throws Exception {
		RegionDO regionDO = null;
		if (0 == tripBo.getId()) {
			regionDO = new RegionDO();
			regionDO.setCityCode(tripBo.getCityCode());
			regionDO.setType(tripBo.getType());
			regionDO.setBgUrl(tripBo.getCoverURL());// 封面logo
			regionDO.setUrl(tripBo.getLogoURL());// logo
			regionDO.setGmtModified(new Date());
			regionDO.setStatus(RegionStatus.VALID.getStatus());
			RcResult<RegionDO> res = regionClientServiceRef.insert(regionDO);
			if (null != res && res.isSuccess() && null != res.getT()) {
				return res.getT();
			}
			return null;
		} else {
			RcResult<RegionDO> res = regionClientServiceRef.selectById(tripBo.getId());
			if (null == res || !res.isSuccess() || null == res.getT()) {
				throw new Exception("data [RegionDO] not available,id=" + tripBo.getId());
			}
			regionDO = res.getT();
			regionDO.setId(tripBo.getId());
			regionDO = new RegionDO();
			regionDO.setCityCode(tripBo.getCityCode());
			regionDO.setType(tripBo.getType());
			regionDO.setBgUrl(tripBo.getCoverURL());// 封面logo
			regionDO.setUrl(tripBo.getLogoURL());// logo
			regionDO.setGmtModified(new Date());
			regionDO.setStatus(RegionStatus.VALID.getStatus());
			RcResult<Boolean> resb = regionClientServiceRef.updateById(regionDO);
			if (null != resb && resb.isSuccess() && null != resb.getT()) {
				return regionDO;
			}
			return null;
		}
	}

	@Override
	public RegionDO saveOrUpdateDetail(TripBo tripBo) throws Exception {
		RegionDO regionDO = saveOrUpdate(tripBo);
		if (tripBo.getType() == RegionType.DESC_REGION.getType()) {// 目的地
			// 保存相应的概况 民俗等信息
			List<NeedKnow> list = new ArrayList<NeedKnow>();
			NeedKnow gaikuang = tripBo.getGaikuang();
			gaikuang.setExtraInfoUrl(ColumnType.SURVER.toString());
			NeedKnow minsu = tripBo.getMinsu();
			minsu.setExtraInfoUrl(ColumnType.FOLKWAYS.toString());
			NeedKnow tieshi = tripBo.getTieshi();
			tieshi.setExtraInfoUrl(ColumnType.HIGHLIGHTS.toString());
			NeedKnow xiaofei = tripBo.getXiaofei();
			xiaofei.setExtraInfoUrl(ColumnType.CONSUMPTION.toString());
			list.add(gaikuang);
			list.add(minsu);
			list.add(tieshi);
			list.add(xiaofei);
			saveShowCase(list, tripBo.getCityCode());
		}
		return regionDO;
	}

	public void saveShowCase(List<NeedKnow> list, int cityCode) {
		// XXX:根据设计，流程如下：先往rc_booth表插城市的NeedKnow，然后根据返回的id,继续往rc_showcase表中插具体的NeedKnow包含的TextItem信息.
		for (NeedKnow nk : list) {
			if (null != nk && CollectionUtils.isNotEmpty(nk.getFrontNeedKnow())) {
				System.out.println(nk.getExtraInfoUrl());
				BoothDO boothDO = new BoothDO();
				boothDO.setCode(nk.getExtraInfoUrl() + "-" + cityCode);
				boothDO.setName(ColumnType.getByName(nk.getExtraInfoUrl()).getCode());
				boothDO.setDesc(nk.getExtraInfoUrl() + "-" + cityCode);
				boothDO.setStatus(10);
				boothDO.setType(2);
				boothDO.setGmtCreated(new Date());
				boothDO.setGmtModified(new Date());
				List<ShowcaseDO> listShowcaseDO = needKnowToShowCase(nk, cityCode, 0);
				RcResult<Boolean> resb = showcaseClientServerRef.batchInsertShowcase(listShowcaseDO, boothDO);
				System.out.println(resb.isSuccess());
			}
		}
	}

	/**
	 * @Title: needKnowToShowCase @Description:(将needKnow转换成showcase) @author
	 *         create by yushengwei @ 2015年12月19日 下午3:05:35 @param @param
	 *         needKnow @param @param cityCode @param @param
	 *         boothId @param @return @return List<ShowcaseDO> 返回类型 @throws
	 */
	public List<ShowcaseDO> needKnowToShowCase(NeedKnow needKnow, int cityCode, long boothId) {
		List<ShowcaseDO> listShowCase = new ArrayList<ShowcaseDO>();
		ShowcaseDO sw = null;
		List<TextItem> listItem = needKnow.getFrontNeedKnow();
		for (int i = 0; i < listItem.size(); i++) {
			if (null != listItem.get(i) && StringUtils.isNotEmpty(listItem.get(i).getTitle())) {
				sw = new ShowcaseDO();
				sw.setGmtCreated(new Date());
				sw.setTitle(listItem.get(i).getTitle());
				sw.setBusinessCode(String.valueOf(cityCode));
				sw.setBoothId(boothId);
				sw.setOperationContent(listItem.get(i).getContent());
				listShowCase.add(sw);
			}
		}
		return listShowCase;
	}

	@Override
	public boolean delTrip(long id) {
		RcResult<Boolean> res = regionClientServiceRef.deleteById(id);
		if (null != res && res.isSuccess()) {
			return true;
		}
		return false;
	}

	@Override
	public TripBo getTripBo(int id) {
		TripBo tripBo = new TripBo();
		RcResult<RegionDO> res = regionClientServiceRef.selectById(id);
		if (null != res && res.isSuccess() && null != res.getT()) {
			RegionDO regionDO = res.getT();
			// 组装其余信息
			// -------------------------------------------------------------------
			int cityCode = regionDO.getCityCode();
			tripBo.setCityCode(cityCode);
			// tripBo.setCityLevel();
			tripBo.setType(regionDO.getType());
			tripBo.setLogoURL(regionDO.getUrl());
			tripBo.setCoverURL(regionDO.getBgUrl());
			tripBo.setStatus(regionDO.getStatus());

			NeedKnow gaikuang = new NeedKnow();
			gaikuang.setExtraInfoUrl(ColumnType.SURVER.getCode());
			gaikuang.setFrontNeedKnow(getListShowcaseDO(cityCode, ColumnType.SURVER.getCode()));

			NeedKnow minsu = new NeedKnow();
			minsu.setExtraInfoUrl(ColumnType.FOLKWAYS.getCode());
			minsu.setFrontNeedKnow(getListShowcaseDO(cityCode, ColumnType.FOLKWAYS.getCode()));

			NeedKnow xiaofei = new NeedKnow();
			xiaofei.setExtraInfoUrl(ColumnType.CONSUMPTION.getCode());
			xiaofei.setFrontNeedKnow(getListShowcaseDO(cityCode, ColumnType.CONSUMPTION.getCode()));

			NeedKnow tieshi = new NeedKnow();
			tieshi.setExtraInfoUrl(ColumnType.HIGHLIGHTS.getCode());
			tieshi.setFrontNeedKnow(getListShowcaseDO(cityCode, ColumnType.HIGHLIGHTS.getCode()));

			tripBo.setGaikuang(gaikuang);
			tripBo.setTieshi(tieshi);
			tripBo.setXianLu(null);
			tripBo.setXiaofei(xiaofei);
			tripBo.setMinsu(minsu);

			return tripBo;
		}
		return null;
	}

	public List<TextItem> getListShowcaseDO(int cityCode, String type) {
		// 先查booth的
		String code = type + "-" + cityCode;
		BoothDO boothDO = boothClientServerRef.getBoothDoByCode(code);
		if (null == boothDO) {
			return null;
		}
		ShowcaseQuery showcaseQuery = new ShowcaseQuery();
		showcaseQuery.setBoothId(boothDO.getId());
		RCPageResult<ShowCaseResult> res = showcaseClientServerRef.getShowcaseResult(showcaseQuery);
		if (null != res && res.isSuccess() && CollectionUtils.isNotEmpty(res.getList())) {
			List<ShowCaseResult> list = res.getList();
			return getListTextItem(list);
		}
		return null;
	}

	public List<TextItem> getListTextItem(List<ShowCaseResult> list) {
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		List<ShowcaseDO> listShowcaseDO = new ArrayList<ShowcaseDO>();
		for (ShowCaseResult showCaseResult : list) {
			listShowcaseDO.add(showCaseResult.getShowcaseDO());
		}
		List<TextItem> listTextItem = showCaseToTextItem(listShowcaseDO);
		return listTextItem;
	}

	public List<TextItem> showCaseToTextItem(List<ShowcaseDO> list) {
		List<TextItem> listTextItem = new ArrayList<TextItem>();
		if (CollectionUtils.isEmpty(list)) {
			return listTextItem;
		}
		TextItem textItem = null;
		for (ShowcaseDO showcaseDO : list) {
			textItem = new TextItem();
			textItem.setContent(showcaseDO.getOperationContent());
			textItem.setPic_url(showcaseDO.getSerialNo() == 0 ? "0" : String.valueOf(showcaseDO.getSerialNo()));
			textItem.setTitle(showcaseDO.getTitle());
			listTextItem.add(textItem);
		}
		return listTextItem;
	}

	@Override
	public List<RegionDO> getTripBo() {

		return null;
	}

	@Override
	public boolean editTripBo(TripBo tripBo) {

		return false;
	}

	@Override
	public boolean relevanceRecommended(int type, String cityCode, int[] resourceId) throws Exception {
		ColumnType columnType = ColumnType.getByType(type);
		if (null == columnType) {
			throw new Exception("parameter[type] " + type + " ,Enum does not exist");
		}
		BoothDO boothDO = new BoothDO();
		boothDO.setCode(columnType.toString() + "-" + cityCode);
		boothDO.setName(columnType.getCode());
		boothDO.setDesc(columnType.getCode() + "-" + cityCode);
		boothDO.setStatus(10);
		boothDO.setType(2);
		boothDO.setGmtCreated(new Date());
		boothDO.setGmtModified(new Date());
		List<ShowcaseDO> list = new ArrayList<ShowcaseDO>();
		ShowcaseDO sc = null;
		for (int i = 0; i < resourceId.length; i++) {
			sc = new ShowcaseDO();
			sc.setTitle("目的地_" + cityCode + "	关联	" + columnType.getCode() + " [" + resourceId[i] + "]");
			sc.setStatus(10);// BoothStatusType.ON_SHELF.getValue()
			sc.setGmtCreated(new Date());
			sc.setGmtModified(new Date());
			sc.setOperationContent(String.valueOf(resourceId[i]));
			list.add(sc);
		}
		RcResult<Boolean> resb = showcaseClientServerRef.batchInsertShowcase(list, boothDO);
		System.out.println(resb.isSuccess());
		return resb.isSuccess();
	}

	@Override
	public List<RegionDO> selectRegion(int type) {
		RCPageResult<RegionDO> res = regionClientServiceRef.getRegionDOListByType(type);
		if (null != res && CollectionUtils.isNotEmpty(res.getList())) {
			return res.getList();
		}
		return null;
	}

	@Override
	public PageVO<ScenicDO> selectScenicDO(ScenicPageQuery scenicPageQuery) throws Exception {
		return scenicSpotService.getList(scenicPageQuery);
	}

	@Override
	public List<RegionIntroduceDO> getListShowCaseResult(int type) {
		RegionIntroduceQuery regionIntroduceQuery = new RegionIntroduceQuery();
		regionIntroduceQuery.setType(type);
		// List<RegionIntroduceDO> list =
		// RegionIntroduceClientServiceRef.queryRegionIntroduceList(regionIntroduceQuery);
		List<RegionIntroduceDO> list = new ArrayList<RegionIntroduceDO>();
		RegionIntroduceDO e = new RegionIntroduceDO();
		e.setCityCode(31231);
		e.setContent("eqweqweqweqwe");
		e.setId(1);
		e.setTitle("eqwe");
		e.setType(10);

		RegionIntroduceDO e1 = new RegionIntroduceDO();
		e1.setCityCode(31231);
		e1.setContent("eqweqweqweqwe");
		e1.setId(1);
		e1.setTitle("eqwe");
		e1.setType(10);

		RegionIntroduceDO e2 = new RegionIntroduceDO();
		e2.setCityCode(31231);
		e2.setContent("eqweqweqweqwe");
		e2.setId(1);
		e2.setTitle("eqwe");
		e2.setType(10);
		list.add(e);
		list.add(e1);
		list.add(e2);
		return list;
	}

	@Override
	public List<RegionIntroduceDO> getListDestinationShowCaseResult(int type, String cityCode) {

		return null;
	}

	public List<HotelDO> getListHotelDO(String cityCode) {
		HotelPageQuery hotelPageQuery = new HotelPageQuery();
		hotelPageQuery.setRegionId(Long.valueOf(cityCode));
		ICPageResult<HotelDO> res = itemQueryServiceRef.pageQueryHotel(hotelPageQuery);
		if (res != null && res.isSuccess() && CollectionUtils.isNotEmpty(res.getList())) {
			return res.getList();
		}
		return null;

	}

	@Override
	public boolean blockOrUnBlock(long id, int cityCode, int type) throws Exception {
		RcResult<RegionDO> res = regionClientServiceRef.selectById(id);
		if (null == res || !res.isSuccess() || null == res.getT()) {
			throw new Exception("data [RegionDO] not available,id=" + id);
		}
		TripBo tripBo = new TripBo();
		tripBo.setId(id);
		tripBo.setCityCode(cityCode);
		if (type == BaseStatus.AVAILABLE.getType()) {
			tripBo.setStatus(BaseStatus.AVAILABLE.getType());
		} else {
			tripBo.setStatus(BaseStatus.DELETED.getType());
		}
		RegionDO db = saveOrUpdate(tripBo);
		if (null == db) {
			return false;
		}
		return true;
	}

}
