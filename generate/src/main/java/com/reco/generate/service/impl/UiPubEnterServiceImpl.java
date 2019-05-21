package com.reco.generate.service.impl;

import com.reco.generate.core.BaseServiceImpl;
import com.reco.generate.entity.UiPubEnter;
import com.reco.generate.entity.UiPubEnterExample;
import com.reco.generate.repository.UiPubEnterMapper;
import com.reco.generate.service.UiPubEnterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by
 *
 * @User: xiesq
 * @Date: 2019/5/20 18:39
 * @Description: 双入口服务
 */
@Service(value = "uiPubEnterService")
public class UiPubEnterServiceImpl extends BaseServiceImpl<UiPubEnter, Integer, UiPubEnterExample, UiPubEnterMapper> implements UiPubEnterService {

    @Autowired
    @Override
    public void setDao(UiPubEnterMapper uiPubEnterMapper) {
        this.dao = uiPubEnterMapper;
    }

    @Override
    public UiPubEnter findByPos(Integer pos) {
        UiPubEnterExample example = new UiPubEnterExample();
        example.createCriteria().andPosEqualTo(pos);
        List<UiPubEnter> list = this.dao.selectByExample(example);
        return null != list && list.size() > 0 ? list.get(0) : null;
    }
}
