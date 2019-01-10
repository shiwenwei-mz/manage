package com.sys.manage.config.druid;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.SQLException;

@Slf4j
public abstract class AbstractDynamicDataSourceProvider implements DynamicDataSourceProvider {
    protected DataSource createDataSource(DynamicItemDataSourceProperties properties) {
        Class<? extends DataSource> type = properties.getType();
        if (type == null) {
            try {
                Class.forName("com.alibaba.druid.pool.DruidDataSource");
                return createDruidDataSource(properties);
            } catch (ClassNotFoundException e) {
                log.debug("dynamic not found DruidDataSource");
            }
            throw new RuntimeException("please set master and slave type like spring.dynamic.datasource.master.type");
        } else {
            if ("com.alibaba.druid.pool.DruidDataSource".equals(type.getName())) {
                return createDruidDataSource(properties);
            } else {
                return properties.initializeDataSourceBuilder().build();
            }
        }
    }

    private DataSource createDruidDataSource(DynamicItemDataSourceProperties properties) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(properties.getUrl());
        druidDataSource.setUsername(properties.getUsername());
        druidDataSource.setPassword(properties.getPassword());
        druidDataSource.setDriverClassName(properties.getDriverClassName());

        DruidDataSourceProperties druidProperties = properties.getDruid();

        druidDataSource.setInitialSize(druidProperties.getInitialSize());
        druidDataSource.setMaxActive(druidProperties.getMaxActive());
        druidDataSource.setMinIdle(druidProperties.getMinIdle());
        druidDataSource.setMaxWait(druidProperties.getMaxWait());
        druidDataSource.setTimeBetweenEvictionRunsMillis(druidProperties.getTimeBetweenEvictionRunsMillis());
        druidDataSource.setMinEvictableIdleTimeMillis(druidProperties.getMinEvictableIdleTimeMillis());
        druidDataSource.setValidationQuery(druidProperties.getValidationQuery());
        druidDataSource.setValidationQueryTimeout(druidProperties.getValidationQueryTimeout());
        druidDataSource.setTestOnBorrow(druidProperties.isTestOnBorrow());
        druidDataSource.setTestOnReturn(druidProperties.isTestOnReturn());
        druidDataSource.setPoolPreparedStatements(druidProperties.isPoolPreparedStatements());
        druidDataSource.setMaxOpenPreparedStatements(druidProperties.getMaxOpenPreparedStatements());
        druidDataSource.setSharePreparedStatements(druidProperties.isSharePreparedStatements());
        druidDataSource.setConnectProperties(druidProperties.getConnectionProperties());
        try {
            druidDataSource.setFilters(druidProperties.getFilters());
            druidDataSource.init();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return druidDataSource;
    }
}
