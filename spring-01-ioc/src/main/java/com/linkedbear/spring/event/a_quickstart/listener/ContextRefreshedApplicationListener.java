package com.linkedbear.spring.event.a_quickstart.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
//内置的监听器接口ApplicationListener
//ContextRefreshedEvent 和 ContextClosedEvent ，它们分别代表容器刷新完毕和即将关闭
@Component
public class ContextRefreshedApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("ContextRefreshedApplicationListener监听到ContextRefreshedEvent事件！");
    }
}
