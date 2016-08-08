package com.sohu.mpV2.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sohu.mpV2.dao.mp.MpChannelDao;
import com.sohu.mpV2.vo.MpChannel;

@Service
public class MpChannelService {

    @Resource
    private MpChannelDao mpChannelDao;

    public MpChannel byId(int id) {
        return mpChannelDao.byId(id);
    }
    
    public List<MpChannel> activeChannels() {
        return mpChannelDao.byStatus(1);
    }
}
