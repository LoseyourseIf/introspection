package xingyu.lu.lab.springcloud.alibaba.svc.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Description: SVCProviderApplication  Allow Parallel RUN
 * @Version: 1.0
 * @Author: XingYu.Lu
 * @CreateTime: 2024-06-03  16:04
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class SVCProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(SVCProviderApplication.class, args);
    }
}
