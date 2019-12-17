package com.sht.deal.controller;

import com.sht.deal.domain.User;
import com.sht.deal.service.UserService;
import com.sht.deal.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("findById")
    public User findById(Integer id) {
        return userService.findById(id);
    }

    @PostMapping("save")
    public ResponseEntity<Map> save(@RequestBody User user){
        //return new ResponseEntity(userService.save(user), HttpStatus.CREATED);
        return ResponseEntity.ok(userService.save(user));
    }

    @PostMapping("login")
    public ResponseEntity<Map> login(@RequestBody User userParam)  {
        return new ResponseEntity(userService.login(userParam), HttpStatus.CREATED);
    }

    @GetMapping("findAll")
    public ResponseEntity<PageResult<User>> findAll(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "rows", defaultValue = "5") Integer rows
    ) {
        PageResult<User> userPageResult = userService.findAll(page, rows);
        return ResponseEntity.ok(userPageResult);
    }

    @GetMapping("delete")
    public ResponseEntity delete(Integer id) {
        return ResponseEntity.ok(userService.delete(id));
    }

    @PutMapping("update")
    public ResponseEntity<User> update(@RequestBody User user) {
        return ResponseEntity.ok(userService.update(user));
    }

}
