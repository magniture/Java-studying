package com.max.service.impl;

import com.max.dao.CourseMapper;
import com.max.domain.Course;
import com.max.domain.CourseVO;
import com.max.domain.Teacher;
import com.max.service.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseMapper courseMapper;

    /**
     * 根据课程名称和课程状态进行的多条件查询
     * @param courseVO
     * @return
     */
    @Override
    public List<Course> findCourseByCondition(CourseVO courseVO) {
        List<Course> courseList = courseMapper.findCourseByCondition(courseVO);
        return courseList;
    }
    /**
     * 新建课程信息中保存课程信息和讲师信息
     * 需要注意的是：补充courseVO中的属性，将新建课程页面传递的参数都封装到了courseVO这个实体类中
     * 为什么要补全呢？
     * 因为在之前根据课程名称和课程状态查询时，这个courseVO实体类中只封装了页面传递的课程名称和课程状态
     * @param courseVO
     */
    @Override
    public void saveCourseOrTeacher(CourseVO courseVO) throws InvocationTargetException, IllegalAccessException {
        // 1.完成Course对象的封装
        Course course = new Course();
        // 不建议调用get set方法实现封装
        // course.setCourseName(courseVO.getCourseName());
        // 使用BeanUtils工具类完成封装 将courseVO中的数据封装到course对象中  apache下的这个工具类是浅拷贝
        // 调用courseVO的get()获取到值然后调用course.set()方法设置值
        // 而且它只支持基本数据类型的拷贝，是不支持uitl.Date类型的拷贝，所以有时候需要声明下面这句代码
        // ConvertUtils.register(new DateConverter(null), java.util.Date.class)
        // 这样就允许源目标的日期类型的属性值可以为空

        BeanUtils.copyProperties(course,courseVO);

        // 2.补全course实体类中的属性值
        Date date = new Date();
        course.setCreateTime(date);
        course.setUpdateTime(date);

        // 3.调用dao完成保存课程信息的操作
        courseMapper.saveCourse(course);
        // 经过上述保存课程的操作，此时course对象中就存储了刚刚保存课程的id信息
        int id = course.getId();

        // 4.创建teacher对象
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(teacher,courseVO);

        // 5.补全讲师的信息
        teacher.setCreateTime(date);
        teacher.setUpdateTime(date);
        teacher.setCourseId(id);
        // 首先页面中没有传递这个参数，那么courseVO对象中的isDel属性的值是默认值 0，他封装到了course对象中的isDel属性上
        // 所以教师表中的这个属性就没有进行封装，那么我就手动设置一下这个逻辑删除为0 表示没有被删除
        teacher.setIsDel(0);

        // 6.调用dao完成讲师信息的保存
        courseMapper.saveTeacher(teacher);
    }

    /**
     * 调用dao层根据课程id查询课程信息和讲师信息
     * @param id
     * @return
     */
    @Override
    public CourseVO findCourseById(Integer id) {
        return courseMapper.findCourseById(id);
    }

    /**
     * 更新课程信息和讲师信息
     * 需要注意的是：在更新讲师信息的时候需要根据课程id进行修改，
     * 但是courseVO实体类中没有封装course_id字段，所以是无法封装到teacher实体类中的course_id属性上
     * 所以需要获取到course实体类中的id值，封装到teacher实体类中course_id属性上
     * @param courseVO
     */
    @Override
    public void updateCourseOrTeacher(CourseVO courseVO) throws InvocationTargetException, IllegalAccessException {
        // 创建Course实体类对象
        Course course = new Course();
        // 调用BeanUtils工具类完成对象的浅拷贝
        BeanUtils.copyProperties(course,courseVO);
        // 补充courseVO实体类中没有的属性和属性值
        Date date = new Date();
        course.setUpdateTime(date);

        // 调用dao层完成更新操作
        courseMapper.updateCourse(course);

        // 创建teacher对象
        Teacher teacher = new Teacher();
        // 调用BeanUtils工具类完成对象的浅拷贝
        BeanUtils.copyProperties(teacher,courseVO);
        // 补充teacher实体类中的属性和属性值
        teacher.setCourseId(course.getId());
        teacher.setUpdateTime(date);

        // 调用dao层接口完成更新讲师信息的操作
        courseMapper.updateTeacher(teacher);
    }

    /**
     * 将controller传来的课程id和课程状态封装到course对象中，然后调用dao完成修改
     * @param courseId
     * @param status
     */
    @Override
    public void updateCourseStatus(Integer courseId, Integer status) {
        // 1.封装course对象
        Course course = new Course();
        course.setId(courseId);
        course.setStatus(status);

        // 2.注意补充update_time属性
        course.setUpdateTime(new Date());

        // 3.调用dao完成修改操作
        courseMapper.updateCourseStatus(course);
    }


}