package com.sohu.mpV2.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sohu.mpV2.dao.news.MpNewsDao;
import com.sohu.mpV2.vo.MpNews;

@Service
public class MpNewsService {

    @Resource
    private MpNewsDao mpNewsDao;

    public MpNews byId(long id, String mpMediaId) {
        return mpNewsDao.byId(id, mpMediaId);
    }

    public boolean publish(MpNews mpNews) {
        return true;
    }

}
