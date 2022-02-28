package com.max.service;

import com.max.domain.Course;
import com.max.domain.CourseLesson;
import com.max.domain.CourseSection;

import java.util.List;

/**
 * 课程内容功能的业务逻辑层
 */
public interface CourseContentService {
    /**
     * 根据课程id查询课程章节和课时信息
     * 多表查询
     * @param courseId
     * @return
     */
    List<CourseSection> findSectionAndLessonByCourseId(Integer courseId);

    /**
     * 根据课程id查询课程名称
     * 这个功能用在
     * 当点击内容管理会跳转到课程章节信息和课时信息的页面，而在这个页面中需要显示当前课程的名称
     * @param id
     * @return
     */
    Course findCourseNameByCourseId(Integer id);

    /**
     * 新建章节信息
     * 注意：需要补充页面没有传递的值
     * @param courseSection
     */
    void saveSection(CourseSection courseSection);

    /**
     * 根据章节id修改章节信息
     * 注意：需要补充updateTime的信息
     * @param courseSection
     */
    void updateSection(CourseSection courseSection);

    /**
     * 根据章节id修改章节状态信息
     * 注意：需要将参数封装到course_section实体类中，而且还要补全信息update_time
     * @param id
     * @param status
     */
    void updateSectionStatus(Integer id, Integer status);

    /**
     * 新建课时信息
     * @param courseLesson
     */
    void saveLesson(CourseLesson courseLesson);
}
