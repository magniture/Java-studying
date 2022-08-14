package com.imooc.bilibili.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.imooc.bilibili.dao.DanmuDao;
import com.imooc.bilibili.domain.Danmu;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DanmuService {

    private static final String DANMU_KEY = "dm-video-";

    @Autowired
    private DanmuDao danmuDao;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void addDanmu(Danmu danmu){
        danmuDao.addDanmu(danmu);
    }

    @Async
    public void asyncAddDanmu(Danmu danmu){
        danmuDao.addDanmu(danmu);
    }

    /**
     * 查询策略是优先查redis中的弹幕数据，
     * 如果没有的话查询数据库，然后把查询的数据写入redis当中
     */
    public List<Danmu> getDanmus(Long videoId,
                                 String startTime, String endTime) throws Exception {

        String key = DANMU_KEY + videoId;
        String value = redisTemplate.opsForValue().get(key);
        List<Danmu> list;
        if(!StringUtil.isNullOrEmpty(value)){
            list = JSONArray.parseArray(value, Danmu.class);
            if(!StringUtil.isNullOrEmpty(startTime)
                    && !StringUtil.isNullOrEmpty(endTime)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date startDate = sdf.parse(startTime);
                Date endDate = sdf.parse(endTime);
                List<Danmu> childList = new ArrayList<>();
                for(Danmu danmu : list){
                    Date createTime = danmu.getCreateTime();
                    if(createTime.after(startDate) && createTime.before(endDate)){
                        childList.add(danmu);
                    }
                }
                list = childList;
            }
        }else{
            Map<String, Object> params = new HashMap<>();
            params.put("videoId", videoId);
            params.put("startTime", startTime);
            params.put("endTime", endTime);
            list = danmuDao.getDanmus(params);
            //保存弹幕到redis
            redisTemplate.opsForValue().set(key, JSONObject.toJSONString(list));
        }
        return list;
    }

    public void addDanmusToRedis(Danmu danmu) {
        String key = DANMU_KEY + danmu.getVideoId();
        String value = redisTemplate.opsForValue().get(key);
        List<Danmu> list = new ArrayList<>();
        if(!StringUtil.isNullOrEmpty(value)){
            list = JSONArray.parseArray(value, Danmu.class);
        }
        list.add(danmu);
        redisTemplate.opsForValue().set(key, JSONObject.toJSONString(list));
    }

}
