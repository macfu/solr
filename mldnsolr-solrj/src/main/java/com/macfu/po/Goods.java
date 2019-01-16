package com.macfu.po;

import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: liming
 * @Date: 2019/01/16 11:23
 * @Description:
 */
public class Goods implements Serializable {
    @Id
    @Indexed(name = "id")
    private Long id;
    @Indexed(name = "solr_s_name")
    private String name;
    @Indexed(name = "solr_s_catalog")
    private String catalog;
    @Indexed(name = "solr_s_provider")
    private String provider;
    @Indexed(name = "solr_d_price")
    private Double price;
    @Indexed(name = "solr_s_note")
    private String note;
    @Indexed(name = "solr_s_photo")
    private String photo;
    @Indexed(name = "solr_data_recdate")
    private Date recdate;
    @Indexed(name = "solr_i_isdelet")
    private Integer isdelet;
    @Indexed(name = "gook_keyword")
    private String keyword;
}
