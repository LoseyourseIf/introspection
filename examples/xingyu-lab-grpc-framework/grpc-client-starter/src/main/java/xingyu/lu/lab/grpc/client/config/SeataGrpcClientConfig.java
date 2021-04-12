package xingyu.lu.lab.grpc.client.config;

import io.grpc.ClientInterceptor;
import io.seata.integration.grpc.interceptor.client.ClientTransactionInterceptor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.interceptor.GrpcGlobalClientInterceptor;
import org.springframework.context.annotation.Configuration;

/**
 * @author xingyu.lu
 * @create 2021-03-02 14:05
 **/
@Slf4j
@Configuration
public class SeataGrpcClientConfig {

    @GrpcGlobalClientInterceptor
    ClientInterceptor ClientTransactionInterceptor() {
        log.info("ClientTransactionInterceptor Registered");
        return new ClientTransactionInterceptor();
    }

}
