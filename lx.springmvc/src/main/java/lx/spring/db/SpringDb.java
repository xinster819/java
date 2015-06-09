package lx.spring.db;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class SpringDb {

    static Logger logger = LoggerFactory.getLogger(SpringDb.class);

    @Resource
    JdbcTemplate jdbcTemplate;

    @SuppressWarnings("deprecation")
    public long go() {
        logger.info("info lx");
        logger.debug("debug lx");
        return jdbcTemplate.queryForLong("select count(*) from labs_stat");
    }

}
