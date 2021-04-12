package xingyu.lu.lab.grpc.server.config;

import io.grpc.ServerInterceptor;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import xingyu.lu.lab.grpc.server.interceptor.GrpcInterceptor;
import org.springframework.context.annotation.Configuration;

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
}
