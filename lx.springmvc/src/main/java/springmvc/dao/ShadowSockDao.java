package springmvc.dao;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import springmvc.dao.mapper.ShadowSockMapper;
import springmvc.vo.ShadowSock;

@Repository
public class ShadowSockDao {

    static Logger LOGGER = LoggerFactory.getLogger(ShadowSockDao.class);

    @Resource
    private ShadowSockMapper shadowSockMapper;

    public ShadowSock byUrl(String url) {
        return shadowSockMapper.byUrl(url);
    }
    
    public List<ShadowSock> all() {
        return shadowSockMapper.all();
    }

    public void checkIn(ShadowSock s) {
        shadowSockMapper.check_in(s);
    }
    
    public void updateStatus(ShadowSock s) {
        shadowSockMapper.updateStatus(s);
    }
    
    public void insert(ShadowSock s) {
        shadowSockMapper.insert(s);
    }
}
