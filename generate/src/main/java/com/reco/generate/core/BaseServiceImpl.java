package com.reco.generate.core;

import com.reco.generate.core.easyui.PageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class BaseServiceImpl<E, PK, Example, DAO extends BaseDao<E, PK, Example>> implements BaseService<E, PK, Example> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected DAO dao;

    public DAO getDao() {
        return dao;
    }

    public abstract void setDao(DAO dao);

    @Override
    public int countByExample(Example example) {
        return this.dao.countByExample(example);
    }

    @Override
    public int deleteByExample(Example example) {
        return this.dao.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(PK id) {
        return this.dao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(E record) {
        return this.dao.insert(record);
    }

    @Override
    public int insertSelective(E record) {
        return this.dao.insertSelective(record);
    }

    @Override
    public List<E> selectByExample(Example example) {
        return this.dao.selectByExample(example);
    }

    @Override
    public E selectByPrimaryKey(PK id) {
        return this.dao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByExampleSelective(E record, Example example) {
        return this.dao.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(E record, Example example) {
        return this.dao.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(E record) {
        return this.dao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(E record) {
        return this.dao.updateByPrimaryKey(record);
    }

    @Override
    public PageModel<E> findByPage(E entity, PageModel<E> page) {
        page.setTotal(this.dao.getPageTotal(entity));
        page.setRows(this.dao.findByPage(entity));
        return page;
    }

    @Override
    public List<E> selectByExample(Example example, int offset, int size) {
        return this.dao.selectByExampleByPage(example, offset, size);
    }

    @Override
    public int getMaxId() {
        Integer maxId = this.dao.getMaxId();
        return null == maxId ? 0 : maxId;
    }
}
