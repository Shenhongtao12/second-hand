package com.sht.deal.service;

import com.sht.deal.controller.UploadController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

@Service
public class UploadService {

    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

    // 支持的文件类型
    private static final List<String> suffixes = Arrays.asList("image/png", "image/jpeg");

    public Map upload(MultipartFile file, String site) {
        try {
            // 1、图片信息校验
            // 1)校验文件类型
            String type = file.getContentType();
            if (!suffixes.contains(type)) {
                logger.info("上传失败，文件类型不匹配：{}", type);
                return null;
            }
            // 2)校验图片内容
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image == null) {
                logger.info("上传失败，文件内容不符合要求");
                return null;
            }
            // 2、保存图片
            //修改文件名
            String fileName = file.getOriginalFilename();  // 文件名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 只保留后缀名
            fileName = UUID.randomUUID() + suffixName; // 生成新文件名  UUID+文件后缀名
            // 2.1、生成保存目录
            File dir = new File(site);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            // 2.2、保存图片
            file.transferTo(new File(dir, fileName));

            // 2.3、拼接图片地址
            String url = "http://39.106.188.22:8800"+ site+"/" + fileName;
            /*String str = site.substring(8);
            String url = "http://localhost:8800/deal/"+ str +"/" + fileName;*/

            String name = fileName;
            Map result = new HashMap();
            result.put("name", name);
            result.put("url", url);

            return result;
        } catch (Exception e) {
            return null;
        }
    }


}
