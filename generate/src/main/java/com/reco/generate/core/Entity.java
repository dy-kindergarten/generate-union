package com.reco.generate.core;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class Entity implements Serializable {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 操作类型
     */
    protected String action = "add";

}
