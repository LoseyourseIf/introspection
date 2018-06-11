package xingyu.lu.review.springboot.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xingyu.lu
 * @create 2018-05-29 15:28
 **/
@RestController
public class HelloSpringBoot {
    @RequestMapping("/hello")
    public Object hello() {
        return "Hello Spring Boot";
    }
}