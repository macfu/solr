package com.macfu.util;

import com.macfu.interceptor.AuthRequestInterceptor;
import org.apache.http.client.HttpClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpClientUtil;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.params.ModifiableSolrParams;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: liming
 * @Date: 2019/01/15 18:54
 * @Description:
 */
public class SolrConnectionUtil {
    private static final String SOLR_HOST_URL = "http://localhost/solr/mldncore";
    private static final String USER_NAME = "mldn";
    private static final String PASSWORD = "java";
    private static final int CONNECTION_TIMEOUT = 6000;
    private static final int SOCKET_TIMEOUT = 6000;
    private static final int CONNECTION_MAX = 1000;
    private static final int HOST_CONNECTION_MAX = 1000;

    private SolrConnectionUtil(){}

    public static CloudSolrClient getClient() {
        List<String> solrHostList = new ArrayList<>();
        solrHostList.add("http://912.168.188.160:80/solr");
        solrHostList.add("http://912.168.188.161:80/solr");
        solrHostList.add("http://912.168.188.162:80/solr");
        solrHostList.add("http://912.168.188.163:80/solr");
        solrHostList.add("http://912.168.188.164:80/solr");
        solrHostList.add("http://912.168.188.165:80/solr");

        ModifiableSolrParams initParams = new ModifiableSolrParams();
        initParams.set(HttpClientUtil.PROP_BASIC_AUTH_USER, USER_NAME);
        initParams.set(HttpClientUtil.PROP_BASIC_AUTH_PASS, PASSWORD);
        // 如果服务器支持压缩传输则启用
        initParams.set(HttpClientUtil.PROP_ALLOW_COMPRESSION, true);
        // 不进行重定向配置
        initParams.set(HttpClientUtil.PROP_FOLLOW_REDIRECTS, false);
        // 设置每台主机最大允许的连接数
        initParams.set(HttpClientUtil.PROP_MAX_CONNECTIONS, CONNECTION_MAX);
        // 设置最大允许连接数
        initParams.set(HttpClientUtil.PROP_MAX_CONNECTIONS_PER_HOST, HOST_CONNECTION_MAX);
        // 进行请求拦截器
        HttpClientUtil.addRequestInterceptor(new AuthRequestInterceptor());
        // 根据配置的初始化参数创建httpclient对象
        HttpClient httpClient = HttpClientUtil.createClient(initParams);
//        HttpSolrClient solrClient = new HttpSolrClient.Builder(SOLR_HOST_URL).withConnectionTimeout(CONNECTION_TIMEOUT)
//                .withSocketTimeout(SOCKET_TIMEOUT).withHttpClient(httpClient).build();
//        return solrClient;
        CloudSolrClient cloudSolrClient = new CloudSolrClient.Builder(solrHostList).withConnectionTimeout(CONNECTION_TIMEOUT)
                .withSocketTimeout(SOCKET_TIMEOUT).withHttpClient(httpClient).build();
        return cloudSolrClient;
    }

}
