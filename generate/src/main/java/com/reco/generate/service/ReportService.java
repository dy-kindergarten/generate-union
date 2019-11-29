package com.reco.generate.service;

import com.reco.generate.bo.enumEntity.DateType;

import java.util.Date;

/**
 * Created by
 *
 * @User: xiesq
 * @Date: 2019/10/16 9:41
 * @Description: 报表服务
 */
public interface ReportService {

    void report(Date startDate, Date endDate, DateType type);
}
