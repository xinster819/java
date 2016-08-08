package com.sohu.mpV2.dao.mp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.sohu.mpV2.dao.mp.mapper.CodeAreaMapper;
import com.sohu.mpV2.vo.CodeArea;

@Component
public class CodeAreaDao {

    @Resource
    private CodeAreaMapper codeAreaMapper;

    public List<CodeArea> getAllProvince() {
        return codeAreaMapper.getAllProvince();
    }

    public List<CodeArea> getAllCity() {
        return codeAreaMapper.getAllCity();
    }
}
