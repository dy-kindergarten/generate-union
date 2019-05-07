package com.reco.generate.core.easyui;

import lombok.Data;

import java.util.List;

@Data
public class PageModel<T> {

    private Integer pageNo;

    private Integer pageSize;

    private Integer total;

    private List<T> rows;
}
