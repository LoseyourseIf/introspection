package xingyu.lu.lab.unified;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author xingyu.lu
 * @create 2021-04-15 11:12
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class UnifiedAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(UnifiedAuthApplication.class, args);
    }
}
