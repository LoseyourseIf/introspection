package xingyu.lu.review.springboot.web.interceptors;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

/**
 * @author xingyu.lu
 * @create 2018-04-09 13:18
 **/
@SpringBootConfiguration
public class AppInterceptorAdapter extends WebMvcConfigurerAdapter {

    @Resource
    private AppLoginInterceptor appLoginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册自定义拦截器，添加拦截路径和排除拦截路径
        InterceptorRegistration loginIR = registry.addInterceptor(appLoginInterceptor);
        loginIR
                .addPathPatterns("/**")
                .excludePathPatterns("/hello");
    }
}
