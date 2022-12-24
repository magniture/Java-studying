package com.zq.spring.withdao;

import com.zq.spring.withdao.config.EnableWebMvcConfiguration;
import com.zq.spring.withdao.config.RootConfiguration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebMvcApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {RootConfiguration.class};
    }
    
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {EnableWebMvcConfiguration.class};
    }
    
    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }
}
