package com.reco.generate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by
 *
 * @User: xiesq
 * @Date: 2019/10/16 15:38
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report implements Serializable {

    public String day;

    public Integer pv;

    public Integer uv;

    public Integer orderIncrement;

    public Integer total;

}
