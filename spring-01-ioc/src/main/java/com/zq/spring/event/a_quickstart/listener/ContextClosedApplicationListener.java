package com.zq.spring.event.a_quickstart.listener;

import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
//ContextRefreshedEvent 和 ContextClosedEvent ，它们分别代表容器刷新完毕和即将关闭
@Component
public class ContextClosedApplicationListener {
    
    @EventListener
    public void onContextClosedEvent(ContextClosedEvent event) {
        System.out.println("ContextClosedApplicationListener监听到ContextClosedEvent事件！");
    }

    @EventListener
    public void onCeshi(ContextClosedEvent event) {
        System.out.println("ContextClosedApplicationListener监听到ceshi事件！");
    }

    @EventListener
    public void onCeshi2(ContextClosedEvent event) {
        System.out.println(event.getClass());
        System.out.println(event.getApplicationContext());
        System.out.println("ContextClosedApplicationListener监听到测试关闭容器！！！");
    }
}
