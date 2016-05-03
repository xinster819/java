package springmvc.dao;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import junit.framework.TestCase;
import springmvc.vo.ShadowSock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/app-dao.xml" })
public class ShadowSockDaoTest extends TestCase {

    @Resource
    private ShadowSockDao shadowSockDao;

    @Test
    public void one() {

        ShadowSock s = new ShadowSock();
        s.setUrl("http://ss.shire.ml/");
        s.setCheckInTime(new Date());
        System.out.println(s.getCheckInTime().toLocaleString());
        shadowSockDao.checkIn(s);
    }

}
