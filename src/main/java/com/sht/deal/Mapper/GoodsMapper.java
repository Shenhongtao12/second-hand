package com.sht.deal.Mapper;

import com.sht.deal.domain.Goods;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface GoodsMapper extends Mapper<Goods> {


    //@Select("select * from goods join images on goods.id = images.goodsid where id = #{id}")
    @Select("select * from goods where id = #{id}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "intro", column = "intro"),
            @Result(property = "price1", column = "price1"),
            @Result(property = "price2", column = "price2"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "create_time", column = "create_time"),
            @Result(property = "state", column = "state"),
            @Result(property = "classify2_id", column = "classify2_id"),
            @Result(property = "userid", column = "userid"),
            @Result(property = "images", column = "id", javaType = java.util.List.class, many = @Many(select = "com.sht.deal.Mapper.ImagesMapper.findByGoodsId")),
    })
    Goods findImagesById(int id);
}
