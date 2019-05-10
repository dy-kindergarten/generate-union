package com.reco.generate.core;

import com.reco.generate.core.easyui.Datagrid;
import com.reco.generate.core.easyui.PageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public abstract class BaseController<Entity extends Datagrid, PK, Example, Service extends BaseService> {

    protected Logger logger = LoggerFactory.getLogger(BaseController.class);

    protected Service service;

    protected Service getService() {
        return service;
    }

    protected abstract void setService(Service service);

    @ResponseBody
    @GetMapping(value = "datagrid")
    public PageModel datagrid(Entity entity, PageModel<Entity> page) {
        page.setPageNo(entity.getPage());
        page.setPageSize(entity.getRows());
        return service.findByPage(entity, page);
    }

    @ResponseBody
    @PostMapping(value = "insert")
    public AjaxResult insert(Entity entity) {
        entity.setId(service.getMaxId() + 1);
        int count = service.insert(entity);
        if (count > 0) {
            return new AjaxResult(200, "新增成功");
        }
        return new AjaxResult(201, "新增失败");
    }
}
