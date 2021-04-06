package xingyu.lu.individual.rpc.grpc.config;

import io.grpc.ClientInterceptor;
import io.grpc.netty.shaded.io.grpc.netty.NegotiationType;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import net.devh.boot.grpc.client.channelfactory.GrpcChannelConfigurer;
import net.devh.boot.grpc.client.interceptor.GrpcGlobalClientInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author xingyu.lu
 * @create 2020-12-04 15:48
 **/
@Configuration(proxyBeanMethods = false)
public class GrpcClientConfig {

    @GrpcGlobalClientInterceptor
    ClientInterceptor clientInterceptor() {
        return new GrpcInterceptor();
    }

    @Bean
    public GrpcChannelConfigurer keepAliveClientConfigurer() {
        return (channelBuilder, name) -> {
            if (channelBuilder instanceof NettyChannelBuilder) {
                ((NettyChannelBuilder) channelBuilder)
                        .negotiationType(NegotiationType.PLAINTEXT)
                        .keepAliveWithoutCalls(true)
                        .keepAliveTime(60, TimeUnit.SECONDS)
                        .keepAliveTimeout(10, TimeUnit.SECONDS);
            }
        };
    }
}
