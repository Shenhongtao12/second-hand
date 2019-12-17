package com.sht.deal.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sht.deal.Mapper.UserMapper;
import com.sht.deal.domain.User;
import com.sht.deal.exception.AllException;
import com.sht.deal.utils.JwtUtils;
import com.sht.deal.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User findById(int id) {
        User user = userMapper.selectByPrimaryKey(id);
        user.setPassword("******");
        if (user == null){
            throw new AllException(-1, "没有此用户");
        }
        return user;
    }

    public Map save(User user) {
        String test = (String) isExist(user);
        System.out.println(test);
        if (Objects.nonNull(isExist(user))){
            throw new AllException(-1, String.valueOf(isExist(user)));
        }
        int i = userMapper.insertSelective(user);
        int id = user.getId();//返回新增的用户的id
        Map result = new HashMap();
        result.put("success", i);
        result.put("id", id);
        return result;
    }

    public Map login(User userParam)  {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",userParam.getUsername());
        User user = userMapper.selectOneByExample(example);
        Map result = new HashMap();
        if (Objects.isNull(user) || !user.getPassword().equals(userParam.getPassword())) {
            throw new AllException(-1,"用户名或密码错误");
        }
        String token = JwtUtils.geneJsonWebToken(user);
        result.put("code", 0);
        result.put("token", token);
        result.put("data", user);
        return result;
    }

    public PageResult<User> findAll(int page, int rows) {
        PageHelper.startPage(page, rows);
        Page<User> userPage = (Page<User>) userMapper.selectAll();
        return new PageResult<>(userPage.getTotal(), userPage.getPages(), userPage.getResult());
    }

    public int delete(int id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    public User update(User user) {
        if (user.getImg() != null){
            User user1 = findById(user.getId());
            String path = user1.getImg();
            //path = "F:"+ path.substring(21);
            path = path.substring(25);
            File file = new File(path);
            file.delete();
        }
        userMapper.updateByPrimaryKeySelective(user);
        return findById(user.getId());
    }

    /**
     * 校验用户名
     * @param user
     * @return
     */
    private Object isExist(User user) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",user.getUsername());
        User currentUser = userMapper.selectOneByExample(example);
        if (Objects.nonNull(currentUser)) {
            if (currentUser.getUsername().equals(user.getUsername())) return "用户名已存在";
        }
        return null;
    }
}
