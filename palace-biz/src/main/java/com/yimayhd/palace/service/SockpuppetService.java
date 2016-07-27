package com.yimayhd.palace.service;

import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.query.SockpuppetListQuery;
import com.yimayhd.palace.model.vo.SockpuppetVO;

/**
 * 运营小号
 * @return
 * date:2016年7月5日
 * author:xmn
 */
public interface SockpuppetService{

	PageVO<SockpuppetVO> getUserListByPage(SockpuppetListQuery query);

}
