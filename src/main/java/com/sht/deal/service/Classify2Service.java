package com.sht.deal.service;

import com.sht.deal.Mapper.Classify2Mapper;
import com.sht.deal.domain.Classify2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class Classify2Service {

    @Autowired
    private Classify2Mapper classify2Mapper;

    public int add(Classify2 classify2){
        return classify2Mapper.add(classify2);
    }

    public int delete(int id) {
        return classify2Mapper.deleteByPrimaryKey(id);
    }

    public int update(Classify2 classify2) {
        return classify2Mapper.update(classify2);
    }


    public List<Classify2> findAll(int id){
        Example example = new Example(Classify2.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("classify1id", id);
        return classify2Mapper.selectByExample(example);
    }
}
