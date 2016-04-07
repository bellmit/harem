package com.yimayhd.gf.service;

import com.yimayhd.gf.model.BbsPostsQueryVO;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.snscenter.client.domain.SnsMasterDO;
import com.yimayhd.snscenter.client.domain.SnsModuleDO;
import com.yimayhd.snscenter.client.domain.SnsPostsDO;
import com.yimayhd.snscenter.client.dto.PostsResultDTO;
import com.yimayhd.snscenter.client.query.SnsMasterPageQuery;
import com.yimayhd.snscenter.client.query.SnsModulePageQuery;
import com.yimayhd.snscenter.client.query.SnsPostsQuery;
import com.yimayhd.snscenter.client.result.BaseResult;

public interface BbsService {

	PageVO<SnsModuleDO> moduleQueryList(SnsModulePageQuery snsModulePageQuery);

	BaseResult<SnsModuleDO> updateSnsModule(SnsModuleDO bbsModuleDO);

	BaseResult<SnsModuleDO> saveSnsModule(SnsModuleDO bbsModuleDO);

	BaseResult<SnsModuleDO> selectSnsModuleById(long id);

	BaseResult<SnsMasterDO> saveBbsMaster(SnsMasterDO bbsMasterDO);

	BaseResult<SnsMasterDO> updateBbsMaster(SnsMasterDO bbsMasterDO);

	BaseResult<SnsMasterDO> getBbsMasterById(long parseLong);

	PageVO<SnsMasterDO> masterQueryList(SnsMasterPageQuery bbsMasterPageQuery);

	PageVO<PostsResultDTO> postsQueryList(BbsPostsQueryVO postsQuery);

	BaseResult<SnsPostsDO> updatePosts(SnsPostsDO snsPosts);

	BaseResult<SnsPostsDO> savePosts(SnsPostsDO snsPosts);

	BaseResult<SnsPostsDO> getPostsDetail(long postsId);

	BaseResult<Boolean> updatePostsStatus(SnsPostsDO bbsPostsDO);

}
