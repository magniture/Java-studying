package com.max.controller;

import com.max.domain.Course;
import com.max.domain.CourseLesson;
import com.max.domain.CourseSection;
import com.max.domain.ResponseResult;
import com.max.service.CourseContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/courseContent")
public class CourseContentController {

    @Autowired
    private CourseContentService courseContentService;

    @RequestMapping("/findSectionAndLesson")
    public ResponseResult findSectionAndLessonByCourseId(Integer courseId) {

        List<CourseSection> sectionList = courseContentService.findSectionAndLessonByCourseId(courseId);

        ResponseResult responseResult = new ResponseResult(true,200,"课程章节和课时信息查询成功！",sectionList);

        return responseResult;
    }

    /**
     * 根据课程id查询课程名称
     * 这个功能用在
     * 当点击内容管理会跳转到课程章节信息和课时信息的页面，而在这个页面中需要显示当前课程的名称
     * @param courseId
     * @return
     */
    @RequestMapping("/findCourseByCourseId")
    public ResponseResult findCourseByCourseId(Integer courseId) {
        Course course = courseContentService.findCourseNameByCourseId(courseId);

        ResponseResult responseResult = new ResponseResult(true,200,"查询课程名称成功！",course);

        return responseResult;
    }

    /**
     * 新建章节信息或者修改章节信息
     * 如何区分是新建操作还是修改操作呢？
     * 就是看页面是否传递了参数id，如果传递了，就是根据章节id修改章节信息
     * @param courseSection
     * @return
     */
    @RequestMapping("/saveOrUpdateSection")
    public ResponseResult saveOrUpdateSection(@RequestBody CourseSection courseSection) {
        if (courseSection.getId() == null) {
            // 新建章节信息
            courseContentService.saveSection(courseSection);
            ResponseResult responseResult = new ResponseResult(true, 200, "新建章节成功！", null);
            return responseResult;
        } else {
            // 修改章节信息
            courseContentService.updateSection(courseSection);
            ResponseResult responseResult = new ResponseResult(true, 200, "修改章节信息成功！", null);
            return responseResult;
        }
    }

    /**
     * 根据章节id修改当前章节的状态
     * @param id
     * @param status
     * @return
     */
    @RequestMapping("/updateSectionStatus")
    public ResponseResult updateSectionStatus(Integer id,Integer status) {
        // 调用service完成参数的传递
        courseContentService.updateSectionStatus(id,status);

        // 根据接口文档中的响应示例进行响应
        Map<String,Object> map = new HashMap<>();
        map.put("status",status);
        ResponseResult responseResult = new ResponseResult(true, 200, "修改章节状态成功！", map);
        return responseResult;
    }

    /**
     * 新建/修改课时信息
     * @return
     */
    @RequestMapping("/saveOrUpdateLesson")
    public ResponseResult saveOrUpdateLesson(@RequestBody CourseLesson courseLesson) {
        // 调用service完成保存操作
        courseContentService.saveLesson(courseLesson);

        // 响应
        ResponseResult responseResult = new ResponseResult(true,200,"新建课时信息成功！",null);
        return responseResult;
    }
}
