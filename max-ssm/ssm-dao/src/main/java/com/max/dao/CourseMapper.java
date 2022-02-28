package com.max.dao;

import com.max.domain.Course;
import com.max.domain.CourseVO;
import com.max.domain.Teacher;

import java.util.List;

public interface CourseMapper {
    /**
     * 前端页面中通过课程名称和课程状态进行的多条件查询
     * @param courseVO
     * @return
     */
    List<Course> findCourseByCondition(CourseVO courseVO);

    /**
     * 新增课程信息
     * 需要注意的是：在这个插入语句中，插入完毕之后，需要把该课程的id返回。
     * 因为在新增课程信息的同时：需要新增讲师，讲师表中需要保存课程的id信息
     * 所以需要返回课程的id信息，供新增讲师信息的功能中使用
     * @param course
     */
    void saveCourse(Course course);

    /**
     * 新增课程信息中也需要新增讲师的信息
     * 因为新增课程信息中，前端页面的表单中也填写了讲师的信息，需要将讲师的信息保存到teacher表中
     * @param teacher
     */
    void saveTeacher(Teacher teacher);

    /**
     * 根据课程id回显课程信息（课程信息和讲师信息）
     * 因为CourseVO实体类中包含课程字段和讲师字段，所以将查询出来的结果封装到CourseVO实体类上
     * @param id
     * @return
     */
    CourseVO findCourseById(Integer id);

    /**
     * 更新课程信息
     * @param course
     */
    void updateCourse(Course course);

    /**
     * 更新讲师信息
     * @param teacher
     */
    void updateTeacher(Teacher teacher);

    /**
     * 根据课程id和课程状态（在service层封装到了course对象中）修改课程的状态信息
     * 需要注意的是：也要对update_time字段进行修改
     * @param course
     */
    void updateCourseStatus(Course course);
}
