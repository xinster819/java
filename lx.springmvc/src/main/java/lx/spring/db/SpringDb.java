package lx.spring.db;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class SpringDb {

    @Resource
    JdbcTemplate jdbcTemplate;
    
    public long go() {
        return jdbcTemplate.queryForLong("select count(*) from agency_daily_record");
    }

}
