package com.clouddo.monitor.server.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @author zhongming
 * @since 3.0
 * 2018/8/27上午9:53
 */
@Configuration
@MapperScan(basePackages = {"com.clouddo.monitor.server.mapper"}, sqlSessionTemplateRef = "sqlSessionTemplate")
public class MonitorMybatisConfig {

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(matchMapperLocations());
        return sqlSessionFactoryBean.getObject();
    }

    private Resource[] matchMapperLocations() throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        return  resolver.getResources("classpath*:mybatis/*.xml");
    }

    /**
     * 创建数据库事物t
     * @param dataSource 系统数据源
     * @return 事物类
     */
    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 创建SqlSessionTemplate
     * @param sqlSessionFactory sqlSessionFactory
     * @return 系统库SqlSessionTemplate
     */
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
