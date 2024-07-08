package xingyu.lu.lab.springcloud.alibaba.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import xingyu.lu.lab.springcloud.alibaba.common.redis.RedissonConfig;

/**
 * @Description: GATEWAY
 * @Version: 1.0
 * @Author: XingYu.Lu
 * @CreateTime: 2024-05-21  16:52
 *
 * <p>
 * SpringCloudGateway 路由匹配
 * [Request]
 * ⬇
 * org.springframework.web.reactive.DispatcherHandler
 * ⬇
 * RoutePredicateHandlerMapping
 * ⬇
 * Route
 * ⬇
 * org.springframework.cloud.gateway.handler.FilteringWebHandle
 * ⬇
 * GatewayFilterChain
 * ⬇
 * Proxied Service
 * ⬇
 * [Response]
 */

@SpringBootApplication
@EnableDiscoveryClient
public class SpringCloudGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudGatewayApplication.class, args);
    }
}
