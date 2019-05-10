package com.reco.generate.core;

import com.reco.generate.core.easyui.PageModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseService<E, PK, Example> {

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

    PageModel<E> findByPage(E entity, PageModel<E> page);

    List<E> selectByExample(Example example, int offset, int size);

    int getMaxId();
}
