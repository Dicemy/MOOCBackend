package com.dicemy.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dicemy.commonutils.R;
import com.dicemy.eduservice.entity.EduCourse;
import com.dicemy.eduservice.entity.chapter.ChapterVo;
import com.dicemy.eduservice.entity.frontvo.CourseFrontVo;
import com.dicemy.eduservice.entity.frontvo.CourseWebVo;
import com.dicemy.eduservice.service.EduChapterService;
import com.dicemy.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {
    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduChapterService eduChapterService;

    @PostMapping("/getFrontInfo/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<EduCourse> pageCourse = new Page<>(page, limit);
        Map<String, Object> map = eduCourseService.getCourseFrontList(pageCourse, courseFrontVo);
        return R.ok().data(map);
    }

    @GetMapping("/getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId) {
        CourseWebVo courseWebVo = eduCourseService.getBaseCourseInfo(courseId);
        List<ChapterVo> chapterVideoList = eduChapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("courseWebVo", courseWebVo).data("chapterVideoList", chapterVideoList);
    }
}
