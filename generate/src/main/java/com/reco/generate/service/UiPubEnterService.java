package com.reco.generate.service;

import com.reco.generate.core.BaseService;
import com.reco.generate.entity.UiPubEnter;
import com.reco.generate.entity.UiPubEnterExample;

/**
 * Created by
 *
 * @User: xiesq
 * @Date: 2019/5/20 18:38
 * @Description: 双入口服务接口
 */
public interface UiPubEnterService extends BaseService<UiPubEnter, Integer, UiPubEnterExample> {

    UiPubEnter findByPos(Integer pos);
}
