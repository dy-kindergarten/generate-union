package com.reco.generate.core;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseDao<E, PK, Example> {

    int countByExample(Example example);

    int deleteByExample(Example example);

    int deleteByPrimaryKey(PK id);

    int insert(E record);

    int insertSelective(E record);

    List<E> selectByExample(Example example);

    E selectByPrimaryKey(PK id);

    int updateByExampleSelective(@Param("record") E record, @Param("example") Example example);

    int updateByExample(@Param("record") E record, @Param("example") Example example);

    int updateByPrimaryKeySelective(E record);

    int updateByPrimaryKey(E record);

    int getPageTotal(E entity);

    List<E> findByPage(E entity);

    List<E> selectByExampleByPage(@Param("example") Example example, @Param("offset") int offset, @Param("size") int size);

    Integer getMaxId();
}
