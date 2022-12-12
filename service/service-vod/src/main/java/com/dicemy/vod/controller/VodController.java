package com.dicemy.vod.controller;

import com.dicemy.commonutils.R;
import com.dicemy.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    @PostMapping("/uploadAlyiVideo")
    public R uploadAlyiVideo(@RequestBody MultipartFile file) {
        String videoId = vodService.uploadVideoAly(file);
        return R.ok().data("videoId", videoId);
    }

    @DeleteMapping("/removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable String id) {
        vodService.removeAlyVideo(id);
        return R.ok();
    }

    @DeleteMapping("/delect-batch")
    public R delectBatch(@RequestParam("videoIdList") List<String> videoIdList) {
        vodService.removeMoreAlyVideo(videoIdList);
        return R.ok();
    }
}
