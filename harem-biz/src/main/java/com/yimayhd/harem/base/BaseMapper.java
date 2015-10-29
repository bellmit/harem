package com.yimayhd.harem.base;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author czf
 */
public interface BaseMapper<T extends BaseModel> {

    Long getCount(@Param("entity") T entity) throws Exception;

    List<T> getList(PageVo<T> vo) throws Exception;

    void modify(T entity) throws Exception;

    void add(T entity) throws Exception;

    T getById(String id) throws Exception;

    void delete(String id) throws Exception;
}
