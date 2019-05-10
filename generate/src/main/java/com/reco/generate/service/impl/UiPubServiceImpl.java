package com.reco.generate.service.impl;

import com.reco.generate.core.BaseServiceImpl;
import com.reco.generate.entity.UiPub;
import com.reco.generate.entity.UiPubExample;
import com.reco.generate.repository.UiPubMapper;
import com.reco.generate.service.UiPubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "uiPubService")
public class UiPubServiceImpl extends BaseServiceImpl<UiPub, Integer, UiPubExample, UiPubMapper> implements UiPubService {

    @Autowired
    @Override
    public void setDao(UiPubMapper uiPubMapper) {
        this.dao = uiPubMapper;
    }

    @Override
    public UiPub findByUnionId(Integer pid, Integer nid) {
        UiPubExample example = new UiPubExample();
        example.createCriteria().andPidEqualTo(pid).andNidEqualTo(nid);
        List<UiPub> list = this.dao.selectByExample(example);
        return null != list && list.size() > 0 ? list.get(0) : null;
    }
}
