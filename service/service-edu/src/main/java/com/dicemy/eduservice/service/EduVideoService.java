package com.dicemy.eduservice.service;

import com.dicemy.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author dicemy
 * @since 2022-12-11
 */
public interface EduVideoService extends IService<EduVideo> {
    void removeVideoByCourseId(String courseId);
}
