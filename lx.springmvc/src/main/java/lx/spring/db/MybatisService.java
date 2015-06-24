package lx.spring.db;

import java.util.List;

import javax.annotation.Resource;

import lx.spring.db.mapper.LabsStatMapper;
import lx.springmvc.vo.LabsStat;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class MybatisService {

    @Resource
    TransactionTemplate txTemplate;

    @Resource
    LabsStatMapper labsStatMapper;

    @Cacheable("cache")
    public List<LabsStat> print() {
        List<LabsStat> select = labsStatMapper.select();
        System.out.println("in");
        return select;
    }

    public void insert() throws Exception {
        txTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                labsStatMapper.insert("123", "123");
                labsStatMapper.update("1234", "1234");
                status.setRollbackOnly();
                return false;
            }
        });
    }

}
