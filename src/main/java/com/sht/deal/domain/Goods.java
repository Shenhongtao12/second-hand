package com.sht.deal.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

@Table(name = "goods")
@Data
public class Goods implements Serializable {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private String name;
    private String intro;
    private Double price1;  //原价
    private Double price2;  //现价
    private String cover; //封面图片
    private String phone;
    private String create_time;
    private Integer state;
    private Integer classify2_id;
    private Integer userid;

    @Transient
    private String[] image;
    @Transient
    private List<Images> images;
    @Transient
    private User user;

}
