package com.example.alijavapta.config;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.atomikos.jdbc.AtomikosDataSourceBean;
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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"com.example.alijavapta.mapper.bank"},
        sqlSessionFactoryRef = "bankSqlSessionFactory")
public class BankDataSourceConfig {

    @Bean(name = "bankDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.bank-db")
    public DataSource dataSource() {
        return new DruidXADataSource();
    }

    @Bean(name = "bankSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("bankDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "bankTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("bankDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "bankSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("bankSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}