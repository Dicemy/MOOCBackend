package com.dicemy.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dicemy.eduservice.entity.EduCourse;
import com.dicemy.eduservice.entity.EduCourseDescription;
import com.dicemy.eduservice.entity.vo.CourseInfoVo;
import com.dicemy.eduservice.entity.vo.CoursePublishVo;
import com.dicemy.eduservice.mapper.EduCourseMapper;
import com.dicemy.eduservice.service.EduChapterService;
import com.dicemy.eduservice.service.EduCourseDescriptionService;
import com.dicemy.eduservice.service.EduCourseService;
import com.dicemy.eduservice.service.EduVideoService;
import com.dicemy.servicebase.exceptionhandler.CustomException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author dicemy
 * @since 2022-12-11
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;
    @Autowired
    private EduChapterService eduChapterService;
    @Autowired
    private EduVideoService eduVideoService;

    @Override
    @Transactional
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        boolean save = this.save(eduCourse);
        if (!save) {
            throw new CustomException(20001, "添加课程信息失败");
        }
        String cid = eduCourse.getId();
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescription.setId(eduCourse.getId());
        eduCourseDescriptionService.save(eduCourseDescription);
        return cid;
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        EduCourse eduCourse = this.getById(courseId);
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();

        BeanUtils.copyProperties(eduCourse, courseInfoVo);
        courseInfoVo.setDescription(eduCourseDescription.getDescription());

        return courseInfoVo;
    }

    @Override
    @Transactional
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        boolean update = this.update(eduCourse, null);
        if (!update) {
            throw new CustomException(20001, "修改课程信息失败");
        }
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo, eduCourseDescription);
        eduCourseDescriptionService.updateById(eduCourseDescription);
    }

    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    @Override
    @Transactional
    public void removeCourse(String courseId) {
        eduVideoService.removeVideoByCourseId(courseId);
        eduChapterService.removeChapterByCourseId(courseId);
        eduCourseDescriptionService.removeById(courseId);
        boolean flag = this.removeById(courseId);
        if (!flag) {
            throw new CustomException(20001, "删除失败");
        }
    }

    @Override
    public List<EduCourse> getAllCourse() {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<EduCourse> list = this.list(wrapper);
        return list;
    }
}
