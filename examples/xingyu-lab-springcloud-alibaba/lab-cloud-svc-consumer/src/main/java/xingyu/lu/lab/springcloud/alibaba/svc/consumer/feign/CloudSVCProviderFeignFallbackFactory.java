package xingyu.lu.lab.springcloud.alibaba.svc.consumer.feign;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Description: FallbackFactory
 * @Version: 1.0
 * @Author: XingYu.Lu
 * @CreateTime: 2024-06-04  14:35
 */
@Component
public class CloudSVCProviderFeignFallbackFactory implements FallbackFactory<CloudSVCProviderFeignFallback> {

    @Override
    public CloudSVCProviderFeignFallback create(Throwable cause) {
        return new CloudSVCProviderFeignFallback(cause);
    }
}
