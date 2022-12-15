package com.dicemy.eduservice.controller;


import com.dicemy.commonutils.R;
import com.dicemy.eduservice.client.VodClient;
import com.dicemy.eduservice.entity.EduVideo;
import com.dicemy.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author dicemy
 * @since 2022-12-1
 *
 *
 * 1
 */
@Api(description = "小节管理")
@RestController
@RequestMapping("/eduservice/video")
//@CrossOrigin
public class EduVideoController {

    @Autowired
    private VodClient vodClient;
    @Autowired
    private EduVideoService eduVideoService;

    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        eduVideoService.save(eduVideo);
        return R.ok();
    }

    @DeleteMapping("/{videoId}")
    @Transactional
    public R deleteVide0(@PathVariable String videoId) {
        EduVideo video = eduVideoService.getById(videoId);
        String videoSourceId = video.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)) {
            vodClient.removeAlyVideo(videoSourceId);
        }
        eduVideoService.removeById(videoId);
        return R.ok();
    }

    @PostMapping("/updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo) {
        return R.ok();
    }
}

