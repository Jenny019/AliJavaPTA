package com.example.alijavapta.config;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"com.example.alijavapta.mapper.my"},
        sqlSessionFactoryRef = "mySqlSessionFactory")
public class MyDataSourceConfig {

    @Bean(name = "myDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.my-db")
    @Primary
    public DataSource dataSource() {
        return new DruidXADataSource();
    }

    @Bean(name = "mySqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("myDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "myTransactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager(@Qualifier("myDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "mySqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("mySqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 配置监控服务器
     * @return 返回监控注册的servlet对象
     * @author SimpleWu
     */
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        //ServletRegistrationBean reg = new ServletRegistrationBean();
        //reg.setServlet(new StatViewServlet());
        //reg.addUrlMappings("/druid/*");
        // 添加IP白名单
        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
        // 添加IP黑名单，当白名单和黑名单重复时，黑名单优先级更高
        //servletRegistrationBean.addInitParameter("deny", "127.0.0.1");
        // 添加控制台管理用户
        servletRegistrationBean.addInitParameter("loginUsername", "admin");
        servletRegistrationBean.addInitParameter("loginPassword", "123456");
        // 是否能够重置数据
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }
    /**
     * 配置服务过滤器
     *
     * @return 返回过滤器配置对象
     */
    @Bean
    public FilterRegistrationBean statFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        // 添加过滤规则
        filterRegistrationBean.addUrlPatterns("/*");
        // 忽略过滤格式
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*,");
        return filterRegistrationBean;
    }
}