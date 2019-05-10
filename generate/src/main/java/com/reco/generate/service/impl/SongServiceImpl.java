package com.reco.generate.service.impl;

import com.reco.generate.core.BaseServiceImpl;
import com.reco.generate.core.easyui.PageModel;
import com.reco.generate.entity.Song;
import com.reco.generate.entity.SongExample;
import com.reco.generate.repository.SongMapper;
import com.reco.generate.service.SongService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "songService")
public class SongServiceImpl extends BaseServiceImpl<Song, Integer, SongExample, SongMapper> implements SongService {

    @Autowired
    @Override
    public void setDao(SongMapper songMapper) {
        this.dao = songMapper;
    }

    @Override
    public PageModel<Song> findByPage(Song entity, PageModel<Song> page) {
        page.setTotal(this.dao.getPageTotal(entity));
        page.setRows(this.dao.findByPage(entity));
        return page;
    }

    @Override
    public List<Song> findByCname(String cname) {
        SongExample example = new SongExample();
        if(StringUtils.isNotBlank(cname)) {
            example.createCriteria().andCnameLike(cname + "%");
        }
        return this.dao.selectByExample(example);
    }
}
