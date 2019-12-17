package com.sht.deal.controller;

import com.sht.deal.domain.Images;
import com.sht.deal.service.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/images")
public class ImagesController {

    @Autowired
    private ImagesService imagesService;

    @PostMapping("add")
    public ResponseEntity addImages(Images images){
        return ResponseEntity.ok(imagesService.addImages(images));
    }

    @GetMapping("delete")
    public ResponseEntity delete(Integer id) {
        return ResponseEntity.ok(imagesService.delete(id));
    }
}
