package com.dicemy.eduservice.service;

import com.dicemy.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dicemy.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author dicemy
 * @since 2022-12-11
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file, EduSubjectService eduSubjectService);

    List<OneSubject> getAllOneTwoSubject();
}
