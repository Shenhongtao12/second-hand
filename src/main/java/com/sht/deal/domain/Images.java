package com.sht.deal.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;

@Data
public class Images {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private String url;
    private Integer goodsid;
}
