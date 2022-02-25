package com.javapandeng.controller;

import com.javapandeng.utils.SystemContext;
import com.javapandeng.utils.UUIDUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ueditor上传文件处理
 */
@Controller
@RequestMapping("/ueditor")
public class UeditorController {

    @ResponseBody
    @RequestMapping("/saveFile")
    public Map<String,Object> saveFile(@RequestParam(value="upfile",required = false)MultipartFile file) throws IOException {
        Map<String,Object> params = new HashMap<>();
        String n = UUIDUtils.create();
        String path = SystemContext.getRealPath() + "resource\\ueditor\\upload\\" + n + file.getOriginalFilename();
        File newFile = new File(path);
        //通过CommonsMultipartFile的方法直接写文件
        file.transferTo(newFile);
        String visitUrl = "/resource/ueditor/upload/"+n+file.getOriginalFilename();
        params.put("state","SUCCESS");
        params.put("url",visitUrl);
        params.put("size",file.getSize());
        params.put("original",file.getOriginalFilename());
        params.put("type",file.getContentType());
        return params;
    }
}
