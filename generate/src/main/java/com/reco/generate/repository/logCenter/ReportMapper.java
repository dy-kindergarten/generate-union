package com.reco.generate.repository.logCenter;

import com.reco.generate.entity.Report;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by
 *
 * @User: xiesq
 * @Date: 2019/10/16 15:36
 * @Description:
 */
public interface ReportMapper {

    Report countVolume(@Param("startDate") String startDate, @Param("endDate") String endDate);

    List<Report> dailyCount(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
