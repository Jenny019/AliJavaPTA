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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import javax.sql.XADataSource;

@Configuration
@MapperScan(basePackages = {"com.example.alijavapta.mapper.alipay"},
        sqlSessionFactoryRef = "alipaySqlSessionFactory")
public class AlipayDataSourceConfig {

    @Bean(name = "alipayDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.alipay-db")
    public DataSource dataSource() {
        return new DruidXADataSource();
    }

    @Bean(name = "alipaySqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("alipayDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "alipayTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("alipayDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "alipaySqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("alipaySqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}