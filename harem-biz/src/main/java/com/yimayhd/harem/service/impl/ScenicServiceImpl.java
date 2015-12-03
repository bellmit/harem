package com.yimayhd.harem.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.query.ScenicSpotListQuery;
import com.yimayhd.harem.model.vo.ScenicSpotVO;
import com.yimayhd.harem.service.ScenicService;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.service.item.ItemQueryService;

/**
 * Created by Administrator on 2015/11/18.
 */
public class ScenicServiceImpl implements ScenicService {
	private List<ScenicDO> table = new ArrayList<ScenicDO>();

	public ScenicServiceImpl() {
		for (long i = 0; i < 100; i++) {
			try {
				table.add(getById(i));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Autowired
	private ItemQueryService itemQueryService;

	@Override
	public List<ScenicDO> getList(ScenicSpotVO scenicSpotVO) throws Exception {

		return null;
	}

	@Override
	public ScenicDO getById(long id) throws Exception {
		ScenicDO scenicDO = new ScenicDO();
		scenicDO.setId(id);
		scenicDO.setName("景点" + id);
		scenicDO.setStatus(1);
		return scenicDO;
	}

	@Override
	public ScenicDO add(ScenicDO scenicDO) throws Exception {
		return null;
	}

	@Override
	public void modify(ScenicDO scenicDO) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void setScenicStatusList(List<Integer> idList, int scenicStatus) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void setScenicStatus(long id, int scenicStatus) throws Exception {

	}

	@Override
	public PageVO<ScenicDO> getListByPage(ScenicSpotListQuery query) throws Exception {
		int totalCount = count(query);
		PageVO<ScenicDO> page = new PageVO<ScenicDO>(query.getPageNumber(), query.getPageSize(), totalCount);
		List<ScenicDO> itemList = find(query);
		page.setItemList(itemList);
		return page;
	}

	protected Integer count(ScenicSpotListQuery query) throws Exception {
		return query(table, query).size();
	}

	protected List<ScenicDO> find(ScenicSpotListQuery query) throws Exception {
		int fromIndex = query.getPageSize() * (query.getPageNumber() - 1);
		int toIndex = query.getPageSize() * query.getPageNumber();
		List<ScenicDO> result = query(table, query);
		if (result.size() > 0) {
			if (toIndex > result.size()) {
				toIndex = result.size();
			}
			result = result.subList(fromIndex, toIndex);
		}
		return result;
	}

	private List<ScenicDO> query(List<ScenicDO> hotels, ScenicSpotListQuery query) {
		List<ScenicDO> result = new ArrayList<ScenicDO>();
		for (ScenicDO hotel : hotels) {
			if (StringUtils.isNotBlank(query.getName())) {
				if (hotel.getName().indexOf(query.getName()) != -1) {
					result.add(hotel);
				}
			} else {
				result.add(hotel);
			}
		}
		return result;
	}

}
