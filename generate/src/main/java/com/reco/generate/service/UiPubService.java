package com.reco.generate.service;

import com.reco.generate.core.BaseService;
import com.reco.generate.entity.UiPub;
import com.reco.generate.entity.UiPubExample;

public interface UiPubService extends BaseService<UiPub, Integer, UiPubExample> {

    UiPub findByUnionId(Integer pid, Integer nid);
}
