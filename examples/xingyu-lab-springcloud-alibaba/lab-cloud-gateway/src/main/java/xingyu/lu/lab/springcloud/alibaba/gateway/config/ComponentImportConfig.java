package xingyu.lu.lab.springcloud.alibaba.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import xingyu.lu.lab.springcloud.alibaba.common.redis.RedissonConfig;

/**
 * @Description: 组件配置引入
 * @Version: 1.0
 * @Author: XingYu.Lu
 * @CreateTime: 2024-07-08  17:18
 */
@Configuration
@Import(RedissonConfig.class)
public class ComponentImportConfig{
}
