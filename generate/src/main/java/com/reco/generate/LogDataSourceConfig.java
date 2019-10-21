package com.reco.generate;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @Author xsq
 * @Date 2019-5-6 11:56:15
 * @Description 数据源配置
 */
@Configuration
@ConfigurationProperties
@MapperScan(basePackages = {LogDataSourceConfig.PACKAGE}, sqlSessionFactoryRef = "logSqlSessionFactory")
public class LogDataSourceConfig {

    public static final String PACKAGE = "com.reco.generate.repository.logCenter";
    public static final String MAPPER_LOCATION = "classpath*:repository/logCenter/*.xml";

    @Bean(name = "logTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("logsDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean("logSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("logsDataSource") DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(LogDataSourceConfig.MAPPER_LOCATION));
        sessionFactory.setConfigLocation(new ClassPathResource("mybatis/mybatis-config.xml"));
        return sessionFactory.getObject();
    }
}
