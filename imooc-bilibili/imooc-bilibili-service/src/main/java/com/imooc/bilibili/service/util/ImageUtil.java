package com.imooc.bilibili.service.util;

import com.alibaba.fastjson.JSONObject;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


@Component
public class ImageUtil {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String BAIDU_TOKEN_KEY = "baidu-access-token";

    @Value("${baidu.develop.auth.url}")
    private String authUrl;

    @Value("${baidu.develop.clientId}")
    private String clientId;

    @Value("${baidu.develop.clientSecret}")
    private String clientSecret;

    @Value("${baidu.develop.splitBody.url}")
    private String splitBodyUrl;

    public BufferedImage getBodyOutline(BufferedImage image, InputStream inputStream) throws Exception {
        //调用百度api进行人像分割
        String base64 = this.bodySeg(inputStream);
        JSONObject resultJson = JSONObject.parseObject(base64);
        //将结果转换为黑白剪影（二值图）
        return this.convert(resultJson.getString("labelmap"), image.getWidth(), image.getHeight());
    }

    public String bodySeg(InputStream inputStream) throws Exception{
        System.out.println("开始请求百度人体分割api");
        long start = System.currentTimeMillis();
        String imgStr = this.convertFileToBase64(inputStream);
        String accessToken = redisTemplate.opsForValue().get(BAIDU_TOKEN_KEY);
        if(StringUtil.isNullOrEmpty(accessToken)){
            accessToken = this.getAuth();
            redisTemplate.opsForValue().set(BAIDU_TOKEN_KEY, accessToken);
        }
        Map<String, Object> params = new HashMap<>();
        params.put("image", imgStr);
        splitBodyUrl += "?access_token=" + accessToken;
        HttpUtil.HttpResponse result = HttpUtil.postUrlEncoded(splitBodyUrl, params);
        System.out.println("请求结束，总时间(s)为：" +( (System.currentTimeMillis() - start)/ 1000F));
        return result.getBody();
    }

    /**
     * 灰度图转为纯黑白图
     */
    public BufferedImage convert(String labelmapBase64, int realWidth, int realHeight) {
        try {
            byte[] bytes = Base64.getDecoder().decode(labelmapBase64);
            InputStream is = new ByteArrayInputStream(bytes);
            BufferedImage image = ImageIO.read(is);
            BufferedImage newImage = resize(image, realWidth, realHeight);
            BufferedImage grayImage = new BufferedImage(realWidth, realHeight, BufferedImage.TYPE_BYTE_BINARY);
            for (int i = 0; i < realWidth; i++) {
                for (int j = 0; j < realHeight; j++) {
                    int rgb = newImage.getRGB(i, j);
                    grayImage.setRGB(i, j, rgb * 255);  //将像素存入缓冲区
                }
            }
            return grayImage;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return dimg;
    }

    public String convertFileToBase64(InputStream inputStream) {
        byte[] data = null;
        // 读取图片字节数组
        try {
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组进行Base64编码，得到Base64编码的字符串
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    /**
     * 百度获取Token
     */
    private String getAuth() throws Exception{
        // 获取token地址
        StringBuilder sb = new StringBuilder(authUrl);
        sb.append("?grant_type=client_credentials").append("&client_id=" + clientId).append("&client_secret=" + clientSecret);
        URL realUrl = new URL(sb.toString());
        // 打开和URL之间的连接
        HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        // 定义 BufferedReader输入流来读取URL的响应
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String result = "";
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject.getString("access_token");
    }

    public void transferAlpha(File file, File outputFile) throws Exception{
        InputStream is = new FileInputStream(file);
        Image image = ImageIO.read(is);
        ImageIcon imageIcon = new ImageIcon(image);
        BufferedImage bufferedImage = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(),
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();
        g2D.drawImage(imageIcon.getImage(), 0, 0, imageIcon.getImageObserver());
        int alpha = 0;
        for (int j1 = bufferedImage.getMinY(); j1 < bufferedImage.getHeight(); j1++) {
            for (int j2 = bufferedImage.getMinX(); j2 < bufferedImage.getWidth(); j2++) {
                int rgb = bufferedImage.getRGB(j2, j1);
                int R = (rgb & 0xff0000) >> 16;
                int G = (rgb & 0xff00) >> 8;
                int B = (rgb & 0xff);
                if (((255 - R) < 30) && ((255 - G) < 30) && ((255 - B) < 30)) {
                    rgb = ((alpha + 1) << 24) | (rgb & 0x00ffffff);
                }
                bufferedImage.setRGB(j2, j1, rgb);
            }
        }
        g2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());
        // 输出文件
        ImageIO.write(bufferedImage, "png", outputFile);
    }
}
