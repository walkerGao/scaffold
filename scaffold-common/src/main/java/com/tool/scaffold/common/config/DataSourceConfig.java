package com.tool.scaffold.common.config;


import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 默认数据源配置，
 */
@Configuration
@MapperScan(basePackages = "com.tool.scaffold.dao", sqlSessionFactoryRef = "sqlSessionFactory")
public class DataSourceConfig {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean(name = "defaultDataSource")
    @Primary
    public DataSource defaultDataSource() {
        return new HikariDataSource();
    }

    @Bean(name = "dynamicDataSource")
    public DynamicDataSource dataSource(@Qualifier("defaultDataSource") DataSource defaultDataSource) {
        Map<Object, Object> targetDataSource = new HashMap<>();
        targetDataSource.put(DataSourceType.DataBaseType.DEFAULT, defaultDataSource);
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSource);
        dataSource.setDefaultTargetDataSource(defaultDataSource);
        return dataSource;
    }

    @Bean(name = "sqlSessionFactoryBean")
    public SqlSessionFactoryBean defaultSqlSessionFactoryBean(@Qualifier("dynamicDataSource") DataSource datasource) throws IOException {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mappers/*.xml"));
        bean.setDataSource(datasource);
        return bean;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory defaultSqlSessionFactory(@Qualifier("sqlSessionFactoryBean") SqlSessionFactoryBean sqlSessionFactoryBean)
            throws Exception {
        return sqlSessionFactoryBean.getObject();
    }

    @Bean("sqlSessionTemplate")
    public SqlSessionTemplate defaultSqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sessionfactory) {
        return new SqlSessionTemplate(sessionfactory);
    }
}
