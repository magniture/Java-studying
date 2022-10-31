package com.linkedbear.spring.resource;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ProtocolResolverApplication {
/**
 * @Author Zhang Q
 * @Description 在resources下建立个Dog.txt
 * @Date 11:14 2022/10/31
 * @Param
 * @param args
 * @return
 **/
    public static void main(String[] args) throws Exception {
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        DogProtocolResolver dogProtocolResolver = new DogProtocolResolver();
        resourceLoader.addProtocolResolver(dogProtocolResolver);

        Resource resource = resourceLoader.getResource("dog:Dog.txt");
        InputStream inputStream = resource.getInputStream();
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(reader);
        String readLine;
        while ((readLine = br.readLine()) != null) {
            System.out.println(readLine);
        }
        br.close();
    }
}
