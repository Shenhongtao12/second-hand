package com.sht.deal.controller;

import com.sht.deal.exception.AllException;
import com.sht.deal.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("api/upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;


    /**
     * 上传图片功能
     * @param file
     * @return
     */
    @PostMapping("image")
    public ResponseEntity uploadImage(
            @RequestParam("file") MultipartFile file,
            //@RequestParam(name = "site", defaultValue = "F:\\deal\\user") String site) {
            @RequestParam(name = "site", defaultValue = "/deal/user") String site) {
        Map url = this.uploadService.upload(file, site);
        if (StringUtils.isEmpty(url)) {
            // url为空，证明上传失败
            throw new AllException(-1, "图片上传失败");
        }
        // 返回200，并且携带url路径
        return ResponseEntity.ok(url);
    }
}
