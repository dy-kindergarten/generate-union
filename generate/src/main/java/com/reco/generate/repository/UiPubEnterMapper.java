package com.reco.generate.repository;

import com.reco.generate.core.BaseDao;
import com.reco.generate.entity.UiPubEnter;
import com.reco.generate.entity.UiPubEnterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UiPubEnterMapper extends BaseDao<UiPubEnter, Integer, UiPubEnterExample> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ui_pub_enter
     *
     * @mbg.generated Mon May 20 18:37:09 CST 2019
     */
    int countByExample(UiPubEnterExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ui_pub_enter
     *
     * @mbg.generated Mon May 20 18:37:09 CST 2019
     */
    int deleteByExample(UiPubEnterExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ui_pub_enter
     *
     * @mbg.generated Mon May 20 18:37:09 CST 2019
     */
    int insert(UiPubEnter record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ui_pub_enter
     *
     * @mbg.generated Mon May 20 18:37:09 CST 2019
     */
    int insertSelective(UiPubEnter record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ui_pub_enter
     *
     * @mbg.generated Mon May 20 18:37:09 CST 2019
     */
    List<UiPubEnter> selectByExample(UiPubEnterExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ui_pub_enter
     *
     * @mbg.generated Mon May 20 18:37:09 CST 2019
     */
    int updateByExampleSelective(@Param("record") UiPubEnter record, @Param("example") UiPubEnterExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ui_pub_enter
     *
     * @mbg.generated Mon May 20 18:37:09 CST 2019
     */
    int updateByExample(@Param("record") UiPubEnter record, @Param("example") UiPubEnterExample example);
}