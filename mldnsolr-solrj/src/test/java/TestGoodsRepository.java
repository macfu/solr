import com.macfu.dao.IGoodsRepository;
import com.macfu.po.Goods;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Author: liming
 * @Date: 2019/01/16 12:12
 * @Description:
 */
@ContextConfiguration(locations = {"classpath:spring/spring-*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestGoodsRepository  {

    @Autowired
    private IGoodsRepository goodsRepository;

    @Test
    public void testName() {
        Sort sort = new Sort(Sort.Direction.DESC, "solr_d_price");
        Pageable pageable = PageRequest.of(0, 5, sort);
        HighlightPage<Goods> all = this.goodsRepository.findByNameContaining("人", pageable);
        System.out.println("总记录数：" + all.getTotalElements());
        System.out.println("总页数" + all.getTotalPages());
        List<HighlightEntry<Goods>> list = all.getHighlighted();
        list.forEach(e -> {
            System.out.println(e.getEntity());
        });
    }

    @Test
    public void testKeyword() {
        Sort sort = new Sort(Sort.Direction.DESC, "solr_d_price");
        Pageable pageable = PageRequest.of(0, 5, sort);
        HighlightPage<Goods> all = this.goodsRepository.findByKeywordContaining("空", pageable);
        System.out.println("总记录数：" + all.getTotalElements());
        System.out.println("总页数" + all.getTotalPages());
        List<HighlightEntry<Goods>> list = all.getHighlighted();
        list.forEach(e -> {
            System.out.println(e.getEntity());
        });
    }

    @Test
    public void testFindAll() {
        Sort sort = new Sort(Sort.Direction.DESC, "solr_d_price");
        Pageable pageable = PageRequest.of(0, 5, sort);
        Page<Goods> all = this.goodsRepository.findAll(pageable);
        System.out.println("总记录数：" + all.getTotalElements());
        System.out.println("总页数" + all.getTotalPages());
        List<Goods> list = all.getContent();
        list.forEach(System.out::println);
    }
}
