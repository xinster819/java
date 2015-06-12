package lx.spring.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import lx.springmvc.vo.LabsStat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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

    public List<LabsStat> select(int offset, int limit) {
        return jdbcTemplate.query("select * from labs_stat order by date desc limit ? , ?", new RowMapper<LabsStat>() {
            @Override
            public LabsStat mapRow(ResultSet rs, int rowNum) throws SQLException {
                LabsStat ls = new LabsStat();
                ls.setId(rs.getLong("id"));
                ls.setType(rs.getInt("type"));
                ls.setUri(rs.getString("uri"));
                ls.setMs200(rs.getInt("ms_200"));
                ls.setMs500(rs.getInt("ms_500"));
                ls.setMs500(rs.getInt("ms_1000"));
                ls.setMs500(rs.getInt("ms_2000"));
                ls.setDate(rs.getInt("date"));
                ls.setReqCount(rs.getInt("req_count"));
                return ls;
            }
        }, offset, limit);
    }

}
