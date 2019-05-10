package com.reco.generate.service;

import com.reco.generate.bo.PurchaseRecordBo;
import com.reco.generate.core.BaseService;
import com.reco.generate.entity.UserPayHistory;
import com.reco.generate.entity.UserPayHistoryExample;

import java.util.Date;
import java.util.List;

public interface UserPayHistoryService extends BaseService<UserPayHistory, Integer, UserPayHistoryExample> {

    List<PurchaseRecordBo> findBySongIds(String songIds, Date from, Date to);
}
