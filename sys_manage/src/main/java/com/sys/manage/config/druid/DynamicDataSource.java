package com.sys.manage.config.druid;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 
 * 动态数据源
 * Created by oycx
 * Date: 2018-4-17
 *
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {
	private String[] multiDataSourceLookupKeys;

	@Setter
	private DynamicDataSourceProvider dynamicDataSourceProvider;
	@Setter
	private DynamicDataSourceStrategy dynamicDataSourceStrategy;

	@Override
	protected Object determineCurrentLookupKey() {
		String dataSourceLookupKey = DynamicDataSourceContextHolder.getDataSourceLookupKey();
		if (dataSourceLookupKey == null) {
			dataSourceLookupKey = "master";
		} else if (dataSourceLookupKey.isEmpty()) {
			dataSourceLookupKey = dynamicDataSourceStrategy.determineMultiDataSource(multiDataSourceLookupKeys);
		}
		log.debug("determine to use datasource named : {}", dataSourceLookupKey);
		return dataSourceLookupKey;
	}

	@Override
	public void afterPropertiesSet() {
		DataSource masterDataSource = dynamicDataSourceProvider.loadMasterDataSource();
		Map<String, DataSource> multiDataSource = dynamicDataSourceProvider.loadMultiDataSource();

		Set<String> mulitDataSourceIds = multiDataSource.keySet();
		this.multiDataSourceLookupKeys = mulitDataSourceIds.toArray(new String[multiDataSource.size()]);

		Map<Object, Object> targetDataSource = new HashMap<>(multiDataSource.size() + 1);
		targetDataSource.put("master", masterDataSource);
		targetDataSource.putAll(multiDataSource);
		super.setDefaultTargetDataSource(masterDataSource);
		super.setTargetDataSources(targetDataSource);

		super.afterPropertiesSet();
	}
}
