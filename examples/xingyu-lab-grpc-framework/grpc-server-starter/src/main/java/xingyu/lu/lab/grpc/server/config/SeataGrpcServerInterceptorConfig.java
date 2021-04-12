package xingyu.lu.lab.grpc.server.config;

import io.seata.integration.grpc.interceptor.server.ServerTransactionInterceptor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xingyu.lu
 * @create 2021-03-02 14:01
 **/
@Configuration
@AutoConfigureBefore({GrpcServerAutoConfiguration.class})
@Slf4j
public class SeataGrpcServerInterceptorConfig {

    @Bean
    @ConditionalOnMissingBean(ServerTransactionInterceptor.class)
    public ServerTransactionInterceptor serverTransactionInterceptor() {
        log.info("Pre-Registering Seata-Grpc ServerTransactionInterceptor");
        return new ServerTransactionInterceptor();
    }
}
