package com.yimayhd.palace.service;

import com.yimayhd.activitycenter.domain.ActActivityPromotionDO;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.ActActivityEditVO;
import com.yimayhd.palace.model.GiftActivityVO;
import com.yimayhd.palace.model.query.ActPromotionPageQuery;
import com.yimayhd.palace.result.BizResultSupport;
import com.yimayhd.promotion.client.domain.PromotionDO;

/**
 * Created by czf on 2016/1/19.
 */
public interface PromotionCommService {
    /**
     * 根据条件查询列表
     *
     * @param actPromotionPageQuery 查询条件
     * @throws Exception
     */
    PageVO<ActActivityPromotionDO> getList(ActPromotionPageQuery actPromotionPageQuery) throws Exception;

    /**
     * 修改优惠
     *
     * @param actActivityEditVO
     *            需要修改的字段和主键
     * @throws Exception
     */
    void modify(ActActivityEditVO actActivityEditVO) throws Exception;

    /**
     * 添加优惠
     *
     * @return 优惠
     * @param entity
     *            数据实体
     * @throws Exception
     */
    boolean add(ActActivityEditVO entity) throws Exception;

    BizResultSupport check(ActActivityEditVO entity) ;

    /**
     * 根据主键获取优惠
     *
     * @param id
     *            主键long类型的
     * @throws Exception
     */
    ActActivityEditVO getById(long id) throws Exception;
    ActActivityEditVO getGiftById(long id) throws Exception;
    /**
     * 优惠上架
     * @param id 优惠ID
     */
    boolean publish(long id)throws Exception;

    /**
     * 优惠下架
     * @param id 优惠ID
     */
    boolean close(long id)throws Exception;

    boolean isActivityNameRepeat(String name, int type, long activityId);

    boolean addGift(ActActivityEditVO actActivityEditVO) throws Exception;

    boolean updateEndGift(ActActivityEditVO actActivityEditVO) throws Exception;

}
