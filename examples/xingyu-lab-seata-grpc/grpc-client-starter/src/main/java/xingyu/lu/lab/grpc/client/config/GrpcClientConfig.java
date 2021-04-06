package xingyu.lu.lab.grpc.client.config;

import io.grpc.ClientInterceptor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.interceptor.GrpcGlobalClientInterceptor;
import xingyu.lu.lab.grpc.client.interceptor.GrpcInterceptor;
import org.springframework.context.annotation.Configuration;

/**
 * 配置Client端拦截器
 * 配置 NettyChannel KeepAlive
 *
 * <p>GrpcClientConfig</p>
 *
 * @author xingyu.lu
 * @date 2021/1/25 16:20
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
public class GrpcClientConfig {
    @GrpcGlobalClientInterceptor
    ClientInterceptor clientInterceptor() {
        log.info("GrpcClientInterceptor Registered");
        return new GrpcInterceptor();
    }
}
