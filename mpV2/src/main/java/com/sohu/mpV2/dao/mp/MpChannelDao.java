package com.sohu.mpV2.dao.mp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.sohu.mpV2.dao.mp.mapper.MpChannelMapper;
import com.sohu.mpV2.vo.MpChannel;

@Component
public class MpChannelDao {

    @Resource
    private MpChannelMapper mpChannelMapper;

    public MpChannel byId(int id) {
        return mpChannelMapper.byId(id);
    }
    
    public List<MpChannel> byStatus(int status) {
        return mpChannelMapper.byStatus(status);
    }
}
