package com.macfu.main;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

/**
 * @Author: liming
 * @Date: 2019/01/15 14:41
 * @Description: 基础查询带认证
 */
public class SolrJBasicQueryAuth {
    public static final String SOLR_HOT_URL = "http://localhsot/solr/mldncore";
    public static final String USER_NAME = "mldn";
    public static final String PASSWORD = "java";
    public static final int CONNECTION_TIMEOUT = 6000;
    public static final int SOCKET_TIMEOUT = 6000;

    public static void main(String[] args) throws Exception {
        // 针对于访问路径的认证信息进行配置
        // 创建认证对象
        CredentialsProvider provider = new BasicCredentialsProvider();
        // 设置认证标记
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(USER_NAME, PASSWORD);
        // 在认证提供者对象上设置认证信息
        provider.setCredentials(AuthScope.ANY, credentials);
        // 手工创建httpClient访问对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        // solrj本身是基于HttpClient的一种访问应用，所有需要依靠httpSolrclient类来创建一个客户端访问连接
        HttpSolrClient solrClient = new HttpSolrClient.Builder(SOLR_HOT_URL).withConnectionTimeout(CONNECTION_TIMEOUT)
                .withSocketTimeout(SOCKET_TIMEOUT).withHttpClient(httpClient).build();
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        QueryResponse response = solrClient.query(query);
        // 所有的回应的数据信息实际上都在response中包含了，需要通过回应对象获取信息
        SolrDocumentList documents = response.getResults();
        System.out.println("【数据行数】" + documents.getNumFound());
        for (SolrDocument doc : documents) {
            System.out.println("【返回信息】id=" + doc.get("id") + "，name = " + doc.get("solr_s_name") + "，catalog = " + doc.get("solr_s_catalog"));
        }
        solrClient.close();
    }
}
