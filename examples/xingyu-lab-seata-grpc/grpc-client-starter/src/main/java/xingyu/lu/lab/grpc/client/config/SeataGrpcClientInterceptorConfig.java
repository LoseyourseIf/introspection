package xingyu.lu.lab.grpc.client.config;

import io.seata.integration.grpc.interceptor.client.ClientTransactionInterceptor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.autoconfigure.GrpcClientAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xingyu.lu
 * @create 2021-03-02 14:01
 **/
@Configuration
@AutoConfigureBefore({GrpcClientAutoConfiguration.class})
@Slf4j
public class SeataGrpcClientInterceptorConfig {

    @Bean
    @ConditionalOnMissingBean(ClientTransactionInterceptor.class)
    public ClientTransactionInterceptor clientTransactionInterceptor() {
        log.info("Pre-Registering Seata-Grpc ClientTransactionInterceptor");
        return new ClientTransactionInterceptor();
    }
}
