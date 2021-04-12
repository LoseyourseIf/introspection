package xingyu.lu.lab.grpc.server.config;

import io.grpc.ServerInterceptor;
import io.seata.integration.grpc.interceptor.server.ServerTransactionInterceptor;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.springframework.context.annotation.Configuration;

/**
 * @author xingyu.lu
 * @create 2021-03-02 14:05
 **/
@Configuration
public class SeataGrpcServerConfig {

    @GrpcGlobalServerInterceptor
    ServerInterceptor ServerTransactionInterceptor() {
        return new ServerTransactionInterceptor();
    }

}
