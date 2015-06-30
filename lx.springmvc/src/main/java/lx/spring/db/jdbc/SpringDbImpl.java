package lx.spring.db.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import lx.springmvc.vo.LabsStat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(isolation = Isolation.SERIALIZABLE)
public class SpringDbImpl implements SpringDb {

    static Logger logger = LoggerFactory.getLogger(SpringDbImpl.class);

    JdbcTemplate jdbcTemplate;

    public void insert() {
        jdbcTemplate.execute("insert into article (url, content) values ('aaa','aaa')");
    }

    public List<LabsStat> select(int offset, int limit) {
        logger.error("this is a test");
        return jdbcTemplate.query("select * from labs_stat order by date desc limit ? , ?", new RowMapper<LabsStat>() {
            @Override
            public LabsStat mapRow(ResultSet rs, int rowNum) throws SQLException {
                LabsStat ls = new LabsStat();
                ls.setId(rs.getLong("id"));
                ls.setType(rs.getInt("type"));
                ls.setUri(rs.getString("uri"));
                ls.setMs200(rs.getInt("ms_200"));
                ls.setMs500(rs.getInt("ms_500"));
                ls.setMs1000(rs.getInt("ms_1000"));
                ls.setMs2000(rs.getInt("ms_2000"));
                ls.setDate(rs.getInt("date"));
                ls.setReqCount(rs.getInt("req_count"));
                return ls;
            }
        }, offset, limit);
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

}
