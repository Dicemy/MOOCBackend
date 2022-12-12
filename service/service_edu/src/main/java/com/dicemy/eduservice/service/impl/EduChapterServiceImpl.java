package com.dicemy.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dicemy.eduservice.entity.EduChapter;
import com.dicemy.eduservice.entity.EduVideo;
import com.dicemy.eduservice.entity.chapter.ChapterVo;
import com.dicemy.eduservice.entity.chapter.VideoVo;
import com.dicemy.eduservice.mapper.EduChapterMapper;
import com.dicemy.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dicemy.eduservice.service.EduVideoService;
import com.dicemy.servicebase.exceptionhandler.CustomException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        QueryWrapper<EduChapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id", courseId);
        List<EduChapter> chapterList = this.list(chapterWrapper);

        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id", courseId);
        List<EduVideo> videoList = eduVideoService.list(videoWrapper);

        List<ChapterVo> finalChapterList = new ArrayList<>();
        for (EduChapter eduChapter : chapterList) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            List<VideoVo> finalVideoList = new ArrayList<>();
            for (EduVideo eduVideo : videoList) {
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    finalVideoList.add(videoVo);
                }
            }
            chapterVo.setChildren(finalVideoList);
            finalChapterList.add(chapterVo);
        }
        return finalChapterList;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("chapter_id", chapterId);
        int count = eduVideoService.count(videoQueryWrapper);
        if (count > 0) {
            throw new CustomException(20001, "不能删除");
        }else {
            boolean flag = this.removeById(chapterId);
            return flag;
        }
    }

    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        this.remove(wrapper);
    }
}
