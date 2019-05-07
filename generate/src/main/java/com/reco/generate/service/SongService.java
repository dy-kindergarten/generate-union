package com.reco.generate.service;

import com.reco.generate.core.BaseService;
import com.reco.generate.entity.Song;
import com.reco.generate.entity.SongExample;

import java.util.List;

public interface SongService extends BaseService<Song, Integer, SongExample> {

    List<Song> findByCname(String cname);
}
