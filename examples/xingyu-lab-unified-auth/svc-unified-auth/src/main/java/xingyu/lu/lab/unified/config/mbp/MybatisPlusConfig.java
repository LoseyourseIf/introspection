package xingyu.lu.lab.unified.config.mbp;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.github.pagehelper.PageInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xingyu.lu
 * @create 2021-04-22 15:49
 **/
@Slf4j
@Configuration
@MapperScan("xingyu.lu.lab.unified.mapper*")
public class MybatisPlusConfig {


    @Value("${project.dataCenter:1}")
    private Integer dataCenterId;
    @Value("${project.workerId:1}")
    private Integer workerId;

    /**
     * 自定义 IdGenerator
     */
    @Bean
    public IdentifierGenerator idGenerator() {
        return new MbpCustomIdGenerator(workerId, dataCenterId);
    }

    /**
     * pageHelper的分页插件
     */
    @Bean
    public PageInterceptor pageInterceptor() {
        return new PageInterceptor();
    }
}
