package com.yimayhd.palace.service;

import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.Region;
import com.yimayhd.palace.model.vo.booth.ShowcaseVO;
import com.yimayhd.palace.result.BizResult;
import com.yimayhd.resourcecenter.domain.BoothDO;
import com.yimayhd.resourcecenter.domain.OperationDO;
import com.yimayhd.resourcecenter.domain.RegionDO;
import com.yimayhd.resourcecenter.model.enums.RegionType;
import com.yimayhd.resourcecenter.model.enums.ShowcaseStauts;
import com.yimayhd.resourcecenter.model.query.OperationQuery;
import com.yimayhd.resourcecenter.model.query.ShowcaseQuery;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.model.result.ShowCaseResult;

import java.util.List;

/**
 * Created by czf on 2016/4/13.
 */
public interface ShowcaseService {
    /**
     *  根据boothId获取showcase列表
     * @param boothId
     * @return
     * @throws Exception
     */
    List<ShowcaseVO> getList(long boothId)throws Exception;

    /**
     * 根据showcaseId获取showcase实体
     * @param id
     * @return
     * @throws Exception
     */
    ShowcaseVO getById(long id)throws Exception;

    /**
     * 新增showcase
     * @param entity
     * @return
     * @throws Exception
     */
    ShowcaseVO add(ShowcaseVO entity)throws Exception;

    /**
     *  修改showcase
     * @param entity
     * @return
     * @throws Exception
     */
    ShowcaseVO saveOrUpdate(ShowcaseVO entity)throws Exception;

    /**
     *  上下架
     * @return
     * @throws Exception
     */
    boolean publish(long id,ShowcaseStauts status)throws Exception;

    /**
     *  根据查询条件查询showcase列表返回page对象
     * @return
     * @throws Exception
     */
    PageVO<ShowCaseResult> getShowcaseResult(ShowcaseQuery showcaseQuery);

    PageVO<OperationDO> getPageOperationDO(OperationQuery operationQuery);

    //操作跳转列表
    List<OperationDO> getListOperationDO(OperationQuery operationQuery);

    //目的地
    List<RegionDO> getListdestination(RegionType regionType);

    //主题
    List<OperationDO> getListtheme(OperationQuery operationQuery);

    BoothDO getBoothInfoByBoothCode(String code) throws Exception;;
}
