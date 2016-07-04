package com.yimayhd.palace.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.base.BaseException;
import com.yimayhd.palace.base.BaseServiceImpl;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.enums.SockpuppetStatusEnum;
import com.yimayhd.palace.mapper.SockpuppetMapper;
import com.yimayhd.palace.model.SockpuppetDO;
import com.yimayhd.palace.model.query.SockpuppetListQuery;
import com.yimayhd.palace.model.vo.SockpuppetVO;
import com.yimayhd.palace.service.SockpuppetService;
import com.yimayhd.user.client.result.BasePageResult;

/**
 * 角色表（菜单）
 * @author czf
 */
//@Service
public class SockpuppetServiceImpl extends BaseServiceImpl<SockpuppetDO> implements SockpuppetService{
	protected final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private SockpuppetMapper sockpuppetMapper;

    @Override
    protected void initBaseMapper() {
        setBaseMapper(sockpuppetMapper);
    }

	@Override
	public PageVO<SockpuppetVO> getUserListByPage(SockpuppetListQuery query) {
		SockpuppetListQuery sockpuppetListQuery = new SockpuppetListQuery();
		sockpuppetListQuery.setPageSize(query.getPageSize());
		sockpuppetListQuery.setPageNo((query.getPageNo()-1)*query.getPageSize());
		if (StringUtils.isNotBlank(query.getNickname())) {
			sockpuppetListQuery.setNickname(query.getNickname());
		}
		int totalCount = 0;
		List<SockpuppetVO> itemList;
		try {
			BasePageResult<SockpuppetDO> result = new BasePageResult<SockpuppetDO>();
			List<SockpuppetDO> resultByCondition = sockpuppetMapper.findPageResultByCondition(sockpuppetListQuery);
			totalCount = sockpuppetMapper.getTotalCount(sockpuppetListQuery);
			itemList = new ArrayList<SockpuppetVO>();
			if (result != null && result.isSuccess()) {
				result.setList(resultByCondition);
				if (CollectionUtils.isNotEmpty(result.getList())) {
					List<SockpuppetDO> list = result.getList();
					for (SockpuppetDO sockpuppetDO : list) {
						SockpuppetVO sockpuppetVO = new SockpuppetVO();
						BeanUtils.copyProperties(sockpuppetVO, sockpuppetDO);
						sockpuppetVO.setStatusText(SockpuppetStatusEnum.get(sockpuppetDO.getStatus()).getText());
						itemList.add(sockpuppetVO);
					}
				}
			} else {
				log.error("查询用户列表失败：query={}", JSON.toJSONString(query));
			}
			return new PageVO<SockpuppetVO>(query.getPageNo(), query.getPageSize(), totalCount, itemList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
