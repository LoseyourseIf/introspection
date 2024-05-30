package xingyu.lu.lab.springcloud.alibaba.gateway.config;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Description: gateway 编程式路由
 * @Version: 1.0
 * @Author: XingYu.Lu
 * @CreateTime: 2024-05-29  09:35
 */
@Configuration
@Slf4j
public class GatewayRoutesConfig {

    @Resource
    RouteLocatorBuilder builder;

    /**
     * @description: Gateway route
     *
     * 路由创建
     * RouteDefineLocator:
     *  - org.springframework.cloud.gateway.config.PropertiesRouteDefinitionLocator;
     *  - org.springframework.cloud.gateway.route.RouteDefinitionRepository;
     *  - org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
     * ⬇
     * CompositeRouteDefinitionLocator
     * ⬇
     * RouteDefinitionRouteLocator      CustomRouteLocator
     * ⬇
     * CompositeRouteDefinitionLocator
     *
     **/
    @Bean
    public RouteLocator customRouteLocator() {

        /*
         *  Between
         *  After=2017-01-20T17:42:47.789-07:00[America/Denver]
         *  仅匹配发生在 2017-01-20 17:42 北美山区时区 (Denver) 之后的请求
         **/
        ZonedDateTime timeBegin = ZonedDateTime.now().plusDays(-1);
        ZonedDateTime timeEnd = ZonedDateTime.now().plusDays(1);

        log.info("[CustomRouteLocator] DateBefore = {} , DateEnd = {}",
                timeBegin.format(DateTimeFormatter.ISO_ZONED_DATE_TIME),
                timeEnd.format(DateTimeFormatter.ISO_ZONED_DATE_TIME));

        /*
         * @description: 编程式路由
         *
         * return builder.routes()
         * .route(r -> r.order(100)
         * .path("/custom/**")
         * .and()
         * .header("X-CUSTOM-TOKEN", "SVC-TOKEN")
         * .and()
         * .cookie("CUSTOM-COOKIE", "SVC-COOKIE")
         * .and()
         * .before(timeEnd).and()
         * .between(timeBegin, timeEnd).and()
         * .after(timeBegin)
         * .filters(f -> f
         * .addRequestHeader("X-CUSTOM-HEADER", "CUSTOM")
         * .addResponseHeader("X-CUSTOM-HEADER", "CUSTOM")
         * .stripPrefix(1))
         * .uri("http://www.github.com:80")
         * ).build();
         */

        return builder.routes()
                .route(r -> r.path("/github/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://www.github.com")
                ).build();
    }

}
