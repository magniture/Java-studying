package com.imooc.bilibili.service.config;

import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 跨域解决配置
 *
 * 跨域概念：
 *      出于浏览器的同源策略限制，同源策略会阻止一个域的javascript脚本和另外一个域的内容进行交互。
 *      所谓同源就是指两个页面具有相同的协议（protocol），主机（host）和端口号（port）
 *
 * 非同源的限制：
 *  【1】无法读取非同源网页的 Cookie、LocalStorage 和 IndexedDB
 *  【2】无法接触非同源网页的 DOM
 *  【3】无法向非同源地址发送 AJAX 请求
 *
 *  spingboot解决跨域方案：CORS 是跨域资源分享（Cross-Origin Resource Sharing）的缩写。
 *  它是 W3C 标准，属于跨源 AJAX 请求的根本解决方法。
 *
 *
 *  Filter是用来过滤任务的，既可以被使用在请求资源，也可以是资源响应，或者二者都有
 *  Filter使用doFilter方法进行过滤
 */

@Configuration
public class CorsConfig implements Filter {

    private final String[] allowedDomain = {"http://localhost:8080", "http://39.107.54.180"};


    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        Set<String> allowedOrigins= new HashSet<>(Arrays.asList(allowedDomain));
        String origin=httpRequest.getHeader("Origin");
        if (origin == null) {
            chain.doFilter(request, response);
            return;
        }
        if (allowedOrigins.contains(origin)){
            httpResponse.setHeader("Access-Control-Allow-Origin", origin);
            httpResponse.setContentType("application/json;charset=UTF-8");
            httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
            httpResponse.setHeader("Access-Control-Max-Age", "3600");
            httpResponse.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With, userId, token, ut");//表明服务器支持的所有头信息字段
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true"); //如果要把Cookie发到服务器，需要指定Access-Control-Allow-Credentials字段为true;
            httpResponse.setHeader("XDomainRequestAllowed","1");
        }
        chain.doFilter(request, response);
    }
}
