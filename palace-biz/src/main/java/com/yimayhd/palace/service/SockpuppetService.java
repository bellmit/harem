package com.yimayhd.palace.service;

import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.query.SockpuppetListQuery;
import com.yimayhd.palace.model.vo.SockpuppetVO;

/**
 * 运行小号
 * @author xmn
 */
public interface SockpuppetService{

	PageVO<SockpuppetVO> getUserListByPage(SockpuppetListQuery query);

}
