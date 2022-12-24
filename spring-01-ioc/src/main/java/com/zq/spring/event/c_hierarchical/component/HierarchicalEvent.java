package com.zq.spring.event.c_hierarchical.component;

import org.springframework.context.ApplicationEvent;

public class HierarchicalEvent extends ApplicationEvent {
    
    public HierarchicalEvent(Object source) {
        super(source);
    }
}
