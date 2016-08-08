package com.sohu.mpV2.dao.mp;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.sohu.mpV2.dao.mp.mapper.MpExpertCertMapper;
import com.sohu.mpV2.vo.MpExpertCert;

@Component
public class MpExpertCertDao {

    @Resource
    private MpExpertCertMapper mpExpertCertMapper;

    public MpExpertCert byId(int mpMediaProfileId) {
        return mpExpertCertMapper.byMediaProfieId(mpMediaProfileId);
    }
}
