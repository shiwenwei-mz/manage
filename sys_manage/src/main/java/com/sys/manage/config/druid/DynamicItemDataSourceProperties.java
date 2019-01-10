package com.sys.manage.config.druid;

import lombok.Data;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @Title: DynamicItemDataSourceProperties
 * @Description: 多数据源配置文件
 * @author： sww
 * @date 2019/1/10
 **/
@Data
public class DynamicItemDataSourceProperties extends DataSourceProperties {

    @NestedConfigurationProperty
    private DruidDataSourceProperties druid = new DruidDataSourceProperties();
}
