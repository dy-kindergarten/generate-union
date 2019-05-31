package com.reco.generate.service.impl;

import com.reco.generate.core.BaseServiceImpl;
import com.reco.generate.entity.UserPayVal;
import com.reco.generate.entity.UserPayValExample;
import com.reco.generate.repository.UserPayValMapper;
import com.reco.generate.service.UserPayValService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by
 *
 * @User: xiesq
 * @Date: 2019/5/31 10:56
 * @Description: todo
 */
@Service(value = "userPayValService")
public class UserPayValServiceImpl extends BaseServiceImpl<UserPayVal, Integer, UserPayValExample, UserPayValMapper> implements UserPayValService {

    @Autowired
    @Override
    public void setDao(UserPayValMapper userPayValMapper) {
        this.dao = userPayValMapper;
    }

    @Override
    public List<UserPayVal> findByUserId(String userId) {
        UserPayValExample example = new UserPayValExample();
        example.createCriteria().andUidEqualTo(userId);
        return this.dao.selectByExample(example);
    }
}
