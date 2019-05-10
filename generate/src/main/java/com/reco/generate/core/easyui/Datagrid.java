package com.reco.generate.core.easyui;

import com.reco.generate.core.Entity;
import lombok.Data;

@Data
public abstract class Datagrid extends Entity {

    /**
     * 当前页数
     */
    private Integer page = 1;

    /**
     * 每页数据量
     */
    private Integer rows = 20;

    /**
     * 排序字段
     */
    private String sort;

    /**
     * 正序倒序
     */
    private String orderBy;

}
