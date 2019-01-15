package com.macfu.main;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

/**
 * @Author: liming
 * @Date: 2019/01/15 14:23
 * @Description: Solr基础查询
 */
public class SolrJBasicQuery {
    // 定义要操作的Solr_Core的访问路径，如果存在有认证则需要配置有认证信息
    public static final String SOLR_HOST_URL = "http://mldn:java@localhost/solr/mldncore";

    public static void main(String[] args) throws Exception {
        // SolrJ本身是基于HttpClient的一种访问应用，所以需要依赖HttpSolrClient类来创建一个客户端访问连接
        HttpSolrClient solrClient = new HttpSolrClient.Builder(SOLR_HOST_URL).build();
        // 进行Solr查询可以利用SolrQuery来完成
        SolrQuery query = new SolrQuery();
        // 查询全部内容
        query.setQuery("*:*");
        // 当执行查询之后一定要有一个数据的返回结果，需要接收返回的数据回应
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
