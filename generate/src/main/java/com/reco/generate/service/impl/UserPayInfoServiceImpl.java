package com.reco.generate.service.impl;

import com.reco.generate.core.BaseServiceImpl;
import com.reco.generate.entity.UserPayInfo;
import com.reco.generate.entity.UserPayInfoExample;
import com.reco.generate.repository.UserPayInfoMapper;
import com.reco.generate.service.UserPayInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by
 *
 * @User: xiesq
 * @Date: 2019/5/31 10:55
 * @Description: todo
 */
@Service(value = "userPayInfoService")
public class UserPayInfoServiceImpl extends BaseServiceImpl<UserPayInfo, Integer, UserPayInfoExample, UserPayInfoMapper> implements UserPayInfoService {

    @Autowired
    @Override
    public void setDao(UserPayInfoMapper userPayInfoMapper) {
        this.dao = userPayInfoMapper;
    }

    @Override
    public List<UserPayInfo> findByUserId(String userId) {
        UserPayInfoExample example = new UserPayInfoExample();
        example.createCriteria().andUidEqualTo(userId);
        return this.dao.selectByExample(example);
    }
}
