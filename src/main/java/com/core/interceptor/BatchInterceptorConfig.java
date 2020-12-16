package com.core.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Component
public class BatchInterceptorConfig extends WebMvcConfigurationSupport {

    /*@Autowired
    private SecurityInterceptor securityInterceptor;*/

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        //interceptorRegistry.addInterceptor(securityInterceptor).addPathPatterns("/v1/**", "/**");
    }

}
