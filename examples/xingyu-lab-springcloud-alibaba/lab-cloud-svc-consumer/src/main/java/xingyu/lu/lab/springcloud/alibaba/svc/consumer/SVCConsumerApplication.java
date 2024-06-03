package xingyu.lu.lab.springcloud.alibaba.svc.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Description: SVCConsumerApplication
 * @Version: 1.0
 * @Author: XingYu.Lu
 * @CreateTime: 2024-06-03  16:50
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class SVCConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SVCConsumerApplication.class, args);
    }
}
