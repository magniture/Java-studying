package com.max.controller;

import com.max.domain.Course;
import com.max.domain.CourseVO;
import com.max.domain.ResponseResult;
import com.max.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    /**
     * 查询课程信息&条件查询 接口 *
     */
    @RequestMapping("/findCourseByConditioin")
    public ResponseResult findCourseByConditioin(@RequestBody CourseVO courseVO) {
        List<Course> courseList =
                courseService.findCourseByCondition(courseVO);
        ResponseResult result = new ResponseResult(true, 200, "响应成 功", courseList);
        return result;
    }


    @RequestMapping("/courseUpload")
    public ResponseResult fileUpload(@RequestParam("file")MultipartFile file, HttpServletRequest request) throws IOException {
        if (file.isEmpty()){
            throw new RuntimeException();
        }
        String realPath = request.getServletContext().getRealPath("/");
        String substring = realPath.substring(0, realPath.indexOf("ssm-web"));

        String originalFlename = file.getOriginalFilename();

        String newFileName = System.currentTimeMillis() + originalFlename.substring(originalFlename.lastIndexOf("."));

        String uploadPath = substring+"upload\\";

        File filePath = new File(uploadPath,newFileName);

        if (!filePath.getParentFile().exists()){
            filePath.getParentFile().mkdirs();
            System.out.println("创建目录"+filePath);
        }

        file.transferTo(filePath);

        Map<String,String> map = new HashMap<>();
        map.put("fileName",newFileName);
        map.put("filePath","http://locathost:8080/ipload"+newFileName);

        ResponseResult responseResult = new ResponseResult(true, 200, "图片上传成功", map);

        return responseResult;
    }


    @RequestMapping("/saveOrUpdateCourse")
    public ResponseResult saveOrUpdateCourse(@RequestBody CourseVO courseVO) throws InvocationTargetException, IllegalAccessException {

        if (courseVO.getId() == null){
            // 说明是新增操作

            // 1.调用service完成保存课程信息
            courseService.saveCourseOrTeacher(courseVO);
            ResponseResult responseResult = new ResponseResult(true, 200, "新增课程成功", null);
            return responseResult;

        }else {
            // 说明是修改操作

            // 1.调用service完成g更新课程信息
            courseService.updateCourseOrTeacher(courseVO);
            // 2.响应json格式的数据
            ResponseResult responseResult = new ResponseResult(true, 200, "更新课程成功", null);
            return responseResult;
        }



    }

    @RequestMapping("/findCourseById")
    public ResponseResult findCourseById(Integer id){
        CourseVO courseById = courseService.findCourseById(id);

        ResponseResult responseResult = new ResponseResult(true, 200,"根据ID查询课程信息成功",courseById);
        return responseResult;

    }

    /**
     * 根据课程id和课程状态完成修改课程状态功能
     * @param id
     * @param status
     * @return
     */
    @RequestMapping("/updateCourseStatus")
    public ResponseResult updateCourseStatus(Integer id,Integer status) {

        courseService.updateCourseStatus(id,status);

        // 1.按照接口文档中的响应示例格式进行响应
        Map<String,Object> map = new HashMap<>();
        map.put("status",status);

        ResponseResult responseResult = new ResponseResult(true,200,"响应成功！",map);
        return responseResult;
    }
}

