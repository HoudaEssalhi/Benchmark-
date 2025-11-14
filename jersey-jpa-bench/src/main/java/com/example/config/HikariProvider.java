package com.example.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.Properties;
import java.io.InputStream;

public class HikariProvider {
    private static HikariDataSource ds;

    public static synchronized HikariDataSource getDataSource() {
        if (ds == null) {
            try (InputStream in = HikariProvider.class.getResourceAsStream("/config.properties")) {
                Properties p = new Properties();
                p.load(in);
                HikariConfig hc = new HikariConfig();
                hc.setJdbcUrl(p.getProperty("jdbc.url"));
                hc.setUsername(p.getProperty("jdbc.user"));
                hc.setPassword(p.getProperty("jdbc.password"));
                hc.setMaximumPoolSize(Integer.parseInt(p.getProperty("hikari.maximumPoolSize","20")));
                hc.setMinimumIdle(Integer.parseInt(p.getProperty("hikari.minimumIdle","10")));
                ds = new HikariDataSource(hc);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return ds;
    }

    public static synchronized void close() {
        if (ds != null) ds.close();
    }
}
