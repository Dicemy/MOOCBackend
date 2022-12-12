package com.dicemy.eduservice.controller;


import com.dicemy.commonutils.R;
import com.dicemy.eduservice.entity.subject.OneSubject;
import com.dicemy.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author dicemy
 * @since 2022-12-11
 */
@Api(description = "分类管理")
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;

    /**
     * 上传xlsx文件完成课程的上传
     * @param file
     * @return
     */
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
        eduSubjectService.saveSubject(file,eduSubjectService);
        return R.ok();
    }

    /**
     * 返回所有分类，包含二级分类
     * @return
     */
    @GetMapping("/getAllSubject")
    public R getAllSubject() {
        List<OneSubject> list = eduSubjectService.getAllOneTwoSubject();
        return R.ok().data("list", list);
    }
}

