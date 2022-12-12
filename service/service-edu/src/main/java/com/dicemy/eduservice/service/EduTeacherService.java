package com.dicemy.eduservice.service;

import com.dicemy.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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
}
