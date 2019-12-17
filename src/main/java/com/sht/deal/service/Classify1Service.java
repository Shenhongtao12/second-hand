package com.sht.deal.service;

import com.sht.deal.Mapper.Classify1Mapper;
import com.sht.deal.domain.Classify1;
import com.sht.deal.domain.Classify2;
import com.sht.deal.exception.AllException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Objects;

@Service
public class Classify1Service {

    @Autowired
    private Classify1Mapper classify1Mapper;

    @Autowired
    private Classify2Service classify2Service;

    public int add(Classify1 classify1) {
        if (Objects.nonNull(isExist(classify1))){
            throw new AllException(-1, String.valueOf(isExist(classify1)));
        }
        return classify1Mapper.insertSelective(classify1);
    }

    public Classify1 findById(Integer id) {
        return classify1Mapper.selectByPrimaryKey(id);
    }

    public int delete(Integer id) {
        return classify1Mapper.deleteByPrimaryKey(id);
    }

    public Classify1 update(Classify1 classify1) {
        if (Objects.nonNull(isExist(classify1))){
            throw new AllException(-1, String.valueOf(isExist(classify1)));
        }
        classify1Mapper.updateByPrimaryKeySelective(classify1);
        return findById(classify1.getId());
    }

    /*public Classify1 findChildById(int id) throws Exception {
        return classify1Mapper.findChildById(id);
    }*/

    public List<Classify2> findChildById(int id){
        return classify2Service.findAll(id);
    }

    public List<Classify1> findAll() {
        List<Classify1> classify1List = classify1Mapper.selectAll();
        for (Classify1 classify1 : classify1List) {
            System.out.println(classify1);
            List<Classify2> classify2List = classify2Service.findAll(classify1.getId());
            classify1.setClassify2List(classify2List);
        }
        return classify1List;
    }



    /**
     * 校验分类名称
     * @param
     * @return
     */
    private Object isExist(Classify1 classify1) {
        Example example = new Example(Classify1.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("name",classify1.getName());
        Classify1 classify = classify1Mapper.selectOneByExample(example);
        if (Objects.nonNull(classify)) {
            if (classify.getName().equals(classify1.getName())) return "分类名已存在";
        }
        return null;
    }

    public List<Classify1> findClassify1() {
        return classify1Mapper.selectAll();
    }
}
