package com.dicemy.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dicemy.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author dicemy
 * @since 2022-12-08
 */
public interface EduTeacherService extends IService<EduTeacher> {

    List<EduTeacher> getAllTeacher();

    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher);
}
