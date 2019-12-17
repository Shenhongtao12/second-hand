package com.sht.deal.Mapper;

import com.sht.deal.domain.Classify2;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface Classify2Mapper extends Mapper<Classify2> {

    @Select("select * from classify2 where classify1id = #{classify1id}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "classify1id", column = "classify1id"),
            })
    public List<Classify2> findByClassId(int classify1Id) throws Exception;

    @Insert("INSERT INTO `classify2`(`name`, `classify1id`) VALUES (#{name}, #{classify1id})")
    int add(Classify2 classify2);

    @Update("UPDATE `classify2` SET `name` = #{name}, `classify1id` = #{classify1id} WHERE `id` = #{id}")
    int update(Classify2 classify2);
}
