package com.reco.generate.service;

import com.reco.generate.core.BaseService;
import com.reco.generate.entity.UserPayVal;
import com.reco.generate.entity.UserPayValExample;

import java.util.List;

/**
 * Created by
 *
 * @User: xiesq
 * @Date: 2019/5/31 10:56
 * @Description: todo
 */
public interface UserPayValService extends BaseService<UserPayVal, Integer, UserPayValExample> {

    List<UserPayVal> findByUserId(String userId);
}
