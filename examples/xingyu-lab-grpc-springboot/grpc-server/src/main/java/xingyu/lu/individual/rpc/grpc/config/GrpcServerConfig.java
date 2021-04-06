package xingyu.lu.individual.rpc.grpc.config;

import io.grpc.ServerInterceptor;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import net.devh.boot.grpc.server.serverfactory.GrpcServerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xingyu.lu.individual.rpc.grpc.interceptor.GrpcInterceptor;

import java.util.concurrent.TimeUnit;

/**
 * @author xingyu.lu
 * @create 2020-12-04 15:48
 **/
@Configuration(proxyBeanMethods = false)
public class GrpcServerConfig {

    @GrpcGlobalServerInterceptor
    ServerInterceptor logServerInterceptor() {
        return new GrpcInterceptor();
    }

    @Bean
    public GrpcServerConfigurer keepAliveServerConfigurer() {
        return serverBuilder -> {
            if (serverBuilder instanceof NettyServerBuilder) {
                ((NettyServerBuilder) serverBuilder)
                        .permitKeepAliveWithoutCalls(true)
                        .keepAliveTime(60, TimeUnit.SECONDS)
                        .keepAliveTimeout(10, TimeUnit.SECONDS);
            }
        };
    }
}
