import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * @Author: liming
 * @Date: 2019/01/16 10:49
 * @Description:
 */
@ContextConfiguration(locations = {"classpath:spring/spring-*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestSolrAddDocument {

    @Autowired
    private HttpSolrClient solrClient;

    @Test
    public void testAdd() throws Exception {
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
