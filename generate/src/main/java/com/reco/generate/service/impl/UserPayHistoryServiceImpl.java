package com.reco.generate.service.impl;

import com.google.common.collect.Lists;
import com.reco.generate.bo.PurchaseRecordBo;
import com.reco.generate.core.BaseServiceImpl;
import com.reco.generate.entity.UserPayHistory;
import com.reco.generate.entity.UserPayHistoryExample;
import com.reco.generate.repository.UserPayHistoryMapper;
import com.reco.generate.service.UserPayHistoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service(value = "userPayHistoryService")
public class UserPayHistoryServiceImpl extends BaseServiceImpl<UserPayHistory, Integer, UserPayHistoryExample, UserPayHistoryMapper> implements UserPayHistoryService {

    @Autowired
    @Override
    public void setDao(UserPayHistoryMapper userPayHistoryMapper) {
        this.dao = userPayHistoryMapper;
    }

    @Override
    public List<PurchaseRecordBo> findBySongIds(String songIds, Date from, Date to) {
        if (StringUtils.isNotBlank(songIds)) {
            List<String> stringList = Lists.newArrayList(songIds.split(","));
            List<Integer> songIdList = Lists.newArrayList();
            for (String songId : stringList) {
                if (!StringUtils.equalsIgnoreCase(songId, "0")) {
                    songIdList.add(Integer.valueOf(songId));
                }
            }
            return this.dao.findBySongIds(songIdList, from, to);
        }
        return Lists.newArrayList();
    }

    @Override
    public List<UserPayHistory> findByUserId(String userId) {
        UserPayHistoryExample example = new UserPayHistoryExample();
        example.createCriteria().andUidEqualTo(userId);
        return this.dao.selectByExample(example);
    }
}
