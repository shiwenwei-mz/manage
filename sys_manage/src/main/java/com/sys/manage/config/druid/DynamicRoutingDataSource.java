/**
 * Copyright © 2018 TaoYu (tracy5546@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sys.manage.config.druid;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The core DynamicRoutingDataSource,It use determineCurrentLookupKey to determineDatasource.
 *
 * @author TaoYu Kanyuxia
 * @since 1.0.0
 */
@Slf4j
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

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

    Set<String> multiDataSourceIds = multiDataSource.keySet();
    this.multiDataSourceLookupKeys = multiDataSourceIds.toArray(new String[multiDataSource.size()]);

    Map<Object, Object> targetDataSource = new HashMap<>(multiDataSource.size() + 1);
    targetDataSource.put("master", masterDataSource);
    targetDataSource.putAll(multiDataSource);
    super.setDefaultTargetDataSource(masterDataSource);
    super.setTargetDataSources(targetDataSource);

    super.afterPropertiesSet();
  }

}