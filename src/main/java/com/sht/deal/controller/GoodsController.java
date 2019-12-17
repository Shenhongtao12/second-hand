package com.sht.deal.controller;

import com.sht.deal.domain.Classify2;
import com.sht.deal.domain.Goods;
import com.sht.deal.exception.AllException;
import com.sht.deal.service.GoodsService;
import com.sht.deal.service.UploadService;
import com.sht.deal.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;

@RestController
@RequestMapping("api/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private UploadService uploadService;

    /**
     * 添加商品
     *
     * @param goods
     * @return
     */
    @PostMapping("add")
    public ResponseEntity add(@RequestBody Goods goods) {
        return ResponseEntity.ok(goodsService.add(goods));
    }


    /**
     * 根据id查找商品
     *
     * @param id
     * @return
     */
    @GetMapping("findById")
    public ResponseEntity<Goods> findById(@RequestParam(value = "id") Integer id) {
        return ResponseEntity.ok(goodsService.findImagesById(id));
    }

    /**
     * 更新商品信息
     *
     * @param goods
     * @return
     */
    @PutMapping("update")
    public ResponseEntity update(@RequestBody Goods goods) {
        return ResponseEntity.ok(goodsService.update(goods));
    }

    /**
     * 多条件查询
     * @param id  分类id
     * @param orderBy  排序的关键字
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("findByPage")
    public ResponseEntity<PageResult<Goods>> findByPage(
            @RequestParam(value = "id",defaultValue = "") Integer id,
            @RequestParam(value = "orderBy", defaultValue = "") String orderBy,
            @RequestParam(value = "userid", defaultValue = "") Integer userid,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows
    ) {
        return ResponseEntity.ok(goodsService.findByPage(id, orderBy, userid, page, rows));
    }

    /**
     * 删除商品
     *
     * @param id
     * @return
     */
    @GetMapping("deleteGoods")
    public ResponseEntity deleteGoods(Integer id) {
        return ResponseEntity.ok(goodsService.deleteGoods(id));
    }

    /**
     * 根据商品名称模糊查询
     * @param goodsName
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("findByLike")
    public ResponseEntity<PageResult<Goods>> findByLike(
            @RequestParam(value = "goodsName") String goodsName,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows
    ) {
        return ResponseEntity.ok(goodsService.findByLike(goodsName, page, rows));
    }

    /**
     * 上传图片功能
     *
     * @param file
     * @return
     */
    @PostMapping("image")
    public ResponseEntity uploadImage(
            @RequestParam("file") MultipartFile file,
            //@RequestParam(name = "site", defaultValue = "F:\\deal\\goods") String site) {
            @RequestParam(name = "site", defaultValue = "/deal/goods") String site) {
        Map url = this.uploadService.upload(file, site);
        if (StringUtils.isEmpty(url)) {
            // url为空，证明上传失败
            throw new AllException(-1, "图片上传失败");
        }
        // 返回200，并且携带url路径
        return ResponseEntity.ok(url);
    }

    // 删除文件
    @PostMapping("deleteFile")
    public String delFile(String name) {
        String resultInfo = null;
        //String path = "F:\\deal\\goods\\" + name;
        String path = "/deal/goods/" + name;
        File file = new File(path);
        if (file.exists()) {
            if (file.delete()) {
                resultInfo =  "1-删除成功";
            } else {
                resultInfo =  "0-删除失败";
            }
        } else {
            resultInfo = "文件不存在！";
        }

        return resultInfo;
    }
}
