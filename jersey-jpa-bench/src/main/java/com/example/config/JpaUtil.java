package com.example.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class JpaUtil {
    private static EntityManagerFactory emf;

    public static synchronized EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            Map<String, Object> props = new HashMap<>();
            props.put("jakarta.persistence.nonJtaDataSource", HikariProvider.getDataSource());
            props.put("hibernate.show_sql", "false");
            props.put("hibernate.format_sql", "false");
            props.put("hibernate.hbm2ddl.auto", "validate");
            props.put("hibernate.cache.use_second_level_cache", "false");
            // optionally tuning:
            props.put("hibernate.jdbc.batch_size", "50");
            emf = Persistence.createEntityManagerFactory("bench-pu", props);
        }
        return emf;
    }

    public static void shutdown() {
        if (emf != null && emf.isOpen()) emf.close();
        HikariProvider.close();
    }
}