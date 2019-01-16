package com.macfu.config;

import com.macfu.interceptor.AuthRequestInterceptor;
import org.apache.http.client.HttpClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpClientUtil;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

import java.util.List;

/**
 * @Author: liming
 * @Date: 2019/01/16 10:36
 * @Description: 基于Bean配置Solr连接
 */
@Configuration
@Scope("prototype")
@PropertySource("classpath:config/solr.properties")
@EnableSolrRepositories(basePackages={"com.macfu.dao"})
public class SolrConfig {
    @Value("#{'${solr.host.url}'.split(',')}")
    private List<String> solrHostList;
    @Value("${solr.collection.name}")
    private String collectionName;
    @Value("${solr.host.username}")
    private String username;
    @Value("${solr.host.password}")
    private String password;
    @Value("${solr.connection.timeout}")
    private int connectionTimeout = 6000;
    @Value("${solr.socket.timeout}")
    private int socketTimeout = 6000;
    @Value("${solr.max.connection}")
    private int maxConnection = 1000;
    @Value("${solr.pre.connection}")
    private int preConnection = 1000;

    @Bean(name = "solrClient")
    public CloudSolrClient getClient() {
        ModifiableSolrParams initParams = new ModifiableSolrParams();
        initParams.set(HttpClientUtil.PROP_BASIC_AUTH_USER, this.username);
        initParams.set(HttpClientUtil.PROP_BASIC_AUTH_PASS, this.password);
        // 如果服务器支持压缩传输则启用
        initParams.set(HttpClientUtil.PROP_ALLOW_COMPRESSION, true);
        // 不进行重定向配置
        initParams.set(HttpClientUtil.PROP_FOLLOW_REDIRECTS, false);
        // 设置每台主机最大允许的连接数
        initParams.set(HttpClientUtil.PROP_MAX_CONNECTIONS, this.maxConnection);
        // 设置最大允许连接数
        initParams.set(HttpClientUtil.PROP_MAX_CONNECTIONS_PER_HOST, this.preConnection);
        // 进行请求拦截器
        HttpClientUtil.addRequestInterceptor(new AuthRequestInterceptor());
//        // 根据配置的初始化参数创建httpclient对象
        HttpClient httpClient = HttpClientUtil.createClient(initParams);
//        HttpSolrClient solrClient = new HttpSolrClient.Builder(this.solrHostUrl).withConnectionTimeout(this.connectionTimeout)
//                .withSocketTimeout(this.socketTimeout).withHttpClient(httpClient).build();
//        return solrClient;
        CloudSolrClient cloudSolrClient = new CloudSolrClient.Builder(this.solrHostList).withConnectionTimeout(this.connectionTimeout)
                .withConnectionTimeout(this.socketTimeout).withHttpClient(httpClient).build();
        cloudSolrClient.setDefaultCollection(this.collectionName);
        return cloudSolrClient;
    }

}
