package com.yimayhd.harem.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.harem.base.BaseException;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.PictureVO;
import com.yimayhd.harem.model.RestaurantVO;
import com.yimayhd.harem.model.query.RestaurantListQuery;
import com.yimayhd.harem.repo.PictureRepo;
import com.yimayhd.harem.repo.RestaurantRepo;
import com.yimayhd.harem.service.RestaurantService;
import com.yimayhd.harem.util.DateUtil;
import com.yimayhd.ic.client.model.domain.PicturesDO;
import com.yimayhd.ic.client.model.domain.RestaurantDO;
import com.yimayhd.ic.client.model.enums.PictureOutType;
import com.yimayhd.ic.client.model.param.item.PictureUpdateDTO;
import com.yimayhd.ic.client.model.query.RestaurantPageQuery;

public class RestaurantServiceImpl implements RestaurantService {
	@Autowired
	private RestaurantRepo restaurantRepo;
	private PictureRepo pictureRepo;

	@Override
	public PageVO<RestaurantDO> pageQueryRestaurant(RestaurantListQuery restaurantListQuery) {

		RestaurantPageQuery restaurantPageQuery = new RestaurantPageQuery();
		restaurantPageQuery.setNeedCount(true);
		restaurantPageQuery.setPageNo(restaurantListQuery.getPageNumber());
		restaurantPageQuery.setPageSize(restaurantListQuery.getPageSize());
		// 酒店名称
		restaurantPageQuery.setName(restaurantListQuery.getName());
		// 状态
		restaurantPageQuery.setStatus(restaurantListQuery.getStatus());
		// // 创建时间
		if (!StringUtils.isBlank(restaurantListQuery.getBeginTime())) {
			Date startTime = DateUtil.parseDate(restaurantListQuery.getBeginTime());
			restaurantPageQuery.setStartTime(startTime);
		}
		// // 结束时间
		if (!StringUtils.isBlank(restaurantListQuery.getEndTime())) {
			Date endTime = DateUtil.parseDate(restaurantListQuery.getEndTime());
			restaurantPageQuery.setEndTime(DateUtil.add23Hours(endTime));
		}
		return restaurantRepo.pageQueryRestaurant(restaurantPageQuery);

	}

	@Override
	public RestaurantDO getRestaurantById(long id) {
		return restaurantRepo.getRestaurantById(id);
	}

	@Override
	public void publish(RestaurantVO restaurantVO) {
		long id = restaurantVO.getId();
		if (id > 0) {
			RestaurantDO restaurantDO = getRestaurantById(id);
			RestaurantDO restaurantDTO = restaurantVO.toRestaurantDO(restaurantDO);
			restaurantRepo.updateRestaurant(restaurantDTO);
			List<PicturesDO> picturesDOList = pictureRepo.queryAllPictures(PictureOutType.RESTAURANT, id);
			String picListStr = restaurantVO.getPicListStr();
			List<PictureVO> pictureVOList = JSON.parseArray(picListStr, PictureVO.class);
			PictureUpdateDTO pictureUpdateDTO = new PictureUpdateDTO();
			if (PictureVO.setPictureListPictureUpdateDTO(id, PictureOutType.RESTAURANT, pictureUpdateDTO,
					picturesDOList, pictureVOList) != null) {
				boolean updatePictures = restaurantRepo.updatePictures(pictureUpdateDTO);
				if (!updatePictures) {
					throw new BaseException("餐厅资源保存成功，图片集保存失败");
				}
			}
		} else {
			RestaurantDO restaurantDO = restaurantVO.toRestaurantDO();
			restaurantRepo.addRestaurant(restaurantDO);
			String picListStr = restaurantVO.getPicListStr();
			if (StringUtils.isNotBlank(picListStr)) {
				List<PictureVO> pictureVOList = JSON.parseArray(picListStr, PictureVO.class);
				List<PicturesDO> picList = new ArrayList<PicturesDO>();
				for (PictureVO pictureVO : pictureVOList) {
					PicturesDO picturesDO = new PicturesDO();
					picturesDO.setPath(pictureVO.getValue());
					picturesDO.setName(pictureVO.getName());
					picturesDO.setOutId(restaurantDO.getId());
					picturesDO.setOutType(PictureOutType.RESTAURANT.getValue());
					// TODO picturesDO.setOrderNum(pictureVO.getIndex());
					picturesDO.setIsTop(pictureVO.isTop());
					picList.add(picturesDO);
				}
				if (CollectionUtils.isNotEmpty(picList)) {
					boolean addPictures = restaurantRepo.addPictures(picList);
					if (!addPictures) {
						throw new BaseException("餐厅资源保存成功，图片集保存失败");
					}
				}
			}
		}
	}

	@Override
	public void changeStatus(long id, int status) {
		// TODO 暂不实现
	}
}
