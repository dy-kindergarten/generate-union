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

    private Integer total;

    private Date startDate;

    private Date endDate;
}
