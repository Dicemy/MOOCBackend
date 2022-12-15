package com.dicemy.eduservice.controller;


import com.dicemy.commonutils.R;
import com.dicemy.eduservice.entity.EduChapter;
import com.dicemy.eduservice.entity.chapter.ChapterVo;
import com.dicemy.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author dicemy
 * @since 2022-12-11
 */
@Api(description = "章节管理")
@RestController
@RequestMapping("/eduservice/chapter")
//@CrossOrigin
public class EduChapterController {
    @Autowired
    private EduChapterService eduChapterService;

    /**
     * 根据课程id返回课程的章节信息
     * @param courseId
     * @return
     */
    @GetMapping("/getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId) {
        List<ChapterVo> list = eduChapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo", list);
    }

    /**
     * 添加章节信息
     * @param eduChapter
     * @return
     */
    @PostMapping("/addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter  ) {
        eduChapterService.save(eduChapter);
        return R.ok();
    }

    /**
     * 通过章节Id获取章节信息
     * @param chapterId
     * @return
     */
    @GetMapping("/getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId) {
        EduChapter eduCHapter = eduChapterService.getById(chapterId);
        return R.ok().data("chapter", eduCHapter);
    }

    /**
     * 更新章节信息
     * @param eduChapter
     * @return
     */
    @PostMapping("/updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter  ) {
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    /**
     * 根据章节Id删除章节信息
     * @param chapterId
     * @return
     */
    @DeleteMapping("/{chapterId}")
    public R deleteChapter(@PathVariable String chapterId) {
        boolean flag = eduChapterService.deleteChapter(chapterId);
        if (flag) {
            return R.ok();
        }else {
            return R.error();
        }
    }
}

