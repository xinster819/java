package com.sohu.mpV2.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.sohu.mpV2.dao.mp.CodeAreaDao;
import com.sohu.mpV2.vo.CodeArea;

@Service
public class CodeAreaService implements InitializingBean {

    @Resource
    private CodeAreaDao codeAreaDao;

    private HashMap<String, CodeArea> provinces = new HashMap<String, CodeArea>();
    private HashMap<String, CodeArea> cities = new HashMap<String, CodeArea>();

    @Override
    public void afterPropertiesSet() throws Exception {
        List<CodeArea> list = codeAreaDao.getAllProvince();
        for (CodeArea one : list) {
            provinces.put(one.getCode(), one);
        }

        list = codeAreaDao.getAllCity();
        for (CodeArea one : list) {
            cities.put(one.getCode(), one);
        }
    }

    public CodeArea getProvinceByCode(String code) {
        return provinces.get(code);
    }

    public CodeArea getCityByCode(String code) {
        return cities.get(code);
    }

    public Collection<CodeArea> getAllProvince() {
        return provinces.values();
    }

    public Collection<CodeArea> getAllCity() {
        return cities.values();
    }

}
