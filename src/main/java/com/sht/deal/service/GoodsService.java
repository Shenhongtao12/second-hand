package com.sht.deal.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sht.deal.Mapper.GoodsMapper;
import com.sht.deal.domain.Goods;
import com.sht.deal.domain.Images;
import com.sht.deal.domain.User;
import com.sht.deal.exception.AllException;
import com.sht.deal.utils.DateUtils;
import com.sht.deal.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private ImagesService imagesService;

    @Autowired
    private UserService userService;


    public Map add(Goods goods) {
        Map result = new HashMap();
        if (goods.getClassify2_id()==null || goods.getUserid() == null){
            throw new AllException(-1, "请选择分类");
        }
        //设置添加时间
        goods.setCreate_time(DateUtils.dateByString());
        int i = goodsMapper.insertSelective(goods);
        if (i != 1){
            throw new AllException(-1, "添加失败");
        }
        int id = goods.getId();
        if (goods.getImage() != null){
            //获取到图片的url
            String[] imageArray = goods.getImage();
            for (int num =0;num< imageArray.length;num++){
                Images images = new Images();
                images.setUrl(imageArray[num]);
                images.setGoodsid(id);
                imagesService.addImages(images);
            }
        }

        result.put("code", 0);
        result.put("msg", "添加成功");
        return result;
    }

    public Map update(Goods goods) {
        Map result = new HashMap();
        //更新时间
        goods.setCreate_time(DateUtils.dateByString());
        int i = goodsMapper.updateByPrimaryKeySelective(goods);
        if (i != 1) {
            throw new AllException(-1, "更新失败");
        }
        if (goods.getImage() != null){
            //删除关联的图片
            imagesService.deleteByGoodsid(goods.getId());
            //获取到图片的url
            String[] imageArray = goods.getImage();
            for (int num =0;num < imageArray.length;num++){
                Images images = new Images();
                images.setUrl(imageArray[num]);
                images.setGoodsid(goods.getId());
                imagesService.addImages(images);
            }
        }
        result.put("code", 0);
        result.put("msg", "修改成功");
        return result;
    }

    public Goods findImagesById(Integer id) {
        Goods goods = goodsMapper.findImagesById(id);
        //商品关联的发布者信息
        User user = userService.findById(goods.getUserid());
        user.setPhone("");
        goods.setUser(user);
        return goods;
    }

    public PageResult<Goods> findByPage(Integer id, String orderBy , Integer userid, int page, int rows) {
        PageHelper.startPage(page, rows);
        Example example = new Example(Goods.class);
        Example.Criteria criteria = example.createCriteria();
        if (userid == null){
            criteria.andEqualTo("state", 0);
        }
        if (id != null){
            criteria.andEqualTo("classify2_id", id);
        }
        if (orderBy.length() > 0){
            example.setOrderByClause(orderBy);
        }
        if (userid != null){
            criteria.andEqualTo("userid", userid);
        }
        List<Goods> goodsList = goodsMapper.selectByExample(example);
        for (Goods goods : goodsList) {
            List<Images> imageList = imagesService.findUrlListByGoodsId(goods.getId());
            goods.setImages(imageList);
        }
        Page<Goods> goodsPage = (Page<Goods>) goodsList;
        if (goodsPage.getTotal() < 1){
            throw new AllException(-1, "该分类没有商品");
        }
        return new PageResult<>(goodsPage.getTotal(), goodsPage.getPages(), goodsPage.getResult());
    }

    public Map deleteGoods(int id) {
        Map result = new HashMap();
        imagesService.deleteByGoodsid(id);
        int i = goodsMapper.deleteByPrimaryKey(id);
        if (i != 1){
            throw new AllException(-1, "删除商品失败");
        }
        result.put("code", -1);
        result.put("msg", "删除成功");
        return result;
    }

    public PageResult<Goods> findByLike(String goodsName, int page, int rows) {
        Example example = new Example(Goods.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("name", "%" +goodsName + "%");
        PageHelper.startPage(page, rows);
        Page<Goods> pageInfo = (Page<Goods>) goodsMapper.selectByExample(example);
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getPages(), pageInfo.getResult());
    }
}
