package com.yimayhd.harem.base;

import java.util.List;

public interface BaseService<T extends BaseModel> {

    /**
     * 统计数目
     *
     * @param entity 条件
     * @throws Exception
     */
    Long getCount(T entity) throws Exception;

    /**
     * 查询
     *
     * @param vo 查询条件及其他参数
     * @throws Exception
     */
    List<T> getList(PageVo<T> vo) throws Exception;

    /**
     * 修改
     *
     * @param entity 需要修改的字段和主键
     * @throws Exception
     */
    void modify(T entity) throws Exception;

    /**
     * 添加
     *
     * @param entity 数据实体
     * @throws Exception
     */
    void add(T entity) throws Exception;

    /**
     * 根据主键获取
     *
     * @param id 主键String类型的
     * @throws Exception
     */
    T getById(String id) throws Exception;

    /**
     * 删除
     *
     * @param id 主键String类型的
     * @throws Exception
     */
    void delete(String id) throws Exception;
}
