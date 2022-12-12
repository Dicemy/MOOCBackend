package com.dicemy.eduservice.service;

import com.dicemy.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dicemy.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author dicemy
 * @since 2022-12-11
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);

    void removeChapterByCourseId(String courseId);
}
