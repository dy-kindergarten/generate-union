package com.reco.generate.service;

import com.reco.generate.core.BaseService;
import com.reco.generate.entity.UserPayInfo;
import com.reco.generate.entity.UserPayInfoExample;

import java.util.List;

/**
 * Created by
 *
 * @User: xiesq
 * @Date: 2019/5/31 10:54
 * @Description: todo
 */
public interface UserPayInfoService extends BaseService<UserPayInfo, Integer, UserPayInfoExample> {

    List<UserPayInfo> findByUserId(String userId);
}
