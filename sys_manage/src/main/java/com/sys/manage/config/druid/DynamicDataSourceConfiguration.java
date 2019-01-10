package com.sys.manage.config.druid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author oycx
 * @Date
 */
@Configuration
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
public class DynamicDataSourceConfiguration {
    @Autowired
    private DynamicDataSourceProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public DynamicDataSourceStrategy dynamicDataSourceStrategy() {
        return new LoadBalanceDynamicDataSourceStrategy();
    }

    @Bean
    @ConditionalOnMissingBean
    public DynamicDataSourceProvider dynamicDataSourceProvider() {
        return new YmlDynamicDataSourceProvider(properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public DynamicRoutingDataSource dynamicDataSource(
            DynamicDataSourceProvider dynamicDataSourceProvider,
            DynamicDataSourceStrategy dynamicDataSourceStrategy) {
        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
        dynamicRoutingDataSource.setDynamicDataSourceProvider(dynamicDataSourceProvider);
        dynamicRoutingDataSource.setDynamicDataSourceStrategy(dynamicDataSourceStrategy);
        return dynamicRoutingDataSource;
    }
}
