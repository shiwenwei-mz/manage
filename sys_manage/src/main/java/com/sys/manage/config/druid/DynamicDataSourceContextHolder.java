package com.sys.manage.config.druid;

import org.springframework.core.NamedThreadLocal;

/**
 * 
 * datasource的上下文
 * @author  oycx
 * Date: 2018-4-17
 */
public class DynamicDataSourceContextHolder {

    private static final ThreadLocal<String> LOOKUP_KEY_HOLDER =  new NamedThreadLocal("current dynamic datasource");

    private DynamicDataSourceContextHolder() {
    }

    public static String getDataSourceLookupKey() {
        return LOOKUP_KEY_HOLDER.get();
    }

    public static void setDataSourceLookupKey(String dataSourceLookupKey) {
        LOOKUP_KEY_HOLDER.set(dataSourceLookupKey);
    }

    public static void clearDataSourceLookupKey() {
        LOOKUP_KEY_HOLDER.remove();
    }
}
