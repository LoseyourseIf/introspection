package xingyu.lu.review.sb.multiple.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author xingyu.lu
 * @create 2018-03-16 10:20
 **/
@SpringBootApplication
@MapperScan(basePackages = {"xingyu.lu.review.sb.multiple.*.dao"})
@ComponentScan(basePackages = {"xingyu.lu.review.sb.multiple"})
@ServletComponentScan(basePackages = {"xingyu.lu.review.sb.multiple.*.web"})
@EnableScheduling
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
