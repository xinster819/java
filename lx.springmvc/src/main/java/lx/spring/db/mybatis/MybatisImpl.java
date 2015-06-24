package lx.spring.db.mybatis;

import javax.annotation.Resource;

import lx.spring.db.mapper.LabsStatMapper;
import lx.springmvc.vo.LabsStat;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MybatisImpl implements Mybatis {
    @Resource
    LabsStatMapper labsStatMapper;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void insert() {
        labsStatMapper.insert("3333", "3333");
        LabsStat a = null;
        a.getId();
    }

}
