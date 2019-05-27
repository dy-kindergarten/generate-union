package com.reco.generate.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsBo implements Serializable {

    private Integer clickNum;

    private String activityName;

    private String url;

    private Date createTime;
}
