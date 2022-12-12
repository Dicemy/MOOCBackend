package com.dicemy.eduservice.controller.front;

import com.dicemy.commonutils.R;
import com.dicemy.eduservice.entity.EduCourse;
import com.dicemy.eduservice.entity.EduTeacher;
import com.dicemy.eduservice.service.EduCourseService;
import com.dicemy.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin
public class IndexFrontController {
    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduTeacherService eduTeacherService;

    @Cacheable(value = "teacherAndCourse", key = "'index'")
    @GetMapping("/index")
    public R index() {
        List<EduCourse> courseList = eduCourseService.getAllCourse();
        List<EduTeacher> teacherList = eduTeacherService.getAllTeacher();
        return R.ok().data("edulist", courseList).data("teacherList", teacherList);
    }
}
