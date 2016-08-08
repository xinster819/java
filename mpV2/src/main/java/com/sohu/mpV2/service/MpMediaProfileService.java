package com.sohu.mpV2.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sohu.mpV2.dao.mp.MpExpertCertDao;
import com.sohu.mpV2.dao.mp.MpMediaProfileDao;
import com.sohu.mpV2.vo.MpExpertCert;
import com.sohu.mpV2.vo.MpMediaProfile;

@Service
public class MpMediaProfileService {

    @Resource
    private MpExpertCertDao mpExpertCertDao;

    @Resource
    private MpMediaProfileDao mpMediaProfileDao;

    public MpMediaProfile byId(int id) {
        return mpMediaProfileDao.byId(id);
    }

    public MpMediaProfile byMpMediaId(String mpMediaId) {
        return mpMediaProfileDao.byMpMediaId(mpMediaId);
    }

    public boolean isExpert(int id) {
        MpExpertCert expert = mpExpertCertDao.byId(id);
        if (expert != null && expert.getStatus() == 2) {
            return true;
        }
        return false;
    }

    public void updateWeiboUrl(MpMediaProfile mpMediaProfile) {
        mpMediaProfileDao.updateWeiboUrl(mpMediaProfile);
    }
}
