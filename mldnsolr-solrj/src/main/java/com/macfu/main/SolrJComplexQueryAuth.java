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

import java.util.List;
import java.util.Map;

/**
 * @Author: liming
 * @Date: 2019/01/15 15:17
 * @Description: 加强复杂查询
 */
public class SolrJComplexQueryAuth {
    public static final String SOLR_HOT_URL = "http://localhsot/solr/mldncore";
    public static final String USER_NAME = "mldn";
    public static final String PASSWORD = "java";
    public static final int CONNECTION_TIMEOUT = 6000;
    public static final int SOCKET_TIMEOUT = 6000;

    public static void main(String[] args) throws Exception {
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(USER_NAME, PASSWORD);
        provider.setCredentials(AuthScope.ANY, credentials);
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        HttpSolrClient solrClient = new HttpSolrClient.Builder(SOLR_HOT_URL).withConnectionTimeout(CONNECTION_TIMEOUT)
                .withSocketTimeout(SOCKET_TIMEOUT).withHttpClient(httpClient).build();
        SolrQuery query = new SolrQuery();
        query.setStart(0);
        query.setRows(5);
        query.setQuery("goods_keyword:*空*");
        query.setSort("solr_d_price", SolrQuery.ORDER.desc);
        query.setHighlight(true);
        query.addHighlightField("solr_s_name");
        query.setHighlightSimplePre("<strong>");
        query.setHighlightSimplePost("</strong>");
        QueryResponse response = solrClient.query(query);
        SolrDocumentList documents = response.getResults();
        System.out.println("【数据行数】" + documents.getNumFound());
        System.out.println("------ 普通的数据查询 ------");
        for (SolrDocument doc : documents) {
            System.out.println("【返回信息】id =" + doc.get("id") + "，name=" + doc.get("solr_s_name") + "， catalog =" + doc.get("solr_s_catalog") + "，provider = " + doc.get("solr_s_provider"))
        }
        System.out.println("------ 显示高亮查询内容 ------");
        Map<String, Map<String, List<String>>> map = response.getHighlighting();
        for (SolrDocument doc : documents) {
            Map<String, List<String>> resultMap = map.get(doc.get("id"));
            System.out.println(resultMap.get("solr_s_name"));
        }
        solrClient.close();
    }
}
