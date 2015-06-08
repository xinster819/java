package lx.spring.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.tomcat.jdbc.pool.DataSourceFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class SimpleConnect {

    public static void main(String[] args) throws Exception {
        DataSourceFactory dsf = new DataSourceFactory();
        Properties p = new Properties();
        p.setProperty("url", "jdbc:mysql://10.11.157.57:3307/advert");
        p.setProperty("username", "cmt");
        p.setProperty("password", "cmt");
        p.setProperty("driverClassName", "com.mysql.jdbc.Driver");
        DataSource d = dsf.createDataSource(p);
        Connection c = DataSourceUtils.getConnection(d);
        Statement st = c.createStatement();
        ResultSet r = st.executeQuery("select * from pay_stat");
        while(r.next()) {
            System.out.println(r.getString(1));
        }
    }

}
