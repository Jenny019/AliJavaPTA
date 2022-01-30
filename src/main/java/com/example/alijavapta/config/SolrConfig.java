package com.example.alijavapta.config;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author admin
 * @createTime 2021-02-27 17:00:58
 * @description 当前内容主要是solr的配置类
 *
 */
@Configuration
@EnableSolrRepositories(basePackages = "com.example.alijavapta.dao")
@EntityScan(basePackages = "com.example.alijavapta.entity")
@EnableTransactionManagement
public class SolrConfig {

    @Bean
    public SolrClient solrClient() {
        return new HttpSolrClient.Builder("http://192.168.1.247:8983/solr").build();
    }

    @Bean
    public SolrOperations solrTemplate(){
        return new SolrTemplate(solrClient());
    }
}
