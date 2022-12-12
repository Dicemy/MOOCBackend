package com.dicemy.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dicemy.eduservice.entity.EduCourse;
import com.dicemy.eduservice.entity.vo.CourseInfoVo;
import com.dicemy.eduservice.entity.vo.CoursePublishVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author dicemy
 * @since 2022-12-11
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo publishCourseInfo(String id);

    void removeCourse(String courseId);

    List<EduCourse> getAllCourse();
}
