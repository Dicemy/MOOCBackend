package com.dicemy.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dicemy.eduservice.entity.EduSubject;
import com.dicemy.eduservice.entity.excel.SubjectData;
import com.dicemy.eduservice.entity.subject.OneSubject;
import com.dicemy.eduservice.entity.subject.TwoSuject;
import com.dicemy.eduservice.listener.SubjectExcelListener;
import com.dicemy.eduservice.mapper.EduSubjectMapper;
import com.dicemy.eduservice.service.EduSubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author dicemy
 * @since 2022-12-11
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        try{
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", 0);
        List<EduSubject> oneSubjectList = this.list(wrapperOne);

        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", 0);
        List<EduSubject> twoSubjectList = this.list(wrapperTwo);

        List<OneSubject> finalSubjectList = new ArrayList<>();
        for (EduSubject eduSubject : oneSubjectList) {
            OneSubject oneSubject = new OneSubject();
//            oneSubject.setId(eduSubject.getId());
//            oneSubject.setTitle(eduSubject.getTitle());
            BeanUtils.copyProperties(eduSubject, oneSubject);
            List<TwoSuject> twoFinalSujectsList = new ArrayList<>();
            for (EduSubject subject : twoSubjectList) {
                if (subject.getParentId().equals(oneSubject.getId())) {
                    TwoSuject twoSuject = new TwoSuject();
                    BeanUtils.copyProperties(subject, twoSuject);
                    twoFinalSujectsList.add(twoSuject);
                }
            }
            oneSubject.setChildren(twoFinalSujectsList);
            finalSubjectList.add(oneSubject);
        }
        return finalSubjectList;
    }
}
