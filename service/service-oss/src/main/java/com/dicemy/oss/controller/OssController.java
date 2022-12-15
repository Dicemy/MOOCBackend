package com.dicemy.oss.controller;

import com.dicemy.commonutils.R;
import com.dicemy.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {
    @Autowired
    private OssService ossService;
    @PostMapping
    public R uploadOssFile(MultipartFile file) {
        String url = ossService.uploadFileAvatar(file);
        return R.ok().data("url",url);
    }
}
