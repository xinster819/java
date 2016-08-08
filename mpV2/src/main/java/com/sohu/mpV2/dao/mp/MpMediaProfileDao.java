package com.sohu.mpV2.dao.mp;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.sohu.mpV2.dao.mp.mapper.MpMediaProfileMapper;
import com.sohu.mpV2.vo.MpMediaProfile;

@Component
public class MpMediaProfileDao {

    @Resource
    private MpMediaProfileMapper mpMediaProfileMapper;

    public MpMediaProfile byId(int id) {
        return mpMediaProfileMapper.byId(id);
    }

    public MpMediaProfile byMpMediaId(String mpMediaId) {
        return mpMediaProfileMapper.byMpMediaId(mpMediaId);
    }

    public void updateWeiboUrl(MpMediaProfile mpMediaProfile) {
        mpMediaProfileMapper.updateWeiboUrl(mpMediaProfile);
    }
}
