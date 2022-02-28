package com.max.dao;

import com.max.domain.Course;
import com.max.domain.CourseLesson;
import com.max.domain.CourseSection;

import java.util.List;

/**
 * 课程内容管理模块：完成章节信息和课时信息的功能
 */
public interface CourseContentMapper {
    /**
     * 根据课程id查询出该课程的章节信息和课时信息
     * 多表查询
     * @param courseId
     * @return
     */
    List<CourseSection> findSectionAndLessonByCourseId(Integer courseId);

    /**
     * 根据课程id查询出课程的名称
     * 这个功能用在
     * 当点击内容管理会跳转到课程章节信息和课时信息的页面，而在这个页面中需要显示当前课程的名称
     * @param id
     * @return
     */
    Course findCourseNameById(Integer id);

    /**
     * 保存章节信息
     * 需要注意的是：页面中传递的请求参数是有限的，需要在service层进行补充
     * @param courseSection
     */
    void saveSection(CourseSection courseSection);

    /**
     * 根据章节id修改章节信息
     * @param courseSection
     */
    void updateSection(CourseSection courseSection);

    /**
     * 根据章节id修改章节信息
     * @param courseSection
     */
    void updateSectionStatus(CourseSection courseSection);

    /**
     * 新建课时信息
     * @param courseLesson
     */
    void saveLesson(CourseLesson courseLesson);
}
