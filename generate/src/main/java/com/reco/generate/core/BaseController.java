package com.reco.generate.core;

import com.reco.generate.core.easyui.Datagrid;
import com.reco.generate.core.easyui.PageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;

public abstract class BaseController<E extends Datagrid, PK, Example, Service extends BaseService> {

    protected Logger logger = LoggerFactory.getLogger(BaseController.class);

    protected Service service;

    protected Service getService() {
        return service;
    }

    protected abstract void setService(Service service);

    @GetMapping(value = "datagrid")
    public PageModel datagrid(E entity, PageModel<E> page) {
        Integer pageNo = null != entity.getPage() ? entity.getPage() : 1;
        Integer rows = null != entity.getRows() ? entity.getRows() : 20;
        entity.setPage(pageNo);
        entity.setRows(rows);
        return service.findByPage(entity, page);
    }
}
