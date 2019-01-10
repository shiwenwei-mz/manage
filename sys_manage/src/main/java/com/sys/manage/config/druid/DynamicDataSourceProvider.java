package com.sys.manage.config.druid;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author admin
 */
public interface DynamicDataSourceProvider {

    /**
     * load master
     *
     * @return masterDataSource
     */
    DataSource loadMasterDataSource();

    /**
     * load slaves
     *
     * @return slaveDataSource
     */
    Map<String, DataSource> loadMultiDataSource();

}
