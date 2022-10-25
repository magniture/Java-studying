package com.linkedbear.spring.event.a_quickstart.listener;

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
}
