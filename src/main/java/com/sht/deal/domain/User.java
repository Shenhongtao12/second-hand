package com.sht.deal.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;

@Data
public class User {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private String username;
    private String password;
    private String phone;
    private String sex;
    private String img;
}
