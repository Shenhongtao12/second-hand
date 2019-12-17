package com.sht.deal.service;

import com.sht.deal.Mapper.ImagesMapper;
import com.sht.deal.domain.Images;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ImagesService {

    @Autowired
    private ImagesMapper imagesMapper;

    List<Images> findUrlListByGoodsId(Integer id) {
        Example example = new Example(Images.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("goodsid", id);
        return imagesMapper.selectByExample(example);
    }

    public Map delete(int id){
        Map result = new HashMap();
        imagesMapper.deleteByPrimaryKey(id);
        result.put("code", 0);
        result.put("msg", "删除成功");
        return result;
    }

    public int deleteByGoodsid(int id) {
        Example example = new Example(Images.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("goodsid", id);
        return imagesMapper.deleteByExample(example);
    }


    public Object addImages(Images images) {
        return imagesMapper.insertSelective(images);
    }
}
