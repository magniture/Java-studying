package com.max.service.impl;

import com.max.dao.CourseContentMapper;
import com.max.domain.Course;
import com.max.domain.CourseLesson;
import com.max.domain.CourseSection;
import com.max.service.CourseContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class CourseContentServiceImpl implements CourseContentService {

    @Autowired
    private CourseContentMapper courseContentMapper;

    /**
     * 根据课程id查询课程章节和课时信息
     * 多表查询
     * @param courseId
     * @return
     */
    @Override
    public List<CourseSection> findSectionAndLessonByCourseId(Integer courseId) {
        List<CourseSection> sectionList = courseContentMapper.findSectionAndLessonByCourseId(courseId);
        return sectionList;
    }

    /**
     * 根据课程id查询课程名称
     * 这个功能用在
     * 当点击内容管理会跳转到课程章节信息和课时信息的页面，而在这个页面中需要显示当前课程的名称
     * @param id
     * @return
     */
    @Override
    public Course findCourseNameByCourseId(Integer id) {
        Course course = courseContentMapper.findCourseNameById(id);
        return course;
    }

    /**
     * 新建章节信息
     * 注意：需要补充页面没有传递的值
     * @param courseSection
     */
    @Override
    public void saveSection(CourseSection courseSection) {
        Date date = new Date();
        courseSection.setCreateTime(date);
        courseSection.setUpdateTime(date);

        courseContentMapper.saveSection(courseSection);
    }

    /**
     * 根据章节id修改章节信息
     * 注意：需要补充updateTime的信息
     * @param courseSection
     */
    @Override
    public void updateSection(CourseSection courseSection) {
        courseSection.setUpdateTime(new Date());

        // 调用dao完成修改
        courseContentMapper.updateSection(courseSection);
    }

    /**
     * 根据章节id修改章节状态信息
     * 注意：需要将参数封装到course_section实体类中，而且还要补全信息update_time
     * @param id
     * @param status
     */
    @Override
    public void updateSectionStatus(Integer id, Integer status) {
        // 创建课程章节实体类
        CourseSection courseSection = new CourseSection();
        courseSection.setId(id);
        courseSection.setStatus(status);
        courseSection.setUpdateTime(new Date());

        // 调用dao完成修改操作
        courseContentMapper.updateSectionStatus(courseSection);
    }

    /**
     * 新建课时信息
     * 注意：要补充信息，因为页面传递的参数是不够的
     * @param courseLesson
     */
    @Override
    public void saveLesson(CourseLesson courseLesson) {
        // 补全信息
        Date date = new Date();
        courseLesson.setCreateTime(date);
        courseLesson.setUpdateTime(date);

        // 调用dao完成保存操作
        courseContentMapper.saveLesson(courseLesson);

    }


}
