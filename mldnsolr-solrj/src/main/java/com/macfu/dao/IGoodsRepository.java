package com.macfu.dao;

import com.macfu.po.Goods;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.repository.Highlight;
import org.springframework.data.solr.repository.SolrCrudRepository;


/**
 * @Author: liming
 * @Date: 2019/01/16 12:06
 * @Description: dao接口
 */
public interface IGoodsRepository extends SolrCrudRepository<Goods, Long> {

    @Highlight(prefix = "<strong>", postfix = "</strong>")
    HighlightPage<Goods> findByKeywordContaining(String keywords, Pageable page);

    @Highlight(prefix = "<strong>", postfix = "</strong>")
    HighlightPage<Goods> findByNameContaining(String name, Pageable page);
}
