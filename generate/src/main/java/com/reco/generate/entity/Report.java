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

    private Integer pv;

    private Integer uv;

    private Integer orderIncrement;
}
