package com.reco.generate.repository;

import com.reco.generate.core.BaseDao;
import com.reco.generate.entity.UserPayInfo;
import com.reco.generate.entity.UserPayInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserPayInfoMapper extends BaseDao<UserPayInfo, Integer, UserPayInfoExample> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_pay_info
     *
     * @mbg.generated Fri May 31 10:52:59 CST 2019
     */
    int countByExample(UserPayInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_pay_info
     *
     * @mbg.generated Fri May 31 10:52:59 CST 2019
     */
    int deleteByExample(UserPayInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_pay_info
     *
     * @mbg.generated Fri May 31 10:52:59 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_pay_info
     *
     * @mbg.generated Fri May 31 10:52:59 CST 2019
     */
    int insert(UserPayInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_pay_info
     *
     * @mbg.generated Fri May 31 10:52:59 CST 2019
     */
    int insertSelective(UserPayInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_pay_info
     *
     * @mbg.generated Fri May 31 10:52:59 CST 2019
     */
    List<UserPayInfo> selectByExample(UserPayInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_pay_info
     *
     * @mbg.generated Fri May 31 10:52:59 CST 2019
     */
    UserPayInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_pay_info
     *
     * @mbg.generated Fri May 31 10:52:59 CST 2019
     */
    int updateByExampleSelective(@Param("record") UserPayInfo record, @Param("example") UserPayInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_pay_info
     *
     * @mbg.generated Fri May 31 10:52:59 CST 2019
     */
    int updateByExample(@Param("record") UserPayInfo record, @Param("example") UserPayInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_pay_info
     *
     * @mbg.generated Fri May 31 10:52:59 CST 2019
     */
    int updateByPrimaryKeySelective(UserPayInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_pay_info
     *
     * @mbg.generated Fri May 31 10:52:59 CST 2019
     */
    int updateByPrimaryKey(UserPayInfo record);
}