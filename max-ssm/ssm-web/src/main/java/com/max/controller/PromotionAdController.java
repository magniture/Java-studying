package com.max.controller;

import com.github.pagehelper.PageInfo;
import com.max.domain.PromotionAd;
import com.max.domain.PromotionAdVo;
import com.max.domain.ResponseResult;
import com.max.service.PromotionAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/PromotionAd")
public class PromotionAdController {
    @Autowired
    private PromotionAdService promotionAdService;

    /**
     * 广告分页查询
     * @param promotionAdVo
     * @return
     */
    @RequestMapping("/findAllPromotionAdByPage")
    public ResponseResult findAllPromotionAdByPage(PromotionAdVo promotionAdVo) {
        PageInfo<PromotionAd> pageInfo = promotionAdService.findPromotionAdByPage(promotionAdVo);

        ResponseResult responseResult = new ResponseResult(true, 200, "广告列表分页查询成功！！", pageInfo);
        return responseResult;

    }

    /**
     * 新建广告中的图片上传
     * @param file
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/PromotionAdUpload")
    public ResponseResult fileUpload(@RequestParam(name = "file") MultipartFile file, HttpServletRequest request) throws IOException {
        // 1.先判断下这个file对象是否为空，如果为空，说明没有获取到上传文件的信息
        if (file.isEmpty()) {
            throw new RuntimeException();
        }
        // 2.获取项目的真实路径 D:\java\apache-tomcat-8.5.31-windows-64位\apache-tomcat-8.5.31\webapps\ssm_web
        String realPath = request.getServletContext().getRealPath("/");
        System.out.println("该项目的真实路径是：" + realPath);
        // 我想在tomcat中webapps目录下新建一个upload文件夹，这个文件夹中存储的是客户上传的文件
        // D:\java\apache-tomcat-8.5.31-windows-64位\apache-tomcat-8.5.31\webapps\
        String webapps = realPath.substring(0, realPath.indexOf("ssm_web"));
        System.out.println("webapps所在的目录是：" + webapps);

        // 3.获取该文件的名称，给他一个新的名称
        String originalFilename = file.getOriginalFilename();   // 文件的初始名称
        System.out.println("文件的初始名称：" + originalFilename);
        System.out.println("文件的后缀名是：" + originalFilename.substring(originalFilename.lastIndexOf(".")));
        // 4.通过时间戳的方式拼接一个新的文件名称，目的就是为了避免文件的名称重复
        long time = System.currentTimeMillis();
        System.out.println("获取到的时间戳是：" + time);
        String newFileName = time + originalFilename.substring(originalFilename.lastIndexOf("."));
        System.out.println("文件的 新名称是：" + newFileName);

        // 5.我上面获取到了webapps目录，然后通过字符串的拼接使得创建一个upload的目录
        String uploadPath = webapps + "upload\\";

        // 6.通过文件对象创建拼接出来的目录
        // uploadPath 表示文件要上传的位置
        // newFileName 表示文件的名称
        File filePath = new File(uploadPath, newFileName);
        File parentFile = filePath.getParentFile();
        System.out.println("文件上传的父目录位置：" + parentFile);
        // 7.判断该文件的父目录是否存在，如果不存在则要创建。
        if (!filePath.getParentFile().exists()) {
            // 这里需要注意的是：我们需要创建多级目录，因为父目录就是一个多级目录
            // D:/java/apache-tomcat-8.5.31-windows-64位/apache-tomcat-8.5.31/webapps/upload
            if (filePath.getParentFile().mkdirs()) {
                System.out.println("创建成功！");
            } else {
                System.out.println("创建失败！");
            }
        }

        // 8.完成图片的上传
        file.transferTo(filePath);

        // 9.将文件的信息保存到map集合中
        HashMap<String, String> map = new HashMap<>();
        map.put("fileName",newFileName);
        // 要通过网页的形式访问该图片

        map.put("filePath","http://localhost:8080/upload/" + newFileName);

        // 10.封装数据，响应结果
        ResponseResult result = new ResponseResult(true, 200, "响应成功", map);

        return result;
    }

    /**
     * 新建或者修改广告信息
     * 在编写新建广告的时候，出现了一个BUG。
     * 前端页面传递的时间字符串必须是这种格式的 yyyy-MM-dd HH:mm:ss。
     * 所以说：页面中传递的json数据要注意日期字符串的填写
     * @param promotionAd
     * @return
     */
    @RequestMapping("/saveOrUpdatePromotionAd")
    public ResponseResult saveOrUpdatePromotionAd(@RequestBody PromotionAd promotionAd) {
        if (promotionAd.getId() == null) {
            promotionAdService.savePromotionAd(promotionAd);

            ResponseResult responseResult = new ResponseResult(true, 200, "新建广告信息成功！", null);
            return responseResult;
        } else {
            promotionAdService.updatePromotionAd(promotionAd);
            ResponseResult responseResult = new ResponseResult(true, 200, "修改广告信息成功!", null);
            return responseResult;
        }
    }

    /**
     * 回显广告信息功能
     * @param id
     * @return
     */
    @RequestMapping("/findPromotionAdById")
    public ResponseResult findPromotionAdById(Integer id) {
        PromotionAd promotionAd = promotionAdService.findPromotionAdById(id);

        ResponseResult responseResult = new ResponseResult(true,200,"广告信息回显成功！",promotionAd);
        return responseResult;
    }

    /**
     * 修改广告状态
     * @param id
     * @param status
     * @return
     */
    @RequestMapping("/updatePromotionAdStatus")
    public ResponseResult updatePromotionAdStatus(Integer id,Integer status) {
        promotionAdService.updatePromotionAdStatus(id,status);

        ResponseResult responseResult = new ResponseResult(true, 200, "修改广告状态成功！", null);
        return responseResult;
    }
}
