package com.reco.generate.core.easyui;

import lombok.Data;

import java.util.List;

@Data
public class PageModel<T> {

    private int pageNo;

    private int pageSize;

    private int total;

    private List<T> rows;

    private Object footer;

    private String msg;
}
