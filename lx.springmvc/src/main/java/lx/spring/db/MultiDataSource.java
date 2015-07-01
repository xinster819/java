package lx.spring.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.tomcat.jdbc.pool.DataSourceFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.AbstractDataSource;

public class MultiDataSource extends AbstractDataSource implements InitializingBean, DisposableBean {

    private static final String URL = "url";

    private Map<Long, String> map = new HashMap<Long, String>();

    private DataSourceFactory dataSourceFactory = new DataSourceFactory();

    private Map<String, String> _dataSources;

    private final Map<String, DataSource> dataSources;

    private Properties defaultProps;

    public MultiDataSource() {
        dataSources = new HashMap<String, DataSource>();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Properties props = new Properties();
        props.putAll(defaultProps);
        for (Map.Entry<String, String> entry : _dataSources.entrySet()) {
            String urlPrefix = entry.getKey();
            String[] range2 = entry.getValue().split("-");
            if (range2.length != 2) {
                continue;
            }
            int start = NumberUtils.toInt(range2[0]);
            int end = NumberUtils.toInt(range2[1]);
            for (; start <= end; start++) {
                props.setProperty(URL, (urlPrefix + start));
                DataSource dataSource = dataSourceFactory.createDataSource(props);
                dataSources.put(String.valueOf(start), dataSource);
            }
        }
    }

    @Override
    public void destroy() throws Exception {
        for (DataSource ds : dataSources.values()) {
            ((org.apache.tomcat.jdbc.pool.DataSource) ds).close();
        }
    }

    public Connection getConnection() throws SQLException {
        return determineDataSource().getConnection();
    }

    public Connection getConnection(String username, String password) throws SQLException {
        return determineDataSource().getConnection(username, password);
    }

    protected DataSource determineDataSource() {
        String lookupKey = determineCurrentLookupKey();
        DataSource dataSource = this.dataSources.get(lookupKey);
        if (dataSource == null) {
            throw new IllegalStateException("Cannot determine  DataSource for lookup key [" + lookupKey + "]");
        }
        return dataSource;
    }

    protected String determineCurrentLookupKey() {
        String value = map.get(Thread.currentThread().getId());
        if(value==null) {
            value = "33";
        }
        return value;
    }

    public void setDefaultProps(Properties defaultProps) {
        this.defaultProps = defaultProps;
    }

    public Map<String, String> get_dataSources() {
        return _dataSources;
    }

    public void set_dataSources(Map<String, String> _dataSources) {
        this._dataSources = _dataSources;
    }

    public Properties getDefaultProps() {
        return defaultProps;
    }

    
    //  以下是临时代码，要迁走的
    public void put(long id, String value) {
        map.put(id, value);
    }
}
