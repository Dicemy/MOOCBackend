package com.dicemy.eduservice.controller;


import com.dicemy.commonutils.R;
import com.dicemy.eduservice.entity.EduVideo;
import com.dicemy.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author dicemy
 * @since 2022-12-11
 */
@Api(description = "小节管理")
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;

    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        eduVideoService.save(eduVideo);
        return R.ok();
    }

    //TODO 删除小节时同时要删除视频
    @DeleteMapping("/{videoId}")
    public R deleteVide0(@PathVariable String videoId) {
        eduVideoService.removeById(videoId);
        return R.ok();
    }

    @PostMapping("/updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo) {
        return R.ok();
    }
}

