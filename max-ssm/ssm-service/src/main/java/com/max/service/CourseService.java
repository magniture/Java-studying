package com.max.service;

import com.max.domain.Course;
import com.max.domain.CourseVO;
import com.max.domain.Teacher;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface CourseService {
    /**
     * 根据课程名称和课程状态进行的多条件查询
     * @param courseVO
     * @return
     */
    List<Course> findCourseByCondition(CourseVO courseVO);

    /**
     * 新建课程信息中保存课程信息和讲师信息
     * 需要注意的是：补充courseVO中的属性，将新建课程页面传递的参数都封装到了courseVO这个实体类中
     * 为什么要补全呢？
     * 因为在之前根据课程名称和课程状态查询时，这个courseVO实体类中只封装了页面传递的课程名称和课程状态
     * @param courseVO
     */
    void saveCourseOrTeacher(CourseVO courseVO) throws InvocationTargetException, IllegalAccessException;

    /**
     * 调用dao层根据课程id查询课程信息和讲师信息
     * @param id
     * @return
     */
    CourseVO findCourseById(Integer id);

    /**
     * 更新课程信息和讲师信息
     * 需要注意的是：在更新讲师信息的时候需要根据课程id进行修改，
     * 但是courseVO实体类中没有封装course_id字段，所以是无法封装到teacher实体类中的course_id属性上
     * 所以需要获取到course实体类中的id值，封装到teacher实体类中course_id属性上
     * @param courseVO
     */
    void updateCourseOrTeacher(CourseVO courseVO) throws InvocationTargetException, IllegalAccessException;

    /**
     * 将controller传来的课程id和课程状态封装到course对象中，然后调用dao完成修改
     * @param courseId
     * @param status
     */
    void updateCourseStatus(Integer courseId,Integer status);
}
