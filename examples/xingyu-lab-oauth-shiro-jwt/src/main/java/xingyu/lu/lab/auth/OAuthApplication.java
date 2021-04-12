package xingyu.lu.lab.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author xingyu.lu
 * @create 2021-04-12 09:46
 **/
@EnableDiscoveryClient
@SpringBootApplication
public class OAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(OAuthApplication.class, args);
    }

}
