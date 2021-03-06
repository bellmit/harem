package com.yimayhd.palace.mapper;

import java.util.List;

import com.yimayhd.palace.base.BaseMapper;
import com.yimayhd.palace.model.SockpuppetDO;
import com.yimayhd.palace.model.query.SockpuppetListQuery;
/**
 * 运营小号
 * @return
 * date:2016年7月5日
 * author:xmn
 */
public interface SockpuppetMapper extends BaseMapper<SockpuppetDO>{
    /**
     *  根据主键删除数据库的记录,sockpuppet
     *
     * @param id
     */
    int deleteByPrimaryKey(Long id);

    /**
     *  新写入数据库记录,sockpuppet
     *
     * @param record
     */
    int insert(SockpuppetDO record);

    /**
     *  动态字段,写入数据库记录,sockpuppet
     *
     * @param record
     */
    int insertSelective(SockpuppetDO record);

    /**
     *  根据指定主键获取一条数据库记录,sockpuppet
     *
     * @param id
     */
    SockpuppetDO selectByPrimaryKey(Long id);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,sockpuppet
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SockpuppetDO record);

    /**
     *  根据主键来更新符合条件的数据库记录,sockpuppet
     *
     * @param record
     */
    int updateByPrimaryKey(SockpuppetDO record);

	List<SockpuppetDO> findPageResultByCondition(SockpuppetListQuery sockpuppetListQuery);

	int getTotalCount(SockpuppetListQuery sockpuppetListQuery);
}