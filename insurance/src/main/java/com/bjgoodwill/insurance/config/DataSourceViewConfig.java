package com.bjgoodwill.insurance.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * @Package: com.bjgoodwill.insurance.config
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liuxiaochuan 
 * @date 2018年4月10日 下午4:11:43
 * @GUID {9f6dbb9d-257e-4d27-a1a1-82394914a1af}
 */
@Configuration
@MapperScan(basePackages = {"com.bjgoodwill.insurance.*.dao"}, sqlSessionTemplateRef  = "viewSqlSessionTemplate")

public class DataSourceViewConfig {
    @Bean(name = "viewDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.view")
    public DataSource testDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "viewSqlSessionFactory")
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("viewDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:com/bjgoodwill/insurance/*/dao/*/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "viewTransactionManager")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("viewDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "viewSqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("viewSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
