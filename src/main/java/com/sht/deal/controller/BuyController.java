package com.sht.deal.controller;

import com.sht.deal.domain.Buy;
import com.sht.deal.service.BuyService;
import com.sht.deal.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user/api/buy")
public class BuyController {

    @Autowired
    private BuyService buyService;

    @PostMapping("add")
    public ResponseEntity add(@RequestBody Buy buy){
        return ResponseEntity.ok(buyService.add(buy));
    }

    /**
     * 分页展示所有求购信息
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("findByPage")
    public ResponseEntity<PageResult<Buy>> findByPage(
            @RequestParam(value = "userid", defaultValue = "") Integer userid,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows
    ){
        return ResponseEntity.ok(buyService.findByPage(userid, page, rows));
    }

    @DeleteMapping("delete")
    public ResponseEntity delete(@RequestParam(value = "id") Integer id) {
        return ResponseEntity.ok(buyService.delete(id));
    }

    @PutMapping("update")
    public ResponseEntity update(@RequestBody Buy buy){
        return ResponseEntity.ok(buyService.update(buy));
    }

}
