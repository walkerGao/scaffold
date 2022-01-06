package com.tool.scaffold.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author GoodBai   the last day of 2019
 */
public class DataSourceType {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceType.class);

    public enum DataBaseType {
        /**
         * DEFAULT
         */
        DEFAULT;
    }

    private static final ThreadLocal<DataBaseType> TYPE = new ThreadLocal<DataBaseType>();

    public static void setDataBaseType(DataBaseType dataBaseType) {
        if (dataBaseType == null) {
            throw new NullPointerException();
        }
        LOGGER.info("[set data source]：" + dataBaseType);
        TYPE.set(dataBaseType);
    }

    public static DataBaseType getDataBaseType(String defaultDataBase) {
        DataBaseType defaultDataBaseType;
        if (defaultDataBase.equals(DataBaseType.DEFAULT.name())) {
            defaultDataBaseType = DataBaseType.DEFAULT;
        } else {
            defaultDataBaseType = DataBaseType.DEFAULT;
        }
        DataBaseType dataBaseType = TYPE.get() == null ? defaultDataBaseType : TYPE.get();
        LOGGER.info("[get data source]：" + dataBaseType);
        return dataBaseType;
    }

    public static void clearDataBaseType() {
        System.err.println("[当前数据源已清理]");
        TYPE.remove();
        LOGGER.info("[remove data source]：");
    }
}