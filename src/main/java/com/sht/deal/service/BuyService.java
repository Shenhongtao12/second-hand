package com.sht.deal.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sht.deal.Mapper.BuyMapper;
import com.sht.deal.domain.Buy;
import com.sht.deal.domain.User;
import com.sht.deal.exception.AllException;
import com.sht.deal.utils.DateUtils;
import com.sht.deal.utils.JsonData;
import com.sht.deal.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Transactional
public class BuyService {

    @Autowired
    private BuyMapper buyMapper;

    @Autowired
    private UserService userService;


    public JsonData add(Buy buy) {
        //传入一个现在的时间
        buy.setCreate_time(DateUtils.dateByString());
        int i = buyMapper.insertSelective(buy);
        if (i != 1){
            throw new AllException(-1, "添加失败");
        }
        return JsonData.buildSuccess();
    }

    public PageResult<Buy> findByPage(Integer userid, int page, int rows) {
        Example example = new Example(Buy.class);
        Example.Criteria criteria = example.createCriteria();
        if (userid != null){
            criteria.andEqualTo("userid", userid);
        }
        example.setOrderByClause("create_time desc");
        PageHelper.startPage(page, rows);
        //关联发布者的信息
        List<Buy> buyList = buyMapper.selectByExample(example);
        for (Buy buy : buyList) {
            User user = userService.findById(buy.getUserid());
            user.setPhone("");
            buy.setUser(user);
        }
        Page<Buy> buyPage = (Page<Buy>) buyList;

        return new PageResult<>(buyPage.getTotal(), buyPage.getPages(), buyPage.getResult());
    }

    public JsonData delete(int id) {
        int i = buyMapper.deleteByPrimaryKey(id);
        return JsonData.buildSuccess(i, "删除成功");
    }

    public JsonData update(Buy buy) {
        //设置更改时间
        buy.setCreate_time(DateUtils.dateByString());
        int i = buyMapper.updateByPrimaryKeySelective(buy);
        if (i != 1){
            throw new AllException(-1, "更新失败");
        }
        return JsonData.buildSuccess(buy,"更新成功");
    }
}
