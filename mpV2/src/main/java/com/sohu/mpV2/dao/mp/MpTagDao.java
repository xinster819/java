package com.sohu.mpV2.dao.mp;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.sohu.mpV2.dao.mp.mapper.MpTagMapper;
import com.sohu.mpV2.vo.MpTag;

@Component
public class MpTagDao {

    @Resource
    private MpTagMapper mpTagMapper;
    
    public MpTag byId(int id) {
        return mpTagMapper.byId(id);
    }
    
}
