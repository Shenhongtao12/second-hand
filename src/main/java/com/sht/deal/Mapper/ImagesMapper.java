package com.sht.deal.Mapper;

import com.sht.deal.domain.Images;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ImagesMapper extends Mapper<Images> {

    @Select("select * from images where goodsid = #{goodsid}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "url", column = "url"),
            @Result(property = "goodsid", column = "goodsid"),
    })
    public List<Images> findByGoodsId(int goodsid) throws Exception;
}
