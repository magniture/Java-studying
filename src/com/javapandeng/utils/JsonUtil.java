package com.javapandeng.utils;
import java.io.IOException;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

@SuppressWarnings("unused")
public class JsonUtil {

	private static final String[] DATE_FORMAT = { "yyyy-MM-dd HH:mm:ss" };

	private static SerializeConfig config = new SerializeConfig();
	static {
		String dateFormat = "yyyy-MM-dd HH:mm:ss";
		config.put(java.util.Date.class, new SimpleDateFormatSerializer(dateFormat));
	}

	public static String toJson(Object obj) {
		String json = null;

		try {
			if (obj != null) {
				json = JSON.toJSONString(obj, SerializerFeature.WriteDateUseDateFormat);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	public static <T> T toBean(String json, Class<T> clazz) {
		T bean = null;

		try {
			if (json != null) {
				bean = JSON.parseObject(json, clazz);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}

	public static <T> List<T> toBeanList(String json, Class<T> clazz) {
		List<T> beanList = null;

		try {
			if (json != null) {
				beanList = JSON.parseArray(json, clazz);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return beanList;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) throws IOException {

//		{
//
//			Map map = new HashMap();
//			map.put("total", 0);
//			map.put("rows", new Object[0]);
//			map.put("sessionStatus", 0);
//			map.put("null", null);
//			map.put("date", new Date());
//			map.put("time", new Timestamp(System.currentTimeMillis()));
//			
//			String json = toJson(map);
//			System.out.println(json);
//
//			Map toMap = toBean(json, Map.class);
//			System.out.println(toMap);
//		}
		
//		{
//			List<HeatInfoVO> heatInfoList = new ArrayList<HeatInfoVO>();
//			
//			HeatInfoVO heatInfoVO  = new HeatInfoVO();
//			heatInfoVO.setErrMsg("err");
//			heatInfoList.add(heatInfoVO);
//			
//			List<HeatInfoDetailVO> heatInfoDetailList = new ArrayList<HeatInfoDetailVO>();
//			
//			for (int i = 0; i < 3; i++) {
//				HeatInfoDetailVO heatInfoDetailVO = new HeatInfoDetailVO();
//				heatInfoDetailVO.setAreaType("type" + i);
//				heatInfoDetailList.add(heatInfoDetailVO);
//			}
//			
//			heatInfoVO.setHeatInfoDetailList(heatInfoDetailList);
//			
//			String json = JsonUtil.toJson(heatInfoList);
//			System.out.println(json);
//			
//			List<HeatInfoVO> heatInfoList2 = JsonUtil.toBeanList(json, HeatInfoVO.class);
//			System.out.println(heatInfoList2.get(0).getErrMsg());
//			System.out.println(heatInfoList2.get(0).getHeatInfoDetailList().get(0).getAreaType());
//			System.out.println(JsonUtil.toJson(heatInfoList2));
//			
//		}
		
		
	}
}