package com.macfu.main.index;

import com.macfu.util.SolrConnectionUtil;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;

import java.util.Date;

/**
 * @Author: liming
 * @Date: 2019/01/15 18:52
 * @Description: 索引数据的添加控制
 */
public class SolrAddDocument {
    public static void main(String[] args) throws Exception {
        CloudSolrClient solrClient = SolrConnectionUtil.getClient();
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", "99");
        document.addField("solr_s_name", "好吃的");
        document.addField("solr_s_note", "德国进口");
        document.addField("solr_s_provider", "高氏");
        document.addField("solr_s_catalog", "熟食");
        document.addField("solr_d_price", 89.76);
        document.addField("solr_s_photo", "nophoto.jpg");
        document.addField("solr_i_isdelete", 0);
        document.addField("solr_data_recdate", new Date());
        UpdateResponse response = solrClient.add(document);
        System.out.println("花费时间:" + response.getElapsedTime());
        solrClient.commit();
        solrClient.close();
    }
}
