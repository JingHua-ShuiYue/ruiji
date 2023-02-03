package com.example.ruiji.config;

import com.example.ruiji.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

@Slf4j
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/front/**").
                addResourceLocations("classpath:/front/");
        registry.addResourceHandler("/backend/**").
                addResourceLocations("classpath:/backend/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/backend/page/login/login.html");
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 创建消息转换器
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        // 设置消息转换器
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        // 将消息转换器添加到mvc框架中
        converters.add(0, messageConverter);
    }
}
