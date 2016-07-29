package com.sohu.mpV2.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sohu.mpV2.dao.mp.MpMediaProfileDao;
import com.sohu.mpV2.vo.MpMediaProfile;

@Service
public class MpMediaProfileService {

    @Resource
    private MpMediaProfileDao mpMediaProfileDao;

    public MpMediaProfile byId(int id) {
        return mpMediaProfileDao.byId(id);
    }
    
}
