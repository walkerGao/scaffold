package com.tool.scaffold.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author gw
 * @date 2022/1/5 7:02 下午
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    //  默认数据源，默认DEFAULT
    @Value("${default_db:DEFAULT}")
    private String DEFAULT_DB;

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceType.getDataBaseType(DEFAULT_DB);
    }

}

