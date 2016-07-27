package com.sohu.mpV2.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import springmvc.dao.DemoDao;

@Service
public class DemoService {

    @Resource
    private DemoDao demoDao;

    public String byId(int id) {
        return demoDao.byId(id);
    }

}
